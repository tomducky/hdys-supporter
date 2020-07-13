package com.carry.www.utils.jwt;

import com.carry.www.utils.constant.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 类描述：JWT工具类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月30日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Component
public class JwtTokenUtil {

    // 是否已过期
    public boolean isExpiration(String token) {
        Boolean flag = false;

        if (StringUtils.isNotBlank(token)) {
            try {
                Claims claims = getTokenBody(token);
                // 过期的日期是否小于当前日期
                flag = claims.getExpiration().before(new Date());
            } catch (MalformedJwtException e) {
                // jwt 解析错误
                flag = true;

            } catch (ExpiredJwtException e) {

                // 已经过期，在设置jwt的时候如果设置了过期时间，这里会自动判断jwt是否已经过期，如果过期则会抛出这个异常，我们可以抓住这个异常并作相关处理。
                flag = true;
            }
        }

        return flag;
    }

    /**
     * @方法描述: 获取  Claims
     * @Param: [token]
     * @return: io.jsonwebtoken.Claims
     * @Author: carry
     */
    private Claims getTokenBody(String token) {
        return Jwts.parser().setSigningKey(Constants.TOKEN_SECRET).parseClaimsJws(token).getBody();
    }
}
