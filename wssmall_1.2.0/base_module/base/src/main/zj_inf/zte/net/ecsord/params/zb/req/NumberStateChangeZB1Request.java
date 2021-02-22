package zte.net.ecsord.params.zb.req;

import java.util.List;

import zte.net.ecsord.params.zb.vo.ResourcesInfo;
/**
 * 资源释放专用
 * @author Rapon
 *
 */
public class NumberStateChangeZB1Request extends NumberStateChangeZBRequest{
	
	public List<ResourcesInfo> getResourcesInfo() {
		return resourcesInfo;
	}

	public void setResourcesInfo(List<ResourcesInfo> resourcesInfo) {
		this.resourcesInfo = resourcesInfo;
	}
}
