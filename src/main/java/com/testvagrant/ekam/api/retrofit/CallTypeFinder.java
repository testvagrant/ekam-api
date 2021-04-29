package com.testvagrant.ekam.api.retrofit;

import retrofit2.Call;
import retrofit2.Invocation;

public class CallTypeFinder {

  private static CallTypeFinder instance;

  public static CallTypeFinder getInstance() {
    if (instance == null) {
      synchronized (CallTypeFinder.class) {
        if (instance == null) {
          instance = new CallTypeFinder();
        }
      }
    }
    return instance;
  }

  @SuppressWarnings("unchecked")
  public <T> Class<T> getType(Call<T> call) {
    Invocation tag = call.request().tag(Invocation.class);
    if (tag == null) {
      throw new RuntimeException("Cannot get Request Call Type");
    }
    String typeName = tag.method().getGenericReturnType().getTypeName();
    try {
      String type = extractClassName(typeName);
      return (Class<T>) Class.forName(type);
    } catch (ClassNotFoundException exception) {
      throw new RuntimeException(
          String.format(
              "Cannot create type for call %s. Error: %s" + typeName, exception.getMessage()));
    }
  }

  private String extractClassName(String className) {
    String[] split = className.split("<");
    return split[1].replaceAll(">", "").trim();
  }
}
