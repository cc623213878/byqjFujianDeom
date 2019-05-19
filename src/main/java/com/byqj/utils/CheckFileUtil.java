package com.byqj.utils;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Part;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName:CheckFileUtil
 * @Description:检测上传文件的合法性
 * @Author:lwn
 * @Date:2019/1/18 10:53
 **/
public class CheckFileUtil {
    //检测图片是否合法
    public static boolean checkFileIsIllegle(Part part) {
        String originName = part.getSubmittedFileName();//原始文件名
        if (StringUtils.isBlank(originName)) {
            return false;
        }
        String suffix = originName.substring(originName.lastIndexOf(".") + 1);
        //文件名不为空
        if (StringUtils.isBlank(originName)) {
            return true;
        }
        //文件名不能包含非法字符
        Pattern p = Pattern.compile("[,;:/*?<>|\"&]");
        if (StringUtils.isNotBlank(originName)) {
            Matcher m = p.matcher(originName);
            if (m.find())
                return true;
        }
        if ("php".equals(suffix) || "exe".equals(suffix)
                || "aspx".equals(suffix) || "sh".equals(suffix)
                || "jar".equals(suffix) || "dmg".equals(suffix)
                || "app".equals(suffix)) {
            return true;
        }
        return false;
    }

    /*
           检测文件后缀是否为.xls
     */
    public static boolean isExcelFile(Part part) {
        if (checkFileIsIllegle(part)) {
            return false;
        }
        String originName = part.getSubmittedFileName();//原始文件名
        String suffix = originName.substring(originName.lastIndexOf(".") + 1);
        if ("xls".equals(suffix)) {
            return true;
        }
        return false;

    }


}
