package commint_lint_jira.rest;

import org.gradle.internal.impldep.com.amazonaws.http.HttpResponse;
import org.gradle.internal.impldep.com.amazonaws.http.HttpResponseHandler;
import org.gradle.internal.impldep.org.apache.http.client.methods.CloseableHttpResponse;

public class CustomHttpClientResponseHandler implements HttpResponseHandler<CloseableHttpResponse> {

		@Override
		public CloseableHttpResponse handle(HttpResponse response) throws Exception {
			  return (CloseableHttpResponse) response;
		}

		@Override
		public boolean needsConnectionLeftOpen() {
			return true;
		}
}