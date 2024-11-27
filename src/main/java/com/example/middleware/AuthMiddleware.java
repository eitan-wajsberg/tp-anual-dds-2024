package com.example.middleware;

import com.example.utils.KeycloakKeyUtil;
import com.example.utils.PrettyProperties;
import io.javalin.Javalin;
import io.javalin.http.Context;
import io.javalin.http.UnauthorizedResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import java.util.List;
import java.util.Map;

public class AuthMiddleware {
  private static final String TOKEN_MODULUS = PrettyProperties.getInstance().propertyFromName("token_modulus");
  private static final String TOKEN_EXPONENT = PrettyProperties.getInstance().propertyFromName("token_exponent");

  public static void apply(Javalin app) {
    app.before("/protected", AuthMiddleware::validateToken);
  }

  public static void validateToken(Context ctx) {
    String token = ctx.header("Authorization");
    if (token == null || !token.startsWith("Bearer ")) {
      throw new UnauthorizedResponse("No se encontró un token válido.");
    }

    String jwt = token.substring("Bearer ".length());

    try {
      Claims claims = Jwts.parserBuilder()
          .setSigningKey(KeycloakKeyUtil.getPublicKey(TOKEN_MODULUS, TOKEN_EXPONENT))
          .build()
          .parseClaimsJws(jwt)
          .getBody();

      List<String> roles = (List<String>) claims.get("realm_access", Map.class).get("roles");
      if (!roles.contains("ADMIN")) {
        throw new UnauthorizedResponse("No tienes el rol requerido.");
      }
    } catch (Exception e) {
      throw new UnauthorizedResponse("Token inválido o expirado: " + e.getMessage());
    }
  }
}
