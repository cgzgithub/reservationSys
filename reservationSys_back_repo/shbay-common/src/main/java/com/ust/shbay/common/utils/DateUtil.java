package com.ust.shbay.common.utils;

import java.text.SimpleDateFormat;
import java.util.*;

public class DateUtil {
    /**
     * 根据字符日期返回星期几
     */
    public static String getWeek(Date date){

//        SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
//        String week = dateFm.format(date);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int i = c.get(Calendar.DAY_OF_WEEK);//1--7的值,对应：星期日，星期一，星期二，星期三....星期六
        String[] week = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        return week[i-1];
    }

    /**
     * 获取未来第几天的日期
     * @param past
     * @return
     */
    public static Date getPastDate(Date initDate, int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(initDate);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date date = calendar.getTime();
        return date;
    }

    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String getDateStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(date);
        return result;
    }

    public static String getDateStr2(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        String result = format.format(date);
        return result;
    }

    /**
     * 日期转字符串
     * @param date
     * @return
     */
    public static String getTimeStr(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = format.format(date);
        return result;
    }


    /**
     * 获取八小时后的日期
     * @param date
     * @return
     */
    public static String getNextDateStr(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.HOUR_OF_DAY,8);
        date=calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(date);
        return result;
    }


    /**
     * 获取当前时间下一个时间点，以半小时为单位
     * @return
     */
    public static String getTimeStr() {
        String timeStr;
        Calendar calendar = Calendar.getInstance();
        int curHour24 = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        if(curHour24 < 10){
            if(minute >= 0 && minute < 30){
                timeStr = "0"+curHour24+":00";
            }else{
                timeStr = "0"+curHour24+":30";
            }
        }else{
            if(minute >= 0 && minute < 30){
                timeStr = curHour24+":00";
            }else{
                timeStr = curHour24+":30";
            }
        }
        //获取下一个时间点
        timeStr = getTimeList().get(getTimeList().indexOf(timeStr)+1);
        return timeStr;
    }

    /**
     * 判断是否能够取消，距离开始10分钟不能取消
     * @param date  预约日期
     * @param beginTime   预约开始时间
     * @return
     */
    public static Boolean timeDecide(Date date, String beginTime){


        if(date.after(new Date()) && !getDateStr(date).equals(getDateStr(new Date()))){
            //提前至少一天
            return true;
        }else if(getDateStr(date).equals(getDateStr(new Date()))){
            //当天
            //当日时间换算成分钟
            Calendar calendar = Calendar.getInstance();
            int curHour24 = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int fullMinute = curHour24*60+minute;
            //开始时间换算成分钟
            String arr[]=beginTime.split(":");
            //处理小时
            int beginHour = 0;
            int beginMinute = 0;
            if(arr[0].substring(0,1).equals("0")){
                beginHour = Integer.parseInt(arr[0].substring(0,1));
            }else{
                beginHour = Integer.parseInt(arr[0]);
            }
            if(arr[1].equals("30")){
                beginMinute = 30;
            }
            int fullBeginMinute = beginHour*60+beginMinute;
            if(fullBeginMinute - fullMinute < 10){
                return false;
            }else{
                return true;
            }
        }else{
            //当天以后
            return false;
        }
    }

    //获取一天展厅开始时间的全部取值
    public static List<String> getAllTimeList(){
        List<String> list = new ArrayList<>();
        list.add("08:00");
        list.add("08:30");
        list.add("09:00");
        list.add("09:30");
        list.add("10:00");
        list.add("10:30");
        list.add("11:00");
        list.add("11:30");
        list.add("12:00");
        list.add("12:30");
        list.add("13:00");
        list.add("13:30");
        list.add("14:00");
        list.add("14:30");
        list.add("15:00");
        list.add("15:30");
        list.add("16:00");
        list.add("16:30");
        list.add("17:00");
        return list;
    }

    //获取一天会议室开始时间的全部取值
    public static List<String> getAllMeetingTimeList(){
        List<String> list = new ArrayList<>();
        list.add("08:00");
        list.add("08:30");
        list.add("09:00");
        list.add("09:30");
        list.add("10:00");
        list.add("10:30");
        list.add("11:00");
        list.add("11:30");
        list.add("12:00");
        list.add("12:30");
        list.add("13:00");
        list.add("13:30");
        list.add("14:00");
        list.add("14:30");
        list.add("15:00");
        list.add("15:30");
        list.add("16:00");
        list.add("16:30");
        list.add("17:00");
        list.add("17:30");
        list.add("18:00");
        list.add("18:30");
        list.add("19:00");
        return list;
    }

    //获取全天时间点
    public static List<String> getTimeList(){
        List<String> list = new ArrayList<>();
        list.add("00:00");
        list.add("00:30");
        list.add("01:00");
        list.add("01:30");
        list.add("02:00");
        list.add("02:30");
        list.add("03:00");
        list.add("03:30");
        list.add("04:00");
        list.add("04:30");
        list.add("05:00");
        list.add("05:30");
        list.add("06:00");
        list.add("06:30");
        list.add("07:00");
        list.add("07:30");
        list.add("08:00");
        list.add("08:30");
        list.add("09:00");
        list.add("09:30");
        list.add("10:00");
        list.add("10:30");
        list.add("11:00");
        list.add("11:30");
        list.add("12:00");
        list.add("12:30");
        list.add("13:00");
        list.add("13:30");
        list.add("14:00");
        list.add("14:30");
        list.add("15:00");
        list.add("15:30");
        list.add("16:00");
        list.add("16:30");
        list.add("17:00");
        list.add("17:30");
        list.add("18:00");
        list.add("18:30");
        list.add("19:00");
        list.add("19:30");
        list.add("20:00");
        list.add("20:30");
        list.add("21:00");
        list.add("21:30");
        list.add("22:00");
        list.add("22:30");
        list.add("23:00");
        list.add("23:30");
        return list;
    }

    /**
     *
     */
    public static String getDateDiff(Date date1,Date date2) {
        String TERM = "";
        //相差天数
        long it = date1.getTime() / (1000 * 24 * 60 * 60) - date2.getTime() / (1000 * 24 * 60 * 60);
        //年数
        long nd = 365;//每年毫秒数
        long nh = 30;//每月毫秒数
        long year = it / nd; // 计算差多少年 367/365=1
        long month = it % nd / nh;// 计算差多少月 366%365=2 2/30=0
        long day = it % nd % nh;// 计算差多少天
        if (year > 0) {
            TERM = year + "年" + month + "月" + day + "天";

        } else if ( month > 0) {
            TERM = month + "月" + day + "天";

        } else if (day >= 0) {
            TERM = day + "天";

        }
        return TERM;
    }

}
