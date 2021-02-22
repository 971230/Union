package services;

import java.util.List;
import java.util.Map;

import params.comments.req.AddCommentsReq;
import params.comments.req.CleanCommentsReq;
import params.comments.req.CountCommentsReq;
import params.comments.req.DeleteCommentsReq;
import params.comments.req.GetCommentsReq;
import params.comments.req.RevertReq;
import params.comments.req.UpdateCommentsReq;
import params.comments.resp.CountCommentsResp;
import params.comments.resp.GetCommentsResp;

import com.ztesoft.net.framework.database.Page;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 17:05
 * To change this template use File | Settings | File Templates.
 */
public interface CommentsInf {
    /**
     * 取得评论、咨询，包含其子列表
     *
     * @param comment_id
     * @return CommentDTO,
     * @see com.ztesoft.net.mall.core.model.support.CommentDTO
     */
    public GetCommentsResp getComments(GetCommentsReq getCommentsReq);

    /**
     * 新增评论、咨询或其回复
     *
     * @param comments
     */
    public void addComments(AddCommentsReq addCommentsReq);

    /**
     * 修改评论、咨询或其回复，完成如[显示在页面中][删除]等操作
     *
     * @param comments
     */
    public void updateComments(UpdateCommentsReq updateCommentsReq);

    /**
     * 删除到回收站
     *
     * @param ids
     */
    public void deleteComments(DeleteCommentsReq deleteCommentsReq);

    /**
     * 将回收站清空
     *
     * @param ids
     */
    public void cleanComments(CleanCommentsReq cleanCommentsReq);

    /**
     * 从回收站中还原
     *
     * @param ids
     */
    public void revert(RevertReq revertReq);

    /**
     * 分页显示对应object_id的评论（或咨询），用于前台显示
     *
     * @param pageNo
     * @param pageSize
     * @param object_id
     * @param object_type
     * @return
     */
    public Page pageComments_Display(int pageNo, int pageSize,
                                     Long object_id, String object_type);

    /**
     * @param pageNo
     * @param pageSize
     * @param object_id
     * @param object_type
     * @param commentType
     * @return
     */
    public Page pageComments_Display(int pageNo, int pageSize,
                                     Long object_id, String object_type, String commentType);

    /**
     * 用户中心-评论或咨询
     * @param pageNo
     * @param pageSize
     * @return
     */
    public Page pageComments_Display(int pageNo, int pageSize);

    /**
     * 列表某一会员的评论或咨询
     * 具体类型（评论或咨询）由object_type指定
     * @param member_id
     * @param object_type
     * @return
     */
    public List listComments(String member_id, String object_type);

    public CountCommentsResp iCountComments(CountCommentsReq countCommentsReq);

    /**
     * 分页显示未被删除的评论（或咨询），不针对指定的object_id
     * @param pageNo
     * @param pageSize
     * @param object_type
     * @return
     */
    public Page pageComments(int pageNo, int pageSize,	String object_type);

    /**
     * 分页显示回收站内的评论（或咨询）
     * @param pageNo
     * @param pageSize
     * @param object_type
     * @return
     */
    public Page pageCommentsTrash(int pageNo, int pageSize, String object_type);


    /**
     * 计算某种对象的评价分数
     * @param commentType goods:商品
     * @param objectid 商品id或其他对象id
     * @return key:grade ,value: the grade's num<br>
     *         return empty map when not have grade
     */
    public Map coutObjectGrade(String commentType,Integer objectid);


    public Page listAll(int pageNo, int pageSize,
                        Integer object_id, String commenttype);


    public Map coutObejctNum(String commentType, Integer objectid) ;

    public Page getComment(String order_id, String goods_id);
}
