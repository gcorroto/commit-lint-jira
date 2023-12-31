/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package commint_lint_jira.rest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.VisibilityChecker;
import commint_lint_jira.dto.CommitLintJiraRestModel;
import commint_lint_jira.dto.ResponseJiraDto;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import javax.net.ssl.SSLContext;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.ssl.SSLContexts;

/**
 * A simple 'hello world' plugin.
 */
public class CommitLintJiraRest {

  private CommitLintJiraRestModel model;
  private CookieStore cookieStore = new BasicCookieStore();

  static final String AUTH_PATH = "/rest/auth/latest/session";
  static final String JIRA_PATH = "/rest/api/latest/issue/";
  private HttpClient httpClient = null;
  private HttpClientContext context = null;

  public CommitLintJiraRest(CommitLintJiraRestModel model) {
    this.model = model;
  }

  public boolean authJira() throws IOException {
    configClient();
    String url = model.getUrl() + AUTH_PATH;
    System.out.println("url auth " + url);
    final HttpGet request = new HttpGet(url);
    String basicPass = Base64
      .getEncoder()
      .encodeToString(
        new String(model.getUser() + ":" + model.getPassword()).getBytes()
      );
    request.addHeader("Authorization", "Basic " + basicPass);
    try {
      context = HttpClientContext.create();
      context.setAttribute(HttpClientContext.COOKIE_STORE, cookieStore);

      CloseableHttpResponse responseHttp = (CloseableHttpResponse) httpClient.execute(
        request,
        context
      );

      cookieStore = context.getCookieStore();
      System.out.println(responseHttp.getEntity().getContent().toString());
      readSetCookie();
      responseHttp.getEntity().getContent().close();

      return true;
    } catch (Exception e) {
      System.out.println(e);
      return false;
    }
  }

  public ResponseJiraDto getInfoJira(String jira) throws IOException {
    ResponseJiraDto responseJiraDto = null;
    String url = model.getUrl() + JIRA_PATH + jira;
    System.out.println("url issue " + url);

    final HttpGet request = new HttpGet(url);
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
    objectMapper.setVisibility(
      VisibilityChecker.Std
        .defaultInstance()
        .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
    );
    try {
      CloseableHttpResponse response = (CloseableHttpResponse) httpClient.execute(
        request,
        context
      );
      InputStream stream = response.getEntity().getContent();
      responseJiraDto = objectMapper.readValue(stream, ResponseJiraDto.class);
      response.getEntity().getContent().close();
    } catch (Exception e) {
      System.out.println(e);
    }
    return responseJiraDto;
  }

  private void configClient() {
    try {
      SSLContext sslContext = SSLContexts
        .custom()
        .loadTrustMaterial((chain, authType) -> true)
        .build();

      SSLConnectionSocketFactory sslConnectionSocketFactory = new SSLConnectionSocketFactory(
        sslContext,
        new String[] { "SSLv2Hello", "SSLv3", "TLSv1", "TLSv1.1", "TLSv1.2" },
        null,
        NoopHostnameVerifier.INSTANCE
      );

      HttpClientBuilder builder = HttpClientBuilder
        .create()
        .setSSLSocketFactory(sslConnectionSocketFactory)
        .setDefaultCookieStore(cookieStore);
      httpClient = builder.build();
    } catch (
      KeyManagementException | NoSuchAlgorithmException | KeyStoreException e
    ) {
      System.out.println(e);
    }
  }

  private void readSetCookie() {
    final List<Cookie> cookies = context.getCookieStore().getCookies();
    System.out.println("cookies: " + cookies);
  }
}
