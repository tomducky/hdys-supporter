package com.carry.www.aop.log;

import com.carry.www.aop.aop.SelfAspect;
import com.carry.www.aop.aopUtil.LogAopUtil;
import com.carry.www.aop.aopUtil.UserUtil;
import com.carry.www.aop.log.domain.Aop;
import com.carry.www.aop.log.service.AopService;
import com.carry.www.aop.targetion.AopControllerLog;
import com.carry.www.aop.thread.SaveLogThread;
import com.carry.www.utils.base.AuthUser;
import com.carry.www.utils.base.DateUtils;
import com.carry.www.utils.base.UUIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.NamedThreadLocal;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 类描述：
 * aop日志类
 *
 * @author ：carry
 * @version: 1.0  CreatedDate in  2020年02月12日
 * <p>
 * 修订历史： 日期			修订者		修订描述
 */
@Slf4j
@Component
public class AopLog {

    private static final ThreadLocal<AopLog> logThreadLocal = new NamedThreadLocal<AopLog>("ThreadLocal log");
    private static final ThreadLocal<AuthUser> currentUser = new NamedThreadLocal<>("ThreadLocal user");

    @Autowired
    ThreadPoolTaskExecutor threadPoolTaskExecutor;

    @Autowired
    private AopService aopService;

    @Autowired(required = false)
    HttpServletRequest request;

    public void proceeAopLog(JoinPoint joinPoint, String addviceType, String logType, String errorMsg) {
        String userName = "";
        String userId = "";

        try {
            //从切面织入点处通过反射机制获取织入点处的方法
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            //获取切入点所在的方法
            Method method = signature.getMethod();
            //获取类joinPoint.getTarget().getClass()
            String targetName = joinPoint.getTarget().getClass().getName();
            //获取方法名字
            String methodName = signature.getName();
            Object[] arguments = joinPoint.getArgs();
            Map<String, String[]> params = request.getParameterMap(); //请求提交的参数

            //通过redis获取
            AuthUser user = UserUtil.getUserInfo();
            if (null != user) {
                currentUser.set(user);
                userName = user.getRealName();
                userId = user.getUserId();

                //获取入参
                StringBuilder paramsBuf = new StringBuilder();

                // 获取参数名称和值
                StringBuffer sb = LogAopUtil.getNameAndArgs(SelfAspect.class, targetName, methodName, arguments);
                log.info("请求类方法参数名称和值：" + sb);

                //获取自定义日志类，需要在类上加上log注解，否则此处myLog是null
                AopControllerLog myLog = method.getAnnotation(AopControllerLog.class);
                String operation = "";
                if (null != myLog) {
                    operation = myLog.description();
                }

                Aop aop = new Aop();
                aop.setId(UUIDUtil.createUUID());
                aop.setUserName(userName);
                aop.setOperation(operation);
                aop.setClazz(targetName);
                aop.setMethodName(methodName);
                aop.setParams(sb.toString());
                aop.setOperatTime(DateUtils.getNowDateTimeFmt("yyyy-MM-dd hh24:mi:ss"));
                aop.setUserId(userId);
                aop.setAddviceType(addviceType);
                aop.setLogType(logType);
                aop.setErrorMsg(errorMsg);
                //直接保存
                // aopService.addAopLog(aop);

                //使用异步线程保存日志
                //SaveLogThread saveLogThread = new SaveLogThread(aop, aopService);
                //new Thread(saveLogThread).start();

                //通过线程池来执行日志保存
                threadPoolTaskExecutor.execute(new SaveLogThread(aop, aopService));

                log.info("用户:" + userName + "进行了操作:" + operation + ",类:" + targetName + ",方法名：" + methodName + ",参数:"
                        + paramsBuf.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("新增aop日志出错" + e.getMessage());
        }
    }


}
