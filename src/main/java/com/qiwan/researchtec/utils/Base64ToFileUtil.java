package com.qiwan.researchtec.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

/**
 * <br>类 名: Base64ToFileUtil
 * <br>描 述: 把base64串转换成文件
 * <br>作 者: wangkefengname@163.com
 * <br>创 建: 2019年6月3日 下午3:55:05
 * <br>版 本: v1.0.0
 */
public class Base64ToFileUtil {
	
	/**
	 * @Description: base64串转换成文件方法
	 * @param base64 base64编码过的串
	 * @param filePath 文件全路径，包括文件名
	 */
	public static void toFile(String base64, String filePath){
		BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
        	byte[] bytes = Base64.decodeBase64(base64);
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(bytes);
            bis = new BufferedInputStream(byteInputStream);
            File file = new File(filePath);
            File path = file.getParentFile();
            if(!path.exists()){
                path.mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);

            byte[] buffer = new byte[1024];
            int length = bis.read(buffer);
            while(length != -1){
                bos.write(buffer, 0, length);
                length = bis.read(buffer);
            }
            bos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                if (bis != null){
                    bis.close();
                }
                if (fos != null){
                    fos.close();
                }
                if (bos != null){
                    bos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
	}
}
