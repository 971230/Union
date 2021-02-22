package zte.net.iservice;

import params.order.req.OrderIdReq;
import params.order.resp.OrderIdResp;
import params.req.CenterMallOrderStandardReq;
import params.req.HSMallOrderStandardReq;
import params.req.NewMallOrderStandardReq;
import params.req.TaoBaoFenxiaoOrderStandardReq;
import params.req.TaoBaoMallOrderStandardReq;
import params.req.TaoBaoTianjiOrderStandardReq;
import params.req.TemplatesOrderStandardReq;
import params.req.ZJLocalMallOrderStandardReq;
import params.resp.CenterMallOrderStandardResp;
import params.resp.HSMallOrderStandardResp;
import params.resp.NewMallOrderStandardResp;
import params.resp.TaoBaoFenxiaoOrderStandardResp;
import params.resp.TaoBaoMallOrderStandardResp;
import params.resp.TaoBaoTianjiOrderStandardResp;
import params.resp.TemplatesOrderStandardResp;
import params.resp.ZJLocalMallOrderStandardResp;

import com.ztesoft.net.annotation.ZteSoftCommentAnnotation;

@ZteSoftCommentAnnotation(type="class",desc="浙江联通订单标准化管理API",summary="浙江联通订单标准化管理API[订单标准化]")
public interface IZJOrderstdService {

	@ZteSoftCommentAnnotation(type="method",desc="浙江本地商城订单标准化",summary="浙江本地商城订单标准化")
	public ZJLocalMallOrderStandardResp zjLocalMallOrderStandard(ZJLocalMallOrderStandardReq req);
}
