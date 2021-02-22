package services;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import params.comments.req.AddCommentsReq;
import params.comments.req.CleanCommentsReq;
import params.comments.req.CountCommentsReq;
import params.comments.req.DeleteCommentsReq;
import params.comments.req.GetCommentsReq;
import params.comments.req.RevertReq;
import params.comments.req.UpdateCommentsReq;
import params.comments.resp.CountCommentsResp;
import params.comments.resp.GetCommentsResp;
import services.CommentsInf;

import com.ztesoft.net.framework.database.Page;
import com.ztesoft.net.mall.core.model.support.CommentDTO;
import com.ztesoft.net.mall.core.service.ICommentsManager;

/**
 * Created with IntelliJ IDEA.
 * User: guangping
 * Date: 2013-12-26 17:05
 * To change this template use File | Settings | File Templates.
 */
public class CommentsServ implements CommentsInf {
    @Resource
    private ICommentsManager commentsManager;

    @Override
    public GetCommentsResp getComments(GetCommentsReq getCommentsReq) {
    	GetCommentsResp getCommentsResp = new GetCommentsResp();
    	CommentDTO commentDTO = this.commentsManager.getComments(getCommentsReq.getComment_id());
    	getCommentsResp.setCommentDTO(commentDTO);
        return getCommentsResp;
    }

    @Override
    public void addComments(AddCommentsReq addCommentsReq) {
        this.commentsManager.addComments(addCommentsReq.getComments());
    }

    @Override
    public void updateComments(UpdateCommentsReq updateCommentsReq) {
        this.commentsManager.updateComments(updateCommentsReq.getComments());

    }

    @Override
    public void deleteComments(DeleteCommentsReq deleteCommentsReq) {
        this.commentsManager.deleteComments(deleteCommentsReq.getIds());
    }

    @Override
    public void cleanComments(CleanCommentsReq cleanCommentsReq) {
        this.commentsManager.cleanComments(cleanCommentsReq.getIds());
    }

    @Override
    public void revert(RevertReq revertReq) {
        this.commentsManager.revert(revertReq.getIds());
    }

    @Override
    public Page pageComments_Display(int pageNo, int pageSize, Long object_id, String object_type) {
        return this.commentsManager.pageComments_Display(pageNo,pageSize,object_id,object_type);
    }

    @Override
    public Page pageComments_Display(int pageNo, int pageSize, Long object_id, String object_type, String commentType) {
        return this.commentsManager.pageComments_Display(pageNo,pageSize,object_id,object_type,commentType);
    }

    @Override
    public Page pageComments_Display(int pageNo, int pageSize) {
        return this.commentsManager.pageComments_Display(pageNo,pageSize);
    }

    @Override
    public List listComments(String member_id, String object_type) {
        return this.commentsManager.listComments(member_id,object_type);
    }

    @Override
    public CountCommentsResp iCountComments(CountCommentsReq countCommentsReq) {
    	CountCommentsResp countCommentsResp = new CountCommentsResp();
    	int count = this.commentsManager.iCountComments(countCommentsReq.getObject_type());
    	countCommentsResp.setCount(count);
        return countCommentsResp;
    }

    @Override
    public Page pageComments(int pageNo, int pageSize, String object_type) {
        return this.commentsManager.pageComments(pageNo,pageSize,object_type);
    }

    @Override
    public Page pageCommentsTrash(int pageNo, int pageSize, String object_type) {
        return this.commentsManager.pageCommentsTrash(pageNo,pageSize,object_type);
    }

    @Override
    public Map coutObjectGrade(String commentType, Integer objectid) {
        return this.commentsManager.coutObjectGrade(commentType,objectid);
    }

    @Override
    public Page listAll(int pageNo, int pageSize, Integer object_id, String commenttype) {
        return this.commentsManager.listAll(pageNo,pageSize,object_id,commenttype);
    }

    @Override
    public Map coutObejctNum(String commentType, Integer objectid) {
        return this.commentsManager.coutObejctNum(commentType,objectid);
    }

    @Override
    public Page getComment(String order_id, String goods_id) {
        return this.commentsManager.getComment(order_id,goods_id);
    }
}
