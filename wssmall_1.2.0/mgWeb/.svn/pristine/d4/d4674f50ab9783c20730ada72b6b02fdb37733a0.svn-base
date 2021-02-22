<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!-- <script type="text/javascript" src="/shop/admin/js/member_add.js"></script> -->
<script type="text/javascript">
loadScript("js/member_add.js");
</script>
<div class="input">
<form class="validate" action="javascript:void(0)" method="post" id="theForm"
	name="theForm">
<div class="main-div">
<table  class="form-table" align="center">
	<tr>
	      <th><label class="text"><span class='red'>*</span>用户名：</label></th>
		<!--  <td class="label" style="text-align:right"><label class="text">用户名：</label></td>-->
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.uname"
			id="uname" value="" dataType="string" required="true" /></td>
	</tr>
	<tr>
		<!--<td class="label" style="text-align:right"><label class="text">密码：</label></td>  -->
		  <th><label class="text"><span class='red'>*</span>密码：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt" 
			name="member.password" id="password" value="" dataType="string"
			required="true" /></td>
	</tr>
	<tr>
		<!--  <td class="label" style="text-align:right"><label class="text">
		姓名：</label></td>-->
		 <th><label class="text"><span class='red'>*</span>姓名：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.name"
			  required="true" dataType="string"/></td>
	</tr>
	
	<tr>
		<!--  <td class="label" style="text-align:right"><label class="text">性别：</label></td>-->
		 <th><label class="text">性别：</label></th>
		<td valign="middle">&nbsp; <select  class="ipttxt"  name="member.sex">
			<option value="1">男</option>
			<option value="0">女</option>
		</select></td>
	</tr>
	<tr>
		<!--  <td class="label" style="text-align:right"><label class="text">出生日期：</label></td>-->
		 <th><label class="text">出生日期：</label></th>
		<td valign="middle">&nbsp; <input required="true" dataType="date" name="birthday" id="birthday" value="1980-01-01" readonly="true"  class="dateinput" /> </td>
	</tr>
	<tr>
		<!-- <td class="label" style="text-align:right"><label class="text">Email：</label></td> -->
		 <th><label class="text"><span class='red'>*</span>Email：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.email"
			value="" id="email" dataType="email"  required="true"/></td>
	</tr>
	<tr>
		<!--  <td class="label" style="text-align:right"><label class="text">固定电话：</label></td>-->
		 <th><label class="text">固定电话：</label></th>
		
		<td valign="middle">&nbsp; <input type="text" class="ipttxt" id="tel" name="member.tel"
			value="" /></td>
	</tr>
	<tr>
		<!-- <td class="label" style="text-align:right"><label class="text">移动电话：</label></td> -->
		 <th><label class="text">移动电话：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt" 
			name="member.mobile" value="" id="mobile"/></td>
	</tr>
	
	<tr>
	<!-- 	<td class="label" style="text-align:right"><label class="text">会员等级：</label></td> -->
		 <th><label class="text"><span class='red'>*</span>会员等级：</label></th>
		<td valign="middle">&nbsp; <ul style="width:100%" id="rolesbox">
				<c:forEach items="${lvlist }" var="lv">
				<li style="width:33%;display:block"><input type="checkbox" name='lv_checked' value="${lv.lv_id }" lv_name="${lv.name}" />
					${lv.name }&nbsp;</li>
				</c:forEach>
			</ul>
			<input type="hidden" id = "lv_nameStr" name= "lv_nameStr">
		<!-- <select  class="ipttxt"  name="member.lv_id">
			<option value="0">--请选择等级--</option>
			<c:forEach items="${lvlist }" var="lv">
				<option value="${lv.lv_id }">${lv.name }</option>
			</c:forEach>
		</select>
		 -->
		
		</td>
	</tr>
	<tr>
		<!--  <td class="label" style="text-align:right"><label class="text">邮编：</label></td>-->
		 <th><label class="text">邮编：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.zip"
			value="" style="width: 70px"   /></td>
	</tr>
	<tr>
		<!-- <td class="label" style="text-align:right"><label class="text">地区：</label></td> -->
		 <th><label class="text">地区：</label></th>
		<td valign="middle">&nbsp; 
		<select  class="ipttxt"  name="member.province_id"	id="address_province_id"></select>
		&nbsp; <input type="hidden"	name="member.province" id="address_province" /> 
		<select	name="member.city_id" id="address_city_id"></select>
		&nbsp; <input type="hidden" name="member.city" id="address_city" /> 
		<select	name="member.region_id" id="address_region_id"></select>
		&nbsp; <input type="hidden" name="member.region" id="address_region" />
		</td>
	</tr>
	<tr>
		<!-- <td class="label" style="text-align:right"><label class="text">地址：</label></td> -->
		 <th><label class="text">地址：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt" 
			name="member.address" value="" style="width: 150px" /></td>
	</tr>
	<tr>
		<th><label class="text">QQ：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.qq"
			id="qq" value="" style="width: 70px" /></td>
	</tr>
	<tr>
		<th><label class="text">Msn：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.msn"
			id="msn" value="" style="width: 70px" /></td>
	</tr>
	<tr>
		<th><label class="text">备注：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.remark"
			id="remark" value="" style="width: 70px" /></td>
	</tr>
	<tr>
		<!-- <td class="label" style="text-align:right"><label class="text">安全问题：</label></td> -->
		<th><label class="text">安全问题：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  id="pw_question"
			name="member.pw_question" value="我的宠物名字是？" style="width: 150px" /></td>
	</tr>
	<tr>
		<!--  <td class="label" style="text-align:right"><label class="text">回答：</label></td>-->
		 <th><label class="text">回答：</label></th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  id="pw_answer"
			name="member.pw_answer" style="width: 150px" /></td>
	</tr>
</table>
</div>

<div class="submitlist" align="center">
<table>
	<tr>
	  <th></th>
		<td><input name="button" type="submit" value=" 确    定   "
			class="submitBtn" /></td>
	</tr>
</table>
</div>

</form>

<script type="text/javascript">
</script>

<script>
	function initCity(){
		$("#address_city_id").hide();
		$("#address_region_id").hide();
		$("#address_province_id").empty();
		$("<option value='-1'>请选择...</option>").appendTo($("#address_province_id"));
		
		<c:forEach items="${provinceList}" var="province" >
			$("<option value='${(province.region_id)}' >${province.local_name}</option>").appendTo($("#address_province_id"));
		</c:forEach>
	
		$("#address_province_id").change(function(){
			$("#address_province").val($("#address_province_id option:selected").text());
			$("#address_city_id").empty();
			$("#address_city_id").hide();
			$("#address_region_id").empty();
			$("#address_region_id").hide();
			$.ajax({
				method:"get",
				url:"../area!list_city.do?province_id=" + $("#address_province_id").attr("value"),
				dataType:"html",
				success:function(result){
					$("#address_city_id").show();
					$(result).appendTo($("#address_city_id"));
				},
				error:function(){
					alert("异步失败");
				}
			});
		});
		
		$("#address_city_id").change(function(){
			$("#address_city").val($("#address_city_id option:selected").text());
			$("#address_region_id").empty();
			$("#address_region_id").hide();
			$.ajax({
				method:"get",
				url:"../area!list_region.do?city_id=" + $("#address_city_id").attr("value"),
				dataType:"html",
				success:function(result){
					$("#address_region_id").show();
					$(result).appendTo($("#address_region_id"));
				},
				error:function(){
					alert("异步失败");
				}
			});
		});
		
		$("#address_region_id").change(function(){
			$("#address_region").val($("#address_region_id option:selected").text());
		});
		
		$(".submitBtn").bind("click",function(){
		  
		 
		   if(!checkValue()){
		      return false;
		   }
		   
		    var lv_idArr = [];    
		    var lv_nameArr  = [];
			$("[name='lv_checked']:checked").each(function(){
			       var lv_id = $(this).val();
			       var lv_name = $(this).attr("lv_name");
					   lv_idArr.push(lv_id);
					   lv_nameArr.push(lv_name);
					})
			var lv_idstr = lv_idArr.join(",");
			var lv_nameStr = lv_nameArr.join(",");
			$("#lv_nameStr").val(lv_nameStr);
			
			 var url =  "member!saveMember.do?ajax=yes&lv_idstr="+lv_idstr;
			
		    Cmp.ajaxSubmit('theForm', '', url, {},replyjsonBack, 'json');
			
		});
	}
	function replyjsonBack(responseText){
			    if (responseText.result == 1) {
				    alert(responseText.message);
				    window.location.reload();    
			     }
			     
			    if (responseText.result == 0) {
				   alert(responseText.message);	
				  
			    window.location.href ="member!memberlist.do";
			   }  
	}
	
	function checkValue(){
		var uname = $("#uname").val();
		if (uname.length > 0 && uname.length <= 18) {
			if (/^[0-9a-zA-Z_]+$/.test(uname) == false) {
				alert("用户名只能是数字或者字母或者下划线组成");
				return false;
			}
		}else{
			alert("用户名长度小于18");
			return false;
		}	
		
	   if($("[name='lv_checked']:checked").length==0){
          alert("请至少选择一个会员等级");
       return false;
       }
       else if($("#mobile").val().length!=0&&(isNaN($("#mobile").val())||$("#mobile").val().length!=11)){
          alert("移动电话为11位的数字");
       return false;
       }else if(isNaN($("#tel").val())&&$("#tel").val().length!=0){
          alert("请输入是数字的固定电话号码");
          return  false;
       }else{
        return true;
       }
	}
	
</script>
</div>
