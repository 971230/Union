package services;

import params.attention.req.AttentionReq;
import params.attention.resp.AttentionResp;

/**
 * 关注service
 * 
 * @作者 wu.i
 * @创建日期 2013-9-23
 * @版本 V 1.0
 *	
 * 
 */
public interface AttentionInf {
	
	public AttentionResp addFav(AttentionReq attentionReq);
	
	/**
	 * 添加商品关注
	 * @param json
	 */
	public AttentionResp addGoodsFav(AttentionReq attentionReq);
	
	
	/**
	 * 添加供货商关注
	 * @param json
	 */
	public AttentionResp addSupplerFav(AttentionReq attentionReq);
	
	

	public AttentionResp queryPartnerFav(AttentionReq attentionReq);
	
	public AttentionResp queryGoodsFav(AttentionReq attentionReq);
	
	public AttentionResp querySupplerFav(AttentionReq attentionReq);
	
	public AttentionResp delete(AttentionReq attentionReq);
	
	

}