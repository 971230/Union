package services;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import params.ask.req.AskReq;
import params.ask.resp.AskResp;
import services.AskInf;
import services.ServiceBase;

import com.powerise.ibss.util.DBTUtil;
import com.ztesoft.net.app.base.core.model.Ask;
import com.ztesoft.net.app.base.core.model.Reply;
import com.ztesoft.net.eop.resource.ISiteManager;
import com.ztesoft.net.eop.resource.IUserManager;
import com.ztesoft.net.eop.resource.model.EopSite;
import com.ztesoft.net.eop.resource.model.EopUser;
import com.ztesoft.net.eop.sdk.context.EopContext;
import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.framework.util.StringUtil;

public class AskServ extends ServiceBase implements AskInf{

	private ISiteManager siteManager; 
	private IUserManager userManager;
	
	@Override
	public void add(AskReq askReq) {
		Ask ask = askReq.getAsk();
		HttpServletRequest request  = EopContext.getHttpRequest(); 
		EopSite site  = EopContext.getContext().getCurrentSite();
		EopUser user  = userManager.get(site.getUserid());
		ask.setDateline(DBTUtil.current()); //提问时间
		ask.setUserid(site.getUserid());//提问用户
		ask.setSiteid(site.getId()); 	//提问id
		ask.setIsreply(0); //新的提问标记为未回复
		ask.setSitename(site.getSitename()); //站点名称
		ask.setDomain(request.getServerName());  //域名
		ask.setUsername(user.getUsername());
		
		this.daoSupport.insert("eop_ask", ask);
	}
	
	/**
	 * 读取我的问题
	 */
	@Override
	public AskResp listMyAsk(AskReq askReq) {
		StringBuffer sql  =new StringBuffer();
		EopSite site  = EopContext.getContext().getCurrentSite();
		sql.append("select * from eop_ask where userid =");
		sql.append(site.getUserid());
		sql.append(" and siteid=");
		sql.append(site.getId());
		
		//按关键字检索
		if(!StringUtil.isEmpty(askReq.getKeyword())){
			sql.append(" and  ");
			sql.append("(");
			
			//检索标题
			sql.append("title like '%");
			sql.append(askReq.getKeyword());
			sql.append("%'");
			
			//检索内容
			sql.append(" or content like '%");
			sql.append(askReq.getKeyword());
			sql.append("%'");
			
			
			sql.append(")");
		}
		//开始时间
		if(askReq.getStartTime()!=null){
			sql.append(" and dateline>=");
			sql.append( askReq.getStartTime().getTime()/1000  );
		}
		
		//结束时间
		if(askReq.getEndTime()!=null){
			sql.append(" and dateline<=");
			sql.append( askReq.getEndTime().getTime()/1000  );
		}		
		
		//按是否已读正序排序、按时间倒序排序
		sql.append(" order by isreply asc,dateline desc");
		
		AskResp askResp = new AskResp();
		Page page = this.daoSupport.queryForPage(sql.toString(), askReq.getPageNo(), askReq.getPageSize());
		askResp.setAskPage(page);
		return askResp;
	}


	@Override
	public AskResp get(AskReq askReq) {
		//读取此问题的数据
		AskResp askResp = new AskResp();
		String sql  ="select * from eop_ask where askid = ?";
		Ask ask  = (Ask)this.daoSupport.queryForObject(sql, Ask.class, askReq.getAskid()); 
		
		//读取此问题的回复列表
		sql ="select * from eop_reply where askid=? order by dateline asc";
		List replylist  = this.daoSupport.queryForList(sql, Reply.class, askReq.getAskid());
		ask.setReplyList(replylist);
		askResp.setAsk(ask);
		return askResp;
	}


	public IUserManager getUserManager() {
		return userManager;
	}


	public void setUserManager(IUserManager userManager) {
		this.userManager = userManager;
	}
	
	public ISiteManager getSiteManager() {
		return siteManager;
	}

	public void setSiteManager(ISiteManager siteManager) {
		this.siteManager = siteManager;
	}
}
