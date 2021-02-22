<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.ztesoft.net.mall.core.service.impl.cache.DcPublicInfoCacheProxy"%>
<%@page import="com.ztesoft.net.framework.context.spring.SpringContextHolder"%>
<%@page import="com.ztesoft.net.mall.core.service.IDcPublicInfoManager"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.params.busi.req.OrderTreeBusiRequest"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String order_id = request.getParameter("order_id");
	String hide_div = request.getParameter("hide_div");
	
	//CommonDataFactory.getInstance().updateOrderTree(order_id);
	OrderTreeBusiRequest orderTree = CommonDataFactory.getInstance().getOrderTree(order_id);
	String trace_id =orderTree.getOrderExtBusiRequest().getFlow_trace_id();
	String data_stype = com.ztesoft.ibss.common.util.Const.ORDER_HANDLER_TYPE_DIC_HANDLE_TYPE+"_"+trace_id;
	IDcPublicInfoManager dcPublicInfoManager = SpringContextHolder.getBean("dcPublicInfoManager");
	DcPublicInfoCacheProxy dcPublicCache = new DcPublicInfoCacheProxy(dcPublicInfoManager);
    List<Map> list = dcPublicCache.getList(data_stype);
    request.setAttribute("handler_types", list);
    request.setAttribute("hide_div", hide_div);
%>
    <div class="grid_n_div">     
	    <h2><a href="#" class="closeArrow"></a>路由信息</h2>
		<div class="grid_n_cont">
			<div class="grid_n_cont_sub">
          		<div class="open_more_div"><a href="javascript:void(0);" id="sign_a" class="open_more"><span id="sign_show">展开发货路由信息</span><span id="sign_hide"  style="display: none;">收起发货路由信息</span><i class="arr"></i></a>    
          		</div>
          		<div id="context_sign">	   
          	  </div>  
      		</div>
      		<div class="grid_n_cont_sub">   
          		<div class="open_more_div"><a href="javascript:void(0);" id="receipt_a" class="open_more"><span id="receipt_show">展开回单路由信息</span><span id="receipt_hide"  style="display: none;">收起回单路由信息</span><i class="arr"></i></a>    
          		</div>
          		<div id="context_receipt"> 		   
           		</div>  
      		</div> 	
     	</div>	
	</div>
<script type="text/javascript">
	$("#sign_show").bind("click",function(){		
			$.ajax({
				url:ctx+"/shop/admin/orderFlowAction!showSignRoutes.do?ajax=yes&order_id=<%=order_id%>",
				async : false,
				dataType:"html",
				type:"post",
				success:function(data){
					$("#context_sign").empty().append(data);
					$("#sign_show").hide();
					$("#sign_hide").show()
				}
			});
		});
		$("#sign_hide").click(function(){
	    	$("#context_sign").empty();
	    	$("#sign_show").show();
			$("#sign_hide").hide()
	    });
				
		$("#receipt_show").bind("click",function(){		
			$.ajax({
				url:ctx+"/shop/admin/orderFlowAction!showReceiptRoutes.do?ajax=yes&order_id=<%=order_id%>",
				async : false,
				dataType:"html",
				type:"post",
				success:function(data){
					$("#context_receipt").empty().append(data);
					$("#receipt_show").hide();
					$("#receipt_hide").show()
				}
			});
		});
		$("#receipt_hide").click(function(){
	    	$("#context_receipt").empty();
	    	$("#receipt_show").show();
			$("#receipt_hide").hide()
	    });
	    
</script>
