package com.yapily.filter;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Component
public class RequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            String mdcData = String.format("[requestId:%s] ", requestId());
            MDC.put("mdcData", mdcData);
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }

    private String requestId() {
        return UUID.randomUUID().toString();
    }
}