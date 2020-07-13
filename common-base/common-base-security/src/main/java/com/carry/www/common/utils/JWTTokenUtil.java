package com.carry.www.common.utils;

import com.carry.www.security.entity.SelfUserDetails;
import com.carry.www.utils.constant.Constants;
import io.jsonwebtoken.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

/**
 * 类描述： JWT工具类
 *
 * @author carry
 * @version 1.0        CreateDate: 2020年2月24日
 * iss: 签发者
 * sub: 面向用户
 * aud: 接收者
 * iat(issued at):是一个时间戳，代表这个JWT的签发时间；
 * exp(expires): 过期时间
 * nbf(not before)：是一个时间戳，代表这个JWT生效的开始时间，意味着在这个时间之前验证JWT是会失败的
 * jti： 是JWT的唯一标识
 * 修订历史：
 * 日期			修订者		修订描述
 */
public class JWTTokenUtil {

    /**
     * 私有化构造器
     */
    private JWTTokenUtil() {
    }

    /**
     * 签名秘钥，可以为系统的标识
     */
    static String secretKey  = Constants.TOKEN_SECRET;

    /**
     * @方法描述:  生成Token
     * @Param: [selfUserDetails]
     * @return: java.lang.String
     * @Author: carry
     */
    public static String createAccessToken(SelfUserDetails selfUserDetails) {
        //claims 可以设置成map对象
        // Map claims = new HashMap();
        //claims.put("uid", selfUserDetails.getUserId()+"");

        // 登陆成功 Jwts生成JWT
        String token = Jwts.builder()
                // id  放入用户ID
                .setId(selfUserDetails.getId() + "")
                // 主题 放入用户姓名
                .setSubject(selfUserDetails.getUsername())
                // 签发时间
                .setIssuedAt(new Date())
                // 签发者
                .setIssuer(Constants.ISS)
                // payload和claims 两个属性均可作为载荷，jjwt中二者只能设置其一，如果同时设置，在终端方法compact() 中将抛出异常
                // 此处放入权限集合
                .claim("authorities", JSONObject.fromObject(selfUserDetails.getAuthorities()))
                // 失效时间
                .setExpiration(new Date(System.currentTimeMillis() + Constants.EXPIRATION_12))
                // 签名算法和密钥
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return token;
    }


    /**
     * @方法描述: 处理token 获取token 载荷
     * @Param:  token
     *  parseClaimsJws 载荷为claims
     * @return: io.jsonwebtoken.Claims
     * @Author: carry
     */
    private static Claims getTokenBody(String token) {
        JwtParser jwtParser = Jwts.parser().setSigningKey(secretKey);
        Claims claims=jwtParser.parseClaimsJws(token).getBody();

        return claims;
    }

    /**
     * @方法描述: 判断token是否已过期
     * @Param: [token]
     * @return: boolean
     * @Author: carry
     */
    public static boolean isExpiration(String token) {
        //默认未过期
        Boolean flag = false;

        if (StringUtils.isNotBlank(token)) {
            try {

                Claims claims = getTokenBody(token);
                flag = claims.getExpiration().before(new Date());
            } catch (SignatureException | MalformedJwtException e) {
                flag = true;
            } catch (ExpiredJwtException e) {
                // 在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期
                // 如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
                flag = true;
            }

        }

        return flag;
    }
}