<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%@ include file="/shop/admin/public/public.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>异常订单管理</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/calendar.js"></script>
</head>
<body>

<div class="gridWarp">
	<div class="new_right">
		<!-- 选择框 -->
		<form action="" method="post" id="order_exp_param_f">
		 <input type="hidden" name="btnType" value="ordList"/>
		<jsp:include page="exp_params.jsp"/>
        </form>
        <div class="right_warp">
        <form action="" id="order_exp_list_fm" class="grid_w" method="post">
        	<grid:grid from="webpage" formId="order_exp_param_f">
				<grid:header>
					<grid:cell style="text-align:center; width:60px;">
						<input type="checkbox" id="selAllCheckBox" name="selAllCheckBox"/>
					</grid:cell>
					<grid:cell style="width:220px;">基本信息</grid:cell>
					<grid:cell style="width:250px;">处理信息</grid:cell>
					<grid:cell style="width:330px;">异常信息</grid:cell>
					<grid:cell width="10%">操作</grid:cell>
				</grid:header>
				<grid:body item="orderExp">
			  	    <grid:cell clazz="alignCen" >
			  	    	<c:if test="${orderExp.rel_obj_type eq 'order' }">
			  	    		<i title="${orderExp.lock_user_name }" class="${orderExp.lock_clazz }"></i>
			  	    	</c:if>
			  	    	 <input type="checkbox" name="eckBox" value="${orderExp.excp_inst_id }" record_status="${orderExp.record_status }" is_batch_process="${orderExp.is_batch_process }"/>
			  	    	 <c:if test="${orderExp.warming_flag == 'Y' && orderExp.warming_time > 0}">
			  	    	 	<div title="已超出预警时间${orderExp.warming_time }小时"  class="warning_org"></div>
			  	    	 </c:if>
			  	    </grid:cell>
			        <grid:cell>
			        	<div class="list_t">
	                        	<ul>
		                        	<li>
		                           		<span style="text-align: left;">
		                           			外部单号：
		                           		</span>
		                           		<div class="dddd">
		                          				<c:if test="${orderExp.rel_obj_type == 'order'}">
												<a name="inner_order_id" order_id="${orderExp.rel_obj_id }" href="javascript:void(0);" detail_url="${orderExp.action_url }">
		                           					${orderExp.out_tid }
		                           				</a>
											</c:if>
		                          				<c:if test="${orderExp.rel_obj_type == 'order_queue'}">
		                          					${orderExp.out_tid }
		                          				</c:if>
		                           		</div>
		                           	</li>
		                           	<li>
	                            		<span style="text-align: left;">所属地市：</span>
	                            		<div class="order_pri">
	                            			<p class="ps">${orderExp.order_city }</p>
	                            		</div>
                            		</li>
                            		<li>
	                            		<span style="text-align: left;">订单来源：</span>
	                            		<div class="order_pri">
						                	<p class="ps">${orderExp.order_from_name }</p>
										</div>
                            		</li>
                            		<li>
	                            		<span style="text-align: left;">生产模式：</span>
	                            		<div class="order_pri">
						                	<p class="ps">${orderExp.order_mode_name }</p>
										</div>
                            		</li>
                            		<li>
	                            		<span style="text-align: left;">异常环节：</span>
	                            		<div class="order_pri">
						                	<p class="ps">${orderExp.tache_code_c }</p>
										</div>
                            		</li>
	                            </ul>
	                        </div>
			        </grid:cell>
			         <grid:cell style="width:100px;">
			        	<div class="order_pri">
                        	<p class="tit">
                        		<c:if test="${orderExp.record_status == '0' || orderExp.record_status == null}">
                            		未处理
                            	</c:if>
								<c:if test="${orderExp.record_status == '1'}">已处理</c:if>
							</p>
                       		<p class="tit">
                       			<c:if test="${orderExp.rel_obj_type == 'order'}">订单异常</c:if>
                           		<c:if test="${orderExp.rel_obj_type == 'order_queue'}">收单异常</c:if>
                       		</p>
                       		<c:if test="${not empty orderExp.lock_user_name }">
                            	<p class="ps">锁单人：${orderExp.lock_user_name }</p>
                       		</c:if>
                       		<p class="ps">开户号码：${orderExp.phone_num}</p>                           	
                           	<p class="ps">订单创建时间：<html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${orderExp.rel_obj_create_time}"></html:dateformat></p>
                        </div>
			       </grid:cell>
			        <grid:cell>
                		<div class="order_pri">
                        	<p class="tit"><c:out value="${orderExp.match_content}" /></p>                		
                        	<p class="ps">搜索编码：${orderExp.search_code }</p>                       		
                       		<p class="ps">异常编号：${orderExp.excp_inst_id }</p>
                       		<p class="ps">异常创建时间：<html:dateformat pattern="yyyy-MM-dd HH:mm:ss" d_time="${orderExp.excp_create_time}"></html:dateformat></p>
                        </div>
			        </grid:cell> 
			       <grid:cell style="width:400px;">
			       		<ul>
			       			<li>
					       		<c:choose>
					       			<c:when test="${orderExp.catalog_id == null && orderExp.record_status == '0'}">
					       				<a href="javascript:void(0)" onclick="OrderExp.showSpecvaluesClassify('${orderExp.key_id}');" excp_inst_id="${orderExp.excp_inst_id}">
				                       		归类
					      	            </a>
					       			</c:when>
					       			<c:otherwise>
					       				归类
					       			</c:otherwise>
					       		</c:choose>
			      	            <%-- <span class='tdsper'></span>
			      	            <a href="javascript:void(0)" name="market_enable_action" excp_inst_id="${orderExp.excp_inst_id}">
			                    	核查
			      	            </a>
			      	            <br/>
			      	            <br/>
								<a href="javascript:void(0)" name="market_enable_action" excp_inst_id="${orderExp.excp_inst_id}">
			                    	校验
			      	            </a> --%>
			      	            <span class='tdsper'></span>
			      	            
		                        <c:choose>
					       			<c:when test="${orderExp.record_status == '0' && orderExp.solution_id != null && (orderExp.solution_type == 'PLAN' || orderExp.solution_type == 'RULE' || orderExp.solution_type == 'SQL')}">
					       				<a href="javascript:void(0)" onclick="OrderExp.showOrderExpProcess('${orderExp.excp_inst_id}');" excp_inst_id="${orderExp.excp_inst_id}">
				                    		处理
				      	            	</a>
					       			</c:when>
					       			<c:when test="${orderExp.record_status == '0' && orderExp.solution_id != null && (orderExp.solution_type == 'URL' || orderExp.solution_type == 'DEFAULT')}">
					       				<a href="javascript:void(0)" onclick="OrderExp.redirectUrl(this);" solution_value="${orderExp.solution_value }" rel_obj_id="${orderExp.rel_obj_id }">
				                    		处理
				      	            	</a>
					       			</c:when>
					       			<c:otherwise>
					       				处理
					       			</c:otherwise>
					       		</c:choose>
					       	</li>
					       	<li>
		               			<a href="javascript:void(0);" onclick="OrderExp.showOrderExpInstDetail('${orderExp.excp_inst_id }');" name="market_enable_action" excp_inst_id="${orderExp.excp_inst_id}">
		               				查看详情
		               			</a>
		      	            	<span class='tdsper'></span>
		      	            	<a href="javascript:void(0)" onclick="OrderExp.showExpInfLogDetail('${orderExp.log_id }');">
								   	查看报文
							    </a>	
							</li>
                        	<c:if test="${orderExp.solution_id != null && orderExp.solution_id != ''}">
                        		<li>
		      	            	<a href="javascript:void(0)" onclick="OrderExp.showSolutionDetail('${orderExp.solution_id}','${orderExp.rel_obj_id }');" excp_inst_id="${orderExp.excp_inst_id}">
		                    		查看解决方案
		      	            	</a>
		      	            	</li>
	      	            	</c:if>
	      	            </ul>
			        </grid:cell>
			  </grid:body>
	      	</grid:grid>
        </form>
     </div>
  </div>
</div>
<div id="esearchSpecListDialog"></div>
<div id="esearchCatalogListDialog"></div>
<div id="orderExpMarkProcessedDialog"></div>
<div id="orderExpProcessDialog"></div>
<div id="orderExpInstDetailDialog"></div>
<div id="orderExpInfLogDetailDialog"></div>
<div id="solutionDetailDialog"></div>
<div id="specvaluesClassifyDialog"></div>
<script src="<%=request.getContextPath()%>/shop/admin/orderexp/js/order_exp.js?time=<%=new Date().getTime() %>"></script>
<script type="text/javascript">
$(function(){
	$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
	$("#order_exp_list_fm div table").addClass("grid_w").attr("width","100%").attr("border","0").attr("cellspacing","0").attr("cellpadding","0");
	$("#order_exp_list_fm .page").wrap("<form class=\"grid\"></form>");
	$("#order_exp_list_fm div table tbody tr").unbind("click").bind("click",function(){
		var $this = $(this);
		var obj = $this.find("div.dddd").children("a");
		
		$this.siblings("tr").removeClass("curr");
		$this.addClass("curr");
		if(true == $this.find("input[type='checkbox']").attr("checked")) {
			$this.find("input[type='checkbox']").removeAttr("checked");
		}else {
			$this.find("input[type='checkbox']").attr("checked","checked");
		}
	});
	$("#selAllCheckBox").bind("click",function(){
		var $this = $(this);
		if(true == $this.attr("checked")) {
			$("input[type='checkbox']").attr("checked","checked");
		}else {
			$("input[type='checkbox']").removeAttr("checked");
		}
	});
	//查看订单详细
	$("a[name=inner_order_id]").bind("click",function(){		
		var order_id = $(this).attr("order_id");
		var o_url = OrderExp.getActionUrl(order_id,"DEFAULT");
		window.location.href=o_url;
		
	});
});
OrderExp.init();

//归类完成回调方法
function after_update_catalog(){
	alert("归类成功！");
	Eop.Dialog.close("specvaluesClassifyDialog");
	window.location.reload();
}
</script>
</body>
</html>