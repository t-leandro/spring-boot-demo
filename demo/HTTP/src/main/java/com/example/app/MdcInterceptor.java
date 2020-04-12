package com.example.app;


import com.example.common.Constants;
import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 *  Intercepts the HTTP requests, generates a request id for each request, stores it in MDC on id key and
 *  adds it to Response-id HTTP response header.
 *  The request id could be generated and returned in each controller request method but with an interceptor the
 *  it is common to all requests.
 */
public class MdcInterceptor implements HandlerInterceptor {
    private final static String RESPONSE_ID_HEADER = "Response-id";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
        MDC.put(Constants.REQUEST_ID_KEY, getId());
        response.setHeader(RESPONSE_ID_HEADER, MDC.get(Constants.REQUEST_ID_KEY));
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        MDC.remove(Constants.REQUEST_ID_KEY);
    }

    private String getId() {
        return UUID.randomUUID()
                .toString();
    }
}