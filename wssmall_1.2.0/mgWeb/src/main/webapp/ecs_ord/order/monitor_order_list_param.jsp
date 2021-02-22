<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.searchformDiv table th {
	width: 90px;
}
</style>
<div class="searchBx">
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
                <tr>
                   <th>订单来源：</th>
                <td>
                	<script type="text/javascript">
                		$(function(){
                			/* $("#order_from_vp,#order_from_a").bind("click",function(){
                				$("#order_from_dv").show();
                			});
                			$("#order_from_cancel1,#order_from_cancel2").bind("click",function(){
                    			$("#order_from_dv").hide();
                    		}); */
                			
                			
                		////XMJ修改开始
                    		$("#order_from_vp,#order_from_a,#order_from_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                    	        $("#order_from_dv").show();    //显示DIV
                    	        e.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(){    
                    	        $("#order_from_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#order_from_cancel1,#order_from_cancel2").bind("click",function(e){
                    		   $("#order_from_dv").hide();
                               e.stopPropagation();//阻止事件冒泡
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
                            			<li name="order_from_li"><input type="checkbox" name="order_from" value="${of.pkey }" c_name="${of.pname }"><span name="order_from_span">${of.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                 <th>流程环节：</th>
		             <td>
		                	<select name="params.flow_id" class="ipt_new" style="width:138px;">
		                		<c:forEach items="${flowTraceList }" var="ds">
		                			<option value="${ds.pkey }" ${ds.pkey==params.flow_id?'selected':'' }>${ds.pname }</option>
		                		</c:forEach>
							</select>
		        </td>
		         <th>支付时间：</th>
                 <td>
                    <input type="text" name="params.pay_start" value="${params.pay_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
                    <input type="text" name="params.pay_end" value="${params.pay_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                 </td>
               </tr>
               
               <tr>
                   
                <th>支付方式：</th>
                <td>
                <script type="text/javascript">
                	$(function(){
                		////XMJ修改开始
                    		$("#pay_type_ivp,#pay_type_a,#pay_type_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                    	        $("#pay_type_dv").show();    //显示DIV
                    	        e.stopPropagation();//阻止事件冒泡
                    		})
                	    		
    						$(document).bind("click",function(){    
                    	        $("#pay_type_dv").hide();    //隐藏DIV
                    	  	}) 

    					   $("#pay_type_cancel1,#pay_type_cancel2").bind("click",function(e){
                    		   $("#pay_type_dv").hide();
                               e.stopPropagation();//阻止事件冒泡
                    		}); 
                    	   ////XMJ修改结束
                		$("#pay_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=pay_type]").attr("checked","checked");
                				$("#pay_type_dv li").addClass("curr");
                			}else{
                				$("input[name=pay_type]").removeAttr("checked");
                				$("#pay_type_dv li").removeClass("curr");
                			}
                			selectPayType();
                		});
                		
                		
                		$("input[name=pay_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectPayType();
                		});
                		
                		initCheckBox("pay_type_hivp","pay_type");
                		
                	});
                	
                	function selectPayType(){
            			var regions = $("input[name=pay_type]:checked");
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
            			$("#pay_type_ivp").val(regionNames);
            			$("#pay_type_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.payment_id_c" id="pay_type_ivp" value="${params.payment_id_c }" class="ipt" readonly="readonly" />
                    	<input type="hidden" name="params.payment_id" value="${params.payment_id }" id="pay_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="pay_type_a"></a>
                        <div class="selOp" style="display:none;" id="pay_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id="pay_type_checkall">全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="pay_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="pay_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${pay_type_list }" var="pt">
                            			<li name="pay_type_li"><input type="checkbox" name="pay_type" value="${pt.pkey }" c_name="${pt.pname }"><span name="pay_type_span">${pt.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
               <th>配送方式：</th>
                <td>
                	<script type="text/javascript">
                	$(function(){
                		/* $("#shipping_type_ivp,#shipping_type_a").bind("click",function(){
                    		$("#shipping_type_dv").show();
                    	});$("#shipping_type_cancel1,#shipping_type_cancel2").bind("click",function(){
                			$("#shipping_type_dv").hide();
                		}); */
                		
                	////XMJ修改开始
                		$("#shipping_type_ivp,#shipping_type_a,#shipping_type_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#shipping_type_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#shipping_type_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#shipping_type_cancel1,#shipping_type_cancel2").bind("click",function(e){
                		   $("#shipping_type_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
                	   ////XMJ修改结束  
                		$("#shipping_type_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=shipping_type]").attr("checked","checked");
                				$("#shipping_type_dv li").addClass("curr");
                			}else{
                				$("input[name=shipping_type]").removeAttr("checked");
                				$("#shipping_type_dv li").removeClass("curr");
                			}
                			selectShippintType();
                		});
                		
                		
                		$("input[name=shipping_type]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectShippintType();
                		});
                		
                		initCheckBox("shipping_type_hivp","shipping_type");
                		
                	});
                	
                	function selectShippintType(){
            			var regions = $("input[name=shipping_type]:checked");
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
            			$("#shipping_type_ivp").val(regionNames);
            			$("#shipping_type_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.shipping_id_c" id="shipping_type_ivp" value="${params.shipping_id_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.shipping_id" value="${params.shipping_id }" id="shipping_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="shipping_type_a"></a>
                        <div class="selOp" style="display:none;" id="shipping_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id=shipping_type_checkall>全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="shipping_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="shipping_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${shipping_type_list }" var="st">
                            			<li name="shipping_type_li"><input type="checkbox" name="shipping_type" value="${st.pkey }" c_name="${st.pname }"><span name="shipping_type_span">${st.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                 <th>创建时间：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                </td>
               </tr>
               
               <tr>
                <th>选择城市：</th>
                <td>
                <script type="text/javascript">
                	$(function(){
                		/* $("#region_ivp,#region_a").bind("click",function(){
                    		$("#region_div").show();
                    	});
                		$("#regionCancel,#regionCancel2").bind("click",function(){
                			$("#region_div").hide();
                		}); */
                		
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
                <th>生产模式：</th>
                <td> 
                  <html:selectdict  name="params.order_model"  appen_options="<option value=''>---请选择---</option>"  curr_val="${params.order_model}"   attr_code="DC_MODE_OPER_MODE" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:200px; background:url(${context}/images/ipt.gif) repeat-x;width:138px;"></html:selectdict>
                </td>
                 <th>异常类型：</th>
	                <td>
	                	<select name="params.exception_code" class="ipt_new"  style="width:150px;">
	                    	<c:forEach items="${exceptionTypeList }" var="qs"> 
	                			<option value="${qs.pkey }" ${qs.pkey==params.exception_code?'selected':'' }>${qs.pname }</option>
	                		</c:forEach>
						</select>
				     &nbsp;
				     <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		             &nbsp;
		             <input class="comBtn" type="button" name="excel" id="excelOrd" value="导出" style="margin-right:10px;"/>
	              
					 </td>
	            
               </tr>
               <tr>
	                <th>是否异常订单：</th>
	                <td>
	                	<select name="params.abnormal_type"  class="ipt_new" style="width:138px;">
	                    	<option value="-1" ${params.abnormal_type=='-1'?'selected':'' }>--请选择--</option>
	                    	<option value="0" ${params.abnormal_type=='0'?'selected':'' } >正常单</option>
	                    	<option value="1" ${params.abnormal_type=='1'?'selected':'' }>异常单</option>
						</select>
	                </td>
	                <th>wms退单状态：</th>
	                <td> 
	                    <html:selectdict  name="params.wms_refund_status"  appen_options="<option value=''>---请选择---</option>"  curr_val="${params.wms_refund_status}"   attr_code="WMS_REFUND_STATUS" style="border:1px solid #a4a4a4; height:18px; line-height:18px;width:138px; background:url(${context}/images/ipt.gif) repeat-x;width:138px;"></html:selectdict>
	                </td>
	                <th>发票号：</th>
	                <td> 
              		 	<input type="text" name="params.invoice_num" value="${params.invoice_num }" style="width:150px;" class="ipt_new">
	                </td>
               </tr>
                <tr>
 					<th>签收状态：</th>
	             		<td>
                		<select name="params.sign_status" class="ipt_new" style="width:138px;">
	                      <option value="">---请选择---</option>
	                       <option value="1">已签收</option>
	                       <option value="0">未签收</option>
		               </select>
		               <script type="text/javascript">
		                  $("[name='params.sign_status'] option[value='${params.sign_status}']").attr("selected","selected");
						</script>
	              </td> 
	              <th>回单状态：</th>
	              <td>
	                  <select name="params.receipt_status" class="ipt_new" style="width:138px;">
	                      <option value="">---请选择---</option>
	                      <option value="1">已签收</option>
	                      <option value="0">未签收</option>
		               </select>
		               <script type="text/javascript">
		                  $("[name='params.receipt_status'] option[value='${params.receipt_status}']").attr("selected","selected");
						</script>
	              </td>
              <th>是否预约单1：</th>
	             <td>
                	<select name="params.wm_isreservation_from"  class="ipt_new" style="width:138px;">
                    	<option value="0" ${params.wm_isreservation_from=='0'?'selected':'' }>否</option>
                    	<option value="1" ${params.wm_isreservation_from=='1'?'selected':'' }>是</option>
                    	<option value="-1" ${params.wm_isreservation_from=='-1'?'selected':'' }>--全部--</option>
					</select>
	              </td>
              </tr> 
             </tbody>
            </table>
        </div>

<script type="text/javascript">
	$(function(){
		
		$("#query_order_s").bind("click",function(){
			$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!showOrderList.do").submit();
		});
		//$("#order_from_dv").aramsDiv();
		$("#boxCodeId").bind("blur", function() {
			var boxCode = $("#boxCodeId").val();
			$.ajax({
				type : "post",
				async : false,
				url : "ordAuto!getOrdByBoxIdFromWMS.do?ajax=yes&boxCode="+boxCode,
				data : {},
				dataType : "json",
				success : function(data) {
					if (data!=undefined && data.result=="0") {
						$("input[name='params.order_id']").val(data.message);
						$("#query_order_s").click();
					}
				}
			});
		});
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