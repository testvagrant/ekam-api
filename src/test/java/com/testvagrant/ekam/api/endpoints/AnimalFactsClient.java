package com.testvagrant.ekam.api.endpoints;

import com.testvagrant.ekam.api.models.CatFacts;
import com.testvagrant.ekam.api.retrofit.RetrofitBaseClient;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import retrofit2.Call;
import retrofit2.Response;

import java.util.List;

public class AnimalFactsClient extends RetrofitBaseClient {

  private final AnimalFactsService animalFactsService;

  public AnimalFactsClient(String baseUrl) {
    super(baseUrl);
    animalFactsService = httpClient.getService(AnimalFactsService.class);
  }

  public Response<List<CatFacts>> getCatFacts() {
    Call<List<CatFacts>> responseCall = animalFactsService.catFacts();
    return httpClient.executeAsResponse(responseCall);
  }
}
