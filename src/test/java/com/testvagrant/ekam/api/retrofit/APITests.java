package com.testvagrant.ekam.api.retrofit;

import com.google.inject.Inject;
import com.testvagrant.ekam.api.retrofit.assertions.AnimalFactsAssertions;
import com.testvagrant.ekam.api.retrofit.endpoints.AnimalFactsClient;
import com.testvagrant.ekam.api.retrofit.endpoints.AnimalFactsClientGuice;
import com.testvagrant.ekam.api.retrofit.endpoints.AnimalFactsClientWithoutBaseUrl;
import com.testvagrant.ekam.api.models.CatFacts;
import com.testvagrant.ekam.api.retrofit.modules.PropertyModule;
import com.testvagrant.ekam.api.retrofit.RetrofitClient;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.util.List;

@Guice(modules = {PropertyModule.class})
public class APITests {

  private AnimalFactsClient animalFactsClient;
  private AnimalFactsClientWithoutBaseUrl animalFactsClientWithoutBaseUrl;
  private AnimalFactsAssertions animalFactsAssertions;

  @Inject AnimalFactsClientGuice animalFactsClientGuice;

  @Inject AnimalFactsAssertions animalFactsAssertionsGuice;

  @Test(groups = "api")
  public void getAnimalFacts() {
    Response<List<CatFacts>> catFacts =
        new AnimalFactsClient("https://cat-fact.herokuapp.com").getCatFacts();
    new AnimalFactsAssertions().assertThatCatFactsAreAvailable(catFacts);
  }

  @Test(groups = "api")
  public void getAnimalFactsGuice() {
    Response<List<CatFacts>> catFacts = animalFactsClientGuice.getCatFacts();
    animalFactsAssertionsGuice.assertThatCatFactsAreAvailable(catFacts);
  }

  @Test(groups = "api")
  public void getAnimalFactsWithoutBaseUrl() {
    RetrofitClient retrofitClient = new RetrofitClient("https://cat-fact.herokuapp.com");
    Response<List<CatFacts>> catFacts =
        new AnimalFactsClientWithoutBaseUrl(retrofitClient).getCatFacts();
    new AnimalFactsAssertions().assertThatCatFactsAreAvailable(catFacts);
  }
}
