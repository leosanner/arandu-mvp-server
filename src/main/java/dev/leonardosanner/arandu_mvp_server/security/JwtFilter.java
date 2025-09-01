package dev.leonardosanner.arandu_mvp_server.security;

import dev.leonardosanner.arandu_mvp_server.model.entity.UserEntity;
import dev.leonardosanner.arandu_mvp_server.service.user.useCases.FindUserByCookieUseCase;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private FindUserByCookieUseCase findUserByCookieUseCase;

    @Value("${security.cookies.token.name}")
    private String tokenName;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {


        String token = this.extractTokenFromCookie(request);

        if (request.getRequestURI().startsWith("/user/")) {
            filterChain.doFilter(request, response);
            return;
        }

        if (token == null || token.isEmpty()) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Cookies not defined, please login.");

            return;
        }

        try {

            UserEntity user = this.findUserByCookieUseCase.execute(token);

            if (user == null || user.getEmail().isEmpty()) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                        "Invalid user, please try again.");

                return;
            }

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(user, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(request, response);

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authentication failed.");
            return;
        }
    }

    private String extractTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie: cookies) {
                if (tokenName.equals(cookie.getName())) {
                    return cookie.getValue();
                }

                System.out.println(cookie.getName());
            }
        }

        return null;
    }
}
