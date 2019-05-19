package com.byqj.utils;

import org.apache.commons.lang3.StringUtils;
import org.bson.types.ObjectId;

/**
 * Created by willim on 2019/3/15.
 */
public class IdUtils {

    public static final String ZERO = "0";

    public static final int THREE = 3;

    /**
     *
     * @return MongoID
     */
    public static String getUUID() {
        return new ObjectId().toHexString();
    }

    public static String getId(String date, int num, int count) {
        return getId(date, String.valueOf(num), String.valueOf(count));
    }

    public static String getId(String date, String num, String count) {
        num = StringUtils.leftPad(num, THREE, ZERO);
        count = StringUtils.leftPad(count, THREE, ZERO);

        return count + date + num;
    }

    public static String getId(String date, String num, int numLen,
                               String count, int countLen) {
        num = StringUtils.leftPad(num, numLen, ZERO);
        count = StringUtils.leftPad(count, countLen, ZERO);

        return count + date + num;
    }
}
