package utils;

import io.jsonwebtoken.*;
import java.security.Key;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

public class JWT {
  private static String SECRET_KEY = "tlHsKhOqhsxHqT9zMHogNR8w1hZ5JjYI";

  public static String encode(String id, String issuer, String subject, long ttl) {
    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
    long nowMillis = System.currentTimeMillis();
    Date now = new Date(nowMillis);

    byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
    Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

    JwtBuilder builder = Jwts.builder().setId(id)
                             .setIssuedAt(now)
                             .setSubject(subject)
                             .setIssuer(issuer)
                             .signWith(signatureAlgorithm, signingKey);

    if (ttl >= 0) {
        long expMillis = nowMillis + ttl;
        Date exp = new Date(expMillis);
        builder.setExpiration(exp);
    }

    return builder.compact();
  }

  public static Claims decodeJWT(String jwt) {
    Claims claims = Jwts.parser()
                        .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                        .parseClaimsJws(jwt).getBody();

    return claims;
  }
}