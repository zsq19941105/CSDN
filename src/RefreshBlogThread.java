import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.cookie.CookiePolicy;
import org.apache.commons.httpclient.methods.GetMethod;

/**
 * ˢ�²����߳���
 * @author QIQI 2017��3��19�� 17:45:25
 *
 */
public class RefreshBlogThread implements Runnable {

	private int number = 0; 
	
	private String blogArticleUrl;
	
	public RefreshBlogThread(String blogArticleUrl) {
		// TODO �Զ����ɵĹ��캯�����
		this.blogArticleUrl = blogArticleUrl;
	}
	
	@Override
	public void run() {
		// TODO �Զ����ɵķ������
		refreshBlog();
	}

    public void refreshBlog(){      	
    	HttpClient httpClient = new HttpClient();    
    	GetMethod getMethod = new GetMethod(blogArticleUrl);   
    	getMethod.setRequestHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/39.0.2171.99 Safari/537.36 LBBROWSER");  
    	//��ʵ������Ҳ��̫��.��ֻ��֪��cookie�����ɲ���,������Ϊ csdn���ж���������cookie��ɶ.�����.  
    	getMethod.getParams().setCookiePolicy(CookiePolicy.BROWSER_COMPATIBILITY);  
    	//͵��������һ����ѭ��  
    	while(true) {    
            try {  
            	//��仰�����������Ϊ�����緢������.  
                int statusCode = httpClient.executeMethod(getMethod);  
                if (statusCode != HttpStatus.SC_OK) {    
                    System.out.print("ʧ�ܣ�" + getMethod.getStatusLine());    
                }else{
                	System.out.println(blogArticleUrl+"�Ѿ�ˢ����:"+number++); 
                }                  
                Thread.sleep(1000);
            } catch (Exception e) {    
                System.out.print("���������ַ��");    
            } finally {    
                getMethod.releaseConnection();    
            }    
    	}     
    }
}
