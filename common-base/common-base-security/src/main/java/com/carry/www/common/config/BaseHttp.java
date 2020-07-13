package com.carry.www.common.config;


import com.carry.www.utils.base.AjaxResult;
import com.carry.www.utils.base.BaseRestTemplate;
import net.sf.json.JSONObject;
import org.springframework.http.MediaType;

/**
 * 类描述：
 * 请求三方接口类  解决某些类里面无法多继承的问题，则调用此类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年04月27日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
public class BaseHttp{

    private static BaseHttp baseHttp = null;

    private BaseHttp() {

    }

    public synchronized static BaseHttp getInstance() {
        if (baseHttp == null) {
            synchronized (BaseHttp.class) {
                baseHttp = new BaseHttp();
            }
        }

        return baseHttp;
    }

    /**
     * @方法描述: 调用外部服务
     * url地址 methodType请求类型 jsonObject请求参数
     * @return: com.fencer.common.base.AjaxResult
     * @Author: carry
     */
    public AjaxResult http(String url, String methodType, JSONObject jsonObject) throws Exception {
        AjaxResult ajaxResult = this.httpToHttp(url, methodType, jsonObject);

        return ajaxResult;
    }

    /**
     * @方法描述:调用三方服务(和上面重复，后期合并方法)
     * url 地址 httpMethod请求方法 jsonObject请求参数
     * @return: com.fencer.common.base.AjaxResult
     * @Author: carry
     */
    public AjaxResult httpToHttp(String url,String httpMethod,JSONObject jsonObject) {
        BaseRestTemplate baseRestTemplate=new BaseRestTemplate();
        try {
            AjaxResult ajaxResult = baseRestTemplate.apiToApi(url, httpMethod, MediaType.APPLICATION_JSON, jsonObject, new JSONObject(), "1",null);
            String code = ajaxResult.get("code").toString();

            if("0".equals(code)){
                System.out.println("获取数据成功》》》》》》》》" + ajaxResult);
            }else{
                System.out.println( ajaxResult.get("msg"));
            }

            return ajaxResult;
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
