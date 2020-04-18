package dev.appkr.licenses.clients;

import dev.appkr.licenses.utils.UserContext;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.IOException;

public class UserContextInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request,
                                        byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        HttpHeaders reqHeaders = request.getHeaders();
        reqHeaders.add(UserContext.TRACE_ID, MDC.get(UserContext.TRACE_ID));
        reqHeaders.add(UserContext.AUTH_TOKEN, MDC.get(UserContext.AUTH_TOKEN));

        return execution.execute(request, body);
    }
}
