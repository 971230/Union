package services;

import com.ztesoft.net.app.base.core.model.SiteMenu;
import com.ztesoft.net.app.base.core.service.ISiteMenuManager;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import params.site.req.SiteMenuReq;
import params.site.resp.SiteMenuResp;

import javax.annotation.Resource;
import java.util.List;

public class SiteMenuServ extends ServiceBase implements SiteMenuInf{
	
	@Resource(name="siteMenuManagerImpl")
	private ISiteMenuManager siteMenuManager;
	
	@Override
	public void add(SiteMenuReq siteMenuReq) {
		siteMenuManager.add(siteMenuReq.getSiteMenu());
	}
	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public void delete(SiteMenuReq siteMenuReq) {
		siteMenuManager.delete(siteMenuReq.getId());
	}
	
	@Override
	public void edit(SiteMenuReq siteMenuReq) {
		siteMenuManager.edit(siteMenuReq.getSiteMenu());
	}

	@Override
	public SiteMenuResp list(SiteMenuReq siteMenuReq) {
		SiteMenuResp siteMenuResp = new SiteMenuResp();
		List<SiteMenu> topMenuList = siteMenuManager.list(siteMenuReq.getParentid());
		siteMenuResp.setList(topMenuList);
		return siteMenuResp;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	
	public void updateSort(SiteMenuReq siteMenuReq) {
		siteMenuManager.updateSort(siteMenuReq.getMenuids(), siteMenuReq.getSort());
	}
	
	@Override
	public SiteMenuResp get(SiteMenuReq siteMenuReq) {
		
		SiteMenuResp siteMenuResp = new SiteMenuResp();
		SiteMenu siteMenu = siteMenuManager.get(siteMenuReq.getMenuid());
		siteMenuResp.setSiteMenu(siteMenu);
		return siteMenuResp;
	}
}