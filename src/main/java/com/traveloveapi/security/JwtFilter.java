package com.traveloveapi.security;

import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.traveloveapi.DTO.ErrorResponse;
import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.security.role.UserRole;
import com.traveloveapi.utility.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private final UserRepository userRepository;
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse  response,FilterChain  filterChain) throws ServletException, IOException {
        logger.info(request.getRequestURI());
        String token = getTokenFromRequest(request);
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        if (token.isEmpty())
        {
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(ow.writeValueAsString(new ErrorResponse("Token is required", 403)));
            response.setStatus(403);
            return;
        }
        //-----------------------
        String user_id;
        try {
            user_id =  JwtProvider.getUserIdFromToken(token);
        } catch (ExpiredJwtException ex) {
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(ow.writeValueAsString(new ErrorResponse("Expired token", 401)));
            response.setStatus(401);
            return;
        }
        catch (MalformedJwtException | SignatureException ex) {
            response.setHeader("Content-Type", "application/json");
            response.getWriter().write(ow.writeValueAsString(new ErrorResponse("Malformed token", 401)));
            response.setStatus(401);
            return;
        }

        //UserDetails userDetail = customUserDetailService.loadUserByUsername(user_id);
        UserEntity user = userRepository.find(user_id);
        Collection <GrantedAuthority> roles = new ArrayList<>();
        roles.add(new UserRole());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user.getId(), "", roles);
        SecurityContextHolder.getContext().setAuthentication(auth);
        //------------------------
        filterChain.doFilter(request,response);
    }

    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        logger.info(request.getRequestURL()+"?"+request.getQueryString());
        if (request.getMethod().equals("OPTIONS"))
            return true;
        String requestPath = request.getServletPath();
        String[] whiteList = {"/login/basic","/register/basic", "/favicon.ico"};
        for (String path : whiteList) {
            if (path.equals(requestPath))
                return true;
            if (requestPath.startsWith("/auth")||requestPath.startsWith("/swagger-ui")||requestPath.startsWith("/v3")||requestPath.startsWith("/public") || requestPath.startsWith("/.well-known")) {
                return true;
            }
        }
        return false;
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("Authorization");
        System.out.println("Token from request: "+token);
        if (token != null && token.startsWith("Bearer "))
            return token.replace("Bearer ", "");
        else
            return "";
    }
}
