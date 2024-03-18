package main;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import unibo.basicomm23.utils.CommUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.net.URI;

public class ServiceCallerHTTPNaive {
	/* 1 */private final CloseableHttpClient httpclient = HttpClients.createDefault();

	public void doJob() {
		try {
			/* 2 */ CloseableHttpResponse response = sendMessageHTTP("/");
			if (response != null) {
				/* 3 */ String answer = getAnswer(response);
				CommUtils.outblue("ServiceCallerHTTPNaive|answer=" + answer);
			}
			System.exit(0);
		} catch (Exception e) {
			CommUtils.outred("ERROR " + e.getMessage());
		}
	}

	public CloseableHttpResponse sendMessageHTTP(String msg) {
		CloseableHttpResponse response = null;
		String URL = "http://localhost:8088";
		try {
			StringEntity entity = new StringEntity(msg);
			HttpUriRequest httpget = RequestBuilder.get().setUri(new URI(URL))
					.setHeader("Content-Type", "application/json").setHeader("Accept", "application/json")
					.setEntity(entity).build();
			response = httpclient.execute(httpget);
			CommUtils.outyellow("ServiceCallerHTTPNaive|response=" + response);
		} catch (Exception e) {
			CommUtils.outred("ServiceCallerHTTPNaive|ERROR" + e.getMessage());
		}
		return response;
	}

	protected String getAnswer(CloseableHttpResponse response) throws ParseException, IOException {
		String answer = EntityUtils.toString(response.getEntity());
		return answer;
	}

	public static void main(String[] args) {
		new ServiceCallerHTTPNaive().doJob();
	}
}