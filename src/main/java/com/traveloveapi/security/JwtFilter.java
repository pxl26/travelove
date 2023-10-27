package com.traveloveapi.security;

import com.traveloveapi.entity.UserEntity;
import com.traveloveapi.repository.UserRepository;
import com.traveloveapi.security.role.UserRole;
import jakarta.servlet.ServletException;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.FilterChain;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private JwtProvider jwtProvider;
    private UserRepository userRepository;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse  response,FilterChain  filterChain) throws ServletException, IOException {
        System.out.println("Co request ne!!!");
        String token = getTokenFromRequest(request);
        //-----------------------
        String user_id =  jwtProvider.getUserIdFromToken(token);
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
        if (request.getMethod().equals("OPTIONS"))
            return true;
        String requestPath = request.getServletPath();
        System.out.println("Hello: " + requestPath);
        String[] whiteList = {"/login/basic","/register/basic", "/favicon.ico"};
        for (String path : whiteList) {
            if (path.equals(requestPath))
                return true;
            if (requestPath.startsWith("/auth")||requestPath.startsWith("/swagger-ui")||requestPath.startsWith("/v3")||requestPath.startsWith("/public")) {
                System.out.println("Qua r nhe");
                return true;
            }
        }
        return false;
    }

    private String getTokenFromRequest(HttpServletRequest request){
        String token = request.getHeader("token");
        System.out.println("Token from request: "+token);
        if (token != null && token.startsWith("Bearer "))
            return token.replace("Bearer ", "");
        else
            return "";
    }
}
