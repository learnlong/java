package com.worksyun.commons.util;

import java.util.HashMap;  

public class FileTypeUtils {  
   
public static final String IMAGE_JPEG = "image/jpeg";  
public static final String IMAGE_PNG = "image/png";  
public static final String IMAGE_GIF = "image/gif";  
public static final String IMAGE_BMP = "image/bmp";  
   
   
private static final HashMap<String, String> mFileTypes = new HashMap<String, String>();  
static {  
mFileTypes.put("FFD8FF", IMAGE_JPEG);  
mFileTypes.put("89504E47", IMAGE_PNG);  
mFileTypes.put("47494638", IMAGE_GIF);  
mFileTypes.put("424D", IMAGE_BMP);  
mFileTypes.put("25504446", "application/pdf");  
}  
   
/** 
* 获取图片格式 
*/  
    public static String getImgContentType(byte[] src) {  
StringBuilder builder = new StringBuilder();  
if (src == null || src.length <= 0) {  
return null;  
}  
String contentType;  
String hv;  
for (int i = 0; i < 4 && i < src.length; i++) {  
// 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写  
hv = Integer.toHexString(src[i] & 0xFF).toUpperCase();  
if (hv.length() < 2) {  
builder.append(0);  
}  
builder.append(hv);  
contentType = mFileTypes.get(builder.toString());  
if(contentType!=null){  
return contentType;  
}  
}  
contentType = "image/jpeg";  
return contentType;  
}  
   
}  