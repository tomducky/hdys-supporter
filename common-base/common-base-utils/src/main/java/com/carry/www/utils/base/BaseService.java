package com.carry.www.utils.base;

import com.carry.www.utils.spring.SpringUtils;
import com.carry.www.utils.constant.Constants;
import com.carry.www.utils.redis.RedisUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static com.carry.www.utils.base.AjaxResult.error;
import static com.carry.www.utils.base.AjaxResult.success;


/**
 * 类描述：基础Service,模块间服务条用
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年03月04日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class BaseService {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    BaseRestTemplate baseRestTemplate;

    /**
     * @方法描述: 获取字典
     * @return: java.util.List<com.fencer.common.base.Dict>
     * @Author: carry
     */
    public List<Dict> getdictListByType(String dictType) {
        AuthUser authUser=this.getLoginUser();

        List<Dict> dictList = new ArrayList<Dict>();
        Gson gson = new Gson();
        try {
            String url = "http://API-SYSTEM/system/dict/data/dictList?dictType=" + dictType;
            AjaxResult result = baseRestTemplate.apiToApi(url, "GET", MediaType.APPLICATION_JSON, null, new JSONObject(), "0",authUser);

            JSONObject jo = JSONObject.fromObject(result);
            if ("0".equals(jo.get("code").toString())) {
                jo = JSONObject.fromObject(result.get("result"));
                String dictStr = jo.get("result").toString();
                if (StringUtils.isNotEmpty(dictStr)) {
                    Type type = new TypeToken<List<Dict>>() {
                    }.getType();
                    dictList = gson.fromJson(jo.get("result").toString(), type);
                }

            } else {
                throw new RuntimeException("获取数据失败：" + jo.get("msg").toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取数据失败：" + e.getMessage());
        }

        return dictList;
    }




    /**
     * @方法描述: 向全部用户广播消息
     * @return: com.fencer.rcdd.domain.util.SysUser
     * @Author: carry
     */
    public AjaxResult sendAllWebSocket(String jsonMsg) {
        try {
            AuthUser authUser=this.getLoginUser();
            String url = "http://API-SOCKET/websocket/sendAllWebSocket";
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("jsonMsg", jsonMsg);

            baseRestTemplate.apiToApi(url, "POST", MediaType.APPLICATION_JSON, jsonObject, new JSONObject(), "0",authUser);

        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }

        return success();
    }

    /**
     * @方法描述:一对一发送消息(一人对一人发布同一个消息)
     * @return: com.fencer.rcdd.domain.util.SysUser
     * @Author: carry
     */
    public AjaxResult sendOneMessageToOneToOne(String userId, String jsonMsg) {
        try {
            AuthUser authUser=this.getLoginUser();
            String url = "http://API-SOCKET/websocket/sendOneWebSocketOneToOne";

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("jsonMsg", jsonMsg);

            baseRestTemplate.apiToApi(url, "POST", MediaType.APPLICATION_JSON, jsonObject, new JSONObject(), "0",authUser);

        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }

        return success();
    }

    /**
     * @方法描述:一对一发送多消息(一人对一人发布多个消息)
     * @return: com.fencer.rcdd.domain.util.SysUser
     * @Author: carry
     */
    public AjaxResult sendManaySocketOneToOne(String userId, JSONArray jsonArray) {
        try {
            AuthUser authUser=this.getLoginUser();
            String jsonString = jsonArray.toString();
            String url = "http://API-SOCKET/websocket/sendManayWebSocketOneToOne";
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userId", userId);
            jsonObject.put("jsonString", jsonArray);
            baseRestTemplate.apiToApi(url, "POST", MediaType.APPLICATION_JSON, jsonObject, new JSONObject(), "0",authUser);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }

        return success();
    }

    /**
     * @方法描述:一对多发送消息(一人对多人发布同一个消息)
     * @return: com.fencer.rcdd.domain.util.SysUser
     * @Author: carry
     */
    public AjaxResult sendUserList(List<String> userList, String jsonMsg) {
        try {
            AuthUser authUser=this.getLoginUser();
            String url = "http://API-SOCKET/websocket/sendUserListWebSocket";

            JSONObject jsonObject=new JSONObject();
            jsonObject.put("userList", userList);
            jsonObject.put("jsonMsg", jsonMsg);
            baseRestTemplate.apiToApi(url, "POST", MediaType.APPLICATION_JSON, jsonObject, new JSONObject(), "0",authUser);
        } catch (Exception e) {
            e.printStackTrace();
            return error(e.getMessage());
        }

        return success();
    }

    /**
     * 从redis 中 获取 用户登录信息
     *
     * @return
     */
    public AuthUser getLoginUser() {
        String sysUserJsonStr = null;
        AuthUser sysUser = null;

        try {
            String tokenHeader = request.getHeader(Constants.TOKEN_HEADER);

            if (StringUtils.isNotBlank(tokenHeader) && tokenHeader.startsWith(Constants.TOKEN_PREFIX)) {
                String reqToken = tokenHeader.replace(Constants.TOKEN_PREFIX, "").replaceAll(" ", "");
                RedisUtils redisUtils = SpringUtils.getBean(RedisUtils.class);
                sysUserJsonStr = String.valueOf(redisUtils.get(reqToken + "_" + Constants.APP_NAME + "_user"));
            }

            if (StringUtils.isNotBlank(sysUserJsonStr)) {
                sysUser = (AuthUser) JSONObject.toBean(JSONObject.fromObject(sysUserJsonStr));
            }
        } catch (Exception e) {
            e.printStackTrace();
            sysUser = null;
        }

        return sysUser;
    }


}
