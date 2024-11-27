package com.example.utils;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPublicKeySpec;
import java.security.interfaces.RSAPublicKey;

public class KeycloakKeyUtil {
  public static RSAPublicKey getPublicKey(String modulusBase64, String exponentBase64) throws InvalidKeySpecException, NoSuchAlgorithmException {
    byte[] modulusBytes = java.util.Base64.getUrlDecoder().decode(modulusBase64);
    byte[] exponentBytes = java.util.Base64.getUrlDecoder().decode(exponentBase64);

    BigInteger modulus = new BigInteger(1, modulusBytes);
    BigInteger exponent = new BigInteger(1, exponentBytes);

    RSAPublicKeySpec spec = new RSAPublicKeySpec(modulus, exponent);
    KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return (RSAPublicKey) keyFactory.generatePublic(spec);
  }
}
