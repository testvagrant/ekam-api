package com.testvagrant.ekam.api.endpoints;

import com.google.inject.Inject;
import com.testvagrant.ekam.api.models.CatFacts;
import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

import javax.inject.Named;
import java.util.List;

public class AnimalFactsClientGuice extends RetrofitBaseClient {

  private final AnimalFactsService animalFactsService;

  @Inject
  public AnimalFactsClientGuice(RetrofitClient retrofitClient, @Named("catFactsHost") String baseUrl) {
    super(retrofitClient, baseUrl);
    animalFactsService = httpClient.getService(AnimalFactsService.class);
  }

  public Response<List<CatFacts>> getCatFacts() {
    Call<List<CatFacts>> responseCall = animalFactsService.catFacts();
    return httpClient.executeAsResponse(responseCall);
  }
}
