package com.testvagrant.ekam.api.retrofit.interceptors;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BasicAuthInterceptor implements Interceptor {
  private final String credentials;

  public BasicAuthInterceptor(String user, String password) {
    this.credentials = Credentials.basic(user, password);
  }

  @Override
  public Response intercept(Interceptor.Chain chain) {
    try {
      Request request = chain.request();
      Request authenticatedRequest =
          request.newBuilder().header("Authorization", credentials).build();
      return chain.proceed(authenticatedRequest);
    } catch (Exception ex) {
      throw new RuntimeException(ex.getMessage());
    }
  }
}
