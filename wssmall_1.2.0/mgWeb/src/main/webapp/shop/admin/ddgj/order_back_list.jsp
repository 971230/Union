<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="grid">
	<form method="POST">
		<grid:grid from="webpage">
			<grid:header>
				<grid:cell>选择</grid:cell>
				<grid:cell sort="order_apply_id">申请单号</grid:cell>
				<grid:cell sort="a_order_item_id">订单号</grid:cell>
				<grid:cell sort="apply_state">状态</grid:cell>
				<grid:cell sort="member_name">会员名称</grid:cell>
				<grid:cell sort="create_time">申请时间</grid:cell>
			</grid:header>
			<grid:body item="orderApply">
				<grid:cell><input type="radio" name="apply_id" order_id="${orderApply.order_id}" apply_id="${orderApply.order_apply_id }" service_type="${service_type }"/></grid:cell>			
				<grid:cell>${orderApply.order_apply_id } </grid:cell>
				<grid:cell><a href="javascript:void(0);" name="order_detail_apply">${orderApply.order_id}</a> </grid:cell>
				<grid:cell> 
				 <c:if test="${orderApply.apply_state == 0 }">未审核</c:if>
				 <c:if test="${orderApply.apply_state == 1 }">审核通过</c:if>
				 <c:if test="${orderApply.apply_state == 2 }">已拒绝</c:if>
				 <c:if test="${orderApply.apply_state == 3 }">已确认退货</c:if>
				 <c:if test="${orderApply.apply_state == 4 }">已确认退货</c:if>
				 <c:if test="${orderApply.apply_state == 5 }">已确认退费</c:if>
				 <c:if test="${orderApply.apply_state == 6 }">已确认退费</c:if>
				 <c:if test="${orderApply.apply_state == 7 }">已确认换货</c:if>
				 <c:if test="${orderApply.apply_state == 8 }">已确认换货</c:if>
				</grid:cell>
				<grid:cell>${orderApply.memeber_name} </grid:cell>
				<grid:cell>${orderApply.create_time} </grid:cell>
				<%-- <grid:cell>
<!-- 				<a href="javascript:void(0);" staff_no="" class="showSupplierOrder">查看详情</a>&nbsp;&nbsp; -->
				<c:if test="${orderApply.apply_state==0}">
				<c:if test="${isAdminUser==1}">
				   <a  class="auditName" order_apply_id="${orderApply.order_apply_id }" good_name="${orderApply.good_name}">审批</a>
				</c:if>
				</c:if>
				</grid:cell> --%>
				
			</grid:body>
		</grid:grid>
	</form>
</div>

<div id="orderApply_dialog"></div>
<script>
	var OrderApply=$.extend({},Eop.Grid,{
	    wrapper_id:'orderApply_dialog', //弹出页面id
		form_id:'auditParform',//弹出页面表单id
	    init:function(){
	        Eop.Dialog.init({id:this.wrapper_id,modal:false,title:'申请单审核',width:'850px'});
			//电信员工审核
			$(".auditName").click(function(){
	             //alert(111);
			     var order_apply_id=$(this).attr("order_apply_id");
			     var good_name=$(this).attr("good_name");
			     var service_type=$("#service_type").val();
	             var url="orderApply!getTails.do?ajax=yes&order_apply_id="+order_apply_id;
	                Eop.Dialog.open("orderApply_dialog");
					$("#orderApply_dialog").load(url,function(){
						$("#auditParform #auditSubmitBtn").unbind("click");
						$("#auditParform #auditSubmitBtn").bind("click",function(){
	                        if($("#audit_idea").val()==""){
				              alert("审核描述不能为空！");
				              return false;
				           }
				           if($("#state").val()==""){
				              alert("请选择处理！");
				              return false;
				           }
				      
							var url ="orderApply!updateOrderApplyState.do?ajax=yes&order_apply_id="+order_apply_id;
							Cmp.ajaxSubmit('auditParform', '', url, {}, function(responseText){
							        
									if (responseText.result == 1) {
										alert(responseText.message);
										window.location=app_path+'/shop/admin/orderApply!list.do?service_type='+service_type;
									}
									if (responseText.result == 0) {
										 alert(responseText.message);
									}
									Eop.Dialog.close("orderApply_dialog");
									
							},'json');
							//self.audit();
							OrderApply.onAjaxCallBack();
							return  false;
						});
					});
				
	             return false;
			});
	    
	    },
	    onAjaxCallBack:function(){//ajax回调函数
		    
			$('input.dateinput').datepicker();
		}
	});
	
	
	$(function(){
	    OrderApply.init();
	  
	});
</script>