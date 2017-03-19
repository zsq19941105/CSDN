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
 * CSND���²�����
 * @author QIQI 2017��3��18�� 23:46:35
 * 
 */
public class CSDNArticle {
	
	//CSDN������ҳ��ַ
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
	 * @param blogIndexUrl CSDN������ҳ��ַ
	 */
	public CSDNArticle(String blogIndexUrl){
		this.blogIndexUrl = blogIndexUrl;
	}
	
	
	/**
	 * ��ȡCSDN����������������
	 * @return
	 * @throws IOException
	 */
	public List<String> getBlogAllArticleUrl() throws IOException{		
        //��¼������������Url
        List<String> urls = new ArrayList<String>();
        
        Element body = getDocmentBody(blogIndexUrl);
        //��ȡ��ҳdiv
        Element pageListDiv = body.getElementById("papelist");
		if (pageListDiv == null) {//��������δ����һҳ ������ѯ��ҳ����
			urls.addAll(getBlogArticleUrlByPage(blogIndexUrl));
		}else {//��ҳ
			Element countNode = pageListDiv.select("div span").get(0); 
			String content = countNode.text();			
			int pageCount = Integer.parseInt(content.trim().substring(content.indexOf("��")+1, content.indexOf("ҳ")));
			
			//���Ȼ�ȡ��ҳ����Url
			urls.addAll(getBlogArticleUrlByPage(blogIndexUrl));
			
			//��ȡ����ҳ������Url
			for (int i = 1; i <= pageCount; i++) {
				String pageUrl = blogIndexUrl+ PAGEURL+i;
				urls.addAll(getBlogArticleUrlByPage(pageUrl));
			}
		}
		return urls;
	}

	/**
	 * ��ȡCSDN��������������Ϣ
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public List<Article> getBlogAllArticle() throws ParseException, IOException{
		//��¼��������������Ϣ
        List<Article> articles = new ArrayList<Article>();
        
        Element body = getDocmentBody(blogIndexUrl);
        //��ȡ��ҳdiv
        Element pageListDiv = body.getElementById("papelist");
		if (pageListDiv == null) {//��������δ����һҳ ������ѯ��ҳ����
			articles.addAll(getBlogArticleByPage(blogIndexUrl));
		}else {//��ҳ
			Element countNode = pageListDiv.select("div span").get(0); 
			String content = countNode.text();
			int pageCount = Integer.parseInt(content.trim().substring(content.indexOf("��")+1, content.indexOf("ҳ")));
			
			//���Ȼ�ȡ��ҳ������Ϣ
			articles.addAll(getBlogArticleByPage(blogIndexUrl));;
			
			//��ȡ����ҳ��������Ϣ
			for (int i = 1; i <= pageCount; i++) {
				String pageUrl = blogIndexUrl+ PAGEURL+i;
				articles.addAll(getBlogArticleByPage(pageUrl));;
			}
		}
		return articles;
	}	
	
	/**
	 * ��ȡ��������������
	 * @return
	 * @throws IOException 
	 */
	public int getBlogArticleCount() throws IOException{
		Element body = getDocmentBody(blogIndexUrl);
		
		int count = 0;
		//��ȡ��ҳdiv
		Element pageListDiv = body.getElementById("papelist");
		if (pageListDiv == null) {//��������δ����һҳ
			 Element articleTopListDiv = body.getElementById("article_toplist");
		     Elements articleList = articleTopListDiv.getElementsByClass("list_item");
		     count += articleList.size();//�ö���������
		     Element articleListDiv = body.getElementById("article_list");
		     articleList = articleListDiv.getElementsByClass("list_item");
		     count += articleList.size();//���ö���������
		}else {
			Element countNode = pageListDiv.select("div span").get(0);			
			count = Integer.parseInt(countNode.text().trim().substring(0, countNode.text().indexOf("��")));			
		}
		return count;
	}
	
	public List<String> getBlogArticleUrlByPage(String pageUrl) throws IOException{
		Element body = getDocmentBody(pageUrl);
        
        //��¼������������Url
        List<String> urls = new ArrayList<String>();
        
        //��ȡ�ö�����Url
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
        
        //��ȡ���ö�����Url
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
        
        //��¼��������������Ϣ
        List<Article> articles = new ArrayList<Article>();
        
        //��ȡ�ö�������Ϣ
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
        
        //��ȡ���ö�������Ϣ
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
		//��ȡurl��ַ��http����Connection
        Connection conn = Jsoup.connect(url)    //������ҳ��url��ַ
                .userAgent("Mozilla/5.0 (Windows NT 6.1; rv:47.0) Gecko/20100101 Firefox/47.0") //http��������������
                .timeout(5000)   //http����ʱ��
                .method(Connection.Method.GET); 
        //��ȡҳ���html�ĵ�
        Document doc = conn.get();         
        return doc.body();
	}
	
}
