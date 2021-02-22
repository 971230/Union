<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String exception_type = request.getParameter("exception_type");
	request.setAttribute("exception_type", exception_type);
%>
<div class="searchBx">
        	<a href="javascript:void(0);" id="hide_params_tb" class="arr open">收起</a>
        	<a href="javascript:void(0);" id="show_params_tb" class="arr close" style="display:none;">展开</a>
        	<input type="hidden" name="params.query_btn_flag" value="yes" />
        	<table id="params_tb" width="100%" border="0" cellspacing="0" cellpadding="0" class="tab_form">
             <tbody id="tbody_A">
                <tr>
                	<%--流程环节开始 --%>
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
	                    	<input type="text" name="eiqInner.flow_id_c" id="flow_id_ivp" value="${eiqInner.flow_id_c }" class="ipt" readonly="readonly" />
	                    	<input type="hidden" name="eiqInner.flow_id" value="${eiqInner.flow_id }" id="flow_id_hivp" />
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
	                            		<li name="flow_id_li"><input type="checkbox" name="flow_id" value="GJ" c_name="订单归集"><span name="flow_id_span">订单归集</span></li>
	                                </ul>
	                            </div>
	                        </div>
	                    </span>
		              </td>
                	
                	<%--开户号码开始 --%>
	              	<th>开户号码：</th>
                  	<td>
                  		<input type="text" name="eiqInner.phone_num" value="${eiqInner.phone_num }" style="width:138px;" class="ipt_new">
                  	</td>
                	
                	<th>订单创建时间：</th>
                	<td>
                    	<input type="text" name="eiqInner.start_obj_create_time" value="${eiqInner.start_obj_create_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
                    	<input type="text" name="eiqInner.end_obj_create_time" value="${eiqInner.end_obj_create_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
                	</td>
                </tr>
                <tr>
                	<th>外部单号：</th>
		            <td>
	                	<input type="text" name="eiqInner.out_tid" value="${eiqInner.out_tid }" id="out_tid" style="width:138px;" class="ipt_new">		            
		            </td>
                 	<th>内部单号：</th>
                	<td>
	                	<input type="text" name="eiqInner.rel_obj_id" value="${eiqInner.rel_obj_id }" id="rel_obj_id" style="width:138px;" class="ipt_new">		                           	
                	</td>
                	
	                <th>异常创建时间：</th>
	                <td>
	                    <input type="text" name="eiqInner.start_excp_time" value="${eiqInner.start_excp_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">-
	                    <input type="text" name="eiqInner.end_excp_time" value="${eiqInner.end_excp_time }" readonly="readonly" class="ipt_new" onfocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})">
	                </td>
	            </tr>
	            
	            <tr>
	            	<th>异常TOP10：</th>
	                <td>
	                	<select name="eiqInner.key_id" style="width:138px" class="ipt_new">
              				<option value="">
              						请选择
            				</option>
              				<c:forEach var="topExpKey" items="${topExpKeys}">
              					<option value="${topExpKey.key_id }" <c:if test="${eiqInner.key_id == topExpKey.key_id }">selected</c:if> >
              						${topExpKey.counts } | ${topExpKey.match_content }
              					</option>
              				</c:forEach>
						</select>
	                </td>
	                <%--是否历史数据 --%>
		                <th>是否历史数据：</th>
		            	<td>
		            		<select name="eiqInner.is_history" style="width:138px" class="ipt_new">
		            			<option value="0" <c:if test="${eiqInner.is_history == '0' || empty eiqInner.is_history }">selected</c:if> >否</option>
		            			<option value="1" <c:if test="${eiqInner.is_history == '1' }">selected</c:if> >是</option>
		            		</select>
		            	</td>
	                
		             
		             <%--选择城市开始 --%>
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
		                    	<input type="text" name="eiqInner.order_city_code_c" id="region_ivp" value="${eiqInner.order_city_code_c }" class="ipt" readonly="readonly">
		                    	<input type="hidden" name="eiqInner.order_city_code" value="${eiqInner.order_city_code }" id="region_hivp" />
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
		                    
		                    <a href="javascript:void(0);" id="query_orderexp_s" class="dobtn" style="margin-left:5px;">搜&nbsp;&nbsp;&nbsp;&nbsp;索</a>
			              	<a href="javascript:void(0);" id="showOrderExpMarkProcessedBtn" class="dobtn" style="margin-left:5px;">标记已处理</a>
			              	<a href="javascript:void(0);" id="showOrderBatchExpProcessedBtn" class="dobtn" style="margin-left:5px;">批量处理</a>
		                </td>
	            </tr>
	            </tbody>
                <tbody id="param_tb_F">
                	<tr>
	          	 		<th>异常归类：</th>
						<td>
							<input type="hidden" id="catalog_id" name="eiqInner.catalog_id" value="${eiqInner.catalog_id }" />
		              		<input type="text" class="ipt_new" id="catalog_name" style="width:138px" name="eiqInner.catalog_name" value="${eiqInner.catalog_name }" />
			              	<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1"  id="selectCatalogsBtn"><span>选择</span></a>
		                </td>
		                
			            <th>关联类型：</th>
		                <td>
		                	<select name="eiqInner.rel_obj_type" style="width:138px" class="ipt_new">
			               	    <option value="">请选择</option>
			                    <option value="order" <c:if test="${eiqInner.rel_obj_type == 'order' }"> selected</c:if>>订单</option>
			                    <option value="order_queue" <c:if test="${eiqInner.rel_obj_type == 'order_queue' }"> selected</c:if>>订单队列</option>
							 </select> 	
						</td>
		              
		              	<th>处理状态：</th>
		              	<td>
					  		<select name="eiqInner.record_status" style="width:138px" class="ipt_new">
			          			<option value="">请选择</option>
			                	<option value="0" <c:if test="${eiqInner.record_status == '0' }"> selected</c:if>>未处理</option>
			                	<option value="1" <c:if test="${eiqInner.record_status == '1' }"> selected</c:if>>已处理</option>
							</select> 	                
						</td>
		            </tr>
		            
		            <tr>
		            	<th>搜索编码：</th>
						<td>
							<input type="hidden" id="search_id" name="eiqInner.search_id" value="${eiqInner.search_id }" />
	              			<input type="text" class="ipt_new" id="search_code" style="width:138px" name="eiqInner.search_code" value="${eiqInner.search_code }" />
		              		<a href="javascript:void(0)" style="margin-right:10px;" class="graybtn1"  id="selectSpecsBtn"><span>选择</span></a>
	                	</td>
		                
		                <%--订单来源开始 --%>
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
		                    	<input type="text" name="eiqInner.order_from_c" id="order_from_vp" value="${eiqInner.order_from_c }" class="ipt" readonly="readonly">
		                    	<input type="hidden" name="eiqInner.order_from" value="${eiqInner.order_from }" id="order_from_ivp" />
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
		                <th>人工标记类型:</th>
		            	<td>
		            		<select name="eiqInner.artificial_exception_type" style="width:138px" class="ipt_new">
		            			<option value="-1">请选择异常类型</option>
		            			<c:forEach var="exception" items="${exceptionList }" varStatus="var_status">
									<option value="${exception.key_id }" <c:if test="${eiqInner.artificial_exception_type == exception.key_id }">selected</c:if> >${exception.match_content }</option>
								</c:forEach>
		            		</select>
		            	</td>
		            </tr>
		            <tr>
		            	<th>解决方案：</th>
		            <td>
		                <select name="eiqInner.shortcut_solution_id" style="width:138px" class="ipt_new">
              				<option value="">
              						请选择
            				</option>
              				<c:forEach var="satalogAndSolution" items="${satalogAndSolutions}">
              					<option value="${satalogAndSolution.solution_id }" <c:if test="${eiqInner.shortcut_solution_id == satalogAndSolution.solution_id }">selected</c:if>>
              						${satalogAndSolution.solution_name }
              					</option>
              				</c:forEach>
						</select>
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
	$("#query_orderexp_s").bind("click",function(){
		$.Loading.show("正在加载所需内容，请稍侯...");
		$("#order_exp_param_f").attr("action",ctx+"/shop/admin/orderExp!list.do").submit();
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