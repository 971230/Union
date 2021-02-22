<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
 loadScript("js/goods_agreement.js");
 loadScript("js/select_supplier.js");
</script>

<h3>
<div class="stat_graph">
       	<h3>
               <div class="graph_tab">
                   <ul>
                     
                       <li id="show_click_2" class="selected"><span class="word">添加框架协议</span><span class="bg"></span></li>
                       <div class="clear"></div>
                   </ul>
               </div>
           </h3>
 </div>
</h3>   
<div class="input">
<form  action="javascript:void(0)" class="validate" method="post" name="goods_agreement" id="goods_agreement" enctype="multipart/form-data">
<table  class="form-table">


     <tr>
		<th><label class="text"><span class='red'>*</span>协议名称：</label></th>
		<td><input type="text" class="ipttxt"  name="agreement.agreement_name"  id="realname"  required="true"  /></td>
	</tr>
	
	
	
	<tr id="crmTr" >
	 <th>供应商：</th>
	 <td>
		<!--  <input type='hidden' name='adminUser.relno' id='refCrm_no'  value="" />-->
		<input type='text' class='ipttxt' readonly='readonly' name='supplier_name' id='supplier_name'/>
		<input type="hidden" id="supplier_id" name='agreement.supplier_id' value="${agreement.supplier_id}">
		 <input type="button" id="refsupplierBtn" class="comBtn" name="" value="关联供应商">
		<!-- <a class="sgreenbtn" href="#">
		    <span id="refsupplierBtn" >关联供应商</span>
						</a> -->
		 </td>
	    <!--<input type="button" id="refStaffBtn" value="关联CRM" class="comBtn" />-->    
	
	</tr>
	<input type="hidden" value="OL" pay_type_name="在线支付"  name = "agreement.pay_type"">
<!--  	<tr id="lantr">
		<th><label class="text"><span class='red'>*</span>付款方式：</label></th>
		<td>
		
			<select  class="ipttxt"  style="width:16%" id="lan_sel" name = "agreement.pay_type" required="true">
                 <option  value="">--请选择--</option>
                <option  value="OL">在线支付</option>
			     <!--  <c:forEach var="lan" items="${lanList}">
				 	<option  value="${lan.lan_id }" ${lan_id == lan.lan_id ? ' selected="selected" ' : ''}>${lan.lan_name }</option>
				 </c:forEach>
				 -->
			<!-- </select>
			
		</td>		
	</tr>
	-->
	<tr id="lantr">
		<th><label class="text"><span class='red'>*</span>协议编号类型：</label></th>
		<td>
		    <input type="hidden"  id="agreementCode"  name = "agreement.agreement_code">
		    <c:forEach var ="companyType" items="${companyTypeList}">
		      <input type="checkbox" name="checkCompany" value ="${companyType.pkey }" txt_vlaue= "${companyType.pname }" >${companyType.pname }
		
		    </c:forEach>
			<!-- <input type="checkbox" name="checkCompany" value ="GF" txt_vlaue= "股份公司" >股份公司
			<input type="checkbox" name="checkCompany" value ="XH" txt_vlaue= "信产公司" >信产公司
			<input type="checkbox" name="checkCompany" value ="ZD" txt_vlaue= "终端公司">终端公司
			<input type="checkbox" name="checkCompany" value ="TF" txt_vlaue= "通服公司">通服公司
			<input type="checkbox" name="checkCompany" value ="CX" txt_vlaue= "存续公司">存续公司
			 -->
		</td>		
	</tr>
	<tr id="HB" style="display:none;">
		<th><label class="text"><span class='red'>*</span>号百公司协议编号：</label></th>
		<td>
		   <input type='text' class='ipttxt'  id='HB_id'  dataType="string" required="true"/>
		</td>		
	</tr>
	<tr id="XH" style="display:none;">
		<th><label class="text"><span class='red'>*</span>信产公司协议编号：</label></th>
		<td>
		   <input type='text' class='ipttxt'  id='XH_id'  dataType="string" required="true"/>
		</td>		
	</tr>
	<tr id="GF" style="display:none;">
		<th><label class="text"><span class='red'>*</span>股份公司协议编号：</label></th>
		<td>
		   <input type='text' class='ipttxt'  id='GF_id'  dataType="string" required="true"/>
		</td>		
	</tr>
	
	<tr id="ZD" style="display:none;">
		<th><label class="text"><span class='red'>*</span>终端公司协议编号：</label></th>
		<td>
		   <input type='text' class='ipttxt'  id='ZD_id'  dataType="string" required="true"/>
		</td>		
	</tr>
	<tr id="TF" style="display:none;">
		<th><label class="text"><span class='red'>*</span>通服公司协议编号：</label></th>
		<td>
		   <input type='text' class='ipttxt'  id='TF_id'  dataType="string" required="true"/>
		</td>		
	</tr>
	<tr id="CX" style="display:none;">
		<th><label class="text"><span class='red'>*</span>存续公司协议编号：</label></th>
		<td>
		   <input type='text' class='ipttxt'  id='CX_id'  dataType="string" required="true"/>
		</td>		
	</tr>
	
	<tr id="crmTr" >
	 <th><span class='red'>*</span>关联流程：</th>
	 <td>
	   
		<!--  <input type='hidden' name='adminUser.relno' id='refCrm_no'  value="" />-->
		<input type='text' class='ipttxt'  name='agreement.flow_id' id='flow_id'  dataType="string" required="true"/>
	<!-- 	<a class="sgreenbtn" href="#">
		    <span id="refStaffBtn" >关联流程</span>
						</a>
	 -->
		 </td>
	    <!--<input type="button" id="refStaffBtn" value="关联CRM" class="comBtn" />-->    
	
	</tr>
	
	<tr id="crmTr" >
	 <th><span class='red'>*</span>订单管理员：</th>
	 <td>
		<!--  <input type='hidden' name='adminUser.relno' id='refCrm_no'  value="" />-->
		<input type='text' class='ipttxt'  name='agreement.order_manager' id='order_manager'   dataType="string" required="true"/>
		 </td>
	    <!--<input type="button" id="refStaffBtn" value="关联CRM" class="comBtn" />-->    
	
	</tr>
	
	
	<tr>
		<th><label class="text"><span class='red'>*</span>份额控制：</label></th>
		<td>
		    <input type="hidden" id="controlType" name = "agreementControl.control_type">  
		    <input type="hidden" id="controlName" name = "agreementControl.control_name">
		    <c:forEach var ="controlType" items ="${controlTypeList}">
		      <input type="radio"   name="agreement.portion" control_name= "${controlType.pname}" value="${controlType.pkey }" id="notSuperChk" >${controlType.pname }&nbsp;&nbsp;
		   </c:forEach>
		  <!-- <input type="radio"   name="agreement.portion" control_name= "无份额控制" value="NO" id="notSuperChk" checked="true">无份额控制&nbsp;&nbsp;
		     <input type="radio"  attr_founder='ck_founder' name="agreement.portion" control_name= "按金额控制" value="MO" id="notSuperChk">按金额控制&nbsp;&nbsp;
		     <input type="radio"  attr_founder='ck_founder' name="agreement.portion" control_name= "按产品数量控制" value="PN" id="oneAgentChk" >按产品数量控制
		     <input type="radio"  attr_founder='ck_founder' name="agreement.portion" control_name= "按地市控制" value="CO" id="oneAgentChk" >按地市控制
		    -->  
		</td>
	</tr>
	<tr id="MO" style="display:none;">
		<th><label class="text"><span class='red'>*</span>控制金额：</label></th>
		<td>
	      <input type='text' class='ipttxt' name="money_control_value"  id='MO_id'   required="true"/>
		</td>		
	</tr>
	<!-- 
	<tr id="PN" style="display:none;">
		<th><label class="text"><span class='red'>*</span>产品控制数量：</label></th>
	    
		<td>
		    <input type='text' class='ipttxt' name="qty_control_value" id='PN_id' disabled="disabled"  required="true"/>
		</td>		
	</tr>
	 -->
		<tr id="CO" style="display:none;">
		<th><label class="text"><span class='red'>*</span>控制类型：</label></th>
		
		<td>
	      <c:forEach var ="subControlType" items ="${subControlTypeList}">
		    <input type="radio"  attr_founder='ck_founder' name="agreementControl.sub_control_type" value="${subControlType.pkey }" id="notSuperChk" >${subControlType.pname }&nbsp;&nbsp;
	      </c:forEach>
		  <!--  
		<input type="radio"  attr_founder='ck_founder' name="agreementControl.sub_control_type" value="MO" id="notSuperChk" >按金额控制&nbsp;&nbsp;
	    <input type="radio"  attr_founder='ck_founder' name="agreementControl.sub_control_type" value="PN" id="oneAgentChk" >按产品数量控制
	    -->
	    </td>
		
	</tr>
	
   <tr id="city_control" style="display:none;">
		<th><label class="text"><span class='red'>*</span>控制范围：</label></th>
		
		<td id="control_line">
	
		<ul style="width:100%" id="rolesbox">	
	    <c:forEach var="lan" items="${codeList}" >
	    <li style="width:20%;height:20px">
          <input type="checkbox"  attr_founder='ck_founder' name="agreementControl.control_lan_code" value="${lan.pkey }" id="oneAgentChk" lname="${lan.pname }" >${lan.pname }
		 <span style="float:right;height:20px"> <input type='text' class='ipttxt'   id="${lan.pkey }"    style="width:60px;height:20px" /></span>
	   </li>
		
		</c:forEach>
		</ul> 	
		  </td>
		  
		<input type="hidden" name ="codeStr" id="codeStr" />
	    <input type="hidden" name ="controlValStr" id="controlValStr" />
	</tr>
	
</table>
  
  <div class="submitlist" align="center">
	 <table>
			<tr>
	<th></th>
	<td> 
     <input  type="submit" id="agree_input_btn"	 value=" 提     交 "  class="submitBtn"  /></td>

	</tr>
	 </table>
	</div>  
</form>



<div title="供应商" id="refsupplierDlg"></div>

</div>

<script type="text/javascript">
      
</script>
