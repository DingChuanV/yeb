package com.uin.server.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author wanglufei
 * \* Date: 2021年08月06日 21:33
 * \* Description: JwtToken工具类
 * <p>
 * \
 */
@Component
public class JwtTokenUtil {

    /**
     * JWT包含三部分
     *      1.header   头部
     *      2.payload  负载
     *      3.signature 签名
     *
     * header 头部主要是描述JWT的基本信息，例如类型和加密啊算法。对头部的基本信息，也是jSON格式的字符串进行BASE64的加密
     *        得到的是JwtToken的第一部分。
     * payload 负载 就是存放有效信息的地方。这个名字像是特指飞机上承载的货品。
     *     这是标准注册的声明：
     *          iss: jwt签发者
     *          sub：jwt所面向的用户
     *          aud：接收jwt的一方
     *          exp：jwt的过期时间，这个过期时间必须要大于签发时间
     *          nbf：定义在什么时间之前，该jwt都是不可用的。
     *          iat: jwt的签发时间
     *          jti：jwt的唯一身份标识，主要用来作为一次性token，从而回避重放攻击。
     *   对这些声明进行BASE64加密，得到JwtToken的第二部分，每一部分的编码都用 . 隔开
     * signature 签名 它里面有包含
     *          1. header(是BASE64编码之后的)
     *          2. payload(是BASE64编码之后的)
     *          3. secret 盐
     * 这个部分需要base64加密后的header和base64加密后的payload使用，连接組成的字符串，
     * 然后通过header中声明的加密方式进行加盐secret组合加密，然后就构成了jwt的第三部分
     *
     * JwtToken的组成就是 上面组合而成的
     * @author wanglufei
     * @date 2022/4/12 7:36 PM
     * @param null
     * @return null
     */

    //===========payload也叫负载包含的 配置========
    //sub jwt所面向的用户
    private static final String CLAIM_KEY_USERNAME = "sub";
    //payload 负载的声明token的创建时间
    private static final String CLAIM_KEY_CREATED = "created";

    //=========signature 签名=======
    //
    @Value("${jwt.secret}")
    private String secret;

    //=======配置JwtToken的过期时间
    //jwt的实效时间
    @Value("${jwt.expiration}")
    private Long expiration;

    /**
     * payload负载的声明（claims）
     *
     * 根据用户的信息生成token
     *
     * UserDetails是SpringSecurity中的UserDetails
     * 我们要想实现自定义登陆的逻辑
     * 只要在我们自己的User实现UserDetails，然后自定义登陆参数
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        //准备负载 payload的声明
        Map<String, Object> claims = new HashMap<>();
        //sub：jwt所面向的用户
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        //iat: jwt的签发时间
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     *  从负载的声明信息 得到主体 也就是username
     *
     * @param token
     * @return
     */
    public String getUserNameFromToken(String token) {
        String username;
        //根据token拿取荷载
        Claims claims = getClaimsFromToken(token);
        try {
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    /**
     * token是否有效
     *
     * @param token
     * @param userDetails
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * 判断token是否可以被刷新
     *
     * @param token
     * @return
     */
    public Boolean canRefresh(String token) {
        return !isTokenExpired(token);
    }


    /**
     * 刷新token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFromToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);
        //如果token有效的时间在当前时间之前就表示失效
        return expireDate.before(new Date());
    }

    /**
     * 从中获取实效时间
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 从token获取荷载
     *
     * 解析JwtToken 获取声明主体
     *
     * @param token
     * @return
     */
    private Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 根据负载生成Token
     *
     * 使用Jwts.compact()生成JwtToken
     *
     * 这一步是真正的生成token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                //负载payload的声明信息
                .setClaims(claims)
                //设置失效时间
                .setExpiration(generateExpiration())
                //signature 加密算法+盐
                .signWith(SignatureAlgorithm.HS512, secret)
                //生成JwtToken
                .compact();
    }

    /**
     * 这个是为真正生成token方法（generateToken）打的辅助
     * 配置token过期时间
     *
     * @return
     */
    private Date generateExpiration() {
        // 当前时间+配置时间
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
