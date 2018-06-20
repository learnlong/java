package com.worksyun.commons.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sun.mail.util.MailSSLSocketFactory;

import java.io.File;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.*;
public class MailJob implements Job
{
    @SuppressWarnings("static-access")
	public void execute(JobExecutionContext context)
        throws JobExecutionException{
        //收件人，标题和文本内容
        String to = "1499457139@qq.com";//填写你要发给谁
        //String title = createTitle();
        //String text = createText();
        //设置属性
        Properties props = new Properties();
     // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", "smtp.qq.com");
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        try {
			MailSSLSocketFactory sf = new MailSSLSocketFactory();
			sf.setTrustAllHosts(true);
			//QQ邮箱发件的服务器和端口
			props.put("mail.smtp.ssl.enable", "true");
	        props.put("mail.smtp.ssl.socketFactory", sf);
		} catch (GeneralSecurityException e1) {
			
			e1.printStackTrace();
		}
        Session session = Session.getDefaultInstance(props, 
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {        
                    //填写你的qq邮箱用户名和密码    或者开启stmp后分配的一串密文    
                 return new PasswordAuthentication("3365692439@qq.com", "bwgnssvwyfatchid");
                }
            });
        MimeMessage message = new MimeMessage(session);
        
        //这里用flag来标记是否发件成功（有时候会连不上服务器)，
        //如果没有，继续发送，直到发送成功为止。
        int flag = 0;
        {
        try {
        	//设置发件人，收件人，主题和文本内容，并发送
            message.setFrom(new InternetAddress("3365692439@qq.com"));//填写你自己的qq邮箱，和上面相同
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("fwwl用户信息");
         // 向multipart对象中添加邮件的各个部分内容，包括文本内容和附件  
            Multipart multipart = new MimeMultipart();  
              
            // 添加邮件正文  
            BodyPart contentPart = new MimeBodyPart();  
            contentPart.setContent("这是邮件正文！", "text/html;charset=UTF-8");  
            multipart.addBodyPart(contentPart);  
              
            // 添加附件的内容  
            File attachment = new File("d:/email.zip");  
            if (attachment != null) {  
                BodyPart attachmentBodyPart = new MimeBodyPart();  
                DataSource source = new FileDataSource(attachment);  
                attachmentBodyPart.setDataHandler(new DataHandler(source));  
                  
                // 网上流传的解决文件名乱码的方法，其实用MimeUtility.encodeWord就可以很方便的搞定  
                // 这里很重要，通过下面的Base64编码的转换可以保证你的中文附件标题名在发送时不会变成乱码  
                //sun.misc.BASE64Encoder enc = new sun.misc.BASE64Encoder();  
                //messageBodyPart.setFileName("=?GBK?B?" + enc.encode(attachment.getName().getBytes()) + "?=");  
                  
                //MimeUtility.encodeWord可以避免文件名乱码  
                attachmentBodyPart.setFileName(MimeUtility.encodeWord(attachment.getName()));  
                multipart.addBodyPart(attachmentBodyPart);  
            }  
              
            // 将multipart对象放到message中  
            message.setContent(multipart);  
            // 保存邮件  
            message.saveChanges();
            //message.setText(text);
            System.out.println("Preparing sending mail...");
            //System.out.println(text);
            Transport transport = session.getTransport();
            transport.send(message);
            flag = 1;
            System.out.println("message sent successfully");
        } catch(Exception e) {
            flag = 0;
        }
        } while(flag == 0);
    }
}