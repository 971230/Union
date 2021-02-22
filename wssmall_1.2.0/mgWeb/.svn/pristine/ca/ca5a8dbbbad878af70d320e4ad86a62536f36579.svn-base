<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript">
function manyingGlass(id,type,level){
	if(type==0){
		$("#img_"+id+"_1").show();
		$("#img_"+id+"_0").hide();
		if($("#tr_"+id).attr("brith")=='N'){
			level = level + 1;
			var url=app_path+'/core/admin/user/userAdmin!getOrgListByParentId.do';
			$.ajax({
				type : "post",
				url : url,
				data : {"parent_id":id,"level":level},
				dataType : 'text',
				success : function(result) {
					$("#tr_"+ id).after($(result.substr(result.indexOf("<body >")+7,result.indexOf("</body>")-7)));
				}
			});
			
			$("#tr_"+id).attr({"brith":"Y"});
		}else{
			$("tr[name='tr_"+id+"']").show();
		}
	}else if(type==1){
		$("#img_"+id+"_0").show();
		$("#img_"+id+"_1").hide();
		hideSubItem(id);
	}
	

}

function hideSubItem(id){
	$("#img_"+id+"_0").show();
	$("#img_"+id+"_1").hide();
	if($("tr[name='tr_"+id+"']").length>0){
		$("tr[name='tr_"+id+"']").hide();
		for(var i=0;i<$("tr[name='tr_"+id+"']").length;i++){
			var subId=$("tr[name='tr_"+id+"']")[i].id.substr(3);
			hideSubItem(subId);
		}
	}
}

function selectAll(flag){
	$(":checkbox").attr("checked",flag);
}

function confirm(){
	var ret={};
	if($(":checked").length==0){
		alert("请选择一个评价部门！");
		return;
	}
	if($(":checked").length>1){
		alert("只能选择一个评价部门！");
		return;
	}
	ret.id=$(":checked").val();
	ret.name=$(":checked").attr("depName");
	window.returnValue=ret;
	window.close();
} 

</script>
<form id="orgSelectedFrm">
<table width="auto" cellspacing="0" cellpadding="0" border="0" align="center">
<tbody>
<c:forEach var="org" items="${orgList}" begin="0" end="1200">
	<c:choose>
		<c:when test="${org.level eq 1}">
			<tr id="tr_${org.id}" brith="N">
		     <td width="1%" valign="middle" height="20">
		       <img height="20" src="/wssmall/mgWebthemes/default/images/jian.gif" onclick="manyingGlass('${org.id}',1,${org.level})" id="img_${org.id}_1" style="display: none;">   
		       <img height="20" src="/wssmall/mgWebthemes/default/images/jia.gif" onclick="manyingGlass('${org.id}',0,${org.level})" id="img_${org.id}_0">
		     </td>
		     <td height="20" style="font-size:12px" colspan="5">
		       <label><input ${org_id == org.id ? ' checked="checked" ' : ''} type="checkbox" name="org" depname="${org.dep_name}" level="0" value="${org.id}" id="chk_${org.id}">
		         <b style="font-family:宋体;color:#434343;font-weight:700;font-size:12px;">${org.dep_name}</b>
		       </label>
		     </td>		
		     </tr>	
		</c:when>
		<c:when test="${org.level eq 2}">
			<tr id="tr_${org.id}" name="tr_${org.parent_id}" style="display:none;" brith="N">
			      <td height="20" background="/wssmall/mgWebthemes/default/images/xian.gif"></td>
			      <td width="1%" valign="middle" height="20">
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jian.gif" onclick="manyingGlass('${org.id}',1)" id="img_${org.id}_1" style="display:none;" >   
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jia.gif" onclick="manyingGlass('${org.id}',0)" id="img_${org.id}_0">
			      </td>
			      <td height="20" style="font-size:12px" colspan="4">
			        <label><input ${org_id == org.id ? ' checked="checked" ' : ''} type="checkbox" name="org" depname="${org.dep_name}" level="0" value="${org.id}" id="chk_${org.id}">
			          <b style="font-family:宋体;color:#434343;font-weight:700;font-size:12px;">${org.dep_name}</b>
			        </label>
			      </td>
			</tr>		
		</c:when>
		<c:when test="${org.level eq 3}">
			<tr id="tr_${org.id}" name="tr_${org.parent_id}" style="display:none;" brith="N">
			      <td height="20" background="/wssmall/mgWebthemes/default/images/xian.gif"></td>
			      <td height="20" background="/wssmall/mgWebthemes/default/images/xian.gif"></td>
			      <td width="1%" valign="middle" height="20">
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jian.gif" onclick="manyingGlass('${org.id}',1)" id="img_${org.id}_1" style="display: none;" >   
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jia.gif" onclick="manyingGlass('${org.id}',0)" id="img_${org.id}_0" >
			      </td>
			      <td height="20" style="font-size:12px" colspan="3">
			        <label><input ${org_id == org.id ? ' checked="checked" ' : ''} type="checkbox" name="org" depname="${org.dep_name}" level="0" value="${org.id}" id="chk_${org.id}">
			          <b style="font-family:宋体;color:#434343;font-weight:700;font-size:12px;">${org.dep_name}</b>
			        </label>
			      </td>
			</tr>		
		</c:when>
	</c:choose>
</c:forEach>
</tbody>
</table>

<div align="center" class="submitlist">
<table>
	<tbody>
 		<tr>
 			<td>
  				<input type="button" class="submitBtn" value=" 确    定   " id="orgSubmit">
   			</td>
    	</tr>
	</tbody>
</table>
</div>
</form>