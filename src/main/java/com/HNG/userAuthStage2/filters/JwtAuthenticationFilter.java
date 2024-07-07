package com.HNG.userAuthStage2.filters;


import com.HNG.userAuthStage2.services.JwtServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtServiceImpl jwtServiceImpl;

    private final UserDetailsService userDetailsService;

    @Autowired
    public JwtAuthenticationFilter(JwtServiceImpl jwtServiceImpl, UserDetailsService userDetailsService) {
        this.jwtServiceImpl = jwtServiceImpl;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String jwt;
        final String userEmail;
        final String authHeader = request.getHeader("Authorization");

//        if(request.getRequestURI().startsWith("/keepAlive")){
//            System.out.println("============= keep alive ==============");
//            filterChain.doFilter(request, response);
//            return;
//        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            System.out.println("=============auth endpoint================");
            filterChain.doFilter(request, response);
            return;
        }



        jwt = authHeader.substring(7);
        System.out.println("======================" + jwt);

        userEmail = jwtServiceImpl.extractUsername(jwt);
        System.out.println("===================" + userEmail);
        if(userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null){
            System.out.println("+++++++++++++++++++++++++");
            UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
            System.out.println("+++++++++++++++++++++++++" + userDetails);


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );

            authToken.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);
            System.out.println("+++++++++++++last++++++++++++" + authToken);

        }

        filterChain.doFilter(request, response);
        System.out.println("============filter done======================");

    }
}

