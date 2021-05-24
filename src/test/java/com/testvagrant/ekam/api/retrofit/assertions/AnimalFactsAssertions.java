package com.testvagrant.ekam.api.retrofit.assertions;

import com.testvagrant.ekam.api.models.CatFacts;
import org.testng.Assert;
import retrofit2.Response;

import java.util.List;

public class AnimalFactsAssertions {

  public void assertThatCatFactsAreAvailable(Response<List<CatFacts>> catFactsResponse) {
    List<CatFacts> catFacts = catFactsResponse.body();
    assert catFacts != null;
    Assert.assertTrue(catFacts.size() > 0);
  }
}
