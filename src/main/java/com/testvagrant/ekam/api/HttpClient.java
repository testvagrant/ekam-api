package com.testvagrant.ekam.api;

import retrofit2.Call;
import retrofit2.Response;

public interface HttpClient<Req> {
  Req build(String baseUrl);

  <T> T execute(Call<T> call);

  <T> T executeAsObj(Call<T> call);

  <T> Response<T> executeAsResponse(Call<T> call);

  <Service> Service getService(Class<Service> serviceClazz);
}
