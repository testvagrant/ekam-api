package com.testvagrant.ekam.api;

import com.google.inject.Inject;
import com.testvagrant.ekam.api.assertions.AnimalFactsAssertions;
import com.testvagrant.ekam.api.endpoints.AnimalFactsClient;
import com.testvagrant.ekam.api.endpoints.AnimalFactsClientGuice;
import com.testvagrant.ekam.api.models.CatFacts;
import com.testvagrant.ekam.api.modules.PropertyModule;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;
import retrofit2.Response;

import java.util.List;

@Guice(modules = {PropertyModule.class})
public class APITests {

  AnimalFactsClient animalFactsClient;
  AnimalFactsAssertions animalFactsAssertions;

  @Inject
  AnimalFactsClientGuice animalFactsClientGuice;

  @Inject
  AnimalFactsAssertions animalFactsAssertionsGuice;



  @Test(groups = "api")
  public void getAnimalFacts() {
    Response<List<CatFacts>> catFacts = new AnimalFactsClient("https://cat-fact.herokuapp.com").getCatFacts();
    new AnimalFactsAssertions().assertThatCatFactsAreAvailable(catFacts);
  }

  @Test(groups = "api")
  public void getAnimalFactsGuice() {
    Response<List<CatFacts>> catFacts = animalFactsClientGuice.getCatFacts();
    animalFactsAssertionsGuice.assertThatCatFactsAreAvailable(catFacts);
  }
}