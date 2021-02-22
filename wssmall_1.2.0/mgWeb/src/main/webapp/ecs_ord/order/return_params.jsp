<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<div class="searchBx">
        	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
        	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
              <tbody><tr>
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
                	<span class="selBox" style="width:135px;">
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
                <th>处理状态：</th>
                <td>
                	<select name="params.status" style="width:154px;height:22px;" class='ipt_new'>
                		<c:forEach items="${deal_status }" var="ds">
                			<option value="${ds.pkey }" ${ds.pkey==params.status?'selected':'' }>${ds.pname }</option>
                		</c:forEach>
					</select>
                </td>
               <th>支付时间：</th>
                <td>
                    <input type="text" name="params.pay_start" value="${params.pay_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
                    <input type="text" name="params.pay_end" value="${params.pay_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                </td>
               </tr>
               <tr>
                <th>外部单号：</th>
                <td>
                	<input type="text" name="params.out_tid" value="${params.out_tid }" style="width:153px;height:22px;" class="ipt_new">
                </td>
                <th>内部单号：</th>
                <td><input type="text" name="params.order_id" value="${params.order_id }" style="width:153px;height:22px;" class="ipt_new"></td>
                
                 <th>创建时间：</th>
                <td>
                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                    -
                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                    
                </td>
              </tr>
              <tr>
                <th>流程环节：</th>
                <td>
                	   	 <script type="text/javascript">
                	$(function(){
                		$("#flow_id_ivp,#flow_id_a,#flow_id_dv").bind("click",function(e){
                    		$("#flow_id_dv").show();
                	        e.stopPropagation();//阻止事件冒泡
                    	});
						$(document).bind("click",function(){
                	        $("#flow_id_dv").hide();    //隐藏DIV
                	  	});
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
                		$("#flow_id_cancel1,#flow_id_cancel2").bind("click",function(e){
                			$("#flow_id_dv").hide();
                	        e.stopPropagation();//阻止事件冒泡
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
                	<span class="selBox" style="width:135px;">
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
                            			<li name="flow_id_li"><input type="checkbox" name="flow_id" value="${pt.pkey }" c_name="${pt.pname }"><span name="flow_id_span">${pt.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
					
                </td>
                <th>订单发展归属：</th>
                <td>
                	<select name="params.order_channel" style="width:154px;height:22px;" class="ipt_new">
                		<option value="-1" >请选择</option>
                		<c:forEach items="${channel_mark_list }" var="ds">
                			<option value="${ds.pkey }" ${ds.pkey==params.order_channel?'selected':'' }>${ds.pname }</option>
                		</c:forEach>
					</select>
                </td>
                
                <th>配送方式：</th>
                <td>
                	<select name="params.shipping_id" style="width:154px;height:22px;" class='ipt_new'>
                		<option value="-1" >请选择</option>
                		<c:forEach items="${shipping_type_list }" var="ds">
                			<option value="${ds.pkey }" ${ds.pkey==params.shipping_id?'selected':'' }>${ds.pname }</option>
                		</c:forEach>
					</select>
					<!-- &nbsp;&nbsp;<a href="javascript:void(0);" id="query_order_s" name="query_order_s" class="dobtn" style="margin-left:5px;">查询</a> -->
                </td>
                
              </tr>
              <tr>
              <th>是否预约单：</th>
	             <td>
                	<select name="params.wm_isreservation_from"  class="ipt_new" style="width:154px;height:22px;">
                    	<option value="0" ${params.wm_isreservation_from=='0'?'selected':'' }>否</option>
                    	<option value="1" ${params.wm_isreservation_from=='1'?'selected':'' }>是</option>
                    	<option value="-1" ${params.wm_isreservation_from=='-1'?'selected':'' }>--全部--</option>
					</select>
	              </td>
                <th>退单状态：</th>
                <td>
                	<select name="params.refund_status" style="width:154px;height:22px;" class="ipt_new">
                		<c:forEach items="${refund_satus_list }" var="ds">
                		<c:choose>
                		<c:when test="${userid == '1' }">
                		<option value="${ds.pkey }" ${ds.pkey==params.refund_status?'selected':'' }>${ds.pname }</option>
                		</c:when>
                		<c:otherwise>
                		<c:if test="${ds.pkey != '05'}">
                			<option value="${ds.pkey }" ${ds.pkey==params.refund_status?'selected':'' }>${ds.pname }</option>
                		</c:if>
                		</c:otherwise>
                		</c:choose>
                		</c:forEach>
					</select>
                </td>
                <th>退款状态：</th>
                <td>
                	<select name="params.bss_refund_status" style="width:154px;height:22px;" class="ipt_new">
                		<c:forEach items="${bss_refund_satus_list }" var="ds">		
                		<option value="${ds.pkey }" ${ds.pkey==params.bss_refund_status?'selected':'' }>${ds.pname }</option>
                		</c:forEach>
					</select>
                </td>             
            </tbody>
            <tbody id="param_tb_F">
	            <tr>
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
                	<span class="selBox" style="width:135px;">
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
                <th>生产模式：</th>
	             <td>
	             <script type="text/javascript">
                	$(function(){

											////XMJ修改开始
                		$("#orderModel_ivp,#orderModel_a,#orderModel_div").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#orderModel_div").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#orderModel_div").hide();    //隐藏DIV
                	  	}) 

					   $("#orderModelCancel,#orderModelCancel2").bind("click",function(e){
                		   $("#orderModel_div").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#orderModelcheckAll").bind("click",function(){
                			if(this.checked){
                				$("input[name=order_model]").attr("checked","checked");
                				$("#orderModel_div li").addClass("curr");
                			}else{
                				$("input[name=order_model]").removeAttr("checked");
                				$("#orderModel_div li").removeClass("curr");
                			}
                			selectOrderModels();
                		});
                		
                		
                		$("input[name=order_model]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectOrderModels();
                		});
                		
                		initCheckBox("orderModel_hivp","order_model");
                		
                	});
                	
                	function selectOrderModels(){
            			var orderModels = $("input[name=order_model]:checked");
            			var orderModelNames = "";
            			var orderModelIds = "";
            			orderModels.each(function(idx,item){
            				var name = $(item).attr("c_name");
            				var rid = $(item).attr("value");
            				orderModelNames += name+",";
            				orderModelIds += rid+",";
            			});
            			if(orderModelNames.substr(orderModelNames.length - 1) == ','){
            				orderModelIds = orderModelIds.substr(0,orderModelIds.length-1);
            				orderModelNames = orderModelNames.substr(0,orderModelNames.length-1);
            			}
            			$("#orderModel_ivp").val(orderModelNames);
            			$("#orderModel_hivp").val(orderModelIds);
            		}
                </script>
	             <span class="selBox" style="width:135px;">
	             		<input type="text" name=params.order_model_c id="orderModel_ivp" value="${params.order_model_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_model" value="${params.order_model }" id="orderModel_hivp" />
                    	<a id="orderModel_a" href="javascript:void(0);" class="selArr"></a>
                        <div id="orderModel_div" class="selOp" style="display:none;">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="orderModelcheckAll">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="orderModelCancel">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="orderModelCancel2"></a></label>
                            </div>
                            
                       <div class="listItem">
                       	<ul>
                        	<c:forEach items="${order_model_list }" var="ds">
                        		<c:if test="${ds.pkey != '-1'}">
                        			<li><input type="checkbox" name="order_model" value="${ds.pkey }" c_name="${ds.pname }"><span name="order_model_li">${ds.pname }</span></li>
                        		</c:if>
                        	</c:forEach>
                           </ul>
                       </div>
	              </td>
	              <th>开户号码：</th>
	              <td><input type="text" name="params.phone_num" value="${params.phone_num }" style="width:153px;" class="ipt_new"></td>
             </tr>
             <tr>
           	 <th>订单类型：</th>
           	 <td>
                	   	 <script type="text/javascript">
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
                </script>
                	<span class="selBox" style="width:135px;">
                    	<input type="text" name="params.order_type_c" id="order_type_ivp" value="${params.order_type_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.order_type" value="${params.order_type }" id="order_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="order_type_a"></a>
                        <div class="selOp" style="display:none;" id="order_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="order_type_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${order_type_list }" var="pt">
                            			<li name="order_type_li"><input type="checkbox" name="order_type" value="${pt.pkey }" c_name="${pt.pname }"><span name="order_type_span">${pt.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
					
                </td>
             <th>&nbsp;</th>
             <td>&nbsp;</td>
             <th>&nbsp;</th>
             <td>&nbsp;</td>
             </tr>
             </tbody>
            </table>
        </div>
<input type="hidden" name="query_type" value="${query_type }" />
<script type="text/javascript">
	$(function(){
		$("#hide_params_tb").bind("click",function(){
			$("#params_tb").hide();
			$("#hide_params_tb").hide();
			$("#show_params_tb").show();
		});
		$("#show_params_tb").bind("click",function(){
			$("#params_tb").show();
			$("#hide_params_tb").show();
			$("#show_params_tb").hide();
		});
		
		$("#query_order_s").bind("click",function(){
			$("#aito_order_return_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do").submit();
		});
		//$("#order_from_dv").aramsDiv();
	});
	
	(function($){
		$.fn.aramsDiv = function(){
			//this.isOut = true;
			var $this = $(this);
			$this.bind("mouseout",function(){
				//isOut = false;
				//alert(111);
				//$this.hide();
				//console.log("===mouseout===========");
			});
			$(this).bind("mouseover",function(){
				//isOut = true;
				//console.log("===mouseover===========");
			});
		};
	})(jQuery);
</script>        