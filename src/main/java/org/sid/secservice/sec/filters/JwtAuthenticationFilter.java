package org.sid.secservice.sec.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.sid.secservice.sec.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    	 System.out.println("SuccessfullAuthentication");
    	//        try {
//            Map<String, String> creds = new ObjectMapper().readValue(request.getInputStream(), HashMap.class);
//              String username = creds.get("username");
//              String password = creds.get("password");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        System.out.println(username+"    "+password);
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            return authenticationManager.authenticate(authenticationToken);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    //	return super.attemptAuthentication(request, response);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("SuccessfullAuthentication");
        User user=(User) authResult.getPrincipal();
        Algorithm algo1=Algorithm.HMAC256(JWTUtil.SECRET);
        String jwtAccessToken=JWT.create()
        		.withSubject(user.getUsername())
        		.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_ACCESS_TOKEN))
        		.withIssuer(request.getRequestURL().toString())
        		.withClaim("roles",user.getAuthorities().stream().map(ga->ga.getAuthority()).collect(Collectors.toList()))
        		.sign(algo1);
        String jwtRefreshToken=JWT.create()
        		.withSubject(user.getUsername())
        		.withExpiresAt(new Date(System.currentTimeMillis()+JWTUtil.EXPIRE_REFRESH_TOKEN))
        		.withIssuer(request.getRequestURL().toString())
        		.sign(algo1);
        Map<String,String> idToken=new HashMap<>();
        idToken.put("access-token", jwtAccessToken);
        idToken.put("refresh-token", jwtRefreshToken);
        response.setContentType("application/json");
        new ObjectMapper().writeValue(response.getOutputStream(),idToken);
//        response.setHeader("Authorization", jwtAccessToken);
//        super.successfulAuthentication(request, response, chain, authResult);
    }
}
