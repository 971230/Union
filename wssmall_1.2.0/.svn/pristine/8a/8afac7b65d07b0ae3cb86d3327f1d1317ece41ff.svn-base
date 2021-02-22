package params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

/**
 * @title 查询分类信息，按会员类型查询不同的分类
 * @author zou.qinghua
 *
 */
public class ChildCatsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="分类父ID",type="String",isNecessary="Y",desc="分类父ID")
	private String parent_id;
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parentId) {
		parent_id = parentId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodscats.childCatslist";
	}

}
