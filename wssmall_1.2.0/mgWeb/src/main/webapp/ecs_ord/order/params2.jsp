<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String exception_type = request.getParameter("exception_type");
	request.setAttribute("exception_type", exception_type);
%>
<div class="searchBx">
	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
	<a href="javascript:void(0);" id="show_params_tb" class="arr close"
		style="display: none;">展开</a> <input type="hidden"
		name="params.query_btn_flag" value="yes" />
	<table id="params_tb" width="100%" border="0" cellspacing="0"
		cellpadding="0" class="tab_form">
		<tbody id="tbody_A">
			<tr>
				<th>流程环节：</th>
				<td><script type="text/javascript">
                	$(function(){
						////XMJ修改开始
                		$("#flow_id_ivp,#flow_id_a,#flow_id_dv").bind("click",function(e){//给按钮注册单击事件，点击显示DIV
                	        $("#flow_id_dv").show();//显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		});
            	    		
						$(document).bind("click",function(){    
                	        $("#flow_id_dv").hide();//隐藏DIV
                	  	});

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
                 <span class="selBox" style="width: 120px;"> 
                 <input	 type="text" name="params.flow_id_c" id="flow_id_ivp"
						value="${params.flow_id_c }" class="ipt" readonly="readonly" />
						 <input type="hidden" name="params.flow_id" value="${params.flow_id }"
						id="flow_id_hivp" /> <a href="javascript:void(0);" class="selArr" id="flow_id_a"></a>
						<div class="selOp" style="display: none;" id="flow_id_dv">
							<div class="allSel">
								<label><input type="checkbox" id="flow_id_checkall">全选</label>
								<label><a href="javascript:void(0);" class="aCancel"
									id="flow_id_cancel1">关闭</a></label> <label><a
									href="javascript:void(0);" class="aClear" id="flow_id_cancel2"></a></label>
							</div>
							<div class="listItem">
								<ul>
									<c:forEach items="${flowTraceList }" var="pt">
										<c:if
											test="${query_type=='bss_parallel' && (pt.pkey=='F' || pt.pkey=='H' || pt.pkey=='J' || pt.pkey=='L')}">
											<li name="flow_id_li"><input type="checkbox"
												name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span
												name="flow_id_span">${pt.pname }</span></li>
										</c:if>
										<c:if test="${query_type==null || query_type!='bss_parallel'}">
											<li name="flow_id_li"><input type="checkbox"
												name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span
												name="flow_id_span">${pt.pname }</span></li>
										</c:if>
									</c:forEach>
								</ul>
							</div>
						</div>
				</span></td>

				<th>外部单号：</th>
				<td><input type="text" name="params.out_tid"
					value="${params.out_tid }" style="width: 138px;" class="ipt_new">
				</td>
				<th>内部单号：</th>
				<td><input type="text" name="params.order_id"
					value="${params.order_id }" style="width: 138px;" class="ipt_new"></td>
			</tr>
			<tr>
				<th>创建时间：</th>
				<td><input type="text" name="params.create_start"
					value="${params.create_start }" readonly="readonly" class="ipt_new"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">- <input
					type="text" name="params.create_end" value="${params.create_end }"
					readonly="readonly" class="ipt_new"
					onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"></td>
				<th>开户号码：</th>
				<td><input type="text" name="params.phone_num"
					value="${params.phone_num }" style="width: 138px;" class="ipt_new"></td>
				<td>
					<a href="javascript:void(0);" id="query_order_s" class="dobtn" style="margin-left:5px;">查&nbsp;&nbsp;&nbsp;&nbsp;询&nbsp;&nbsp;</a>
				</td>
			</tr>
		</tbody>
		<tbody id="param_tb_F">
			<tr>
				<th>是否当月办理：</th>
				<td><html:selectdict name="params.bss_time_type"
						appen_options="<option value=''>---请选择---</option>"
						curr_val="${params.bss_time_type}" attr_code="DC_BSS_TIME_TYPE"
						style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:190px; background:url(${context}/images/ipt.gif) repeat-x;width:150px;"></html:selectdict>
				</td>
				<th>选择城市：</th>
				<td><script type="text/javascript">
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
                </script> <span class="selBox" style="width: 120px;"> <input
						type="text" name="params.order_city_code_c" id="region_ivp"
						value="${params.order_city_code_c }" class="ipt"
						readonly="readonly"> <input type="hidden"
						name="params.order_city_code" value="${params.order_city_code }"
						id="region_hivp" /> <a id="region_a" href="javascript:void(0);"
						class="selArr"></a>
						<div id="region_div" class="selOp" style="display: none;">
							<div class="allSel">
								<label><input type="checkbox" id="regioncheckAll">全选</label>
								<label><a href="javascript:void(0);" class="aCancel"
									id="regionCancel">关闭</a></label> <label><a
									href="javascript:void(0);" class="aClear" id="regionCancel2"></a></label>
							</div>
							<div class="listItem">
								<ul>
									<c:forEach items="${regionList }" var="rg">
										<li><input type="checkbox" name="region_id"
											value="${rg.region_id }" c_name="${rg.local_name }"><span
											name="region_li">${rg.local_name }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
				</span></td>

				<th>商品名称：</th>
				<td><input type="text" class="ipt_new" style="width: 138px;"
					name="params.goods_name" value="${params.goods_name }" /></td>
			</tr>
			<tr>
				<th>商品包类型：</th>
				<td><html:selectdict name="params.goods_pagekage_type"
						appen_options="<option value=''>---请选择---</option>"
						curr_val="${params.goods_pagekage_type}"
						attr_code="DC_MODE_GOODS_TYPE"
						style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:138px;"></html:selectdict>
				</td>
				<th>型号：</th>
				<td><input type="text" name="params.model_code"
					value="${params.model_code }" class="ipt_new" style="width: 138px;"></td>
				<th>待业务办理：</th>
				<td><select name="params.exists_business_to_deal_with"
					class="ipt_new" style="width: 138px;">
						<option value="">---请选择---</option>
						<option value="0">已办理</option>
						<option value="1">未办理</option>
					</select> <script type="text/javascript">
		                  $("[name='params.exists_business_to_deal_with'] option[value='${params.exists_business_to_deal_with}']").attr("selected","selected");
						</script>
				</td>
			</tr>
			<%-- <c:if test="${query_type=='order' }">
	                <th>是否异常订单：</th>
	                <td>
	                	<select name="params.refund_deal_type"  class="ipt_new" style="width:138px;">
	                    	<option value="-1" ${params.refund_deal_type=='-1'?'selected':'' }>--请选择--</option>
	                    	<option value="01" ${params.refund_deal_type=='01'?'selected':'' } >正常单</option>
	                    	<option value="02" ${params.refund_deal_type=='02'?'selected':'' }>异常单</option>
						</select>
	                </td>
                </c:if> --%>
			<tr>
				<th>异常单类型：</th>
				<td><input type="hidden" name="query_type"
					value="${query_type }" /> <select name="params.exception_type"
					class="ipt_new" style="width: 138px;">
						<c:forEach items="${orderExceptionTypeList }" var="qs">
							<option value="${qs.pkey }"
								${qs.pkey==params.exception_type?'selected':'' }>${qs.pname }</option>
						</c:forEach>
				</select> <script type="text/javascript">
					var _exception_type = '${params.exception_type}';
					if(_exception_type ==null || _exception_type == ''){
						var exception_type = '${exception_type}';
						if(exception_type=='all'){
							$("select[name='params.exception_type']").val("-1");
						}
						else{
							$("select[name='params.exception_type']").val(exception_type);
						}
					}
				</script></td>
				<th>物流单号：</th>
				<td><input type="text" name="params.logi_no"
					value="${params.logi_no }" class="ipt_new" style="width: 138px;">
				</td>
				<th>订单来源：</th>
				<td><script type="text/javascript">
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
                	</script> <span class="selBox" style="width: 120px;"> <input
						type="text" name="params.order_from_c" id="order_from_vp"
						value="${params.order_from_c }" class="ipt" readonly="readonly">
						<input type="hidden" name="params.order_from"
						value="${params.order_from }" id="order_from_ivp" /> <a
						href="javascript:void(0);" id="order_from_a" class="selArr"></a>
						<div class="selOp" style="display: none;" id="order_from_dv">
							<div class="allSel">
								<label><input type="checkbox" id="order_from_chack_all">全选</label>
								<label><a href="javascript:void(0);" class="aCancel"
									id="order_from_cancel1">关闭</a></label> <label><a
									href="javascript:void(0);" class="aClear"
									id="order_from_cancel2"></a></label>
							</div>
							<div class="listItem">
								<ul>
									<c:forEach items="${order_from_list }" var="of">
										<li name="order_from_li"><input type="checkbox"
											name="order_from" value="${of.value }"
											c_name="${of.value_desc }"><span
											name="order_from_span">${of.value_desc }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
				</span></td>
			</tr>
			<tr>
				<th>订单类型：</th>

				<td><script type="text/javascript">
                	$(function(){
                		$("#order_type_ivp,#order_type_a,#order_type_dv").bind("click",function(e){
                    		$("#order_type_dv").show();
                	        e.stopPropagation();//阻止事件冒泡
                    	});
						$(document).bind("click",function(){    
                	        $("#order_type_dv").hide();    //隐藏DIV
                	  	});
                		$("#order_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=order_type]").attr("checked","checked");
                				$("#order_type_dv li").addClass("curr");
                			}else{
                				$("input[name=order_type]").removeAttr("checked");
                				$("#order_type_dv li").removeClass("curr");
                			}
                			selectOrderType();
                		});
                		$("#order_type_cancel1,#order_type_cancel2").bind("click",function(e){
                			$("#order_type_dv").hide();
                	        e.stopPropagation();//阻止事件冒泡
                		});
                		
                		$("input[name=order_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectOrderType();
                		});
                		
                		initCheckBox("order_type_hivp","order_type");
                		
                	});
                	
                	function selectOrderType(){
            			var regions = $("input[name=order_type]:checked");
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
            			$("#order_type_ivp").val(regionNames);
            			$("#order_type_hivp").val(regionIds);
            		}
                </script> <span class="selBox" style="width: 120px;"> <input
						type="text" name="params.order_type_c" id="order_type_ivp"
						value="${params.order_type_c }" class="ipt" readonly="readonly" />
						<input type="hidden" name="params.order_type"
						value="${params.order_type }" id="order_type_hivp" /> <a
						href="javascript:void(0);" class="selArr" id="order_type_a"></a>
						<div class="selOp" style="display: none;" id="order_type_dv">
							<div class="allSel">
								<label><input type="checkbox" id="order_type_checkall">全选</label>
								<label><a href="javascript:void(0);" class="aCancel"
									id="order_type_cancel1">关闭</a></label> <label><a
									href="javascript:void(0);" class="aClear"
									id="order_type_cancel2"></a></label>
							</div>
							<div class="listItem">
								<ul>
									<c:forEach items="${order_type_list }" var="pt">
										<li name="order_type_li"><input type="checkbox"
											name="order_type" value="${pt.pkey }" c_name="${pt.pname }"><span
											name="order_type_span">${pt.pname }</span></li>
									</c:forEach>
								</ul>
							</div>
						</div>
				</span></td>
				<th>生产模式：</th>
				<td><select name="params.order_model" class="ipt_new"
					style="width: 150px;">
						<c:forEach items="${order_model_list }" var="ds">
							<option value="${ds.pkey }"
								${ds.pkey==params.order_model?'selected':'' }>${ds.pname }</option>
						</c:forEach>
				</select></td>
				<th>ICCID：</th>
				<td><input type="text" class="ipt_new" style="width: 138px;"
					name="params.iccid" value="${params.iccid }"
					onkeydown="if(event.keyCode==13){query_order_s.click();}" />
				</td>
			</tr>
		</tbody>
	</table>
</div>

<script type="text/javascript">
	$(function(){
		$("#param_tb_F").hide();
		$("#hide_params_tb").hide();
		$("#show_params_tb").show();
		var count = -2;
		$("#hide_params_tb").bind("click",function(){
			if(count==1){
				$("#param_tb_F").hide();
				count = 2;
			}else{
				$("#tbody_A").hide();
				count = -1; 
				$("#hide_params_tb").hide();
				$("#show_params_tb").show();
			}
			
		});
		$("#show_params_tb").bind("click",function(){
			if(count==-1){
				$("#tbody_A").show();
				count =-2;
			}else{
				$("#param_tb_F").show();
				count = 1;
				$("#hide_params_tb").show();
				$("#show_params_tb").hide();
			}
			
		});
		
		$("#query_order_s").bind("click",function(){
			$.Loading.show("正在加载所需内容，请稍侯...");
			//只有订单处理的才做特殊处理
			var query_type = $("input[name='query_type']").val();
			if(query_type == "order"){
				//获取订单条形码
				var order_sn = $.trim($("input[name='params.order_sn']").val());
				var iccid = $.trim($("input[name='params.iccid']").val());
				if(order_sn != "" || iccid != ""){
					$.ajax({
						type : "post",
						async : true,
						url : "ordAuto!getOrderInfoBySn.do?ajax=yes&params.order_sn="+order_sn+"&params.iccid="+iccid,
						data : {},
						dataType : "json",
						success : function(data) {
							 $.Loading.hide();
							 if(data.result == "1"){//查询失败
								 alert(data.message);
							 }else{
								 if(data.result == "0" && data.flow_trace_id == "F"){//只有查询到订单，并且订单当前环节为质检稽核环节的才弹出对话框
									 var order_id = data.order_id;
									 //获取质检稽核订单详情页面地址
									 var resultUrl =  AutoFlow.getActionUrl(order_id);
									 var resultObj = resultUrl.split("|");
									 var o_url = "";
									if(resultObj[0]==0){
										o_url = resultObj[1];	
									}else{
										alert(resultObj[1]);
										return false;
									}
									if(o_url && o_url.indexOf("?")!=-1){
										o_url += "&order_id="+order_id+"&query_type="+query_type;
									}else{
										o_url += "?order_id="+order_id+"&query_type="+query_type;
									}
									var url = ctx+"/"+o_url;
									//锁单
									$.ajax({
										type : "post",
										async : false,
										url : "ordAuto!order_lock.do?ajax=yes&order_id="+order_id,
										data : {},
										dataType : "json",
										success : function(data) {
											if (data.result == "0") {
												//弹框
												window.open(url,"newwindow","height=500,width=1100, top=115, left=210, toolbar=no, menubar=no, scrollbars=yes, resizable=no, location=no, status=no");
											} else {
												alert("查询失败！");
											}
										}
									});	
									
								 }else{//否则提交表单查询
									 $("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderListNew.do").submit();
								 }
							 }
						},
						error : function(){
							$.Loading.hide();
						}
					});
					
				}else{
					$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderListNew.do").submit();
				}
			}else{	
				$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderListNew.do").submit();
			}
		});
		$("#boxCodeId").bind("blur", function() {
			var boxCode = $("#boxCodeId").val();
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!getOrdByBoxIdFromWMS.do?ajax=yes&boxCode="+boxCode,
				data : {},
				dataType : "json",
				success : function(data) {
					if (data!= undefined && data.result=="0") {
						$("input[name='params.order_id']").val(data.message);
						$("#query_order_s").click();
					}
				}
			});
		});
		$("#resetBtn").click(function (){
			document.getElementById("username").value = "";
			document.getElementById("lock_user_id").value = "";
		});
	});
	
	(function($){
		$.fn.aramsDiv = function(){
			var $this = $(this);
			$this.bind("mouseout",function(){
				
			});
			$(this).bind("mouseover",function(){
				
			});
		};
	})(jQuery);
</script>
