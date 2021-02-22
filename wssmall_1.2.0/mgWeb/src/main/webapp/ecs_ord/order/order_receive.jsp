<%@ page contentType="text/html;charset=UTF-8"%>

<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>订单归集</title>
<script src="<%=request.getContextPath()%>/ecs_ord/js/autoord.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/preDealOrder.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/WdatePicker.js"></script>
<script src="<%=request.getContextPath()%>/ecs_ord/js/calendar.js"></script>
<style type="text/css">
.red {
	color: red;
}
</style>
</head>
<body>

	<div class="gridWarp">

<form action="" method="post" id="aito_order_f">
		 <input type="hidden" name="btnType" value="ordList"/>
			<div class="searchBx">
        	<!-- <a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
        	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
        	<input type="hidden" name="params.query_btn_flag" value="yes" /> -->
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
                <tr>
                     <th>流程环节：</th>
		             <td>
		                	 <script type="text/javascript">
                	$(function(){
												////XMJ修改开始
                		$("#flow_id_ivp,#flow_id_a,#flow_id_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#flow_id_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#flow_id_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#flow_id_cancel1,#flow_id_cancel2").bind("click",function(e){
                		   $("#flow_id_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
                	   ////XMJ修改结束                		
                		$("#flow_id_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=flow_id]").attr("checked","checked");
                				$("#flow_id_dv li").addClass("curr");
                			}else{
                				$("input[name=flow_id]").removeAttr("checked");
                				$("#flow_id_dv li").removeClass("curr");
                			}
                			selectFlowId();
                		});
                	
                		
                		$("input[name=flow_id]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectFlowId();
                		});
                		
                		initCheckBox("flow_id_hivp","flow_id");
                		
                	});
                	
                	function selectFlowId(){
            			var regions = $("input[name=flow_id]:checked");
            			var regionNames = "";
            			var regionIds = "";
            			regions.each(function(idx,item){
            				var name = $(item).attr("c_name");
            				var rid = $(item).attr("value");
            				regionNames += name+",";
            				regionIds += rid+",";
            			});
            			if(regionIds.length>1){
            				regionIds = regionIds.substr(0,regionIds.length-1);
            				regionNames = regionNames.substr(0,regionNames.length-1);
            			}
            			$("#flow_id_ivp").val(regionNames);
            			$("#flow_id_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.flow_id_c" id="flow_id_ivp" value="${params.flow_id_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.flow_id" value="${params.flow_id }" id="flow_id_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="flow_id_a"></a>
                        <div class="selOp" style="display:none;" id="flow_id_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="flow_id_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="flow_id_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="flow_id_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${flowTraceList }" var="pt">
                            			<c:if test="${query_type=='bss_parallel' && (pt.pkey=='F' || pt.pkey=='H' || pt.pkey=='J' || pt.pkey=='L')}">
                            				<li name="flow_id_li"><input type="checkbox" name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span name="flow_id_span">${pt.pname }</span></li>
                            			</c:if>
                            			<c:if test="${query_type==null || query_type!='bss_parallel'}">
                            				<li name="flow_id_li"><input type="checkbox" name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span name="flow_id_span">${pt.pname }</span></li>
                            			</c:if>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
		              </td>
                
                	<th>外部单号：</th>
	                <td>
	                	<input type="text" name="params.out_tid" value="${params.out_tid }" style="width:138px;" class="ipt_new">
	                </td>
	                <th>内部单号：</th>
	                <td><input type="text" name="params.order_id" value="${params.order_id }" style="width:138px;" class="ipt_new"></td>
	                
                
                </tr>
                <tr>
                   <th>开户号码：</th>
	                <td><input type="text" name="params.phone_num" value="${params.phone_num }" style="width:138px;" class="ipt_new"></td>
                    
                    	
                    	 <th>选择城市：</th>
                <td>
                <script type="text/javascript">
                	$(function(){

											////XMJ修改开始
                		$("#region_ivp,#region_a,#region_div").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#region_div").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#region_div").hide();    //隐藏DIV
                	  	}) 

					   $("#regionCancel,#regionCancel2").bind("click",function(e){
                		   $("#region_div").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#regioncheckAll").bind("click",function(){
                			if(this.checked){
                				$("input[name=region_id]").attr("checked","checked");
                				$("#region_div li").addClass("curr");
                			}else{
                				$("input[name=region_id]").removeAttr("checked");
                				$("#region_div li").removeClass("curr");
                			}
                			selectRegions();
                		});
                		
                		
                		$("input[name=region_id]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectRegions();
                		});
                		
                		initCheckBox("region_hivp","region_id");
                		
                	});
                	
                	function selectRegions(){
            			var regions = $("input[name=region_id]:checked");
            			var regionNames = "";
            			var regionIds = "";
            			regions.each(function(idx,item){
            				var name = $(item).attr("c_name");
            				var rid = $(item).attr("value");
            				regionNames += name+",";
            				regionIds += rid+",";
            			});
            			if(regionIds.length>1){
            				regionIds = regionIds.substr(0,regionIds.length-1);
            				regionNames = regionNames.substr(0,regionNames.length-1);
            			}
            			$("#region_ivp").val(regionNames);
            			$("#region_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_city_code_c" id="region_ivp" value="${params.order_city_code_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_city_code" value="${params.order_city_code }" id="region_hivp" />
                    	<a id="region_a" href="javascript:void(0);" class="selArr"></a>
                        <div id="region_div" class="selOp" style="display:none;">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="regioncheckAll">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="regionCancel">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="regionCancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
	                            	<c:forEach items="${regionList }" var="rg">
	                            		<li><input type="checkbox" name="region_id" value="${rg.region_id }" c_name="${rg.local_name }"><span name="region_li">${rg.local_name }</span></li>
	                            	</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                   
                   <th>创建时间：</th>
	                <td>
	                   <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                </td>
	            </tr>

	            <tr>
	            	<th>商品名称：</th>
	             <td>
                	<input type="text" class="ipt_new" style="width:138px;" name="params.goods_name" value="${params.goods_name }"/>
	              </td> 
	              
	              
	              <th>订单来源：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){

												////XMJ修改开始
                    		$("#order_from_vp,#order_from_a,#order_from_dv").bind("click",function(e1){    //给按钮注册单击事件，点击显示DIV
                    	        $("#order_from_dv").show();    //显示DIV
                    	        e1.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(e1){    
                    	        $("#order_from_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#order_from_cancel1,#order_from_cancel2").bind("click",function(e1){
                    		   $("#order_from_dv").hide();
                               e1.stopPropagation();//阻止事件冒泡
                    		}); 
                    	   ////XMJ修改结束                			
                			$("#order_from_chack_all").bind("click",function(){
                    			if(this.checked){
                    				$("input[name=order_from]").attr("checked","checked");
                    				$("#order_from_dv li").addClass("curr");
                    			}else{
                    				$("input[name=order_from]").removeAttr("checked");
                    				$("#order_from_dv li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		
                    		
                    		$("input[name=order_from]").bind("click",function(){
                    			if(this.checked){
                    				$(this).parents("li").addClass("curr");
                    			}else{
                    				$(this).parents("li").removeClass("curr");
                    			}
                    			selectOrderFroms();
                    		});
                    		
                    		initCheckBox("order_from_ivp","order_from");
                    	});
                    	
                    	function selectOrderFroms(){
                			var regions = $("input[name=order_from]:checked");
                			var regionNames = "";
                			var regionIds = "";
                			regions.each(function(idx,item){
                				var name = $(item).attr("c_name");
                				var rid = $(item).attr("value");
                				regionNames += name+",";
                				regionIds += rid+",";
                			});
                			if(regionIds.length>1){
                				regionIds = regionIds.substr(0,regionIds.length-1);
                				regionNames = regionNames.substr(0,regionNames.length-1);
                			}
                			$("#order_from_vp").val(regionNames);
                			$("#order_from_ivp").val(regionIds);
                		}
                    	
                    	//初始化多选框
                    	function initCheckBox(value_id,check_box_name){
                    		var cv = $("#"+value_id).val();
                    		if(cv){
                    			var arr = cv.split(",");
                    			for(i=0;i<arr.length;i++){
                    				var item = arr[i];
                        			var obj = $("input[type=checkbox][name="+check_box_name+"][value="+item+"]");
                        			obj.attr("checked","checked");
                        			obj.parents("li").addClass("curr");
                    			}
                    		}
                    	}
                	</script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_from_c" id="order_from_vp" value="${params.order_from_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_from" value="${params.order_from }" id="order_from_ivp" />
                    	<a href="javascript:void(0);" id="order_from_a" class="selArr"></a>
                        <div class="selOp" style="display:none;" id="order_from_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_from_chack_all">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_from_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_from_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${order_from_list }" var="of">
                            			<li name="order_from_li"><input type="checkbox" name="order_from" value="${of.value }" c_name="${of.value_desc }"><span name="order_from_span">${of.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
	               <th>种子用户号码：</th>
	             <td>
	                	<input type="text" name="params.share_svc_num" value="${params.share_svc_num }" style="width:138px;" class="ipt_new">
	              </td>  
	            </tr>
	            <tr>
	            	
	              
			     <th>领取数量：</th>
						<td><input type="text" id="receive_num" name="receive_num" value="" style="width: 138px;" class="ipt_new">
						</td>
				<th>证件上传状态：</th>
					<td>
						 <select name="params.if_Send_Photos" class="ipt_new" id="if_Send_Photos" style="width: 171px;height: 20px;">
							<option value="">请选择</option>
								<c:forEach items="${order_vplan_list}" var="of">
									<option value="${of.value}" ${of.value==params.if_Send_Photos?'selected':'' } >${of.value_desc }</option>
								</c:forEach>
						</select> 
						
						<a href="javascript:void(0);" id="ord_receive" class="dobtn" style="margin-left: 5px;">领取选中记录</a>
						<a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a>
						<a href="javascript:void(0);" id="batch_back" class="dobtn" style="margin-left: 5px;">批量退单</a>
					</td>
							
			    
	            </tr>
	            
	            </tbody>
                
            </table>
        </div>
			
</form>


		<div class="right_warp">
			<form action="" id="order_list_fm">

				<grid:grid from="webpage" formId="aito_order_f" asynModel="1">
					<grid:header>
						<grid:cell style="text-align:center; ">状态<input
								type="checkbox" id="checkAlls" />
						</grid:cell>
						<grid:cell>订单信息</grid:cell>
						<grid:cell>商品信息</grid:cell>
						<grid:cell>订单信息</grid:cell>
						<%-- <grid:cell >校验信息</grid:cell>
				<grid:cell ></grid:cell>
				<grid:cell >操作</grid:cell> --%>

					</grid:header>
					<grid:body item="order">
						<grid:cell clazz="alignCen" style="width:60px;">
							<i title="" class="" id="warning_li_${order.orderTree.order_id }"></i>
							<input type="hidden" name="orderids"
								value="${order.orderTree.order_id }" />
							<i title="${order.orderTree.orderExtBusiRequest.lock_user_name}"
								class="${order.lock_clazz }"></i>
							<input id="check" type="checkbox" name="orderidArray"
								value="${order.orderTree.order_id }" />
						</grid:cell>
						<grid:cell style="width:260px;">
							<c:choose>
								<c:when test="${order.bss_time_type == '1' }">
									<div class="list_t red">
										<ul>
											<li><span>外部编号：</span>
											<div class="dddd">
													<%-- <a name="inner_order_id"
														order_id="${order.orderTree.order_id }"
														exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }"
														visible_status="${order.orderTree.orderExtBusiRequest.visible_status }"
														detail_url="${order.action_url }"
														exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }"
														sq="${order.orderTree.orderExtBusiRequest.shipping_quick }"
														order_from="${order.orderTree.orderExtBusiRequest.order_from }"
														href="javascript:void(0);"> --%>${order.orderTree.orderExtBusiRequest.out_tid }<!-- </a> -->
												</div></li>
											<li><span>内部编号：</span>
											<div>${order.orderTree.order_id }</div></li>
											<li><span>订单来源：</span>
											<div>${order.order_from_c }</div></li>
											<li><span>成交时间：</span>
											<div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
										</ul>
									</div>
								</c:when>
								<c:otherwise>
									<div class="list_t">
										<ul>
											<li><span>外部编号：</span>
											<div class="dddd">
													<%-- <a name="inner_order_id"
														order_id="${order.orderTree.order_id }"
														exception_status="${order.orderTree.orderExtBusiRequest.abnormal_status }"
														visible_status="${order.orderTree.orderExtBusiRequest.visible_status }"
														detail_url="${order.action_url }"
														exptype="${order.orderTree.orderExtBusiRequest.abnormal_type }"
														sq="${order.orderTree.orderExtBusiRequest.shipping_quick }"
														href="javascript:void(0);"> --%>${order.orderTree.orderExtBusiRequest.out_tid }<!-- </a> -->
												</div></li>
											<li><span>内部编号：</span>
											<div>${order.orderTree.order_id }</div></li>
											<li><span>订单来源：</span>
											<div>${order.order_from_c }</div></li>
											<li><span>成交时间：</span>
											<div>${order.orderTree.orderExtBusiRequest.tid_time }</div></li>
										</ul>
									</div>
								</c:otherwise>
							</c:choose>
						</grid:cell>
						<grid:cell style="width:350px;">
							<c:choose>
								<c:when test="${order.bss_time_type == '1' }">
									<div class="order_pri">
										<p class="tit red">${order.goods_package }</p>
										<p class="red">号码：${order.phone_num }</p>
										<c:if test="${order.terminal!='' }"><p class="red">终端：${order.terminal }</p></c:if>
										<p class="red">业务办理：${order.bss_status=='1'?'已办理':'未办理' }</p>
									</div>
								</c:when>
								<c:otherwise>
									<div class="order_pri">
										<p class="tit">${order.goods_package }</p>
										<p class="tit">${order.goods_name }</p>
										<p class="ps">号码：${order.phone_num }</p>
										<c:if test="${order.terminal!='' }"><p class="ps">终端：${order.terminal }</p></c:if>
										<p class="ps">业务办理：${order.bss_status=='1'?'已办理':'未办理' }</p>
									</div>
								</c:otherwise>
							</c:choose>
						</grid:cell>
						<grid:cell style="width:120px;">
							<c:choose>
								<c:when test="${order.bss_time_type == '1' }">
									<div class="order_pri">
										<p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
										<p class="tit red">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
										<p class="tit f_bold red">${order.flow_trace }</p>
										<p class="ps red">${order.shipping_type }</p>
									</div>
								</c:when>
								<c:otherwise>
									<div class="order_pri">
										<p class="pri">￥${order.orderTree.orderBusiRequest.order_amount }</p>
										<p class="tit">${order.orderTree.orderBusiRequest.pay_status==0?'未支付':'已支付' }</p>
										<p class="tit f_bold">${order.flow_trace }</p>
										<p class="ps">${order.shipping_type }</p>
									</div>
								</c:otherwise>
							</c:choose>
						</grid:cell>
					</grid:body>
				</grid:grid>
				<input type="hidden" id="order_id_hidden" /> <input type="hidden"
					id="isListBtn" value="true" />
				<!-- 后续把权限控制加上，admin 和库管人员可以见 -->
				<!-- <input type="button"  name="terminal_num_batch_bt" id="terminal_num_batch_bt"  value="批量确认" /> -->
				<!--  	<a href="javascript:void(0);" class="dobtn" style="margin-left:5px;" name="terminal_num_batch_bt" id="terminal_num_batch_bt">批量确认</a> -->
			</form>

			<div class="clear"></div>
		</div>
	</div>
	</div>
	<div id="choose_user_div"></div>

	<div id="addCommentsDlg">
		<form method="post" id="saveCommentsForm">
			<table border="0" cellpadding="0">
				<tr>
					<textarea rows="4" cols="50" name="dealDesc" id="dealDesc"></textarea>
				</tr>
				<tr>
					<td><input type="button" name="sava_comments"
						id="sava_comments" order_id=""
						style="margin-left: 165px; margin-top: 6px;" class="graybtn1"
						value="确&nbsp;定" onclick="addComments()" /></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="backDesc">
	<div id="backDescDialog">

	<br />
	<br />
	<br />

	<script type="text/javascript">
		//添加备注功能
		$(function() {
			Eop.Dialog.init({
				id : "addCommentsDlg",
				modal : true,
				title : "添加备注",
				width : '400px'
			});
			$("a[name='add_comments']").click(function() {
				var orderId = $(this).attr("innerId");
				openCommentsDlg(orderId);
				return false;
			})
			
			Eop.Dialog.init({id:"backDesc",modal:true,title:"退单说明",height:'400px',width:'550px'});
			//批量退单
			$("#batch_back").unbind("click").bind("click", function() {
				var query_type = "order_receive";
				var len = $("input[type='checkbox'][name='orderidArray']:checked").length;
				if(len==0){
					alert("请勾选需要退单的订单!")
					return;
				}
				var orders = '';
				var show_flag = "0";
				$("input[type='checkbox'][name='orderidArray']:checked").each(function(index) {
					var order_id = $(this).val();//单号
					orders += order_id+",";
				});
					
				
				var url = ctx + "/shop/admin/orderFlowAction!showBackDesc.do?ajax=yes&orders="+orders+"&show_flag="+show_flag+"&query_type="+query_type;
				Eop.Dialog.open("backDesc");
				$("#backDescDialog").html("loading...");
				$("#backDescDialog").load(url,function(){});
				
			});
		});

		$("#query_order_s").bind(
				"click",
				function() {
					$.Loading.show("正在加载所需内容，请稍侯...");
					$("#aito_order_f").attr("action",
							ctx + "/shop/admin/ordAuto!showOrderList.do?query_type=order_receive&orderReceiveRetrunMark=1")
							.submit();
				});

		function openCommentsDlg(orderId) {
			$("#addCommentsDlg").load();
			Eop.Dialog.open("addCommentsDlg");
			$("#sava_comments").attr("order_id", orderId);
		}

		function addComments() {
			var comments = $("#dealDesc").val();
			if (comments != "") {
				var order_Id = $("#sava_comments").attr("order_id");
				var url = app_path
						+ "/shop/admin/orderFlowAction!saveOrderComments.do?ajax=yes&order_id="
						+ order_Id;
				var saveBack = function(reply) {
					if (reply.result == 0) {
						alert("备注添加成功");
						Eop.Dialog.close("addCommentsDlg");
					}
					$("#dealDesc").val("");
				};
				Cmp.ajaxSubmit('saveCommentsForm', '', url, {}, saveBack,
						'json');
			} else {
				alert("备注不能为空");
			}
		}
	</script>




	<script type="text/javascript">
		$(function() {

			var jsonBack = function(reply) {
				alert(reply.message);
				if (reply.result == 0) {
					$("#query_order_s").click();
					//or
					/* 
					$.Loading.show("正在加载所需内容，请稍侯...");
					$("#aito_order_f").attr("action",
							ctx + "/shop/admin/ordAuto!showOrderList.do?query_type=order_receive&orderReceiveRetrunMark=1")
							.submit();
					*/
					
					/* var url = window.location.href;
					window.location.href = url; */
					// var url   = "/shop/admin/ordAuto!orderReceive.do?query_type=order_receive;
					//window.location.reload();
				}
			}

			$("#ord_receive")
					.click(
							function() {
								var len = $("[name='orderidArray']:checked").length;
								var receive_num = document
										.getElementById("receive_num").value;
								if (len == 0 && receive_num == 0) {
									alert("请选择要领取的订单或输入领取数量！");
									return false;
								}
								var order_idArr = [];
								$("[name='orderidArray']:checked").each(
										function() {
											var order_id = $(this).val();
											order_idArr.push(order_id);
										});
								var lockOrderIdStr = order_idArr.join(",");
								var url = "/shop/admin/ordAuto!orderReceive.do?ajax=yes&lockOrderIdStr="
										+ lockOrderIdStr
										+ "&receive_num="
										+ receive_num;
								Cmp.excute('', url, {}, jsonBack, 'json');

							});
			$("#ord_refresh").click(function(){
				location.href = "/shop/admin/ordAuto!showOrderList.do?query_type=order_receive";
			});
			/*  $("[name='orderidArray']").click(function(){
				 var order_idArr=1;
				 alert(order_idArr++);
				 var order_id = $(this).val();
				 order_idArr.push(order_id);
			  });
			 */

			$("#checkAlls").bind("click", function() {
				$("input[type=checkbox][name=orderidArray]").trigger("click");
			});
			$(".gridbody").removeClass("gridbody").addClass("grid_w_div");
			$("#order_list_fm div table").addClass("grid_w").attr("width",
					"100%").attr("border", "0").attr("cellspacing", "0").attr(
					"cellpadding", "0");
			$("#order_list_fm .page").wrap("<form class=\"grid\"></form>");
			$("#order_list_fm div table tbody tr")
					.unbind("click")
					.bind(
							"click",
							function() {
								var $this = $(this);
								var obj = $this.find("div.dddd").children("a");
								var order_id = obj.attr("order_id");
								var order_from = obj.attr("order_from");
								var exception_status = obj
										.attr("exception_status");
								var visible_status = obj.attr("visible_status");
								var sq = obj.attr("sq");
								if (!sq)
									sq = "01";
								var expType = obj.attr("exptype");
								if (!expType)
									expType = "0";

								var page_hide = "list,expType" + expType + ","
										+ sq;
								if ("1" == exception_status) {
									page_hide += ",exception";
								}/* else{
												page_hide += ",detail";
											} */
								var q_type = "${query_type}";
								if ("1" != visible_status || "ycl" == q_type) {
									//显示可见订单处理按钮
									var btn = "";
									if ("ycl" == q_type)
										btn = "ycl";
									//OrdBtns.showBtns(order_id,page_hide,btn);
								}

								$this.siblings("tr").removeClass("curr");
								$this.addClass("curr");
								/* 点击tr时，暂时不需要锁定
								//$this.find("i.lock").attr("class", "unlock");
								$.ajax({
									type : "post",
									async : false,
									url : "ordAuto!order_lock.do?ajax=yes&order_id="+order_id,
									data : {},
									dataType : "json",
									success : function(data) {
										if (data.result == "0") {
											$this.find("i.lock").attr("class", "unlock");
										} else {
											$this.find("i.unlock").attr("class", "lock");
										}
									}
								});
								 */
								if (order_from == '10012') {
									$
											.ajax({
												type : "post",
												async : false,
												url : "ordReturned!returnedApplyFromTaobao.do?ajax=yes&order_id="
														+ order_id,
												data : {},
												dataType : "json",
												success : function(data) {
													if (data.result == "0") {
														window.location
																.reload();
													}
												}
											});
								}

							});
			//查看订单详细
			$("a[name=inner_order_id]").bind(
					"click",
					function() {
						/* try{
							if (event.stopPropagation) {
							    event.stopPropagation();
							 } else if (window.event) {
							    window.event.cancelBubble = true;
							 }
						}catch(e){
							
						} */
						var $this = $(this);
						var order_id = $(this).attr("order_id");

						//$this.find("i.lock").attr("class", "unlock");
						$.ajax({
							type : "post",
							async : false,
							url : "ordAuto!order_lock.do?ajax=yes&order_id="
									+ order_id,
							data : {},
							dataType : "json",
							success : function(data) {
								if (data.result == "0") {
									$this.find("i.lock")
											.attr("class", "unlock");
								} else {
									$this.find("i.unlock")
											.attr("class", "lock");
								}
							}
						});

						var o_url = $this.attr("detail_url");
						if (o_url == '' || o_url == null
								|| o_url == 'undefined') {
							var resultUrl = AutoFlow.getActionUrl(order_id);
							alert(resultUrl);
							var resultObj = resultUrl.split("|");
							if (resultObj[0] == 0) {
								o_url = resultObj[1];
							} else {
								alert(resultObj[1]);
								return false;
							}
						}
						if (o_url && o_url.indexOf("?") != -1) {
							o_url += "&order_id=" + order_id
									+ "&query_type=${query_type}";
						} else {
							o_url += "?order_id=" + order_id
									+ "&query_type=${query_type}";
						}
						var url = ctx + "/" + o_url;
						//var url = ctx+"/shop/admin/ordAuto!showDetail.do?order_id="+order_id;
						window.location.href = url;
						//shop/admin/orderFlowAction!preDealOrd.do
					});
		});
	</script>


	<script type="text/javascript">
		/* 异步检查预警信息*/
		$(document).ready(function() {
			//queryWarning();

		});

		function queryWarning() {
			//获取
			var orderids = "";
			$("input[name='orderids']").each(function(i) {
				var order_id = $(this).val();
				if (i == 0) {
					orderids += order_id;
				} else {
					orderids += "," + order_id;
				}
			});

			if (orderids != "") {
				$.ajax({
					type : "post",
					async : false,
					url : "ordAuto!order_warning.do?ajax=yes",
					data : {
						"order_ids" : orderids
					},
					dataType : "json",
					success : function(data) {
						if (data.result == '0') {
							var datalist = data.list;
							for (var i = 0; i < datalist.length; i++) {
								var owResult = datalist[i];
								var order_id = owResult.order_id;
								var warning_colo = owResult.warning_colo;
								var out_time = owResult.out_time;
								var run_time = owResult.run_time;
								var titleMsg = "当前环节已开始" + run_time + "分钟，超时"
										+ out_time + "分钟！";
								$("#warning_li_" + order_id).addClass(
										warning_colo);
								$("#warning_li_" + order_id).attr("title",
										titleMsg);

							}
						}

					}
				});
			}

		};
	</script>
</body>
</html>