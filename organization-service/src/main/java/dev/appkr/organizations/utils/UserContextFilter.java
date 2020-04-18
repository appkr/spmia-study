package dev.appkr.organizations.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        MDC.put(UserContext.TRACE_ID, httpRequest.getHeader(UserContext.TRACE_ID));
        MDC.put(UserContext.USER_ID, httpRequest.getHeader(UserContext.USER_ID));
        MDC.put(UserContext.AUTH_TOKEN, httpRequest.getHeader(UserContext.AUTH_TOKEN));
        MDC.put(UserContext.ORG_ID, httpRequest.getHeader(UserContext.ORG_ID));

        log.info("UserContextFilter traceId: {}", MDC.get(UserContext.TRACE_ID));

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.addHeader(UserContext.TRACE_ID, MDC.get(UserContext.TRACE_ID));

        chain.doFilter(httpRequest, httpResponse);
    }
}
