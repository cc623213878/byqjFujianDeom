package com.byqj.utils;

import com.byqj.constant.CommonConstant;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.text.ParseException;
import java.util.Date;


public class CommonUtil {
//    private static Random random = new Random();

    /**
     * 密码加密处理
     *
     * @param nativePassword 原生密码
     * @return encodePassword 加密处理的密码
     */
    public static String passwordEncodeByBCrypt(String nativePassword) {
        // 密码加密
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodePassword = passwordEncoder.encode(nativePassword);
        return encodePassword;
    }

    public static Date getMaxTime() {
        try {
            return DateUtils.parseDate(CommonConstant.MAX_TIME_STRING, CommonConstant.TIME_PATTERN);
        } catch (ParseException e) {
            Date date = new Date();
            return DateUtils.addYears(date, 2099 - date.getYear());
        }
    }

//    public static String getPassword(){
//        StringBuffer sb = new StringBuffer();
//        for (int i = 0; i < 4; i++) {
//            final int temp = random.nextInt(26) + 97;
//            String s = String.valueOf((char) temp);
//            sb.append(s);
//        }
//        return null;
//    }
}
