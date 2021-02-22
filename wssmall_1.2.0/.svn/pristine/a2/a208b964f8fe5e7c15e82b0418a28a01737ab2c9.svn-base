package services;

import params.tags.req.TagReq;
import params.tags.resp.TagResp;

/**
 * 标签类service
 * 
 * @作者 wu.i
 * @创建日期 2013-9-23
 * @版本 V 1.0
 *	
 * 举例
 * TagReq tagReq = new TagReq("50");
   String json = CommonTools.beanToJson(tagReq);
   String str = HttpUtil.readPostAsString(URL+"TagsServ/queryAllTagsGoods", "param_json="+json, "utf-8", "utf-8", 100000, 100000);
   TagResp tagResp = CommonTools.jsonToBean(str, TagResp.class);
 */
public interface TagsInf {
	/**
	 * 查询标签信息所有商品
	 * @param json
	 */
	public TagResp queryAllTagsGoods(TagReq tagReq);
	
	public TagResp queryTagsGoodsByTagId(TagReq tagReq);

}