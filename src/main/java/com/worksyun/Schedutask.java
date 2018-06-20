package com.worksyun;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import com.worksyun.api.mapper.LucenenewsMapper;
import com.worksyun.api.model.Lucenenews;
import com.worksyun.api.service.LucenenewsService;
import com.worksyun.commons.util.DateUtil;
import com.worksyun.commons.util.WangyiTest;

@Configuration  
@Component // 此注解必加  
@EnableScheduling // 此注解必加 
public class Schedutask {
	  //@Autowired可以使用spring的bean  
	  
	@Autowired
	private LucenenewsMapper lucenenewsMapper;
	  
	@Autowired
	private LucenenewsService LucenenewsService;
	
    /** 
     * 定时接收新闻 10分钟请求一次
     */  
    public void overTimeNotice() {  
        //实际的业务  
          System.out.println("于:"+new Date()+"开始接收新闻");
    	// 爬取条数,10的倍数，网易新闻每10条预留大约2个广告位，所以爬取新闻的真实条数大约为80%
		int deep = 30;
		// 爬取宽度，0:首页，1:社会，2:国内，3:国际，4:历史
		int width = 1;
		// 网易新闻类型
		// 推荐：http://3g.163.com/touch/article/list/BA8J7DG9wangning/20-20.html
		// 新闻：http://3g.163.com/touch/article/list/BBM54PGAwangning/0-10.html
		// 娱乐：http://3g.163.com/touch/article/list/BA10TA81wangning/0-10.html
		// 体育：http://3g.163.com/touch/article/list/BA8E6OEOwangning/0-10.html
		// 财经：http://3g.163.com/touch/article/list/BA8EE5GMwangning/0-10.html
		// 时尚：http://3g.163.com/touch/article/list/BA8F6ICNwangning/0-10.html
		// 军事：http://3g.163.com/touch/article/list/BAI67OGGwangning/0-10.html
		// 手机：http://3g.163.com/touch/article/list/BAI6I0O5wangning/0-10.html
		// 科技：http://3g.163.com/touch/article/list/BA8D4A3Rwangning/0-10.html
		// 游戏：http://3g.163.com/touch/article/list/BAI6RHDKwangning/0-10.html
		// 数码：http://3g.163.com/touch/article/list/BAI6JOD9wangning/0-10.html
		// 教育：http://3g.163.com/touch/article/list/BA8FF5PRwangning/0-10.html
		// 健康：http://3g.163.com/touch/article/list/BDC4QSV3wangning/0-10.html
		// 汽车：http://3g.163.com/touch/article/list/BA8DOPCSwangning/0-10.html
		// 家居：http://3g.163.com/touch/article/list/BAI6P3NDwangning/0-10.html
		// 房产：http://3g.163.com/touch/article/list/BAI6MTODwangning/0-10.html
		// 旅游：http://3g.163.com/touch/article/list/BEO4GINLwangning/0-10.html
		// 亲子：http://3g.163.com/touch/article/list/BEO4PONRwangning/0-10.html
		// String[] typeArray=
		// {"BAI67OGGwangning","BA8D4A3Rwangning","BA10TA81wangning","BA8DOPCSwangning"};
		String[] typeArray = { "BBM54PGAwangning", "BCR1UC1Qwangning", "BD29LPUBwangning", "BD29MJTVwangning",
				"C275ML7Gwangning" };
		String type = typeArray[width];

		// 网易新闻列表url
		String url1 = "http://3g.163.com/touch/reconstruct/article/list/";
		// 网易新闻内容url
		String url2 = "http://3g.163.com/news/article/";

		List<String> ids = new ArrayList<>();

		// 根据url1，爬取条数，新闻类型获取新闻docid
		ids = WangyiTest.getIds(url1, deep, type);
		// 根据url2，新闻docid获取内容并存储到MongoDB
		// getContent(url2,ids);
		Connection connection = Jsoup.connect(url2);
		int i = 1;
		for (; i < ids.size(); i++) {
			url1 = url2 + ids.get(i) + ".html";
			Map<String, Object> murl = new HashMap<String, Object>();
			murl.put("url", url1);
			List<Lucenenews> lurl = lucenenewsMapper.selectNewsByUrl(murl);
			if (!lurl.isEmpty()) {
				// System.out.println("此新闻已存在");
			} else {
				int state = 200;
				try {
					URL url = new URL(url1);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					state = con.getResponseCode();
				} catch (IOException e2) {
				
					e2.printStackTrace();
				}
				if (state == 410) {
					// System.out.println("URL不存在");
				} else {
					connection = Jsoup.connect(url1);
					// 浏览器可接受的MIME类型。
					connection.header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
					connection.header("Accept-Encoding", "gzip, deflate");
					connection.header("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
					connection.header("Connection", "keep-alive");
					connection.header("User-Agent",
							"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:26.0) Gecko/20100101 Firefox/26.0");

					Document document = null;
					try {
						document = connection.get();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					// 获取新闻标题
					Elements title = document.select("[class=title]");
					// 获取新闻来源和文章发布时间
					Elements articleInfo = document.select("[class=info]");
					Elements src = articleInfo.select("[class=source js-source]");
					Elements time = articleInfo.select("[class=time js-time]");
					// 获取新闻内容
					Elements contentEle = document.select("[class=page js-page on]");
					// 获取图片
					// Elements img = document.select("[class=photo ]");
					Lucenenews lu = new Lucenenews();
					int size = document.getElementsByClass("photo").size();
					String imgurl = "";
					String imgurl2 = "";
					if (size == 0) {
						lu.setImg("");
						lu.setImgsize(0);
					} else {
						for (int j = 0; j < size; j++) {
							ListIterator<Element> imgs = document.getElementsByClass("photo").tagName("a").get(j)
									.children().listIterator();
							String myimg = imgs.next().attr("href");
							String suffix = myimg.substring(myimg.lastIndexOf(".") + 1);
							if(suffix.equals("gif")) {
								size = size-1;
								imgurl = imgurl + "";
							}else {
								imgurl = imgurl + myimg + ",";
							}
						}
						if(imgurl.equals("")) {
							lu.setImg("");
							lu.setImgsize(0);
						}else {
							imgurl2 = imgurl.substring(0, imgurl.length() - 1);
						}
						lu.setImg(imgurl2);
						lu.setImgsize(size);
					}
					int size2 = document.getElementsByClass("video").size();
					if (size2 == 0) {
						lu.setVideo("");
					} else {
						ListIterator<Element> video = document.getElementsByClass("video").tagName("video").get(0)
								.children().listIterator();
						String myvideo = video.next().attr("data-src");
						lu.setVideo(myvideo);
					}

					lu.setUrl(url1);
					lu.setContent(contentEle.text().toString().replaceAll("精彩弹幕，尽在客户端", ""));

					lu.setAuthor(src.text().toString());
					lu.setTitle(title.text().toString());
					lu.setNews_id(0);
					lu.setCreate_date(new Date());
					String times = time.text().toString();
					try {
						lu.setPublic_date(DateUtil.formatToDate(times));
					} catch (ParseException e) {
						e.printStackTrace();
					}
					lu.setType("0");
					LucenenewsService.save(lu);
				}
			}
		}
		System.out.println("于:"+new Date()+"接收新闻结束");
    }  
}
