package zte.net.iservice;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="导入管理API",summary="导入管理API",isOpen=false)
public interface IImportService {
	@ZteSoftCommentAnnotation(type="method",desc="修改中间表数据状态",summary="修改中间表数据状态",isOpen=false)
	public void modifyMidDataStatus(String log_id,String id,String status,String desc);
}
