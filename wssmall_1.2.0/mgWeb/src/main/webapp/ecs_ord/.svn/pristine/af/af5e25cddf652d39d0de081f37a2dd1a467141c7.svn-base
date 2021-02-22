<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源释放管理</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>
<div class="gridWarp">
	<div class="new_right">
	<form method="post" id="serchform" action='<%=request.getContextPath() %>/shop/admin/ordAuto!showReleaseList.do'>
	 <div class="searchformDiv">
			<!-- 选择框 -->
		  <jsp:include page="release_record_param.jsp"/>
	</div>
	</form>
	<div class="right_warp" id="goodslist">
     <form method="POST"  id="release_record_list"  >      
         <grid:grid from="webpage" formId="serchform" >
			<grid:header>
				<grid:cell></grid:cell>
				<grid:cell>订单信息</grid:cell>
				<grid:cell>预占信息</grid:cell>
				<grid:cell>处理结果</grid:cell>
				<grid:cell>最新处理信息</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>
		    <grid:body item="record">
		  	    <grid:cell style="width:5px">
		  	    </grid:cell>
		  	    <grid:cell >		  	    
  	    			<div class="order_pri">
	              	<p class="tit">内部订单号：${record.order_id }</p>
	                <p class="ps">操作类型：<html:selectdict curr_val="${record.type}" attr_code="DC_RELEASE_FAIL_INF" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:10px; background:url(${context}/images/ipt.gif) repeat-x;width:110px;" disabled="true"></html:selectdict>
	              </div>	
		  	    </grid:cell>
		  	    <grid:cell>
  	    			<div class="order_pri">
              			<p class="tit">终端串号/号码：${record.serial_or_num }</p>
		                  <p class="ps">预占工号：${record.occupied_essid }</p>
		                  <p class="ps">预占关键字：${record.prokey }</p>
		              </div>	
		  	    </grid:cell>
		  	    <grid:cell style="width:300px;">		  	    
  	    			<div class="list_t">
		              	<p class="tit">${record.deal_desc }</p>
		              </div>	
		  	    </grid:cell>
		  	    <grid:cell>	  	    
  	    			<div class="list_t">
                        <p class="ps">首次失败时间：${record.create_time }</p>
                    	<p class="ps">最后操作人：${record.deal_username }</p>
                        <p class="ps">最后操作时间1：${record.deal_time }</p>
                    </div>	
                </grid:cell>
		  	    <grid:cell clazz="alignCen" >
		  	    	<div class="order_pri">
			  	    <c:if test="${record.is_deal=='0' }">
				  	    <p class="tit"><a title="不需要调接口释放的资源，请手工标记完成" name="released" class="graybtn1" release_id="${record.release_id }" href="javascript:void(0);">标记完成</a></p>
				  	    <p class="tit"><a title="调接口释放资源" name="released_by_intf" class="graybtn1" release_id="${record.release_id }" href="javascript:void(0);">现在释放</a></p>
			  	    </c:if>
			  	    </div>
		  	    </grid:cell>
		  	</grid:body>
		</grid:grid>
	</form>
 </div>
        
       <div class="clear"></div>
     </div>
  </div>
</div>


</body>
</html>
<script type="text/javascript">
function check() {
 	
	return true;
}

$(function(){
	$("a[name=released]").bind("click",function(){
		if(window.confirm("确定已经释放完成?")){
			var $this = $(this);
			var release_id = $(this).attr("release_id");
			var url = ctx+"/shop/admin/ordAuto!releaseRecord.do?ajax=yes&release_id="+release_id;
		
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				alert(data.message);
				if(data.result=='1'){
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!showReleaseList.do");
					$("#serchform").submit();
				}
			},'json');
		}
	});
	$("a[name=released_by_intf]").bind("click",function(){
		if(window.confirm("确定现在释放?")){
			var $this = $(this);
			var release_id = $(this).attr("release_id");
			var url = ctx+"/shop/admin/ordAuto!releaseRecordByI.do?ajax=yes&release_id="+release_id;
		
			Cmp.ajaxSubmit('serchform','',url,{},function(data){
				alert(data.message);
				if(data.result=='1'){
					$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!showReleaseList.do");
					$("#serchform").submit();
				}
			},'json');
		}
	});
});

$(function(){
	$("#release_record_list div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
	$("#release_record_list .page").wrap("<form class=\"grid\"></form>");
	$("#searchBtn").click(function(){
		if (check()) {
			$("#serchform").attr("action",ctx+"/shop/admin/ordAuto!showReleaseList.do");
			$("#serchform").submit();
		}
	});	
});
</script>
 