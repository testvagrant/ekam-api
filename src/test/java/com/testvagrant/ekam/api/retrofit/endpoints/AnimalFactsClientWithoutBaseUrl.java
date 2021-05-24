package com.testvagrant.ekam.api.retrofit.endpoints;

import com.testvagrant.ekam.api.models.CatFacts;
import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

public class AnimalFactsClientWithoutBaseUrl extends RetrofitBaseClient {

  private final AnimalFactsService animalFactsService;

  public AnimalFactsClientWithoutBaseUrl(RetrofitClient retrofitClient) {
    super(retrofitClient);
    animalFactsService = httpClient.getService(AnimalFactsService.class);
  }

  public Response<List<CatFacts>> getCatFacts() {
    Call<List<CatFacts>> responseCall = animalFactsService.catFacts();
    return httpClient.executeAsResponse(responseCall);
  }
}
