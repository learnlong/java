package com.worksyun.commons.LuceneUtil;

import java.text.ParseException;

import org.apache.lucene.search.Query;

public class KnnSearch extends NRTSearch{  
    private static PackQuery packQuery = new PackQuery(KnnIndex.indexName);  
      
    public KnnSearch() {  
        super(KnnIndex.indexName);    
    }  
      
    public String getType(String content) throws org.apache.lucene.queryparser.classic.ParseException{  
        content = LuceneKey.escapeLuceneKey(content);  
        try {  
            Query query = packQuery.getOneFieldQuery(content, "content");  
            SearchResultBean result = search(query, 0, 3);  
            if (result == null || result.getCount() == 0) {  
                return "Î´Öª";  
            }  
            if (result.getCount() < 3) {  
                return result.getDatas().get(0).get("type");  
            }  
            if (result.getDatas().get(1).get("type").equals(result.getDatas().get(2).get("type"))) {  
                return result.getDatas().get(1).get("type");  
            }   
            return result.getDatas().get(0).get("type");  
        } catch (ParseException e) {  
            // TODO Auto-generated catch block    
            e.printStackTrace();  
        }  
        return "Î´Öª";  
    }  
  
  /*  public static void main(String[] args) {  
        // TODO Auto-generated method stub    
        try {
			System.out.println(new KnnSearch().getType("¿Æ±È"));
		} catch (org.apache.lucene.queryparser.classic.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
    } */ 
  
}  