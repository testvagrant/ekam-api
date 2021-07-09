package com.testvagrant.ekam.api.retrofit;

import com.google.gson.*;
import com.testvagrant.ekam.api.HttpClient;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RetrofitClient implements HttpClient<Retrofit> {

  private Retrofit retrofit;
  private Duration readTimeout;
  private Duration connectTimeout;
  private List<Interceptor> interceptors = new ArrayList<>();
  private List<Converter.Factory> converterFactory = new ArrayList<>();

  public RetrofitClient(Interceptor... interceptors) {
    this(Duration.ofSeconds(120), Duration.ofSeconds(120), Arrays.asList(interceptors), new ArrayList<>());
  }

  public RetrofitClient(Duration readTimeout, Duration connectTimeout, Interceptor... interceptors) {
    this(readTimeout, connectTimeout, Arrays.asList(interceptors), new ArrayList<>());
  }

  public RetrofitClient(Converter.Factory... converterFactories) {
    this(Duration.ofSeconds(120), Duration.ofSeconds(120), new ArrayList<>(), Arrays.asList(converterFactories));
  }

  public RetrofitClient(List<Interceptor> interceptors, List<Converter.Factory> converterFactory) {
    this(Duration.ofSeconds(120), Duration.ofSeconds(120), interceptors, converterFactory);
  }

  public RetrofitClient(Duration readTimeout, Duration connectTimeout, List<Interceptor> interceptors, List<Converter.Factory> converterFactory) {
    this.interceptors = interceptors;
    this.converterFactory = converterFactory;
    this.readTimeout = readTimeout;
    this.connectTimeout = connectTimeout;
  }

  public RetrofitClient(String baseUrl) {
    this.readTimeout = Duration.ofSeconds(120);
    this.connectTimeout = Duration.ofSeconds(120);
    this.retrofit = build(baseUrl);
  }

  public RetrofitClient(Retrofit retrofit) {
    this.readTimeout = Duration.ofSeconds(120);
    this.connectTimeout = Duration.ofSeconds(120);
    this.retrofit = retrofit;
  }

  public Retrofit build(String baseUrl) {
    retrofit = createRetrofitClient(baseUrl);
    return retrofit;
  }

  @SuppressWarnings("unchecked")
  public <T> T execute(Call<T> call) {
    Response<T> execute = executeAsResponse(call);
    if (CallTypeFinder.getInstance().getType(call).equals(Response.class)) {
      return (T) execute;
    }
    try {
      return execute.isSuccessful()
          ? execute.body()
          : new Gson()
              .fromJson(
                  execute.errorBody() != null ? execute.errorBody().string() : "{'error': ''}",
                  mapErrorType(call));
    } catch (IOException e) {
      throw new RuntimeException(String.format("Cannot parse error %s", e.getMessage()));
    }
  }

  @Override
  public <T> T executeAsObj(Call<T> call) {
    return execute(call);
  }

  public <T> Response<T> executeAsResponse(Call<T> call) {
    try {
      return call.execute();
    } catch (IOException ex) {
      throw new RuntimeException(
          String.format("Cannot execute call %s due to %s ", call.request(), ex.getMessage()));
    }
  }

  public <Service> Service getService(Class<Service> serviceClass) {
    return retrofit.create(serviceClass);
  }

  private Retrofit createRetrofitClient(String baseUrl) {
    OkHttpClient client = createOkHttpClient();
    Retrofit.Builder builder = new Retrofit.Builder().baseUrl(baseUrl).client(client);
    converterFactory.add(GsonConverterFactory.create(getGson()));
    converterFactory.forEach(builder::addConverterFactory);
    return builder.build();
  }

  private OkHttpClient createOkHttpClient() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    interceptors.forEach(builder::addInterceptor);
    builder.readTimeout(readTimeout);
    builder.connectTimeout(connectTimeout);
    return builder.build();
  }

  private HttpLoggingInterceptor getHttpLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return interceptor;
  }

  private <T> Class<T> mapErrorType(Call<T> call) {
    return CallTypeFinder.getInstance().getType(call);
  }

  private Gson getGson() {
    return new GsonBuilder()
        .setLenient()
        .registerTypeHierarchyAdapter(byte[].class, new ByteArrayToBase64TypeAdapter())
        .create();
  }

  private static class ByteArrayToBase64TypeAdapter implements JsonDeserializer<byte[]> {
    public byte[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
      String asString = json.getAsString();
      return asString.getBytes(StandardCharsets.UTF_8);
    }
  }
}
