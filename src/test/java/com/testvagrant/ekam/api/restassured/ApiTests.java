package com.testvagrant.ekam.api.restassured;

import com.google.inject.Inject;
import com.testvagrant.ekam.api.models.response.ToDos;
import com.testvagrant.ekam.api.restassured.clients.ToDosClient;
import org.junit.Assert;
import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import java.util.List;

@Guice()
public class ApiTests {

  @Inject ToDosClient toDoClient;

  @Test
  public void getAnimalFacts() {
    List<ToDos> toDos = toDoClient.getToDos();
    Assert.assertTrue(toDos.size() > 1);
  }
}
