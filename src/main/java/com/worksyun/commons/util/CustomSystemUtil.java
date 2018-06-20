package com.worksyun.commons.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * 系统工具类，用于获取系统相关信息
 * Created by kagome.
 */
public class CustomSystemUtil {
    public  String INTRANET_IP = getIntranetIp(); // 内网IP
    public  String INTERNET_IP = getInternetIp(); // 外网IP

    

    public CustomSystemUtil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
     * 获得内网IP
     * @return 内网IP
     */
    private  String getIntranetIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    /**
     * 获得外网IP
     * @return 外网IP
     */
    private  String getInternetIp(){
        try{
            Enumeration<NetworkInterface> networks = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            Enumeration<InetAddress> addrs;
            while (networks.hasMoreElements())
            {
                addrs = networks.nextElement().getInetAddresses();
                while (addrs.hasMoreElements())
                {
                    ip = addrs.nextElement();
                    if (ip != null
                            && ip instanceof Inet4Address
                            && ip.isSiteLocalAddress()
                            && !ip.getHostAddress().equals(INTRANET_IP))
                    {
                        return ip.getHostAddress();
                    }
                }
            }

            // 如果没有外网IP，就返回内网IP
            return INTRANET_IP;
        } catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    
    
    @SuppressWarnings("unused")
	public String getMyIP(){
		String ip = "";
		String chinaz = "http://ip.chinaz.com/";
		Connection connection = Jsoup.connect(chinaz);
		Document document = null;
		try {
			document = connection.get();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		Elements myip = document.select("[class=fz24]");
		ip = myip.text().toString();
		return ip;
	}

	@SuppressWarnings("unused")
	public String getMyIPLocal(){
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ia.getHostAddress();
	}
}
