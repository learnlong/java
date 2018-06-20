package com.worksyun.commons.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.worksyun.api.model.Lucenenews;






public class WangyiTest {
	
	//根据新闻列表url，获取新闻docid,并把docid存储到list中
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static List<String> getDocid(String url,int num,String type) {
	    String json = null;
	    List<String> id=new ArrayList<>();
	    Map<String,Object> map=null;
	    JSONArray parseArray=null;
	    json = loadJson(url+type+"/"+num+"-10.html");
	    String jsonStr = StringUtils.substringBeforeLast(json, ")");
	    String jsonStrO = StringUtils.substringAfter(jsonStr,"artiList(");
	    String json1 = jsonStrO.replaceAll("\\\\", "");
	    String json2 = jsonString(json1);
	    Map<String,Object> parse = (Map<String,Object>) JSONObject.parse(json2);
	    parseArray = (JSONArray) parse.get(type);
	    for(int j=0;j<parseArray.size();j++){
	        map = (Map)parseArray.get(j);
	        id.add((String) map.get("docid"));
	    }
	    return id;
	}
	
	public static String loadJson(String url) {
	    StringBuilder json = new StringBuilder();
	    try {
	        URL urlObject = new URL(url);
	        URLConnection uc = urlObject.openConnection();
	        BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	        String inputLine = null;
	        while ((inputLine = in.readLine()) != null) {
	            json.append(inputLine);
	        }
	     //   System.out.println("json.toString()====="+json.toString());
	        in.close();
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    return json.toString();
	}
	
	
	//根据内容url2获取新闻信息并进行存储
	@SuppressWarnings("unused")
	private static void getContent(String url2, List<String> ids) {
	    System.out.println("存储开始！！");
	    String url = null;
	    Connection connection = Jsoup.connect(url2);
	    int i = 1;
	    for (;i<ids.size();i++){
	        url = url2+ids.get(i)+".html";
	        connection = Jsoup.connect(url);
	        try {
	            Document document = connection.get();
	            //获取新闻标题
	            Elements title = document.select("[class=title]");
	            //获取新闻来源和文章发布时间
	            Elements articleInfo = document.select("[class=info]");
	            Elements src = articleInfo.select("[class=source js-source]");
	            Elements time = articleInfo.select("[class=time js-time]");
	            //获取新闻内容
	            Elements contentEle = document.select("[class=page js-page on]");
	            //获取图片
	            Elements img = document.select("[class=photo]");
	            Lucenenews lu = new Lucenenews();
	            lu.setContent(contentEle.text().toString());
	            lu.setImg(img.text().toString());
	            lu.setAuthor(articleInfo.text().toString());
	            lu.setTitle(title.text().toString());
	            
	            //System.out.println(img);
	            /*DBCollection dbCollection= null;
	            try {
	                dbCollection = MongoDBUtils.connMongoDB();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	            BasicDBObject obj = new BasicDBObject();
	            obj.put("title", src.html());
	            obj.put("srcFrom", src.html());
	            obj.put("time", time.html());
	            obj.put("content", contentEle.html());
	            dbCollection.insert(obj);
	            DBCursor dbCursor = dbCollection.find();
	            while(dbCursor.hasNext()){
	                Map map = (Map)dbCursor.next();
	            }*/
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	    }
	    System.out.println("本次共计存储"+i*0.8+"条数据");
	}
	
	
	//设置爬取深度，循环多次获取docid
	public static List<String> getIds(String url1,int num,String type) {
	    List<String> id = new ArrayList<>();
	    List<String> ids = new ArrayList<>();
	    for (int i=0;i<=num;i+=10){
	        id = getDocid(url1,i,type);
	        ids.addAll(id);
	    }
	    return ids;
	}
	
	
	//json去引号
	private static String jsonString(String s){
        char[] temp = s.toCharArray();       
        int n = temp.length;
        for(int i =0;i<n;i++){
            if(temp[i]==':'&&temp[i+1]=='"'){
                    for(int j =i+2;j<n;j++){
                        if(temp[j]=='"'){
                            if(temp[j+1]!=',' &&  temp[j+1]!='}'){
                                temp[j]='n';
                            }else if(temp[j+1]==',' ||  temp[j+1]=='}'){
                                break ;
                            }
                        }
                    }   
            }
        }       
        return new String(temp);
    }
	
	/*public static void main(String[] args) throws Exception {
	    //爬取条数,10的倍数，网易新闻每10条预留大约2个广告位，所以爬取新闻的真实条数大约为80%
	    int deep = 30;
	    //爬取宽度，0:首页，1:社会，2:国内，3:国际，4:历史
	    int width = 1;
	    //网易新闻类型
	    String[] typeArray={"BBM54PGAwangning","BCR1UC1Qwangning","BD29LPUBwangning","BD29MJTVwangning","C275ML7Gwangning"};
	    String type = typeArray[width];

	    //网易新闻列表url
	    String url1 = "http://3g.163.com/touch/reconstruct/article/list/";
	    //网易新闻内容url
	    String url2 = "http://3g.163.com/news/article/";


	    List<String> ids = new ArrayList<>();

	    //根据url1，爬取条数，新闻类型获取新闻docid
	    ids = getIds(url1,deep,type);
	    //根据url2，新闻docid获取内容并存储到MongoDB
	    getContent(url2,ids);
	}*/
}
