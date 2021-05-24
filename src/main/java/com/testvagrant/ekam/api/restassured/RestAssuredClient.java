package com.testvagrant.ekam.api.restassured;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.lang.reflect.Type;

@SuppressWarnings("unchecked")
public class RestAssuredClient {

  public <T> T execute(RequestWrapper requestSpecification, Type tType) {
    RequestSpecification request = requestSpecification.getRequestSpecification();

    HttpMethod method = requestSpecification.getMethod();
    String endPoint = requestSpecification.getEndPoint();

    Response response;
    switch (method) {
      case GET:
        response = request.get(endPoint);
        break;

      case PUT:
        response = request.put(endPoint);
        break;

      case POST:
        response = request.post(endPoint);
        break;

      case PATCH:
        response = request.patch(endPoint);
        break;

      case DELETE:
        response = request.delete(endPoint);
        break;

      default:
        throw new RuntimeException(method + " not a valid HTTP Method");
    }

    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    if (response.contentType().contains(ContentType.HTML.toString())) {
      throw new RuntimeException(response.getBody().asPrettyString());
    }

    return tType.getClass().isInstance(Response.class)
        ? (T) response
        : gson.fromJson(response.body().asPrettyString(), tType);
  }
}
