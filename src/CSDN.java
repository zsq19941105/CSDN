import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * 
 * @author QIQI 2017年3月19日 17:45:10
 *
 */
public class CSDN {
	
	private static final String blogIndexUrl ="http://blog.csdn.net/u012999796"; 
	
	public  static void main(String[] args) {	
		CSDNArticle csdnArticle = new CSDNArticle(blogIndexUrl);
		List<String> articleUrls = null;
		try {
			articleUrls = csdnArticle.getBlogAllArticleUrl();
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
		
		if (csdnArticle != null) {
			int count = articleUrls.size();
			//用一个线程池
			ExecutorService es = Executors.newFixedThreadPool(count);
			
			for (int i = 0; i < count; i++) {
				RefreshBlogThread refreshBlogThread = new RefreshBlogThread(articleUrls.get(i));
				es.submit(refreshBlogThread);
			}
		}else{
			System.out.println("该博客没有文章！");
		}
		
	}
	
}
