package az.sattarhadjiev.paymentgateway.filter;

import az.sattarhadjiev.paymentgateway.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import static az.sattarhadjiev.paymentgateway.constant.Constant.USER_ID;
import static org.apache.http.HttpHeaders.AUTHORIZATION;

@Component
public class AuthFilter extends AbstractGatewayFilterFactory<AuthFilter.Config> {

    private final RouteValidator routeValidator;
    private final JwtUtil jwtUtil;

    public AuthFilter(RouteValidator routeValidator, JwtUtil jwtUtil) {
        super(Config.class);
        this.routeValidator = routeValidator;
        this.jwtUtil = jwtUtil;

    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();

            if (routeValidator.isSecured.test(exchange.getRequest())) {
                if (!exchange.getRequest().getHeaders().containsKey(AUTHORIZATION)) {
                    throw new RuntimeException("Missing header");
                }
            }

            String token = request.getHeaders()
                    .getOrEmpty(AUTHORIZATION)
                    .get(0)
                    .substring(7);

            try {
                jwtUtil.validateToken(token);
                Claims claims = jwtUtil.extractClaims(token);

                ServerHttpRequest modifiedRequest = exchange.getRequest()
                        .mutate()
                        .header(USER_ID, String.valueOf(claims.get(USER_ID)))
                        .build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());

            } catch (Exception ignored) {
                return chain.filter(exchange);
            }
        };
    }

    public static class Config {

    }
}
