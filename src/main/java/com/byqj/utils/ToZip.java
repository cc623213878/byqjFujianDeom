package com.byqj.utils;

import java.io.*;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ToZip {

    public static boolean packageZip(String fileName, Map<String, String> fileNameMap, String uploadFileLocalPath) {

        String zipFilePath = uploadFileLocalPath + File.separator + fileName;
        File zipFile = new File(zipFilePath);

        //图片打包操作
        ZipOutputStream zipStream = null;
        FileInputStream zipSource = null;
        BufferedInputStream bufferStream = null;
        try {
            zipStream = new ZipOutputStream(new FileOutputStream(zipFile));// 用这个构造最终压缩包的输出流

            for (Map.Entry<String, String> entry : fileNameMap.entrySet()) {

                File file = new File(uploadFileLocalPath + File.separator + entry.getValue());

                if (!file.exists()) {
                    continue;
                }

                zipSource = new FileInputStream(file);

                byte[] bufferArea = new byte[1024 * 10];// 读写缓冲区

                ZipEntry zipEntry = new ZipEntry(entry.getKey() + "." + entry.getValue().substring(entry.getValue().lastIndexOf(".") + 1));//附件模板名
                zipStream.putNextEntry(zipEntry);// 定位到该压缩条目位置，开始写入文件到压缩包中

                bufferStream = new BufferedInputStream(zipSource, 1024 * 10);// 输入缓冲流
                int read = 0;

                while ((read = bufferStream.read(bufferArea, 0, 1024 * 10)) != -1) {
                    zipStream.write(bufferArea, 0, read);
                    zipStream.flush();
                }
            }
        } catch (Exception e) {
            return false;
        } finally {
            try {
                if (null != bufferStream)
                    bufferStream.close();
                if (null != zipStream)
                    zipStream.close();
                if (null != zipSource)
                    zipSource.close();
            } catch (IOException e) {
                return false;
            }
        }
        return true;
    }
}
