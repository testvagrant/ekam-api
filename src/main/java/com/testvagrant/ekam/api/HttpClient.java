package com.testvagrant.ekam.api;

import okhttp3.Interceptor;
import retrofit2.Call;
import retrofit2.Response;

public interface HttpClient<Req> {
  Req build(String baseUrl);

  default Req build() {
    throw new UnsupportedOperationException();
  }

  default <Type> Type execute(Call<Type> obj) {
    throw new UnsupportedOperationException();
  }

  default <Type> Type executeAsObj(Call<Type> obj) {
    throw new UnsupportedOperationException();
  }

  default <Type> Response<Type> executeAsResponse(Call<Type> obj) {
    throw new UnsupportedOperationException();
  }

  default <Service> Service getService(Class<Service> serviceClass) {
    return null;
  }
}
