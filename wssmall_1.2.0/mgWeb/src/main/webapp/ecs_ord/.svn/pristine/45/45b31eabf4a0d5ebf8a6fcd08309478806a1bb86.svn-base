<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>


<h3>
</h3>  

<div class="input">
   <form  action="<%=request.getContextPath() %>/shop/admin/orderWarningAction!add.do"  class="validate" method="post" name="warningAddForm" id="warningAddForm" >
      <table  class="form-table">
         <tr>
            <th><label class="text"><span class='red'>*</span>规则名称：</label></th>
            <td><input name="orderWarning.group_name" class="ipttxt" required="true"></td>
            
             <th><label class="text"><span class='red'>*</span>订单环节：</label></th>
             <td>
               	<select name="orderWarning.flow_id" class="ipt_new" style="width:138px;" required="true">
               		<c:forEach items="${flowTraceList }" var="ds">
               			<option value="${ds.pkey }">${ds.pname }</option>
               		</c:forEach>
				</select>
              </td>
              
              <th><label class="text"><span class='red'>*</span>归属地市：</label></th>
              <td>
               	<select name="orderWarning.order_origin" class="ipt_new" style="width:138px;" required="true" >
               		<c:forEach items="${regionList }" var="ds">
               			<option value="${ds.region_id }">${ds.local_name }</option>
               		</c:forEach>
				</select>
              </td>
         
         </tr>
          <tr>
 
             <th><label class="text"><span class='red'>*</span>订单来源：</label></th>
             <td>
               	<select name="orderWarning.order_from" class="ipt_new" style="width:138px;" required="true" >
               		<c:forEach items="${order_from_list }" var="ds">
               			<option value="${ds.pkey }">${ds.pname }</option>
               		</c:forEach>
				</select>
              </td>
              
              <th><label class="text"><span class='red'>*</span>商品类型：</label></th>
              <td>
               	<select name="orderWarning.product_type" class="ipt_new" style="width:138px;" required="true" >
               		<c:forEach items="${goods_type_list }" var="ds">
               			<option value="${ds.pkey }">${ds.pname }</option>
               		</c:forEach>
				</select>
              </td>
         
         </tr>
         
         <tr>
          <th><label class="text">规则组描述：</label></th>
          <td colspan="6">
               <textarea rows="5" cols="100" name="orderWarning.memo" ></textarea>
          </td>
            
         </tr>
           <tr>
	         <th><label class="text"><span class='red'>*</span>黄色预警开始时间（分钟）：</label></th>
	         <td><input name="orderWarning.warning_time_1" id="warning_time_1" class="ipttxt" required="true" ></td>
	         
	         <th><label class="text"><span class='red'>*</span>橙色预警开始时间（分钟）：</label></th>
	         <td><input name="orderWarning.warning_time_2" id="warning_time_2"  class="ipttxt" required="true"></td>
	         
	         <th><label class="text"><span class='red'>*</span>红色预警开始时间（分钟）：</label></th>
	         <td><input name="orderWarning.warning_time_3"  id="warning_time_3"  class="ipttxt" required="true"></td>
	         
	        </tr>
	        <tr>
	           <th><label class="text"><span class='red'>*</span>预警结束时间（分钟）：</label></th>
	           <td><input name="orderWarning.warning_time_4" id="warning_time_4"  class="ipttxt" required="true"></td>
         </tr>
         
      </table>
      
      
       <div class="submitlist" align="center">
		 <table>
				<tr>
					<td  style="text-align: center;"> 
				     	 <input  type="button" id="doSave"	 value=" 保   存 "  class="graybtn1"  />
				     	  <input   type="button"  id="backBtn" value=" 取消 " class="graybtn1"  />
				    </td>
				</tr>
		 </table>
	  </div>  
   </form>
</div>
<div id="selMenuDiv"></div> 
<script type="text/javascript">
  $(function(){
	  $("#backBtn").click(function(){history.go(-1);});
	  $("#warningAddForm").validate();
	  $("#doSave").bind("click",function(){
		  if(checkAddForm()){
			  $("#warningAddForm").submit();
		 }
		 
		});
  });
  function checkAddForm(){
	 
	  var warning_time_1=$("#warning_time_1").val();
	  var warning_time_2=$("#warning_time_2").val();
	  var warning_time_3=$("#warning_time_3").val();
	  var warning_time_4=$("#warning_time_4").val();
	  if(!checkIsInt("黄色预警开始时间",warning_time_1)){
			return false;
	  }else if(!checkIsInt("橙色预警开始时间",warning_time_2)){
			return false;
	  }else if(!checkIsInt("红色预警开始时间",warning_time_3)){
			return false;
	  }else if(!checkIsInt("预警结束时间",warning_time_4)){
			return false;
	  }
      //比较
      var warning_time_1_int=parseInt(warning_time_1);
      var warning_time_2_int=parseInt(warning_time_2);
      var warning_time_3_int=parseInt(warning_time_3);
      var warning_time_4_int=parseInt(warning_time_4);
	  if(warning_time_1_int>=warning_time_2_int){
		  alert("橙色预警开始时间不能小于或等于黄色预警开始时间！");
		  return false;
	  }else if(warning_time_2_int>=warning_time_3_int){
		  alert("红色预警开始时间不能小于或等于橙色预警开始时间！");
			return false;
	  }else if(warning_time_3_int>=warning_time_4_int){
		 
		  alert("预警结束时间不能小于或等于红色预警开始时间！");
			return false;
	  }
	  return true;
   }
  function checkIsInt(myName,myValue){
  	if(myValue!=""){
			if(!isNaN(myValue)){
				var   r   =   /^[0-9]*[1-9][0-9]*$/　　//正整数 
				if(!r.test(myValue)){
		           	 alert(myName+"必须为正整数！");
		             return false;
		         }else{
		        	 return true;
		         }
			}else{
				alert(myName+"必须为数字！");
	             return false;
			}
     	 
      }else{
          alert(myName+"不能为空！");
          return false;
      }
		
  }

  
</script>