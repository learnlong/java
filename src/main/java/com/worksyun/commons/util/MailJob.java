package com.worksyun.commons.util;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.sun.mail.util.MailSSLSocketFactory;

import java.util.Random;
import java.security.GeneralSecurityException;
import java.util.Properties;

import javax.activation.DataHandler;
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
        String text = createText();
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
                 return new PasswordAuthentication("2856410399@qq.com", "fuecgfukfgwydghg");
                }
            });
        MimeMessage message = new MimeMessage(session);
        
        //这里用flag来标记是否发件成功（有时候会连不上服务器)，
        //如果没有，继续发送，直到发送成功为止。
        int flag = 0;
        {
        try {
        	//设置发件人，收件人，主题和文本内容，并发送
            message.setFrom(new InternetAddress("2856410399@qq.com"));//填写你自己的qq邮箱，和上面相同
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject("带图片的邮件");
 /*        // 准备邮件数据
			 // 准备邮件正文数据
			 MimeBodyPart mms = new MimeBodyPart();
			 mms.setContent("这是一封邮件正文带图片<img src='cid:xxx.jpg'>的邮件", "text/html;charset=UTF-8");
			 // 准备图片数据
			 MimeBodyPart image = new MimeBodyPart();
			 DataHandler dh = new DataHandler(new FileDataSource("D:\\test11.doc"));
			 image.setDataHandler(dh);
			 String fname = "我.doc";
			 fname = new String(fname.getBytes("ISO-8859-1"), "UTF-8");
			 image.setFileName(fname);
			 // 描述数据关系
			 MimeMultipart mm = new MimeMultipart();
			 mm.addBodyPart(mms);
			 mm.addBodyPart(image);
			 mm.setSubType("related");

			 message.setContent(mm);
			 message.saveChanges();*/

            message.setText(text);
            System.out.println("Preparing sending mail...");
            System.out.println(text);
            Transport transport = session.getTransport();
            transport.send(message);
            flag = 1;
            System.out.println("message sent successfully");
        } catch(Exception e) {
            flag = 0;
        }
        } while(flag == 0);
    }
    //下面的两个方法，用来随机组合标题和文本内容。文本内容由四部分随机组合。
    //产生标题
    public String createTitle() {
        String[] titles = {"love", "I love you", "Miss you", "My baby"};    
        Random randT = new Random(System.currentTimeMillis());
        String title = titles[randT.nextInt(titles.length)];
        return title;
    }
    //产生文本内容，文本内容由四部分随机组合得到。
    public String createText() {
        //名字纯属虚构，如有雷同（肯定会有），纯属巧合。
        String[] parts1 = {"晓静，你好。", "晓静，你还好吗？"};
        String[] parts2 = {
            "距离上次见面，我感觉已经好长时间了。",
            "流去的时间磨不去我对你的爱。",
            "我仍然记得你在天安门前的那一抹笑容。"
        };
        String[] parts3 = {"今天，我大胆地追求你。",
             "我不怕大胆地对你说，我爱你。",
             "此刻，月亮代表我的心。"
        };
        String[] parts4 = {
            "未来，我的心依旧属于你。",
            "好想在未来陪你一起慢慢变老，当然在我心中你不会老。"
        };
        Random randT = new Random(System.currentTimeMillis());
        String text = parts1[randT.nextInt(parts1.length)]
            + parts2[randT.nextInt(parts2.length)]
            + parts3[randT.nextInt(parts3.length)]
            + parts4[randT.nextInt(parts4.length)];
        return text;
    }
    
}