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
                    <th>外部单号：</th>
	                <td><input type="text" name="params.out_tid" value="${params.out_tid }" style="width:138px;" class="ipt_new"></td>
             
                    <th>商品名称：</th>
	                <td><input type="text" name="params.goods_name" value="${params.goods_name }" style="width:138px;" class="ipt_new"></td>
	                
	                <th>创建时间：</th>
	                <td>
	                    <input type="text" name="params.create_start" value="${params.create_start }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="params.create_end" value="${params.create_end }" readonly="readonly" class="ipt_new"  onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                </td>

             </tr>
                <tr>
                	<th>开户号码：</th>
	                <td><input type="text" name="params.phone_num" value="${params.phone_num }" style="width:138px;" class="ipt_new"></td>
                    
                    <th>开户人姓名：</th>
	                <td><input type="text" name="params.phone_owner_name" value="${params.phone_owner_name }" style="width:138px;" class="ipt_new"></td>
             	    
                    <th>联系电话：</th>
	                <td><input type="text" name="params.ship_mobile" value="${params.ship_mobile }" style="width:138px;" class="ipt_new"></td>
                    
                
                </tr>
                <tr>
                     <th>处理人选：</th>
               <td>
               <input type="text" class="ipt_new" style="width:138px;" id="username" name="username" value="${username }" onfocus="queryUserId()"/>
			   <input type="hidden" class="ipt_new" style="width:138px;" id="lock_user_id" name="params.lock_user_id" value="${params.lock_user_id }" />
               <a href="javascript:void(0);" id="resetBtn" class="dobtn" style="margin-right:10px;">清除</a>
               </td>
                  
               </tr>
               
               <tr>
               
                  <th>数据来源：</th>
                <td>
                	<script type="text/javascript">
                	$(function(){


										////XMJ修改开始
                		$("#data_src_ivp,#data_src_a,#data_src_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#data_src_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#data_src_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#data_src_cancel1,#data_src_cancel2").bind("click",function(e){
                		   $("#data_src_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#data_src_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=data_src]").attr("checked","checked");
                				$("#data_src_dv li").addClass("curr");
                			}else{
                				$("input[name=data_src]").removeAttr("checked");
                				$("#data_src_dv li").removeClass("curr");
                			}
                			selectorder_from();
                		});
                		
                		
                		$("input[name=data_src]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectorder_from();
                		});
                		
                		initCheckBox("data_src_hivp","data_src");
                		
                	});
                	
                	function selectorder_from(){
            			var regions = $("input[name=data_src]:checked");
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
            			$("#data_src_ivp").val(regionNames);
            			$("#data_src_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.data_from_c" id="data_src_ivp" value="${params.data_from_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.data_from" value="${params.data_from}" id="data_src_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="data_src_a"></a>
                        <div class="selOp" style="display:none;" id="data_src_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id=data_src_checkall>全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="data_src_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="data_src_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${data_src_list }" var="st">
                            			<li name="data_src_li"><input type="checkbox" name="data_src" value="${st.pkey }" c_name="${st.pname }"><span name="data_src_span">${st.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>

               
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
                            			<li name="order_from_li"><input type="checkbox" name="order_from" value="${of.value}" c_name="${of.value_desc}"><span name="order_from_span">${of.value_desc}</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                
                
                
                
                
                
                     <th>订单类型：</th>
                <td>
                	<script type="text/javascript">
                	$(function(){


										////XMJ修改开始
                		$("#order_type_ivp,#order_type_a,#order_type_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#order_type_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#order_type_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#order_type_cancel1,#order_type_cancel2").bind("click",function(e){
                		   $("#order_type_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
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
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.order_type_c" id="order_type_ivp" value="${params.order_type_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_type" value="${params.order_type }" id="order_type_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="order_type_a"></a>
                        <div class="selOp" style="display:none;" id="order_type_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id=order_type_checkall>全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="order_type_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="order_type_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${order_type_list }" var="st">
                            			<li name="order_type_li"><input type="checkbox" name="order_type" value="${st.pkey }" c_name="${st.pname }"><span name="order_type_span">${st.pname }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>

               </tr>
               
               <tr>
               
               
            <th>订单状态环节：</th>
                <td>
                	<script type="text/javascript">
                	$(function(){
                		
                		
										////XMJ修改开始
                		$("#dcmode_orderstatus_ivp,#dcmode_orderstatus_a,#dcmode_orderstatus_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#dcmode_orderstatus_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#dcmode_orderstatus_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#dcmode_orderstatus_cancel1,#dcmode_orderstatus_cancel2").bind("click",function(e){
                		   $("#dcmode_orderstatus_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#dcmode_orderstatus_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=dcmode_orderstatus]").attr("checked","checked");
                				$("#dcmode_orderstatus_dv li").addClass("curr");
                			}else{
                				$("input[name=dcmode_orderstatus]").removeAttr("checked");
                				$("#dcmode_orderstatus_dv li").removeClass("curr");
                			}
                			selectdcmodeOrderStatus();
                		});
                		
                		
                		$("input[name=dcmode_orderstatus]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectdcmodeOrderStatus();
                		});
                		
                		initCheckBox("dcmode_orderstatus_hivp","dcmode_orderstatus");
                		
                	});
                	
                	function selectdcmodeOrderStatus(){
            			var regions = $("input[name=dcmode_orderstatus]:checked");
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
            			$("#dcmode_orderstatus_ivp").val(regionNames);
            			$("#dcmode_orderstatus_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.dcmodeOrderStatus_id_c" id="dcmode_orderstatus_ivp" value="${params.dcmodeOrderStatus_id_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.dcmodeOrderStatus_id" value="${params.dcmodeOrderStatus_id }" id="dcmode_orderstatus_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="dcmode_orderstatus_a"></a>
                        <div class="selOp" style="display:none;" id="dcmode_orderstatus_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id=dcmode_orderstatus_checkall>全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="dcmode_orderstatus_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="dcmode_orderstatus_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${dc_MODE_OrderStatusList }" var="st">
                            			<li name="dcmode_orderstatus_li"><input type="checkbox" name="dcmode_orderstatus" value="${st.value }" c_name="${st.value_desc }"><span name="dcmode_orderstatus_span">${st.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                
                
               <th>新老用户：</th>
	             		<td>
                		<select name="params.is_old" class="ipt_new" style="width:138px;">
	                      <option value="">---请选择---</option>
	                       <option value="1">老用户</option>
	                       <option value="0">新用户</option>
		               </select>
		               <script type="text/javascript">
		                  $("[name='params.is_old'] option[value='${params.is_old}']").attr("selected","selected");
						</script>
               
               
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
                
             <th>商品类型：</th>
                <td>
                	<script type="text/javascript">
                	$(function(){
                		
                		
										////XMJ修改开始
                		$("#dcmode_goodstype_ivp,#dcmode_goodstype_a,#dcmode_goodstype_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#dcmode_goodstype_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#dcmode_goodstype_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#dcmode_goodstype_cancel1,#dcmode_goodstype_cancel2").bind("click",function(e){
                		   $("#dcmode_goodstype_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#dcmode_goodstype_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=dcmode_goodstype]").attr("checked","checked");
                				$("#dcmode_goodstype_dv li").addClass("curr");
                			}else{
                				$("input[name=dcmode_goodstype]").removeAttr("checked");
                				$("#dcmode_goodstype_dv li").removeClass("curr");
                			}
                			selectdcmodeGoodsType();
                		});
                		
                		
                		$("input[name=dcmode_goodstype]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectdcmodeGoodsType();
                		});
                		
                		initCheckBox("dcmode_goodstype_hivp","dcmode_goodstype");
                		
                	});
                	
                	function selectdcmodeGoodsType(){
            			var regions = $("input[name=dcmode_goodstype]:checked");
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
            			$("#dcmode_goodstype_ivp").val(regionNames);
            			$("#dcmode_goodstype_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.dcmodeGoodsType_id_c" id="dcmode_goodstype_ivp" value="${params.dcmodeGoodsType_id_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.dcmodeGoodsType_id" value="${params.dcmodeGoodsType_id }" id="dcmode_goodstype_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="dcmode_goodstype_a"></a>
                        <div class="selOp" style="display:none;" id="dcmode_goodstype_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id=dcmode_goodstype_checkall>全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="dcmode_goodstype_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="dcmode_goodstype_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${goods_type_list }" var="st">
                            			<li name="dcmode_goodstype_li"><input type="checkbox" name="dcmode_goodstype" value="${st.value }" c_name="${st.value_desc }"><span name="dcmode_goodstype_span">${st.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                
                
                
<th>活动类型：</th>
                <td>
                	<script type="text/javascript">
                	$(function(){
                		
                		
										////XMJ修改开始
                		$("#dcmode_activitytype_ivp,#dcmode_activitytype_a,#dcmode_activitytype_dv").bind("click",function(e){    //给按钮注册单击事件，点击显示DIV
                	        $("#dcmode_activitytype_dv").show();    //显示DIV
                	        e.stopPropagation();//阻止事件冒泡
                		})
            	    		
						$(document).bind("click",function(){    
                	        $("#dcmode_activitytype_dv").hide();    //隐藏DIV
                	  	}) 

					   $("#dcmode_activitytype_cancel1,#dcmode_activitytype_cancel2").bind("click",function(e){
                		   $("#dcmode_activitytype_dv").hide();
                           e.stopPropagation();//阻止事件冒泡
                		}); 
            		////XMJ修改结束                		
                		$("#dcmode_activitytype_checkall").bind("click",function(){
                			if(this.checked){
                				$("input[name=dcmode_activitytype]").attr("checked","checked");
                				$("#dcmode_activitytype_dv li").addClass("curr");
                			}else{
                				$("input[name=dcmode_activitytype]").removeAttr("checked");
                				$("#dcmode_activitytype_dv li").removeClass("curr");
                			}
                			selectActivityType();
                		});
                		
                		
                		$("input[name=dcmode_activitytype]").bind("click",function(){
                			if(this.checked){
                				$(this).parents("li").addClass("curr");
                			}else{
                				$(this).parents("li").removeClass("curr");
                			}
                			selectActivityType();
                		});
                		
                		initCheckBox("dcmode_activitytype_hivp","dcmode_activitytype");
                		
                	});
                	
                	function selectActivityType(){
            			var regions = $("input[name=dcmode_activitytype]:checked");
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
            			$("#dcmode_activitytype_ivp").val(regionNames);
            			$("#dcmode_activitytype_hivp").val(regionIds);
            		}
                </script>
                	<span class="selBox" style="width:120px;">
                    	<input type="text" name="params.dcmodeActivityType_id_c" id="dcmode_activitytype_ivp" value="${params.dcmodeActivityType_id_c }" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.dcmodeActivityType_id" value="${params.dcmodeActivityType_id }" id="dcmode_activitytype_hivp" />
                    	<a href="javascript:void(0);" class="selArr" id="dcmode_activitytype_a"></a>
                        <div class="selOp" style="display:none;" id="dcmode_activitytype_dv">
                        	<div class="allSel">
                            	<label><input type="checkbox" id=dcmode_activitytype_checkall>全选</label>
                                <label><a href="javascript:void(0);" class="aCancel" id="dcmode_activitytype_cancel1">关闭</a></label>
                                <label><a href="javascript:void(0);" class="aClear" id="dcmode_activitytype_cancel2"></a></label>
                            </div>
                            <div class="listItem">
                            	<ul>
                            		<c:forEach items="${activity_type_list }" var="st">
                            			<li name="dcmode_activitytype_li"><input type="checkbox" name="dcmode_activitytype" value="${st.value }" c_name="${st.value_desc }"><span name="dcmode_activitytype_span">${st.value_desc }</span></li>
                            		</c:forEach>
                                </ul>
                            </div>
                        </div>
                    </span>
                </td>
                
                

                
                
               </tr>
 
 <tr>
 
  <th>所在地市：</th>
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
                    	<input type="text" name="params.order_city_code_c" id="region_ivp" value="${params.order_city_code_c}" class="ipt" readonly="readonly">
                    	<input type="hidden" name="params.order_city_code" value="${params.order_city_code}" id="region_hivp" />
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
 
                 <th></th>
               <td>
				     <input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
		            &nbsp; &nbsp;<input class="comBtn" type="button" name="excel" id="excelOrd" value="导出" style="margin-right:10px;"/>
               </td>
 
 
 </tr>
             </tbody>
            </table>
        </div>
        

        
        
<div id="queryUserListDlg"></div>
<script type="text/javascript">

$(function (){
	Eop.Dialog.init({id:"queryUserListDlg",modal:true,title:"营业日报", width:"800px"});
	
	$("#resetBtn").click(function (){
		document.getElementById("username").value = "";
		document.getElementById("lock_user_id").value = "";
	});

});


function queryUserId(){
	Eop.Dialog.open("queryUserListDlg");
	lock_user_id = $("#lock_user_id").val();
	var url= ctx+"/shop/admin/ordAuto!queryUser.do?ajax=yes&allotType=query";
	    $("#queryUserListDlg").load(url,{},function(){});
}  
</script> 

<script type="text/javascript">
	$(function(){
		
		$("#query_order_s").bind("click",function(){
			$("#aito_order_f").attr("action",ctx+"/shop/admin/ordAuto!getOrderDataBySearch.do").submit();
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