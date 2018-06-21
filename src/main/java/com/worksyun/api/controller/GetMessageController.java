package com.worksyun.api.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.gnu.stealthp.rsslib.RSSChannel;
import org.gnu.stealthp.rsslib.RSSException;
import org.gnu.stealthp.rsslib.RSSHandler;
import org.gnu.stealthp.rsslib.RSSItem;
import org.gnu.stealthp.rsslib.RSSParser;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import com.worksyun.api.mapper.CertificationMapper;
import com.worksyun.api.mapper.HardwareinfoMapper;
import com.worksyun.api.mapper.LucenenewsMapper;
import com.worksyun.api.mapper.UseraddressMapper;
import com.worksyun.api.mapper.UserbaseinfoMapper;
import com.worksyun.api.model.Certification;
import com.worksyun.api.model.Hardwareinfo;
import com.worksyun.api.model.Lucenenews;
import com.worksyun.api.model.Rss;
import com.worksyun.api.model.Userauthenticate;
import com.worksyun.api.model.Userbaseinfo;
import com.worksyun.api.service.LucenenewsService;
import com.worksyun.api.service.RssService;
//import com.worksyun.commons.LuceneUtil.BaiduNewList;
//import com.worksyun.commons.LuceneUtil.KnnIndex;
//import com.worksyun.commons.LuceneUtil.KnnSearch;
//import com.worksyun.commons.LuceneUtil.News;
//import com.worksyun.commons.LuceneUtil.NewsBean;
//import com.worksyun.commons.LuceneUtil.ParseMD5;
import com.worksyun.commons.model.LoginModel;
import com.worksyun.commons.test.QRCodeUtil;
import com.worksyun.commons.util.DateUtil;
import com.worksyun.commons.util.DocumentHandler;
import com.worksyun.commons.util.JedisUtil;
import com.worksyun.commons.util.PoiUtil;
import com.worksyun.commons.util.SerializeUtil;
import com.worksyun.commons.util.UploadFileUtil;
import com.worksyun.commons.util.UseCommonUtil;
import com.worksyun.commons.util.WangyiTest;
import com.worksyun.commons.util.WordExportController;
import com.worksyun.commons.util.ZipUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Demo
 * 
 * @auth:cyf
 * @see: [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
@RestController("/GetMessage")
@Api(description = "业务相关操作")
public class GetMessageController {

	
	@SuppressWarnings("rawtypes")
	@Autowired
	private RedisTemplate redisTemplate;
	
	private final static Pattern ATTR_PATTERN = Pattern.compile("<img[^<>]*?\\ssrc=['\"]?(.*?)['\"]?\\s.*?>",
			Pattern.CASE_INSENSITIVE);

	@GetMapping("/sendMessages")
	@ApiOperation(value = "获取验证码", notes = "根据手机号获取验证码")
	public ResponseEntity<LoginModel> sendMessages(
			@RequestParam(value = "mobile", required = true) String mobile,
			HttpSession session) {
		Map<String, Object> data = new HashMap<String, Object>();

		try {
			boolean bl = UseCommonUtil.isPhoneLegal(mobile);
			if (!bl) {
				return new ResponseEntity<LoginModel>(new LoginModel(false, "请输入正确手机号!"), new HttpHeaders(),
						HttpStatus.OK);
			}

			HttpClient client = new HttpClient();
			PostMethod post = new PostMethod("http://www.qf106.com/sms.aspx?action=send"); // 该第三方短信服务地址
			post.addRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=gbk");// 在头文件中设置转码
			int randomnumber = getRandNum(1, 999999);
			String rm = String.valueOf(randomnumber);
			//session.setAttribute("messagecode", rm);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("messagecode", rm);
			redisTemplate.opsForHash().putAll(mobile, map);
			String message = "您的验证码是："+rm+",感谢您的使用！";
			//session.setMaxInactiveInterval(600);
			NameValuePair[] data1 = { new NameValuePair("userid", "15780"), new NameValuePair("account", "fuweiwangluo"),
					new NameValuePair("password", "123456"), new NameValuePair("mobile", mobile),
					new NameValuePair("content", message) };
			post.setRequestBody(data1);

			client.executeMethod(post);
			Header[] headers = post.getResponseHeaders();
			int statusCode = post.getStatusCode();
			//System.out.println("statusCode:" + statusCode);
			for (Header h : headers) {
				//System.out.println(h.toString());
			}
			String result = new String(post.getResponseBodyAsString().getBytes("gbk"));
			//System.out.println(result); // 打印返回消息状态

			post.releaseConnection();

			return new ResponseEntity<LoginModel>(new LoginModel(true, message, data), new HttpHeaders(), HttpStatus.OK);
		} catch (IllegalArgumentException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return new ResponseEntity<LoginModel>(new LoginModel(false, "操作异常!" + e1.getMessage(), data),
					new HttpHeaders(), HttpStatus.OK);
		}

	}

	public static int getRandNum(int min, int max) {
		int randNum = min + (int) (Math.random() * ((max - min) + 1));
		return randNum;
	}

	@SuppressWarnings("unused")
	@Autowired
	private UseraddressMapper UseradderessMapper;

	@Autowired
	private UserbaseinfoMapper userbaseinfoMapper;


	@GetMapping("/testword")
	@ApiOperation(value = "测试生成word", notes = "测试生成word")
	public void expWord(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("utf-8");
		Map<String, Object> map = new HashMap<String, Object>();

		/*
		 * // 通过循环将表单参数放入键值对映射中 // 提示：在调用工具类生成Word文档之前应当检查所有字段是否完整 //
		 * 否则Freemarker的模板殷勤在处理时可能会因为找不到值而报错 这里暂时忽略这个步骤了 Enumeration<String> paramNames
		 * = request.getParameterNames(); while(paramNames.hasMoreElements()) { String
		 * key = paramNames.nextElement(); String value = request.getParameter(key);
		 * map.put(key, value); }
		 */
		map.put("title", "评定");
		map.put("img", DocumentHandler.getImageString("D:\\3.jpg"));

		File file = null;
		InputStream fin = null;
		// ServletOutputStream out = null;
		OutputStream out = null;
		try {
			// 调用工具类WordGenerator的createDoc方法生成Word文档
			file = DocumentHandler.createDoc(map, "test");
			fin = new FileInputStream(file);

			response.setCharacterEncoding("utf-8");
			response.setContentType("application/msword");
			// 设置浏览器以下载的方式处理该文件默认名为xxx.doc //URLEncoder.encode("成绩评定表.doc",
			// "UTF-8")
			response.addHeader("Content-Disposition",
					"attachment;filename=" + new String("mtest.doc".getBytes("utf-8"), "ISO_8859_1"));

			// out = response.getOutputStream();
			out = new FileOutputStream("D:\\test11.doc");
			byte[] buffer = new byte[512]; // 缓冲区
			int b = -1;
			// 通过循环将读入的Word文件的内容输出到浏览器中
			while ((b = fin.read(buffer)) != -1) {
				out.write(buffer, 0, b);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
			// response.getWriter().print("cuowu ");
		} finally {
			if (fin != null)
				fin.close();
			if (out != null)
				out.close();
			if (file != null)
				file.delete(); // 删除临时文件
		}
	}

	@PostMapping("/testquene")
	@ApiOperation(value = "测试队列", notes = "测试队列")
	public List<Userbaseinfo> testquene(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] userbasekey = "userbaseinfo".getBytes();
		List<Userbaseinfo> userBaseinfo = userbaseinfoMapper.selectAll();
		List<Userbaseinfo> list = new ArrayList<Userbaseinfo>();
		for (int i = 0; i < userBaseinfo.size(); i++) {
			JedisUtil.lpushByTemplate(userbasekey, SerializeUtil.serialize(userBaseinfo.get(i)));
			byte[] bytes = JedisUtil.rpopByTemplate(userbasekey);
			Userbaseinfo userbaseinfo2 = (Userbaseinfo) SerializeUtil.unserialize(bytes);
			list.add(userbaseinfo2);
		}
		return list;
	}

	public static final String filesPath = "QRcode";
	
	@PostMapping("/createQRcode")
	@ApiOperation(value = "生成二维码", notes = "生成二维码")
	public String createQRcode(@RequestParam(value = "url", required = true) String url,
			@RequestParam(value = "imgPath", required = true) String imgPath,HttpServletRequest request) {
		// 生成带logo 的二维码
		// String text = "http://my.csdn.net/ljheee";
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		 //String fileName = UUID.randomUUID().toString().replaceAll("-", "")+".jpg";
		String path = "";
		path=StringUtils.join("D:\\workyun\\ITTP\\",filesPath,File.separator,DateUtil.formatyyyyMMdd(new Date()));
		 //File file=new File(path);
		String a2 = path.replaceAll("\\\\", "/");
		String[] a3 = a2.split("/");
		String a4 = "";
		for (int i = 3; i < a3.length; i++) {
			a4 = a4 + "/" + a3[i];
		}
		String filename = "";
		try {
			filename = QRCodeUtil.encode(url, imgPath, path, true);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String a5 = request.getScheme() + "://" + "192.168.199.226" + ":" + "8888" + "/upload" + a4.trim().toString()
				+ "/" + filename;
		return a5;
	}

	@PostMapping("/decodeQRcode")
	@ApiOperation(value = "解析二维码", notes = "解析二维码")
	public String decodeQRcode(@RequestParam(value = "codePath", required = true) String codePath,
			HttpServletRequest request) {
		String url = "";
		try {
			url = QRCodeUtil.decode(codePath);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}

	@Autowired
	private RssService RssService;

	@Autowired
	private com.worksyun.api.mapper.RssMapper RssMapper;

	@PostMapping("/TestRss")
	@ApiOperation(value = "阅读Rss", notes = "阅读Rss")
	public ResponseEntity<LoginModel> ReadRss(@RequestParam(value = "Url", required = true) String Url,
			HttpServletRequest request) throws MalformedURLException, RSSException {
		// String rss = "http://rss.huanqiu.com/opinion/topic.xml";
		// Locale.setDefault(Locale.ENGLISH);
		List li = new ArrayList();
		RSSHandler remoteRSSHandler = new RSSHandler();
		RSSParser.parseXmlFile(new URL(Url), remoteRSSHandler, false);
		RSSChannel channel = remoteRSSHandler.getRSSChannel();
		List channelItems = channel.getItems();
		StringBuffer rssInfo = new StringBuffer();
		int itemSize = channelItems.size();
		if (itemSize >= 1) {
			for (int i = 0; i < itemSize; i++) {
				int itemNo = i + 1;
				RSSItem item = (RSSItem) channelItems.get(i);

				// (5)摘要的发布日期
				String itemPubDate = item.getPubDate();
				Map mrss = new HashMap();
				mrss.put("publicDate", itemPubDate);
				li.add(mrss);
				rssInfo.append("发布日期: " + itemPubDate);

			}
		}
		try {
			URL url = new URL(Url);
			// 读取Rss源
			XmlReader reader = new XmlReader(url);
			System.out.println("Rss源的编码格式为：" + reader.getEncoding());
			SyndFeedInput input = new SyndFeedInput();
			// 得到SyndFeed对象，即得到Rss源里的所有信息
			SyndFeed feed = input.build(reader);
			// 得到Rss新闻中子项列表
			List entries = feed.getEntries();
			List li2 = new ArrayList();
			// 循环得到每个子项信息
			for (int i = 0; i < entries.size(); i++) {
				SyndEntry entry = (SyndEntry) entries.get(i);
				Map maps = new HashMap();
				maps.put("Title", entry.getTitle());
				maps.put("Link", entry.getLink());
				SyndContent description = entry.getDescription();
				maps.put("RssValue", description.getValue());
				maps.put("Author", entry.getAuthor());
				li2.add(maps);
			}
			for (int k = 0; k < itemSize; k++) {
				Map m1 = (Map) li.get(k);
				String publicDate = (String) m1.get("publicDate");

				Map m2 = (Map) li2.get(k);
				String Title = (String) m2.get("Title");
				String Author = (String) m2.get("Author");
				String RssValue = (String) m2.get("RssValue");
				String Link = (String) m2.get("Link");
				String str = "";
				/*if (StringUtils.hasText(RssValue)) {
					Matcher matcher = ATTR_PATTERN.matcher(RssValue);

					while (matcher.find()) {
						str += matcher.group(1) + ",";

					}
					if (!str.equals("")) {
						str = str.substring(0, str.length() - 1);
					}
					// System.out.println("图片："+str);
				}*/
				// System.out.println("标题："+Title);
				// System.out.println("链接地址："+Link);
				// System.out.println("标题简介："+RssValue);
				// System.out.println("发布时间："+publicDate);
				// System.out.println("标题作者："+Author);
				// System.out.println("");
				Date date = DateUtil.formatToDate(publicDate);

				Map mm = new HashMap();
				mm.put("PublicDate", publicDate);

				List lis = RssMapper.selectRssByDate(mm);
				if (lis.isEmpty()) {
					Rss rm = new Rss();
					rm.setAuthor(Author);
					rm.setLink(Link);
					rm.setPublic_date(date);
					rm.setHeadlines(RssValue);
					rm.setTitle(Title);
					rm.setImage_url(str);
					if (str.isEmpty()) {
						rm.setType(0);
					} else if (str.indexOf("img") != -1) {
						rm.setType(1);
					} else if (str.indexOf("html") != -1) {
						rm.setType(99);
					}
					// RssService.save(rm);
				} else {

				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		List lm = RssMapper.selectRss();
		Map mp = new HashMap();
		mp.put("rss", lm);
		return new ResponseEntity<LoginModel>(new LoginModel(true, mp), new HttpHeaders(), HttpStatus.OK);
	}

	

	@Autowired
	private LucenenewsMapper lucenenewsMapper;


	@PostMapping("/getNews")
	@ApiOperation(value = "网易新闻网", notes = "网易新闻网")
	public ResponseEntity<LoginModel> getNews(@RequestParam(value = "currentPage", required = true) String courrentPage,
			HttpServletRequest request) {
		Map<String,Object> data = new HashMap<String,Object>();
		Map<String,Object> ParamMap = new HashMap<String,Object>();
		int count = lucenenewsMapper.selectCountLucenenews();
		int currentPage2 = Integer.parseInt(courrentPage);
		// 如果整除表示正好分N页，如果不能整除在N页的基础上+1页
		int totalPages = count % 10 == 0 ? count / 10 : (count / 10) + 1;
		String totalPage = String.valueOf(totalPages);
		// 总页数
		// this.last = totalPages;
		// 判断当前页是否越界,如果越界，我们就查最后一页
		if (currentPage2 > totalPages) {
			currentPage2 = totalPages;
			data.put("lucenenews", "[]");
			data.put("totalPages", totalPage);
			return new ResponseEntity<LoginModel>(new LoginModel(true, "", data), new HttpHeaders(), HttpStatus.OK);
		} else {
			// currentPage2=currentPage2;
		}
		if (currentPage2 <= 0) {
			currentPage2 = 1;
		}

		// 计算start 1----0 2 ------ 5
		int start = (currentPage2 - 1) * 10;
		ParamMap.put("startRow", start);
		ParamMap.put("pageSize", 10);

		List<Lucenenews> lunews = lucenenewsMapper.selectLucenenewsPage(ParamMap);

		data.put("lucenenews", lunews);
		return new ResponseEntity<LoginModel>(new LoginModel(true, data), new HttpHeaders(), HttpStatus.OK);

	}
	
	
	@Autowired
	private HardwareinfoMapper hardwareinfoMapper;
	
	@SuppressWarnings("unchecked")
	@PostMapping("/deviceActivation")
	@ApiOperation(value = "申请激活设备", notes = "申请激活设备")
	public ResponseEntity<LoginModel> deviceActivation(
			@RequestParam(value = "iccid", required = true) String iccid,
			@RequestParam(value = "userId",required = true) String userId,
			@RequestParam(value = "token",required = true) String token,
			HttpServletRequest request) {
		Map<String,Object> data  = new HashMap<String,Object>();
		Map<String,Object> mp = new HashMap<String,Object>();
		Map<String,Object> ParamMap = new HashMap<String,Object>();
		ParamMap.put("userId", userId);
		List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
		// 验证用户是否存在
		if (userBaseInfo2.isEmpty()) {
			return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
					HttpStatus.OK);
		}
		// 验证token是否正确
		String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
		if (!newtoken.equals(token)) {
			return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
					HttpStatus.OK);
		}
		
		/*boolean bl = UseCommonUtil.isPhoneLegal(iccid);
		if (!bl) {
			return new ResponseEntity<LoginModel>(new LoginModel(false, "请输入正确手机号!"), new HttpHeaders(),
					HttpStatus.OK);
		}*/
		mp.put("userId", userId);
		List<Hardwareinfo> hardwareinfo = hardwareinfoMapper.SelectHardwareinfoByUserId(mp);
		if(hardwareinfo.isEmpty()) {
			return new ResponseEntity<LoginModel>(new LoginModel(false,"请先购买设备！", data), new HttpHeaders(), HttpStatus.OK);
		}
		Hardwareinfo hard = hardwareinfo.get(0);
		Integer status = hard.getStatus();
		if(status==3) {
			data.put("hardwarestatus", status);//设备激活中
		}else if(status==4){
			data.put("hardwarestatus", status);//设备已激活
		}else if(status==1) {
			return new ResponseEntity<LoginModel>(new LoginModel(false,"请先购买设备！", data), new HttpHeaders(), HttpStatus.OK);
		}else if(status==2) {
			hard.setStatus(3);
			hard.setCardnumber(iccid);
			hardwareinfoMapper.updateByPrimaryKey(hard);//提示激活设备
			Integer hstatus = hard.getStatus();
			data.put("hardwarestatus", hstatus);
		}
		return new ResponseEntity<LoginModel>(new LoginModel(true, data), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@Autowired
	private CertificationMapper certificationMapper;
	
	
	@SuppressWarnings("unchecked")
	@PostMapping("/devicenotice")
	@ApiOperation(value = "激活设备提示", notes = "激活设备提示")
	public ResponseEntity<LoginModel> devicenotice(
			@RequestParam(value = "userId",required = true) String userId,
			@RequestParam(value = "token",required = true) String token) {
		Map<String,Object> data  = new HashMap<String,Object>();
		Map<String,Object> ParamMap = new HashMap<String,Object>();
		Map<String,Object> mp = new HashMap<String,Object>();
		ParamMap.put("userId", userId);
		List<Userbaseinfo> userBaseInfo2 = userbaseinfoMapper.selectUserbaseinfoByUserId(ParamMap);
		// 验证用户是否存在
		if (userBaseInfo2.isEmpty()) {
			return new ResponseEntity<LoginModel>(new LoginModel(false, "用户不存在！"), new HttpHeaders(),
					HttpStatus.OK);
		}
		// 验证token是否正确
		String newtoken = (String) redisTemplate.boundHashOps(userId).get("token");
		if (!newtoken.equals(token)) {
			return new ResponseEntity<LoginModel>(new LoginModel(false, "登录信息已过期，请重新登录！"), new HttpHeaders(),
					HttpStatus.OK);
		}
		mp.put("creationUserId", userId);
		List<Certification> certification = certificationMapper.SelectCertificationByUserId(mp);
		if(certification.isEmpty()) {
			return new ResponseEntity<LoginModel>(new LoginModel(false,"请先进行实名认证！", data), new HttpHeaders(), HttpStatus.OK);
		}
		Integer localhoststatus = certification.get(0).getLocalhoststatus();
		data.put("localhoststatus", localhoststatus);
		List<Hardwareinfo> hardwareinfo = hardwareinfoMapper.SelectHardwareinfoByUserId(ParamMap);
		if(hardwareinfo.isEmpty()) {
			return new ResponseEntity<LoginModel>(new LoginModel(false,"请先购买设备！", data), new HttpHeaders(), HttpStatus.OK);
		}
		Integer hardwarestatus = hardwareinfo.get(0).getStatus();
		data.put("hardwarestatus", hardwarestatus);
		return new ResponseEntity<LoginModel>(new LoginModel(true, data), new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@PostMapping("/createUserZip")
	@ApiOperation(value = "生成用户数据到zip包", notes = "生成用户数据到zip包")
	public ResponseEntity<LoginModel> createUserZip() throws IOException {
		List<Hardwareinfo> hardwareinfo = hardwareinfoMapper.selectHardwareinfoByStsatus2();
		if(hardwareinfo.isEmpty()) {
			return new ResponseEntity<LoginModel>(new LoginModel(false,"请先购买设备！"), new HttpHeaders(), HttpStatus.OK);
		}
		List<Userauthenticate> list = new ArrayList<Userauthenticate>();
		for(int j=0;j<hardwareinfo.size();j++) {
			Userauthenticate uscate = new Userauthenticate();
			uscate.setIccid(hardwareinfo.get(j).getCardnumber());
			String  userId = hardwareinfo.get(j).getUserid();
			Userbaseinfo user = userbaseinfoMapper.selectByPrimaryKey(userId);
			uscate.setIdcard(user.getIdcard());
			uscate.setMobile(user.getMobile());
			uscate.setName(user.getName());
            uscate.setAddr("");
            uscate.setCompany("复威");
            String pic = user.getCardpicture1();
            String pic2 = user.getCardpicture2();
            if(pic.isEmpty()||pic2.isEmpty()) {
            	return new ResponseEntity<LoginModel>(new LoginModel(false,"请先实名认证并购买设备！"), new HttpHeaders(), HttpStatus.OK);
            }
            String idname = user.getName();
            SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String path = "d:\\email\\word\\idcard\\"+DateUtil.formatyyyyMMdd(new Date())+"\\"+idname+df.format(new Date())+".docx";
            File files = new File(path);
			if (!new File(files.getParent()).exists())
				new File(files.getParent()).mkdirs();
				files.createNewFile();
				WordExportController wordcontroller = new WordExportController();
            try {
				wordcontroller.createword(path,pic,pic2);
			} catch (InvalidFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			list.add(uscate);
		}
		//System.out.println(list);
		
		String path = "";
		//for (int i = 0; i < hardwareinfo.size(); i++) {
			// path = "D:/excel/workbook"+i+".xls";
		    SimpleDateFormat df2 = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			path = "D:\\email\\excel\\userbaseinfo\\"+DateUtil.formatyyyyMMdd(new Date())+"\\"+df2.format(new Date())+ ".xls";
			File file = new File(path);
			if (!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			HSSFWorkbook ssfwork = PoiUtil.expExcel2(list);
			FileOutputStream out = new FileOutputStream(path);
			// 如果是浏览器通过request请求需要在浏览器中输出则使用下面方式
			// OutputStream out = response.getOutputStream();
			ssfwork.write(out);
			out.close();
		//}
			FileOutputStream fos1 = new FileOutputStream(new File("d:/email.zip"));
			ZipUtils.toZip("D:/email", fos1,true);
		// return;
			return new ResponseEntity<LoginModel>(new LoginModel(true, "压缩成功！"), new HttpHeaders(), HttpStatus.OK);
	}
}
