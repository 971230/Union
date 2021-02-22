package zte.net.iservice;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;
import zte.params.addr.req.*;
import zte.params.addr.resp.*;

@ZteSoftCommentAnnotation(type="class",desc="会员管理API",summary="会员管理API[查询用户常用地址、添加会员常用地址、修改用户常用地址信息、删除常用地址]")
public interface IMemberAddressService {

	/**
	 * 查询用户常用地址
	 * @作者 MoChunrun
	 * @创建日期 2014-1-10 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询用户常用地址",summary="查询用户常用地址列表")
	public MemberAddressListResp listMemberAddress(MemberAddressListReq req);
	
	/**
	 * 按地址ID查询用户地址
	 * @作者 MoChunrun
	 * @创建日期 2014-1-10 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="查询用户常用地址",summary="按地址ID查询用户常用地址列表")
	public MemberAddressGetResp getMemberAddress(MemberAddressGetReq req);
	
	/**
	 * 添加会员常用地址
	 * @作者 MoChunrun
	 * @创建日期 2014-1-10 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="添加会员常用地址",summary="添加会员常用地址")
	public MemberAddressAddResp addMemeberAddress(MemberAddressAddReq req);
	
	/**
	 * 修改用户常用地址信息
	 * @作者 MoChunrun
	 * @创建日期 2014-1-10 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="修改用户常用地址",summary="修改用户常用地址信息")
	public MemberAddressEditResp editMemberAddress(MemberAddressEditReq req);
	
	/**
	 * 删除常用地址
	 * @作者 MoChunrun
	 * @创建日期 2014-1-10 
	 * @param req
	 * @return
	 */
	@ZteSoftCommentAnnotation(type="method",desc="删除用户常用地址",summary="删除用户常用地址信息")
	public MemberAddressDeleteResp deleteMemberAddress(MemberAddressDeleteReq req);
	
	
}
