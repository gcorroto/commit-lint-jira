/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package commint_lint_jira.rest;

import java.io.IOException;

import org.gradle.internal.impldep.org.apache.http.client.methods.CloseableHttpResponse;
import org.gradle.internal.impldep.org.apache.http.client.methods.HttpGet;
import org.gradle.internal.impldep.org.apache.http.impl.client.BasicCookieStore;
import org.gradle.internal.impldep.org.apache.http.impl.client.CloseableHttpClient;
import org.gradle.internal.impldep.org.apache.http.impl.client.HttpClientBuilder;
import org.gradle.internal.impldep.org.apache.http.impl.cookie.BasicClientCookie;

import commint_lint_jira.dto.CommitLintJiraRestModel;

/**
 * A simple 'hello world' plugin.
 */
public class CommitLintJiraRest  {

	private CommitLintJiraRestModel model;
  final BasicCookieStore cookieStore = new BasicCookieStore();

	static final String AUTH_PATH = "/rest/auth/latest/session";
	static final String JIRA_PATH = "/rest/api/latest/issue/";

	public CommitLintJiraRest(CommitLintJiraRestModel model) {
		this.model=model;
	}

	public boolean authJira() throws IOException {
    
    final BasicClientCookie cookie = new BasicClientCookie(model.getUser(), model.getPassword());
    cookie.setDomain(model.getDomain());
    cookie.setAttribute("domain", "true");
    cookie.setPath("/");
    cookieStore.addCookie(cookie);
    final HttpGet request = new HttpGet(model.getUrl()+AUTH_PATH);

    try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request)) {
							System.out.println(response.getEntity().getContent().toString());
							return true;
    		}catch(Exception e) {
					System.out.println(e);
						return false;
				}

	}

	public String getInfoJira(String jira) throws IOException {
    String responseString = "";
    final HttpGet request = new HttpGet(model.getUrl() + JIRA_PATH + jira);

    try (CloseableHttpClient client = HttpClientBuilder.create().setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request)) {
					responseString = response.getEntity().getContent().toString();
					System.out.println(responseString);
    		}catch(Exception e) {
					System.out.println(e);
				}
		return responseString;
	}
}
