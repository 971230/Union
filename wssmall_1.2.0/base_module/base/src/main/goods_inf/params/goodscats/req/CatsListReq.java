package params.goodscats.req;

import com.ztesoft.api.ApiRuleException;
import com.ztesoft.net.annotation.ZteSoftCommentAnnotationParam;

import params.ZteRequest;

/**
 * @title 查询分类信息，按会员类型查询不同的分类
 * @author zou.qinghua
 *
 */
public class CatsListReq extends ZteRequest {
	@ZteSoftCommentAnnotationParam(name="会员类型ID",type="String",isNecessary="Y",desc="会员类型ID")
	private String lv_id;
	private String parent_id;
	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parentId) {
		parent_id = parentId;
	}

	public String getLv_id() {
		return lv_id;
	}

	public void setLv_id(String lvId) {
		lv_id = lvId;
	}

	@Override
	public void check() throws ApiRuleException {
		// TODO Auto-generated method stub

	}

	@Override
	public String getApiMethodName() {
		return "com.goodsService.goodscats.list";
	}

}
