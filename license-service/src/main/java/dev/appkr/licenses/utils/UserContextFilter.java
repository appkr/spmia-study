package dev.appkr.licenses.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
public class UserContextFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        UserContext userContext = UserContextHolder.getContext();
        userContext.setCorrelationId(httpRequest.getHeader(UserContext.CORRELATION_ID));
        userContext.setUserId(httpRequest.getHeader(UserContext.USER_ID));
        userContext.setAuthToken(httpRequest.getHeader(UserContext.AUTH_TOKEN));
        userContext.setOrgId(httpRequest.getHeader(UserContext.ORG_ID));

        log.info("UserContextFilter Correlation id: {}", userContext.getCorrelationId());

        chain.doFilter(httpRequest, response);
    }
}
