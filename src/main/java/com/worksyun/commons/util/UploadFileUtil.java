package com.worksyun.commons.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

@Component
@ConfigurationProperties(prefix = "fileupload")
public class UploadFileUtil {
	
	public static final String filesPath = "UserCertificate";

    public static String ROOT;

    public void setPath(String path) {
        UploadFileUtil.ROOT = path;
    }

	public static final String UPLOAD = "/upload/";

    public static String handleFileUpload(MultipartFile multipartFile,String filepath){  
    	String filePathString="";
        if (!multipartFile.isEmpty()) {  
            try {  
				String originalFilename=multipartFile.getOriginalFilename();
				
				String fileName = UUID.randomUUID().toString().replaceAll("-", "");
				filePathString= StringUtils.join(fileName, originalFilename.substring(originalFilename.indexOf("."),originalFilename.length())); 
				String filePath = StringUtils.join(ROOT,filepath,File.separator,DateUtil.formatyyyyMMdd(new Date()),File.separator,filePathString);
				File files = new File(filePath);
				if (!new File(files.getParent()).exists())
					new File(files.getParent()).mkdirs();
					files.createNewFile();
					FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), files);
			        return StringUtils.join(UPLOAD,filepath,"/"+DateUtil.formatyyyyMMdd(new Date())+"/",filePathString);
            } catch (RuntimeException e) {  
            	e.printStackTrace();
            	return "";
            } catch (IOException e) {
				e.printStackTrace();
            	return "";
			} 
        }  
        return "";
    } 
    
    
    
    
    @SuppressWarnings({ "rawtypes", "unused" })
	public static String  upload(HttpServletRequest request){
	    FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setHeaderEncoding("UTF-8");
		//最大文件大小
		long maxSize = 1000000;
		try {
			List items = upload.parseRequest(request);
			Iterator itr = items.iterator();
			while (itr.hasNext()) {
				FileItem item = (FileItem) itr.next();
				String fileName = item.getName();
				long fileSize = item.getSize();
				if (!item.isFormField()) {
					//检查文件大小
					if (item.getSize() > maxSize) {
						String fileExt = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
						SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
						String newFileName = df.format(new Date())+ new Random().nextInt(1000) + "." + fileExt;
						File uploadedFile = new File(ROOT, newFileName);
						item.write(uploadedFile);
						String filePath=ROOT+File.separator+newFileName;
						return filePath;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return "上传文件失败.";
		}
		return "";
    }
    
    
    @SuppressWarnings("unused")
	public static String createPath2(MultipartFile pic) {
		  String path = "";
		  try {
			if(pic!=null){
					 SimpleDateFormat df = new SimpleDateFormat(
								"yyyyMMddHHmmssSSS");
					String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
					path=StringUtils.join(UploadFileUtil.ROOT,filesPath,File.separator,DateUtil.formatyyyyMMdd(new Date()),File.separator,fileName);
					  File file=new File(path);
					  if(!file.exists()) {
						createFile(path);
					}
					  //if(!file.getParentFile().exists())file.getParentFile().mkdirs();
					  Thumbnails.of(pic.getInputStream())
					  .scale(1f).outputQuality(0.25f).toFile(file);
				  }
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		  return path;
	  }
    
    @SuppressWarnings("unused")
	public static String createPath(String pic) {
		  String path = "";
		try {
			Base64  decoder = new Base64();
			  byte[] b = decoder.decode(pic);
			 for (int i = 0; i < b.length; ++i) {
			if (b[i] < 0) {
			    b[i] += 256;
			    }
			 }
			 SimpleDateFormat df = new SimpleDateFormat(
						"yyyyMMddHHmmssSSS");
			String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
			path=StringUtils.join(UploadFileUtil.ROOT,filesPath,File.separator,DateUtil.formatyyyyMMdd(new Date()),File.separator,fileName);
			File file = new File(path);
			if(!file.exists()) {
				createFile(path);
			}
			OutputStream out = new FileOutputStream(path);
			out.write(b);
			out.flush();
			out.close();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		return path;
	  }
    
    //生成目录和文件
	  public static boolean createFile(String destFileName) {  
	        File file = new File(destFileName);  
	        if(file.exists()) {  
	            System.out.println("创建单个文件" + destFileName + "失败，目标文件已存在！");  
	            return false;  
	        }  
	        if (destFileName.endsWith(File.separator)) {  
	            System.out.println("创建单个文件" + destFileName + "失败，目标文件不能为目录！");  
	            return false;  
	        }  
	        //判断目标文件所在的目录是否存在  
	        if(!file.getParentFile().exists()) {  
	            //如果目标文件所在的目录不存在，则创建父目录  
	            System.out.println("目标文件所在目录不存在，准备创建它！");  
	            if(!file.getParentFile().mkdirs()) {  
	                System.out.println("创建目标文件所在目录失败！");  
	                return false;  
	            }  
	        }  
	        //创建目标文件  
	        try {  
	            if (file.createNewFile()) {  
	                System.out.println("创建单个文件" + destFileName + "成功！");  
	                return true;  
	            } else {  
	                System.out.println("创建单个文件" + destFileName + "失败！");  
	                return false;  
	            }  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	            System.out.println("创建单个文件" + destFileName + "失败！" + e.getMessage());  
	            return false;  
	        }  
	    } 
    
    
}
