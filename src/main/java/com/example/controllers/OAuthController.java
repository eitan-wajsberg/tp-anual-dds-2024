package com.example.controllers;

import com.example.utils.PrettyProperties;
import io.javalin.http.Context;
import okhttp3.*;

import java.io.IOException;

public class OAuthController {

  private static final String AUTH_SERVER = PrettyProperties.getInstance().propertyFromName("auth_server");
  private static final String CLIENT_ID = PrettyProperties.getInstance().propertyFromName("client_id");
  // javalin-client token
  private static final String CLIENT_SECRET = PrettyProperties.getInstance().propertyFromName("client_secret");
  //Keycloak redirects the user after successful authentication.
  private static final String REDIRECT_URI = "http://localhost:7000/oauth/callback";

  //Redirect the user to the Keycloak authorization endpoint to initiate the login process.
  public static void redirect(Context ctx) {
    String authEndpoint = AUTH_SERVER + "/auth";
    String url = authEndpoint + "?response_type=code&client_id=" + CLIENT_ID + "&redirect_uri=" + REDIRECT_URI;
    ctx.redirect(url);
  }

  // Handle the callback from Keycloak after the user logs in,
  // exchange the authorization code for an access token.
  public static void callback(Context ctx) {
    String code = ctx.queryParam("code");
    OkHttpClient client = new OkHttpClient();
    RequestBody formBody = new FormBody.Builder()
        .add("grant_type", "authorization_code")
        .add("code", code)
        .add("redirect_uri", REDIRECT_URI)
        .add("client_id", CLIENT_ID)
        .add("client_secret", CLIENT_SECRET)
        .build();

    Request request = new Request.Builder()
        .url(AUTH_SERVER + "/token")
        .post(formBody)
        .build();

    try (Response response = client.newCall(request).execute()) {
      if (response.isSuccessful()) {
        String responseBody = response.body().string();
        ctx.result("Token obtenido: " + responseBody);
      } else {
        ctx.status(500).result("Error al obtener el token.");
      }
    } catch (IOException e) {
      e.printStackTrace();
      ctx.status(500).result("Error en la comunicación con el servidor OAuth.");
    }
  }
}
