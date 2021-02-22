<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<div class="input">
<form class="validate" action="javascript:void(0)" id="editMemberForm">
<input type="hidden" name="member.member_id" value="${member_id }">
<div class="main-div">
<table cellspacing="1" cellpadding="3" width="100%" class="form-table">
	<tr>
		<th><label class="text">用户名：</label>
		
		</th>
		<td valign="middle">&nbsp; ${member.uname}</td>
	</tr>
	<tr>
		<th><label class="text">姓名：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.name"
			value="${member.name}" required="true" /></td>
	</tr>
	<tr>
		<th><label class="text">性别：</label>
		</th>
		<td valign="middle">&nbsp; <select  class="ipttxt"  name="member.sex">
			<option value="-1">&nbsp;</option>
			<option value="0" <c:if test="${member.sex==0 }">selected</c:if>>
			女</option>
			<option value="1" <c:if test="${member.sex==1 }">selected</c:if>>
			男</option>

		</select></td>
	</tr>
	<tr>
		<th><label class="text">出生日期：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="birthday" value="${member.birthday }" maxlength="60" dataType="date" required="true" class="dateinput"/></td>
	</tr>
	<tr>
		<th><label class="text">Email：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.email"
			value="${member.email}" id="email" dataType="email" />
		</td>
	</tr>
	<tr>
		<th><label class="text">固定电话：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  id="tel" name="member.tel"
			value="${member.tel}" /></td>
	</tr>
	<tr>
		<th><label class="text">移动电话：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt" 
			name="member.mobile" id="mobile" value="${member.mobile}" /></td>
	</tr>
	<tr>
		<th><label class="text">会员等级：</label>
		</th>
		<td valign="middle">&nbsp; <ul style="width:100%" id="lvbox">
				<c:forEach items="${lvlist }" var="lv">
				<li style="width:33%;display:block"><input type="checkbox" name='lv_checked' value="${lv.lv_id }" lv_name = ${lv.name }  />
					${lv.name }&nbsp;</li>
				</c:forEach>
			</ul>
			<input type="hidden" id = "lv_nameStr" name= "lv_nameStr">
		<!--  <select  class="ipttxt"  name="member.lv_id">
			<option value="0">--选择等级--</option>
			<c:forEach items="${lvlist }" var="lv">
				<option value="${lv.lv_id }"
					<c:if test="${ lv.lv_id == member.lv_id  }">selected</c:if>>
				${lv.name }</option>
			</c:forEach>
		</select>-->
		</td>
	</tr>
	<tr>
		<th><label class="text">邮编：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.zip"
			id="zip" value="${member.zip}" style="width: 70px" dataType="post_code"/></td>
	</tr>
	<tr>
		<th><label class="text">地区：</label>
		</th>
		<td>&nbsp; 
		<select  class="ipttxt"  name="member.province_id"	id="address_province_id"></select>
		&nbsp; <input type="hidden"	name="member.province" id="address_province" value="${member.province }"/> 
		<select	name="member.city_id" id="address_city_id"></select>
		&nbsp; <input type="hidden" name="member.city" id="address_city" value="${member.city }"/> 
		<select	name="member.region_id" id="address_region_id"></select>
		&nbsp; <input type="hidden" name="member.region" id="address_region" value="${member.region }"/>
		</td>
	</tr>
	<tr>
		<th><label class="text">地址：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt" 
			name="member.address" value="${member.address}" style="width: 150px" />
		</td>
	</tr>
	<tr>
		<th><label class="text">QQ：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.qq"
			id="qq" value="${member.qq}" style="width: 70px" /></td>
	</tr>
	<tr>
		<th><label class="text">Msn：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.msn"
			id="msn" value="${member.msn}" style="width: 70px" /></td>
	</tr>
	<tr>
		<th><label class="text">备注：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  name="member.remark"
			id="remark" value="${member.remark }" style="width: 70px" /></td>
	</tr>
	<tr>
		<th><label class="text">安全问题：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  id="pw_question"
			name="member.pw_question" value="${member.pw_question}"
			style="width: 150px" /></td>
	</tr>
	<tr>
		<th><label class="text">回答：</label>
		</th>
		<td valign="middle">&nbsp; <input type="text" class="ipttxt"  id="pw_answer"
			name="member.pw_answer" value="${member.pw_answer}"
			style="width: 150px" /></td>
	</tr>
</table>
</div>

<div class="submitlist" align="center">
<table>
	<tr>
	  <th></th>
		<td><input name="button" type="submit" value=" 确    定   "
			class="submitBtn" id="editMemberBtn"/></td>
	</tr>
</table>
</div>

</form>
<script>
function initCity(){
	//$("#address_city_id").hide();
	//$("#address_region_id").hide();
	$("#address_province_id").empty();
	$("<option value='-1'>请选择...</option>").appendTo($("#address_province_id"));
	
	<c:forEach items="${provinceList}" var="province" >
		$("<option value='${(province.region_id)}' >${province.local_name}</option>").appendTo($("#address_province_id"));
	</c:forEach>

	<c:forEach items="${cityList}" var="city" >
		$("<option value='${(city.region_id)}' >${city.local_name}</option>").appendTo($("#address_city_id"));
	</c:forEach>

	<c:forEach items="${regionList}" var="region" >
		$("<option value='${(region.region_id)}' >${region.local_name}</option>").appendTo($("#address_region_id"));
	</c:forEach>
	
	<c:forEach items="${listLv_id}" var="lv" >
	
	 $("#lvbox input[value='${lv}']").attr("checked",true);
	</c:forEach>
	
	$("#address_province_id option[value=${member.province_id}]").attr("selected", "selected");
	$("#address_city_id option[value=${member.city_id}]").attr("selected", "selected");
	$("#address_region_id option[value=${member.region_id}]").attr("selected", "selected");
	
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
}


$(function(){
	$("form.validate").validate();
	
	  
	initCity();
	$("#editMemberBtn").click(function(){
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
          }
	     //checkValue();
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
		 
		$.Loading.show('正在更新数据，请稍侯...');
		var that =this;
		var options = {
			url : "member!saveEditMember.do?ajax=yes&lv_idstr="+lv_idstr,
			type : "POST",
			dataType : 'json',
			success : function(result) {	
				$.Loading.hide();
				if(result.result==0){
					alert(result.message);
					MemberDetail.showEdit();
				}else{
					alert(result.message);
				}
			},
			error : function(e) {
				$.Loading.hide();
				alert("出现错误 ，请重试");
			}
		};
		$('#editMemberForm').ajaxSubmit(options);
		
	});
	
	
});
</script>
</div>
