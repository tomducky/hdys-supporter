package com.carry.www.utils.base;


import com.carry.www.utils.constant.Constants;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.HashMap;

/**
 * @方法描述:  API统一响应前台类
 * @Param:
 * @return:
 * @Author: carry
 */
public class AjaxResult extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public AjaxResult() {
    }

    /**
     * 返回错误消息
     *
     * @return 错误消息
     */
    public static AjaxResult error() {
        return error("操作失败");
    }

    /**
     * @方法描述: 返回失败消息, 自定义消息
     * @Param: [code, msg]
     * @return: com.carry.www.utils.base.AjaxResult
     * @Author: carry
     */
    public static AjaxResult error(String msg) {
        AjaxResult json = new AjaxResult();
        json.put("code", Constants.SUCCESS)
                .put("msg", msg);

        return json;
    }


    /**
     * @方法描述: 返回成功消息
     * @Param: []
     * @return: com.carry.www.utils.base.AjaxResult
     * @Author: carry
     */
    public static AjaxResult success() {
        return AjaxResult.success("操作成功");
    }

    /**
     * @方法描述: 返回成功消息, 自定义信息
     * @Param: [msg]
     * @return: com.carry.www.utils.base.AjaxResult
     * @Author: carry
     */
    public static AjaxResult success(String msg) {
        AjaxResult json = new AjaxResult();
        json.put("code", Constants.FAIL)
                .put("msg", msg);

        return json;
    }

    /**
     * @方法描述: 返回成功消息, 自定义信息和结果集，JSONObject格式
     * @Param: [msg, result]
     * @return: com.carry.www.utils.base.AjaxResult
     * @Author: carry
     */
    public static AjaxResult successInfo(String msg, JSONObject result) {
        AjaxResult json = new AjaxResult();
        json.put("code", 0)
                .put("msg", msg)
                .put("result", result);

        return json;
    }

    /**
     * @方法描述: 返回成功消息, 自定义信息和结果集，JSONArray格式
     * @Param: [msg, jsonArray]
     * @return: com.carry.www.utils.base.AjaxResult
     * @Author: carry
     */
    public static AjaxResult successInfo(String msg, JSONArray jsonArray) {
        AjaxResult json = new AjaxResult();
        json.put("code", 0)
                .put("msg", msg)
                .put("result", jsonArray);

        return json;
    }

    /**
     * @方法描述: 设值
     * @Param: [key, value]
     * @return: com.carry.www.utils.base.AjaxResult
     * @Author: carry
     */
    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);

        return this;
    }
}
