package com.testvagrant.ekam.api.retrofit;

import okhttp3.Interceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.time.Duration;
import java.util.List;

public class RetrofitBaseClient {

  protected RetrofitClient httpClient;

  public RetrofitBaseClient(RetrofitClient httpClient, String baseUrl) {
    this.httpClient = httpClient;
    this.httpClient.build(baseUrl);
  }

  public RetrofitBaseClient(RetrofitClient httpClient) {
    this.httpClient = httpClient;
  }

  public RetrofitBaseClient(String baseUrl) {
    this.httpClient = new RetrofitClient(baseUrl);
  }

  public RetrofitBaseClient(String baseUrl, Interceptor... interceptor) {
    this.httpClient = new RetrofitClient(interceptor);
    this.httpClient.build(baseUrl);
  }

  public RetrofitBaseClient(String baseUrl, Duration readTimeout, Duration connectTimeout, Interceptor... interceptor) {
    this.httpClient = new RetrofitClient(readTimeout, connectTimeout, interceptor);
    this.httpClient.build(baseUrl);
  }

  public RetrofitBaseClient(String baseUrl, Converter.Factory... converterFactories) {
    this.httpClient = new RetrofitClient(converterFactories);
    this.httpClient.build(baseUrl);
  }

  public RetrofitBaseClient(
      String baseurl, List<Interceptor> interceptors, List<Converter.Factory> converterFactories) {
    this.httpClient = new RetrofitClient(interceptors, converterFactories);
    this.httpClient.build(baseurl);
  }

  public RetrofitBaseClient(Retrofit retrofit) {
    this.httpClient = new RetrofitClient(retrofit);
  }
}
