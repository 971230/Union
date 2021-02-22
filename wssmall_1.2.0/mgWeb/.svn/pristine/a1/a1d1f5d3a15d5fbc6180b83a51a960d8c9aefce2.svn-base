<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
.more_div{ background:url(${context}/images/line.gif) center top no-repeat; text-align:center;}
.more_btn{ background:url(${context}/images/more_btn.gif) no-repeat; width:142px; height:21px; display:inline-block; color:#444; font-size:12px; line-height:21px;}
.more_open{ background:url(${context}/images/more_open.gif) no-repeat; width:15px; height:15px; display:inline-block; margin-right:5px; vertical-align:middle;}
.more_close{ background:url(${context}/images/more_close.gif) no-repeat; width:15px; height:15px; display:inline-block; margin-right:5px; vertical-align:middle;}
.input_text{width:160px;}

ul.tab li{min-width:70px;}
</style>

<script type="text/javascript" src="${ctx}/shop/admin/goods/js/apply_audit_info.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/select_goods_supplier.js"></script>
<script type="text/javascript" src="${ctx}/shop/admin/goods/js/select_goods_agreement.js"></script>

<div class="input">
<form  class="validate" method="post" action="" id='auditInfoForm' validate="true" >
<table cellspacing="0" cellpadding="0">
	<tbody>
		<input type="hidden" id="goods_id" name="goods.goods_id" value='${goodsView.goods_id }'/>
		<input type="hidden" id="ref_obj_id" name="ref_obj_id" value='${ref_obj_id}'/>	
		<input type="hidden" id="selfProdType" name="selfProdType" value='${selfProdType}'/>				
		<tr>
								<th><span class="red">*</span>产品名称：</th>
								<td><input type="text" name="goods.name" id="goods_name"
									class="input_text" dataType="string" required="true"
									maxlength="100" value="${goodsView.name }" style="width:250px"/>
								</td>
							</tr>
							<tr>
								<th><span class="red">*</span>产品编号：</th>
								<td><input type="text" name="goods.sn" class="input_text" id="goods_sn" required="true" 
									maxlength="200" value="${goodsView.sn}" />&nbsp;&nbsp;<span class="help_icon" helpid="goods_sn"></span>
									
									</td>
							</tr>
		<tr>
								<th><span class="red">*</span>框架选择：</th>
								<td>
								<input type="hidden" id="portion_type" name="goodsControl.control_type" value=${control_type}>
								<input type="hidden" id="sub_portion_type"  name="goodsControl.sub_control_type" value="${sub_control_type}">
								<input type="hidden" id="prtcl_id" name="goods.prtcl_id" value="${goodsView.prtcl_id}"/>
								
								<input type="text" id="prtcl_name"  class="input_text" readonly="readonly" required="true"
									value="${nameMap.agreement_name}" />&nbsp;&nbsp;<span class="help_icon" helpid="goods_sn"></span>
								
								<input type="button" id="select_agreement_btn_Aee" class="" style="width:50px" name="" value="选择" />
								
									</td>
							</tr>
								
								<tr id="MO" style="display:none;">
		                            <th><label class="text"><span class='red'>*</span>控制金额：</label></th>
		                            <td>
	                                <input type='text' class='ipttxt' name="money_control_value"  id='MO_id' <c:if test="${control_type=='MO'}"> value="${control_value}"</c:if>   required="true"/>
		                            </td>		
	                            </tr>
	                            <tr id="PN" style="display:none;">
		                            <th><label class="text"><span class='red'>*</span>产品控制数量：</label></th>
		                            <td>
		                            <input type='text' class='ipttxt' name="qty_control_value" id='PN_id' <c:if test="${control_type=='PN'}">value="${control_value}"</c:if>  required="true"/>
		                            </td>		
	                            </tr>
	                            
	                             <tr id="CO" style="display:none;">
		                          <td>
		                            <div  class="tab-panel division"  style='border:0px solid red;width:100px;'>
	                                   <div style='border:0px solid red;width:50%;' >
	                                      <table  class="form-table" style='text-align:center;width:330px;'>
		                                     <thead>
	                                            <tr>
	                                              <th></th>
	        	                                  <th style='width:100px;text-align:right;padding-right:30px;'>地市公司</th>
	 			                                  <th style='text-align:left;'>控制数量(或金额)</th>
	                                            </tr>
     	                                      </thead>
		                                    <tbody id="code">
	 		                                      <c:forEach var="lan" items="${codeList}" >
	                                                 <tr>
                                                   <td>  <input type="checkbox"  attr_founder='ck_founder' name="goodsControl.control_lan_code" value="${lan.pkey }" id="oneAgentChk" lname="${lan.pname }" ></td>
                                                   <td  style='width:100px;text-align:right;padding-right:30px;'>${lan.pname }</td>
		                                           <td>  <input type='text' class='ipttxt' name="control_lan_code"   id="${lan.pkey }"    style="width:60px;height:20px" /></td>
	                                              </tr>
		
		                                         </c:forEach>
	 	                                     
		                                     </tbody>
                                    	</table>
                                     </div>
                                   </div>
		                          </td>
		                         </tr>
		  
	                         	<input type="hidden" name ="codeStr" id="codeStr" />
	                            <input type="hidden" name ="controlValStr" id="controlValStr" />
	                           </tr>
	                           
							
							<tr>
								<th><span class="red">*</span>电信集采价格：</th>
								<td><input type="text" name="goods.price" id="goods_price"
				                     class="input_text" dataType="string" required="true"
				                     value="${goodsView.price}" style="width:250px"/>
			                   </td>
							</tr>	
							
		                    <tr>
								<td colspan="2">
									<div class="form_grid_btn">
    									<a class="formbtn" id="apply_work_flow">确定</a>
    								</div>
    							</td>
							</tr>
	</tbody>
</table>
</form>

</div>

<div id="refAgreementDlg_A"></div>
<div title="供应商" id="refsupplierDlg_A"></div>
<script type="text/javascript">
        $("form.validate").validate();
	    $("#apply_work_flow").unbind();
		$("#apply_work_flow").bind("click",function(){
		
		     if(validataAuditInfo()){
		       getAgreementCode();
		        var options = {
					url :basePath+'goods!saveGoodsTemp.do?ajax=yes',
					type : "POST",
					dataType : 'json',
					success : function(result) {
						if(result.result == 0){
					  		alert("修改保存成功！");
		              		Eop.Dialog.close("auditObjMsgDialog");	
		              		//window.close();
						}
					},
					error : function(e) {
						alert("出现错误 ，请重试");
					}
				};

				$('#auditInfoForm').ajaxSubmit(options);
		     
		     }
        });
        
        function validataAuditInfo(){
            var goods_name = $("#goods_name").val();
            if(goods_name ==undefined || goods_name == null || goods_name ==""){
               return false;
            }
            
		    var prtcl_name = $("#prtcl_name").val();
		    if(prtcl_name ==undefined || prtcl_name == null || prtcl_name ==""){
               return false;
            }
            
		    var goods_sn = $("#goods_sn").val();
		    if(goods_sn ==undefined || goods_sn == null || goods_sn ==""){
               return false;
            }
            
            
		    var goods_price = $("#goods_price").val();
		    if(goods_price ==undefined || goods_price == null || goods_price ==""){
               return false;
            }
		
		
		    return true;
        }
        
        function getAgreementCode(){
    	 var controlTypeValue = $("#portion_type").val();
    	 if(controlTypeValue=='CO'){
    	      var codeArr = [];
    	      var controlValArr = [];
    	      $("[name='goodsControl.control_lan_code']:checked").each(function(){
    	   					var codeValue = $(this).val();
    	   					var control_val = $("#"+codeValue).val();
    	   					codeArr.push(codeValue);
    	   					controlValArr.push(control_val);
    	   	});
    	    var codeStr = codeArr.join(",");
    	    var controlValStr = controlValArr.join(",");
    	   	$("#codeStr").val(codeStr);
    	   	$("#controlValStr").val(controlValStr);
    	}
  }

</script>

<c:if test="${is_edit==true}">
	<script>
$(function(){ 
 
    <c:forEach var="control" items="${goodsControlList }">
     
		$("#code input[value='${control.control_lan_code}']").attr("checked",true);
		
	   $("#code input[id='${control.control_lan_code}']").val(${control.control_value});
	</c:forEach>
       
                var control_type=  $("#portion_type").val();//${control_type};
				var sub_control_type = $("#sub_portion_type").val();//${sub_control_type};

				if(control_type=='NO'){
				    $("#PN_id option[value='0']").attr("selected", true);
				    $("#MO_id option[value='0']").attr("selected", true);
				}else if(control_type=='CO'){
				    $("#"+control_type).show();
				    $("#"+sub_control_type+"_id option[value='1']").attr("selected", true);
				    $("#"+sub_control_type+"_control").show();
				
                    } else{
				    $("#"+control_type).show();
				  
				    $("#"+control_type+"_id option[value='1']").attr("selected", true);
				     
				    $("#"+control_type+"_control").show();
				   }  

					<c:if test="${join_suced==true}">
					   	 $("#prch_supplier_btn").hide();
					   	 $("#select_agreement_btn").hide();
                         $("[name ='goodsControl.control_lan_code']").attr("disabled","disabled");
                         $("[name ='control_lan_code']").attr("disabled","disabled");
					</c:if>  
     
});
</script>
</c:if>

