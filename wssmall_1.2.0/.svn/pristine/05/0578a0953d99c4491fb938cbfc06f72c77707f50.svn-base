package services;

import params.guest.req.GuestBookReq;
import params.guest.resp.GuestBookResp;

public interface GuestBookInf {
		
	/**
	 * 发表留言
	 * @param guestbook
	 */
	public void add(GuestBookReq guestBookReq);
	
	/**
	 * 分页读取留言列表
	 * @param keyword 关键字，可搜索标题，内容和用户名。
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public GuestBookResp list(GuestBookReq guestBookReq);

}
