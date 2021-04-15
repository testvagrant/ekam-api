package com.testvagrant.ekam.api.retrofit;

import com.google.gson.Gson;
import com.testvagrant.ekam.api.HttpClient;
import io.qameta.allure.okhttp3.AllureOkHttp3;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RetrofitClient implements HttpClient<Retrofit> {

  Retrofit retrofit;

  CallTypeFinder callTypeFinder;

  Gson gson;

  private List<Interceptor> interceptors;
  private List<Converter.Factory> converterFactory;
  private OkHttpClient okHttpClient;

  public RetrofitClient() {
    this.callTypeFinder = new CallTypeFinder();
    this.gson = new Gson();
    this.interceptors = Collections.singletonList(getHttpLoggingInterceptor());
    this.converterFactory = Collections.singletonList(GsonConverterFactory.create(new Gson()));
    this.okHttpClient = getOkHttpClient();
  }

  public RetrofitClient(Interceptor... interceptors) {
    this();
    this.interceptors = Arrays.asList(interceptors);
  }

  public RetrofitClient(Converter.Factory... converterFactories) {
    this();
    this.converterFactory = Arrays.asList(converterFactories);
  }

  public RetrofitClient(OkHttpClient okHttpClient) {
    this();
    this.okHttpClient = okHttpClient;
  }

  public RetrofitClient(List<Interceptor> interceptors, List<Converter.Factory> converterFactory) {
    this();
    this.interceptors = interceptors;
    this.converterFactory = converterFactory;
  }


  public RetrofitClient(String baseUrl) {
    this();
    this.retrofit = build(baseUrl);
  }

  public RetrofitClient(Retrofit retrofit) {
    this();
    this.retrofit = retrofit;
  }

  public Retrofit build(String baseUrl) {
    Retrofit.Builder builder = retrofitClientBuilder(baseUrl);
    retrofit = builder.build();
    return retrofit;
  }

  private Retrofit.Builder retrofitClientBuilder(String baseUrl) {
    Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient);
    addConverterFactories(builder);
    return builder;
  }

  private Retrofit.Builder addConverterFactories(Retrofit.Builder retrofitBuilder) {
    converterFactory.forEach(retrofitBuilder::addConverterFactory);
    return retrofitBuilder;
  }

  private OkHttpClient getOkHttpClient() {
    OkHttpClient okHttpClient =
        getOkHttpBuilder()
            .addInterceptor(new AllureOkHttp3())
            .addInterceptor(getHttpLoggingInterceptor())
            .build();
    return okHttpClient;
  }

  private OkHttpClient.Builder getOkHttpBuilder() {
    OkHttpClient.Builder builder = new OkHttpClient.Builder();
    interceptors.forEach(builder::addNetworkInterceptor);
    return builder;
  }

  private HttpLoggingInterceptor getHttpLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return interceptor;
  }

  public <Type> Type execute(Call<Type> call) {
    Response<Type> execute = executeAsResponse(call);
    if (callTypeFinder.getType(call).equals(Response.class)) {
      return (Type) execute;
    }
    try {
      return execute.isSuccessful()
          ? execute.body()
          : gson.fromJson(execute.errorBody().string(), mapErrorType(call));
    } catch (IOException e) {
      throw new RuntimeException(String.format("Cannot parse error %s", e.getMessage()));
    }
  }

  public <Type> Type executeAsObj(Call<Type> call) {
    return execute(call);
  }

  public <Type> Response<Type> executeAsResponse(Call<Type> call) {
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

  private <Type> Class<Type> mapErrorType(Call<Type> call) {
    return callTypeFinder.getType(call);
  }
}
