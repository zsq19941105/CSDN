import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 
 * @author QIQI 2017��3��19�� 17:45:10
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
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}
		
		ExecutorService es = null;
		if (articleUrls != null) {
			int count = articleUrls.size();
			//��һ���̳߳�
			es = Executors.newFixedThreadPool(count);
			
			for (int i = 0; i < count; i++) {
				RefreshBlogThread refreshBlogThread = new RefreshBlogThread(articleUrls.get(i));
				es.submit(refreshBlogThread);
			}
		}else{
			System.out.println("�ò���û�����£�");
		}
				
		es.shutdown();
	}
	
}
