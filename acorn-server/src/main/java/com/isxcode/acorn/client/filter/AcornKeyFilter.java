package com.isxcode.acorn.client.filter;

import com.isxcode.acorn.common.constant.SecurityConstants;
import com.isxcode.acorn.common.properties.AcornProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AcornKeyFilter extends OncePerRequestFilter {

    private final AcornProperties acornProperties;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 检查key是否存在
        String authorization = request.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
        if (authorization == null) {
            request.getRequestDispatcher(SecurityConstants.KEY_IS_NULL_EXCEPTION).forward(request, response);
            return;
        }

        // 检查key是否合法
        if (!authorization.equals(acornProperties.getSecret())) {
            request.getRequestDispatcher(SecurityConstants.KEY_IS_ERROR_EXCEPTION).forward(request, response);
            return;
        }

        doFilter(request, response, filterChain);
    }


}
