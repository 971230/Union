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
	String is_his_order = request.getParameter("is_his_order");
	
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
    <div class="grid_n_div">
    	<div class="grid_n_cont_sub">
              
	    <h2><a href="#" class="closeArrow"></a>历史跟踪</h2>
		<div class="grid_n_cont">
		
		<div class="grid_n_cont_sub">
          	<div class="open_more_div"><a href="javascript:void(0);" id="logs_hide_a" class="open_more"><span>展开所有步骤信息</span><i class="arr"></i></a>
          	</div>
          	<div id="context_bd">
  		   
            </div>  
      	</div>
      	
		<div class="grid_n_cont_sub">
  		   
          	<div class="open_more_div"><a href="javascript:void(0);" id="exception_a" class="open_more"><span id="exp_show">展开异常跟踪信息</span><span id="exp_hide"  style="display: none;">收起异常跟踪信息</span><i class="arr"></i></a>
          	</div>
          	<div id="context_exception">
  		   
            </div>  
      	</div>
      	<div class="grid_n_cont_sub">
  		   
          	<%-- <div class="open_more_div"><a href="javascript:void(0);" id="sds_logs_a" class="open_more"><span id="sds_logs_show">展开闪电送日志</span><span id="sds_logs_hide"  style="display: none;">收起闪电送日志</span><i class="arr"></i></a>
          	</div>
          	<div id="context_sds">--%>
  		   
            </div>  
      	</div>
		<c:if test="${hide_div!='handle_input_area' }"> 
                  <div class="grid_n_div">
			    <div class="grid_n_div">
					<div class="grid_n_cont">
			  		<div class="grid_n_cont_sub">
			              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="blue_grid">
			                <tr>
			                     <th style="text-align: right"><span style="color:red;">*</span> 备注：</th>
			                     <td><textarea id="node_deal_message" name="dealDesc" cols="45" rows="5" class='ipt_new' style="height:100px;width:400px;"></textarea></td>
			                   </tr>
			              </tbody></table>
			      	</div>
			   		</div>
				</div>
			</div>
                   
                 <!-- <h3>当前处理：</h3>
              <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                  <tbody>
                    <tr>
                     <th>备注：</th>
                     <td style="width:90%;"><textarea id="node_deal_message" name="dealDesc" cols="45" rows="5" class='ipt_new' style="height:100px;width:200px;"></textarea></td>
                   </tr> -->
             <!-- </tbody>
             
             </table> -->
            </c:if>
     		</div>
     		
  		
   		</div>
	</div>
</div>
<br />
<br />
<script type="text/javascript">
	$(function(){
		//加载处理日志
		$.ajax({
			url:ctx+"/shop/admin/ordAuto!queryOrderHandlerLogs.do?ajax=yes&is_his_order=<%=is_his_order%>&order_id=<%=order_id%>",
			async : false,
			dataType:"html",
			type:"post",
			success:function(data){
				$("#context_bd").empty().append(data);
			}
		});
		
		$("#logs_hide_a").bind("click",function(){
			var trs = $("tr[name=hidetr]");
			if(trs.is(":visible")){
				trs.hide();
				$(this).find("span").html("展开所有步骤信息");
			}else{
				trs.show();
				$(this).find("span").html("收起所有步骤信息");
			}
		});
		
		
		$("#exp_show").bind("click",function(){
			$.ajax({
				url:ctx+"/shop/admin/ordAuto!queryOrderExceptionLogs.do?ajax=yes&order_id=<%=order_id%>&trace_id=<%=trace_id%>",
				async : false,
				dataType:"html",
				type:"post",
				success:function(data){
					$("#context_exception").empty().append(data);
					$("#exp_show").hide();
					$("#exp_hide").show()
				}
			});
		});
		$("#exp_hide").click(function(){
	    	$("#context_exception").empty();
	    	$("#exp_show").show();
			$("#exp_hide").hide()
	    });
		
		
		
		
	});
</script>
