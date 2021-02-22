<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<script src="/ecs_ord/js/order_fuka.js"></script>
	<div class="grid_n_cont_sub" >
	    <h3>副卡信息：</h3>
	    <div class="grid_w_div">
		<form  method="post" id="phoneFukaInfoForm">
		<table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form" name="fukaxinxi">
			<c:forEach var="orderPhoneInfoFukaBusiRequest" items="${orderPhoneInfoFukaList}">	
				<tr id="tr${orderPhoneInfoFukaBusiRequest.phoneNum}">
					<th>副卡号码：</th>
					<c:choose>
						<c:when test="${orderPhoneInfoFukaBusiRequest.userType=='0'}">
						<td name="dianhuahaoma">
							<input readonly="readonly" type="text" value="${orderPhoneInfoFukaBusiRequest.phoneNum }" name="fuka_phonenum" id="phoneNum_${orderPhoneInfoFukaBusiRequest.phoneNum}" style="width: 128px;"/>
							<span><input type="button" value="变更号码" id="changePhoneFukaBtn" name="changePhoneFukaBtn" class="graybtn1" onclick="selFukaPhone('${orderPhoneInfoFukaBusiRequest.phoneNum}','${orderPhoneInfoFukaBusiRequest.inst_id}')"/></span>
		                </td>
		                <th>操作状态：</th>
		                <td>
				  			<span <c:if test="${orderPhoneInfoFukaBusiRequest.operatorstate=='2'}">style="color:black"</c:if> 
				  				id="spanOperState_${orderPhoneInfoFukaBusiRequest.phoneNum}">
				  				<c:forEach items="${phoneChangeStateList}" var="pm">
				  					<c:if test="${pm.pkey == orderPhoneInfoFukaBusiRequest.operatorstate}">${pm.pname }</c:if>
								</c:forEach>
				  			</span>                 
						</td>
						</c:when>
						<c:otherwise>
							<td name="dianhuahaoma" >${orderPhoneInfoFukaBusiRequest.phoneNum }</td>
						</c:otherwise>
					</c:choose>
				</tr>
				<tr>
					<th>套餐名称：</th>
					<td name="taocanmingcheng">${orderPhoneInfoFukaBusiRequest.productName}</td>
					<th>合约名称：</th>
					<td name="heyuemingcheng">${orderPhoneInfoFukaBusiRequest.activityName}</td>
					<th></th>
					<td></td>
				</tr>
				<tr>
					<th>入网类型：</th>
					<td name="ruwangleixing">
						<html:orderattr attr_code="DC_CUSTOMER_USER_TYPE" order_id="${order_id}"  field_name="bss_order_type" field_value="${orderPhoneInfoFukaBusiRequest.userType }"  field_desc ="入网类型" field_type="select" disabled="disabled" ></html:orderattr>
					</td>
					<th>卡类型：</th>
					<td name="kaleixing"><!-- 这里有个坑，filed_name用的其他字段的。不能不写，会报错，实际上其作用的是value，后续改进 -->
						<html:orderattr  attr_code="DC_PRODUCT_CARD_TYPE" order_id="${order_id}"  field_name="bss_order_type" field_value="${orderPhoneInfoFukaBusiRequest.cardType}"  field_desc ="卡类型" field_type="select" disabled="disabled" ></html:orderattr>
					</td>
					<th></th>
					<td></td>
				</tr>
				<tr><td colspan="6"><div style="border-bottom:1px dashed #c3c3c3;margin-left: 60px;margin-right: 50px" ></div></td></tr>
			</c:forEach>
		</table>
		</form>
		</div>
	</div>
<div id="selFukaPhoneDlg"></div>
<script type="text/javascript">
	$(function(){
		 Eop.Dialog.init({id:"selFukaPhoneDlg",modal:true,title:"选择号码",width:'800px'});
	});
	function selFukaPhone(old_phone_num,inst_id){
		 //alert("1")
		 $.ajax({
         	url:app_path+"/shop/admin/orderFlowAction!checkLockStatus.do?ajax=yes&order_id=${order_id}",
         	dataType:"json",
         	type:"POST",
         	data:$('phoneFukaInfoForm').serialize(),
         	success:function(reply){
         		//alert("2")
         		if(reply.result==1){
    			    var url= app_path+"/shop/admin/orderFlowAction!getPhoneNumFukaList.do?ajax=yes&numType=2&fukaInstId="+inst_id+"&isQuery=yes&order_id=${order_id}&old_phone_num="+old_phone_num;
    			    $("#selFukaPhoneDlg").load(url,{},function(){});
    				Eop.Dialog.open("selFukaPhoneDlg");
    			}else{
    				alert(reply.message);
    			}
         	},
         	error:function(a){
         		alert(a);
         	}
    	});
	}
</script>