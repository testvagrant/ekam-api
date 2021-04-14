package com.testvagrant.ekam.api.retrofit;

import com.testvagrant.ekam.api.restassured.RestAssuredClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import okhttp3.Interceptor;
import retrofit2.Retrofit;

public class RetrofitBaseClient {

  protected RetrofitClient httpClient;

  public RetrofitBaseClient(RetrofitClient httpClient, String baseUrl) {
    this.httpClient = httpClient;
    this.httpClient.build(baseUrl);
  }

  public RetrofitBaseClient(String baseUrl) {
    this.httpClient = new RetrofitClient();
    this.httpClient.build(baseUrl);
  }
}
