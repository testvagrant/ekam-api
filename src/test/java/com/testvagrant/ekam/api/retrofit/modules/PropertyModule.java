package com.testvagrant.ekam.api.retrofit.modules;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class PropertyModule extends AbstractModule {

  @Override
  public void configure() {
    Properties envProps = loadProperties();
    Names.bindProperties(binder(), envProps);
  }

  private Properties loadProperties() {
    String envFile = String.format("envs/%s.properties", System.getProperty("env", "staging"));
    Properties envProps = new Properties();
    try {
      InputStream envStream = this.getClass().getClassLoader().getResourceAsStream(envFile);
      envProps.load(Objects.requireNonNull(envStream));
    } catch (IOException | NullPointerException e) {
      System.out.println(e.getMessage());
    }
    return envProps;
  }
}
