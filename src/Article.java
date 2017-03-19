import java.util.Date;

/**
 * ������Ϣ
 * @author QIQI 2017��3��19�� 17:44:50
 *
 */
public class Article {

	//����
	private String title;
	
	//���ӵ�ַ
	private String url;
	
	//�Ƿ�Ϊ�ö�����
	private boolean isTop;
	
	//�Ƿ�Ϊԭ��
	private boolean isCreate;
	
	//��������
	private String description;
	
	//����ʱ��
	private Date publishTime;
	
	//�Ķ�����
	private long readCount;
	
	//���۴���
	private long tellCount;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public boolean isTop() {
		return isTop;
	}

	public void setTop(boolean isTop) {
		this.isTop = isTop;
	}

	public boolean isCreate() {
		return isCreate;
	}

	public void setCreate(boolean isCreate) {
		this.isCreate = isCreate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public long getReadCount() {
		return readCount;
	}

	public void setReadCount(long readCount) {
		this.readCount = readCount;
	}

	public long getTellCount() {
		return tellCount;
	}

	public void setTellCount(long tellCount) {
		this.tellCount = tellCount;
	}
	
	@Override
	public String toString(){
		return "{"+"title:"+title+","+"Url:"+url+","+"isTop:"+isTop+
				","+"isCreate:"+isCreate+","+"description:"+description+","+
				"publishTime:"+publishTime+","+"readCount:"+readCount+","+
				"tellCount:"+tellCount+"}";
	}
}
