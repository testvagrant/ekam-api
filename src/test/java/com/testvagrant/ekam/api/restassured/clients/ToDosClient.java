package com.testvagrant.ekam.api.restassured.clients;

import com.google.gson.reflect.TypeToken;
import com.testvagrant.ekam.api.models.response.ToDos;
import com.testvagrant.ekam.api.restassured.HttpMethod;
import com.testvagrant.ekam.api.restassured.RequestWrapper;
import com.testvagrant.ekam.api.restassured.RestAssuredClient;

import java.util.List;

public class ToDosClient extends RestAssuredClient {

  public List<ToDos> getToDos() {
    RequestWrapper request =
        RequestWrapper.build()
            .baseUri("https://jsonplaceholder.typicode.com")
            .endPoint("/todos")
            .method(HttpMethod.GET);

    return execute(request, new TypeToken<List<ToDos>>() {}.getType());
  }
}
