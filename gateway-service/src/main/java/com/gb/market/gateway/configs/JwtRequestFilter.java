//package com.gb.market.gateway.configs;
//
//import com.gb.market.gateway.utils.JwtTokenUtil;
//import io.jsonwebtoken.ExpiredJwtException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//import java.io.IOException;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//@Slf4j
//public class JwtRequestFilter extends OncePerRequestFilter {
//    private final JwtTokenUtil jwtTokenUtil;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String authHeader = request.getHeader("Authorization");
//
//        String username = null;
//        String jwt = null;
//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            jwt = authHeader.substring(7);
//            try {
//                username = jwtTokenUtil.getUsernameFromToken(jwt);
//            } catch (ExpiredJwtException e) {
//                log.debug("The token is expired");
//            }
//        }
//
//
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username, null, jwtTokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
//            SecurityContextHolder.getContext().setAuthentication(token);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//}
