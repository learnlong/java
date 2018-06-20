package com.worksyun.api.jersey;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.worksyun.api.model.Mififlowstatistics;
import com.worksyun.api.service.MififlowstatisticsService;
import com.worksyun.commons.util.DateUtil;

@Component
//@Path("/mi-fi")
public class MifiNotController {
	
	/*@Autowired
	private MififlowstatisticsService mififlowstatisticsService;
	
	@GET
	@Path("/stats2") 
	@Consumes("application/x-www-form-urlencoded") 
	@Produces("text/plain") 
	public String jreset(
			@QueryParam("mac") String mac, 
			@QueryParam("imei") String imei, 
			@QueryParam("start_at") int start_at, 
			@QueryParam("up") int up, 
	        @QueryParam("down") int down
	        ) { 
		String reset = "";
		System.out.println(mac);
		System.out.println(imei);
		System.out.println(start_at);
		System.out.println(up);
		System.out.println(down);
		try {
			Calendar c = Calendar.getInstance();
			// int seconds = 1521529976;//数据库中提取的数据
			long millions = new Long(start_at).longValue() * 1000;
			c.setTimeInMillis(millions);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = sdf.format(c.getTime());
			Date date = DateUtil.formatToDate(dateString);
			Mififlowstatistics mififlow = new Mififlowstatistics();
			mififlow.setDown(down);
			mififlow.setImei(imei);
			mififlow.setMac(mac);
			//mififlow.setReset_time(date);
			mififlow.setUp(up);
			//mififlowstatisticsService.save(mififlow);
			// 系统时间
			Date systemdate = new Date();
			long systime = systemdate.getTime();
			c.add(Calendar.HOUR_OF_DAY, 3);
			Date clientdate = c.getTime();
			long clienttime = clientdate.getTime();
			if (systime == clienttime) {
				reset = "reset=true";
			} else {
				reset = "reset=false";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return reset;
	} 
	
	
	@POST
	@Path("/stats") 
	@Consumes("text/plain") 
	@Produces("text/plain") 
	public String jreset2(
			@QueryParam("mac") String mac, 
			@QueryParam("imei") String imei, 
			@QueryParam("start_at") int start_at, 
			@QueryParam("up") int up, 
	        @QueryParam("down") int down,
	        @Context HttpServletRequest request
	        ) { 
		String mm = request.getParameter("mac");
		System.out.println(mm);
		String reset = "";
		System.out.println("-----------------------");
		System.out.println(mac);
		System.out.println(imei);
		System.out.println(start_at);
		System.out.println(up);
		System.out.println(down);
		try {
			Calendar c = Calendar.getInstance();
			// int seconds = 1521529976;//数据库中提取的数据
			long millions = new Long(start_at).longValue() * 1000;
			c.setTimeInMillis(millions);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String dateString = sdf.format(c.getTime());
			Date date = DateUtil.formatToDate(dateString);
			Mififlowstatistics mififlow = new Mififlowstatistics();
			mififlow.setDown(down);
			mififlow.setImei(imei);
			mififlow.setMac(mac);
			//mififlow.setReset_time(date);
			mififlow.setUp(up);
			//mififlowstatisticsService.save(mififlow);
			// 系统时间
			Date systemdate = new Date();
			long systime = systemdate.getTime();
			c.add(Calendar.HOUR_OF_DAY, 3);
			Date clientdate = c.getTime();
			long clienttime = clientdate.getTime();
			if (systime == clienttime) {
				reset = "reset=true";
			} else {
				reset = "reset=false";
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return reset;
	} */

}
