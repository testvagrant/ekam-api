package com.testvagrant.ekam.api.restassured;

import com.google.gson.Gson;
import io.restassured.authentication.OAuthSignature;
import io.restassured.http.ContentType;
import io.restassured.http.Cookie;
import io.restassured.http.Cookies;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.specification.AuthenticationSpecification;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RedirectSpecification;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.io.InputStream;
import java.net.URI;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RequestWrapper {

  private RequestSpecification requestSpecification;
  private String endPoint;
  private HttpMethod httpMethod;

  private RequestWrapper() {
    requestSpecification = given();
  }

  public static RequestWrapper build() {
    return new RequestWrapper();
  }

  public RequestSpecification getRequestSpecification() {
    return requestSpecification;
  }

  public RequestWrapper method(HttpMethod method) {
    httpMethod = method;
    return this;
  }

  public HttpMethod getMethod() {
    return httpMethod;
  }

  public RequestWrapper baseUri(String baseUrl) {
    requestSpecification = requestSpecification.baseUri(baseUrl);
    return this;
  }

  public RequestWrapper endPoint(String endpoint) {
    this.endPoint = endpoint;
    return this;
  }

  public String getEndPoint() {
    return endPoint;
  }

  public RequestWrapper cookie(Cookie cookie) {
    requestSpecification = requestSpecification.cookie(cookie);
    return this;
  }

  public RequestWrapper cookie(String cookie) {
    requestSpecification = requestSpecification.cookie(cookie);
    return this;
  }

  public RequestWrapper cookies(Cookies cookies) {
    requestSpecification = requestSpecification.cookies(cookies);
    return this;
  }

  public RequestWrapper cookies(Map<String, ?> cookies) {
    requestSpecification = requestSpecification.cookies(cookies);
    return this;
  }

  public RequestWrapper body(String body) {
    requestSpecification = requestSpecification.body(body);
    return this;
  }

  public RequestWrapper body(File body) {
    requestSpecification = requestSpecification.body(body);
    return this;
  }

  public RequestWrapper body(byte[] body) {
    requestSpecification = requestSpecification.body(body);
    return this;
  }

  public RequestWrapper body(InputStream body) {
    requestSpecification = requestSpecification.body(body);
    return this;
  }

  public RequestWrapper body(Object body) {
    requestSpecification = requestSpecification.body(body);
    return this;
  }

  public RequestWrapper body(Object body, ObjectMapper mapper) {
    requestSpecification = requestSpecification.body(body, mapper);
    return this;
  }

  public RequestWrapper body(Object body, ObjectMapperType mapperType) {
    requestSpecification = requestSpecification.body(body, mapperType);
    return this;
  }

  public <T> RequestWrapper body(Class<T> object) {
    String body = new Gson().toJson(object);
    requestSpecification = requestSpecification.body(body);
    return this;
  }

  public RequestWrapper params(Map<String, ?> params) {
    requestSpecification = requestSpecification.params(params);
    return this;
  }

  public RequestWrapper queryParams(Map<String, ?> params) {
    requestSpecification = requestSpecification.queryParams(params);
    return this;
  }

  public RequestWrapper pathParams(Map<String, ?> params) {
    requestSpecification = requestSpecification.pathParams(params);
    return this;
  }

  public RequestWrapper formParams(Map<String, ?> formParams) {
    requestSpecification = requestSpecification.formParams(formParams);
    return this;
  }

  public RequestWrapper contentType(ContentType contentType) {
    requestSpecification = requestSpecification.contentType(contentType);
    return this;
  }

  public RequestWrapper contentType(String contentType) {
    requestSpecification = requestSpecification.contentType(contentType);
    return this;
  }

  public RequestWrapper accept(ContentType contentType) {
    requestSpecification = requestSpecification.accept(contentType);
    return this;
  }

  public RequestWrapper accept(String mediaTypes) {
    requestSpecification = requestSpecification.accept(mediaTypes);
    return this;
  }

  public RequestWrapper multiPart(File file) {
    requestSpecification = requestSpecification.multiPart(file);
    return this;
  }

  public RequestWrapper multiPart(String controlName, File file) {
    requestSpecification = requestSpecification.multiPart(controlName, file);
    return this;
  }

  public RequestWrapper multiPart(String controlName, File file, String mimeType) {
    requestSpecification = requestSpecification.multiPart(controlName, file, mimeType);
    return this;
  }

  public RequestWrapper multiPart(
      String controlName, String fileName, Object object, String mimeType) {
    requestSpecification = requestSpecification.multiPart(controlName, fileName, object, mimeType);
    return this;
  }

  public RequestWrapper multiPart(String controlName, Object object, String mimeType) {
    requestSpecification = requestSpecification.multiPart(controlName, object, mimeType);
    return this;
  }

  public RequestWrapper multiPart(String controlName, Object object) {
    requestSpecification = requestSpecification.multiPart(controlName, object);
    return this;
  }

  public RequestWrapper basicAuthentication(String userName, String password) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.basic(userName, password);
    return this;
  }

  public RequestWrapper certificateAuthentication(String certUrl, String password) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.certificate(certUrl, password);
    return this;
  }

  public RequestWrapper authDigest(String username, String password) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.digest(username, password);
    return this;
  }

  public RequestWrapper formAuthentication(String username, String password) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.form(username, password);
    return this;
  }

  public RequestWrapper ntlmAuthentication(
      String username, String password, String workstation, String domain) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.ntlm(username, password, workstation, domain);
    return this;
  }

  public RequestWrapper oathAuthentication(
      String consumerKey, String consumerSecret, String accessToken, String secretToken) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification =
        authSpecification.oauth(consumerKey, consumerSecret, accessToken, secretToken);
    return this;
  }

  public RequestWrapper oathAuthentication(
      String consumerKey,
      String consumerSecret,
      String accessToken,
      String secretToken,
      OAuthSignature signature) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification =
        authSpecification.oauth(consumerKey, consumerSecret, accessToken, secretToken, signature);
    return this;
  }

  public RequestWrapper oath2Authentication(String accessToken) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.oauth2(accessToken);
    return this;
  }

  public RequestWrapper oath2Authentication(String accessToken, OAuthSignature signature) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.oauth2(accessToken, signature);
    return this;
  }

  public RequestWrapper preemptiveBasicAuthentication(String username, String password) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.preemptive().basic(username, password);
    return this;
  }

  public RequestWrapper preemptiveOAuth2Authentication(String accessToken) {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.preemptive().oauth2(accessToken);
    return this;
  }

  public RequestWrapper noAuthentication() {
    AuthenticationSpecification authSpecification = requestSpecification.auth();
    requestSpecification = authSpecification.none();
    return this;
  }

  public RequestWrapper port(int port) {
    requestSpecification = requestSpecification.port(port);
    return this;
  }

  public RequestWrapper sessionId(String sessionId) {
    requestSpecification = requestSpecification.sessionId(sessionId);
    return this;
  }

  public RequestWrapper sessionId(String sessionId, String value) {
    requestSpecification = requestSpecification.sessionId(sessionId, value);
    return this;
  }

  public RequestWrapper sessionId(boolean urlEncodingEnabled) {
    requestSpecification = requestSpecification.urlEncodingEnabled(urlEncodingEnabled);
    return this;
  }

  public RequestWrapper and() {
    requestSpecification = requestSpecification.and();
    return this;
  }

  public RequestWrapper proxy(String host, int port) {
    requestSpecification = requestSpecification.proxy(host, port);
    return this;
  }

  public RequestWrapper proxy(URI uri) {
    requestSpecification = requestSpecification.proxy(uri);
    return this;
  }

  public RequestWrapper proxy(ProxySpecification proxySpecification) {
    requestSpecification = requestSpecification.proxy(proxySpecification);
    return this;
  }

  public RequestWrapper redirects(
      boolean allowCircular,
      boolean followRedirects,
      int maxNumberOfRedirects,
      boolean rejectRelativeRedirects) {
    RedirectSpecification redirect = requestSpecification.redirects();
    requestSpecification = redirect.allowCircular(allowCircular);
    requestSpecification = redirect.follow(followRedirects);
    requestSpecification = redirect.max(maxNumberOfRedirects);
    requestSpecification = redirect.rejectRelative(rejectRelativeRedirects);
    return this;
  }
}
