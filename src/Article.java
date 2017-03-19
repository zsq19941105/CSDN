import java.util.Date;

/**
 * 文章信息
 * @author QIQI 2017年3月19日 17:44:50
 *
 */
public class Article {

	//标题
	private String title;
	
	//链接地址
	private String url;
	
	//是否为置顶文章
	private boolean isTop;
	
	//是否为原创
	private boolean isCreate;
	
	//文章描述
	private String description;
	
	//发布时间
	private Date publishTime;
	
	//阅读次数
	private long readCount;
	
	//评论次数
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
