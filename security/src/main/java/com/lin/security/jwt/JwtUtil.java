package com.lin.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {
    // 盐
    private static String secret = "secret";

    public static void main(String[] args) {
        final String token = createToken(10, "666");
        System.out.println("token = " + token);
        parseToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0U3ViamVjdCIsIm5hbWUiOiJsaW4iLCJpYXQiOjE2NjY2MTQzMDUsImp0aSI6IjEifQ.WaUkIK5QLh1dbchDCJlLEeOLd6xXWe0n-Hc5rP44ls4");
    }

    public static String createToken(int time, String id) {
        Map<String, Object> map = new HashMap<>();
        map.put("name", "lin");
        JwtBuilder jwt = Jwts.builder()
            // 设置头
            .setHeaderParam("typ", "JWT")
            .setHeaderParam("alg", "HS256")
            // 设置签名算法和加盐秘钥
            .signWith(SignatureAlgorithm.HS256, secret)
            .setExpiration(new Date(new Date().getTime() + time * 1000))
            // 自定义内容payload 接受一个map
            .setClaims(map)
            // 唯一id
            .setId(id)
            // JWT的主体 {"sub": "jwtSubject"}
            .setSubject("testSubject")
            // 签发时间
            .setIssuedAt(new Date());
        String token = jwt.compact();
        return token;
    }

    public static String parseToken(String token) {
        System.out.println("====================开始解析JWT====================");

        System.out.println("token --> " + token);
        try {
            Claims body = Jwts.parser()
                // 签名秘钥
                .setSigningKey(secret)
                // 要解析的jwt
                .parseClaimsJws(token)
                .getBody();

            System.out.println("id --> " + body.getId());
            System.out.println("sub --> " + body.getSubject());
            System.out.println("自定义内容 name --> " + body.get("name"));
            System.out.println("iat 创建时间 --> " + body.getIssuedAt());
            Date expiration = body.getExpiration();
            System.out.print("过期时间 --> ");
            System.out.println(expiration);
            return body.getId();
        } catch (Exception e) {
            throw new RuntimeException("无效Token");
        }
    }
}
