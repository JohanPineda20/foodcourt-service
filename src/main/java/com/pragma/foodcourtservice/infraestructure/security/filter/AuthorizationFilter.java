package com.pragma.foodcourtservice.infraestructure.security.filter;

import com.pragma.foodcourtservice.domain.model.UserModel;
import com.pragma.foodcourtservice.infraestructure.security.jwt.JwtProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Component
public class AuthorizationFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String jwt = getTokenFromRequest(request);

        if(jwt != null && JwtProvider.validateToken(jwt)){
            Long id = JwtProvider.getIdFromToken(jwt);
            String email = JwtProvider.getSubjectFromToken(jwt);
            Collection<? extends GrantedAuthority> rol = List.of(new SimpleGrantedAuthority(JwtProvider.getRolFromToken(jwt)));

            UserModel userModel = new UserModel();
            userModel.setId(id);
            userModel.setEmail(email);

            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                    userModel,
                    null,
                    rol);
            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if(header !=null && header.startsWith("Bearer")){
            return header.replace("Bearer ","");
        }
        return null;
    }
}
