package edu.nine14.exam.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import edu.nine14.annotation.AuthenticationLevel;
import edu.nine14.annotation.AuthenticationLevelType;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!this.verifyToken(request, response, handler))
            return false;

        return true;
    }

    private boolean verifyToken(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            System.out.println("handler is not HandlerMethod");
            return true;
        }
        AuthenticationLevel authenticationLevel = ((HandlerMethod) handler).getMethodAnnotation(AuthenticationLevel.class);
        if (authenticationLevel == null) {
            System.out.println("No authentication level annotation");
            return true;
        }
        AuthenticationLevelType needLevel = authenticationLevel.value();
        if (needLevel == AuthenticationLevelType.NONE) {
            System.out.println("no need to check token");
            return true;
        }
        try {
            String token = request.getHeader("token");
            if (token == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("token is null");
                return false;
            }
            DecodedJWT jwt = JWT.decode(token);
            if (jwt.getExpiresAt().getTime() < System.currentTimeMillis()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("token is expired");
                return false;
            }
            AuthenticationLevelType userLevel = AuthenticationLevelType.fromString(jwt.getClaim("user_type").asString());
            if (userLevel == null || userLevel.ordinal() < needLevel.ordinal()) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                System.out.println("user level is not enough");
                return false;
            }
            System.out.println("authentication check passed");
            return true;
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }
    }
}
