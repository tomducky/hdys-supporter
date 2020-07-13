package com.carry.www.utils.base;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @方法描述: 时间工具类
 * @Param:
 * @return:
 * @Author: carry
 */
public class DateUtils {

    /**
     * @方法描述: 获取当前Date型日期
     * @Param: []
     * @return: java.util.Date
     * @Author: carry
     */
    public static Date getNowDateTime() {
        return new Date();
    }

    /**
     * @方法描述: 获取当前字符串日期，自定义格式化
     * @Param: []
     * @return: java.util.Date
     * @Author: carry
     */
    public static String getNowDateTimeFmt(String fmt) {
        return new SimpleDateFormat(fmt).format(new Date());
    }

    /**
     * @方法描述: 计算两个时间差  几天几小时几分钟
     * @Param: [endDate, nowDate]
     * @return: java.lang.String
     * @Author: carry
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        long day = diff / nd;
        // 计算差多少小时
        long hour = diff % nd / nh;
        // 计算差多少分钟
        long min = diff % nd % nh / nm;

        return day + "天" + hour + "小时" + min + "分钟";
    }

    /**
     * @方法描述: 获取两个时间之间的全部月份
     * @Param: [minDate, maxDate]
     * @return: java.util.List<java.lang.String>
     * @Author: carry
     */
    public static List<String> getMonthBetween(String minDate, String maxDate) throws ParseException {
        ArrayList<String> result = new ArrayList<String>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");// 格式化为年月

        Calendar min = Calendar.getInstance();
        Calendar max = Calendar.getInstance();

        min.setTime(sdf.parse(minDate));
        min.set(min.get(Calendar.YEAR), min.get(Calendar.MONTH), 1);

        max.setTime(sdf.parse(maxDate));
        max.set(max.get(Calendar.YEAR), max.get(Calendar.MONTH), 2);

        Calendar curr = min;
        while (curr.before(max)) {
            result.add(sdf.format(curr.getTime()));
            curr.add(Calendar.MONTH, 1);
        }

        return result;
    }

    /**
     * @方法描述: 传入的时间与当前时间比较 -1当前时间小于传入的时间 0相等 1当前时间大于传入的时间
     * @Param: [time 传入的时间,sdf 时间格式化类型]
     * @return: java.lang.String
     * @Author: carry
     */
    public static String compareDateTime(Date time, String sdf) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat(sdf);
        Date nowDate = new Date();
        Date nowTime = sdf1.parse(sdf1.format(nowDate));
        String result = "0";

        if (nowTime.compareTo(time) < 0) {

            return "-1";
        } else if (nowTime.compareTo(time) > 0) {

            return "1";
        }

        return result;
    }


    /**
     * @方法描述: *日期比较 -1开始时间小于结束 1开始时间大于结束 0相等
     * @return: java.lang.String
     * @Author: carry
     */
    public static String compareDateTime(Date startDate, Date endDate) throws ParseException {
        String result = "0";

        if (startDate.compareTo(endDate) > 0) {

            return "1";
        }

        if (startDate.compareTo(endDate) < 0) {

            return "-1";
        }

        return result;
    }

    /**
     * @方法描述: 获取月旬 三旬: 上旬1-10日 中旬11-20日 下旬21-31日
     * @Param: [date]
     * @return: int 1上旬  2中旬 3下旬
     * @Author: carry
     */
    public static int getTenDay(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);

        int i = c.get(Calendar.DAY_OF_MONTH);
        if (i < 11) return 1;
        else if (i < 21) return 2;
        else return 3;
    }
}
