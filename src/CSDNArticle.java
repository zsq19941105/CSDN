import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * 
 * CSND文章操作类
 * @author QIQI 2017年3月18日 23:46:35
 * 
 */
public class CSDNArticle {
	
	//CSDN博客首页地址
	private String blogIndexUrl;
	
	private static final String PAGEURL = "/article/list/"; 
	
	private static final String CSDNURL = "http://blog.csdn.net";
	
	public String getBlogIndexUrl() {
		return blogIndexUrl;
	}

	public void setBlogIndexUrl(String blogIndexUrl) {
		this.blogIndexUrl = blogIndexUrl;
	}

	/**
	 * 
	 * @param blogIndexUrl CSDN博客首页地址
	 */
	public CSDNArticle(String blogIndexUrl){
		this.blogIndexUrl = blogIndexUrl;
	}
	
	
	/**
	 * 获取CSDN博客所有文章链接
	 * @return
	 * @throws IOException
	 */
	public List<String> getBlogAllArticleUrl() throws IOException{		
        //记录爬出来的文章Url
        List<String> urls = new ArrayList<String>();
        
        Element body = getDocmentBody(blogIndexUrl);
        //获取分页div
        Element pageListDiv = body.getElementById("papelist");
		if (pageListDiv == null) {//文章总数未超过一页 仅仅查询首页即可
			urls.addAll(getBlogArticleUrlByPage(blogIndexUrl));
		}else {//多页
			Element countNode = pageListDiv.select("div span").get(0); 
			String content = countNode.text();			
			int pageCount = Integer.parseInt(content.trim().substring(content.indexOf("共")+1, content.indexOf("页")));
			
			//首先获取首页文章Url
			urls.addAll(getBlogArticleUrlByPage(blogIndexUrl));
			
			//获取其他页的文章Url
			for (int i = 1; i <= pageCount; i++) {
				String pageUrl = blogIndexUrl+ PAGEURL+i;
				urls.addAll(getBlogArticleUrlByPage(pageUrl));
			}
		}
		return urls;
	}

	/**
	 * 获取CSDN博客所有文章信息
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public List<Article> getBlogAllArticle() throws ParseException, IOException{
		//记录爬出来的文章信息
        List<Article> articles = new ArrayList<Article>();
        
        Element body = getDocmentBody(blogIndexUrl);
        //获取分页div
        Element pageListDiv = body.getElementById("papelist");
		if (pageListDiv == null) {//文章总数未超过一页 仅仅查询首页即可
			articles.addAll(getBlogArticleByPage(blogIndexUrl));
		}else {//多页
			Element countNode = pageListDiv.select("div span").get(0); 
			String content = countNode.text();
			int pageCount = Integer.parseInt(content.trim().substring(content.indexOf("共")+1, content.indexOf("页")));
			
			//首先获取首页文章信息
			articles.addAll(getBlogArticleByPage(blogIndexUrl));;
			
			//获取其他页的文章信息
			for (int i = 1; i <= pageCount; i++) {
				String pageUrl = blogIndexUrl+ PAGEURL+i;
				articles.addAll(getBlogArticleByPage(pageUrl));;
			}
		}
		return articles;
	}	
	
	/**
	 * 获取博客中文章总数
	 * @return
	 * @throws IOException 
	 */
	public int getBlogArticleCount() throws IOException{
		Element body = getDocmentBody(blogIndexUrl);
		
		int count = 0;
		//获取分页div
		Element pageListDiv = body.getElementById("papelist");
		if (pageListDiv == null) {//文章总数未超过一页
			 Element articleTopListDiv = body.getElementById("article_toplist");
		     Elements articleList = articleTopListDiv.getElementsByClass("list_item");
		     count += articleList.size();//置顶文章总数
		     Element articleListDiv = body.getElementById("article_list");
		     articleList = articleListDiv.getElementsByClass("list_item");
		     count += articleList.size();//非置顶文章总数
		}else {
			Element countNode = pageListDiv.select("div span").get(0);			
			count = Integer.parseInt(countNode.text().trim().substring(0, countNode.text().indexOf("条")));			
		}
		return count;
	}
	
	public List<String> getBlogArticleUrlByPage(String pageUrl) throws IOException{
		Element body = getDocmentBody(pageUrl);
        
        //记录爬出来的文章Url
        List<String> urls = new ArrayList<String>();
        
        //获取置顶文章Url
        Element articleTopListDiv = body.getElementById("article_toplist");
        Elements articleList = null;
        if (articleTopListDiv != null) {
        	 articleList = articleTopListDiv.getElementsByClass("list_item");
             if (articleList.size() != 0) {
             	for (Element article : articleList){
                 	Element articleUrlNode = (article.select("div h1 span a")).get(0);
                 	urls.add(CSDNURL+articleUrlNode.attr("href"));
         		}
     		}    
		}           
        
        //获取非置顶文章Url
        Element articleListDiv = body.getElementById("article_list");
        if (articleListDiv != null) {
        	 articleList = articleListDiv.getElementsByClass("list_item");
             if (articleList.size() != 0) {
             	for (Element article : articleList){
                 	Element articleUrlNode = (article.select("div h1 span a")).get(0);
                 	urls.add(CSDNURL+articleUrlNode.attr("href"));
         		}
     		}    
		}           
  
		return urls;
	} 
	
	public List<Article> getBlogArticleByPage(String pageUrl) throws ParseException, IOException{		
		Element body = getDocmentBody(pageUrl);
        
        //记录爬出来的文章信息
        List<Article> articles = new ArrayList<Article>();
        
        //获取置顶文章信息
        Element articleTopListDiv = body.getElementById("article_toplist");
        Elements articleList = null;
        if (articleTopListDiv != null) {
        	articleList = articleTopListDiv.getElementsByClass("list_item");
        	if (articleList.size() != 0) {
        		for (Element article : articleList){
                	Article articleInfo = getArticleByElement(article);
                	articles.add(articleInfo);
        		}
			}            
		}        
        
        //获取非置顶文章信息
        Element articleListDiv = body.getElementById("article_list");
        if (articleListDiv != null) {
        	 articleList = articleListDiv.getElementsByClass("list_item");
        	 if (articleList.size() != 0) {
        		 for (Element article : articleList){
                  	Article articleInfo = getArticleByElement(article);
                  	articles.add(articleInfo);
          		}
			}            
		}       
  
		return articles;
	}
		

	private Article getArticleByElement(Element articleNode) throws ParseException{
		Article articleInfo = new Article();
		
		Element articleUrlNode = (articleNode.select("div h1 span a")).get(0);
    	articleInfo.setUrl(CSDNURL+articleUrlNode.attr("href"));    	
    	articleInfo.setTitle(articleUrlNode.text());
    	Element createNode = articleNode.select("div span").get(0);
    	if (createNode.className().equals("ico ico_type_Repost")) {
			articleInfo.setCreate(false);
		}else {
			articleInfo.setCreate(true);
		}
    	Elements topNodes = articleNode.select("div h1 span a font");
    	if (topNodes.size() == 0) {
			articleInfo.setTop(false);
		}else {
			articleInfo.setTop(true);
		}
    	
    	String description = articleNode.select("div").get(2).text();
    	articleInfo.setDescription(description);    	
    	String publishTime = articleNode.select("div").get(3).select("span").get(0).text();
    	String readCount = articleNode.select("div").get(3).select("span").get(1).text();
    	String tellCount = articleNode.select("div").get(3).select("span").get(2).text();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd HH:mm");
    	articleInfo.setPublishTime(sdf.parse(publishTime));
    	articleInfo.setReadCount(Integer.parseInt(readCount.substring(readCount.indexOf("(")+1 , readCount.indexOf(")"))));
    	articleInfo.setTellCount(Integer.parseInt(tellCount.substring(tellCount.indexOf("(")+1 , tellCount.indexOf(")"))));
    	
    	return articleInfo;
	}
	
	private Element getDocmentBody(String url) throws IOException{		
		//获取url地址的http链接Connection
        Connection conn = Jsoup.connect(url)    //博客首页的url地址
                .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:47.0) Gecko/20100101 Firefox/47.0") //http请求的浏览器设置
                .timeout(5000)   //http连接时长
                .method(Connection.Method.GET); 
        //获取页面的html文档
        Document doc = conn.get();         
        return doc.body();
	}
	
}
