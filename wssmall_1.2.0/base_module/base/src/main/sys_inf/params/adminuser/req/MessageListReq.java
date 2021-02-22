package params.adminuser.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;
import params.ZteRequest;


/**
 * 消息列表查询实体
 * @author hu.yi
 * @date 2013.12.23
 */
public class MessageListReq extends ZteRequest{
	@ZteSoftCommentAnnotationParam(name="区分收件箱和发件箱的信息",type="Integer",isNecessary="Y",desc="num：区分收件箱和发件箱的信息， 不能为空。")
	private Integer num;			//1是收件箱，2是发件箱
	@ZteSoftCommentAnnotationParam(name="收件人状态",type="Integer",isNecessary="N",desc="reciverState：收件人状态。")
	private Integer reciverState; 
	@ZteSoftCommentAnnotationParam(name="发件人状态",type="Integer",isNecessary="N",desc="SendState：发件人状态。")
	private Integer senderState;
	@ZteSoftCommentAnnotationParam(name="消息主题",type="String",isNecessary="N",desc="topic：消息主题。")
	private String topic;
	@ZteSoftCommentAnnotationParam(name="开始时间",type="String",isNecessary="N",desc="statetime：开始时间。")
	private String starttime;
	@ZteSoftCommentAnnotationParam(name="结束时间",type="String",isNecessary="N",desc="endtime：结束时间。")
	private String endtime; 
	@ZteSoftCommentAnnotationParam(name="消息类型",type="String",isNecessary="N",desc="type：消息类型。")
	private String type;
	@ZteSoftCommentAnnotationParam(name="分页信息",type="String",isNecessary="Y",desc="pageNo：分页信息。")
	private Integer pageNo;
	@ZteSoftCommentAnnotationParam(name="每页记录数",type="String",isNecessary="Y",desc="pageSize：每页记录数。")
	private Integer pageSize;
	@ZteSoftCommentAnnotationParam(name="用户id",type="String",isNecessary="Y",desc="user_id：用户Id。")
	private String user_id;
	
	
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getReciverState() {
		return reciverState;
	}
	public void setReciverState(Integer reciverState) {
		this.reciverState = reciverState;
	}
	public Integer getSenderState() {
		return senderState;
	}
	public void setSenderState(Integer senderState) {
		this.senderState = senderState;
	}
	public String getTopic() {
		return topic;
	}
	public void setTopic(String topic) {
		this.topic = topic;
	}
	public String getStarttime() {
		return starttime;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public String getEndtime() {
		return endtime;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	@Override
	public void check() throws ApiRuleException {
		if(null == pageNo || null == pageSize){
			throw new ApiRuleException("-1","缺失参数");
		}
	}
	@Override
	public String getApiMethodName() {
		return "messageServ.listMsg";
	}
	
}
