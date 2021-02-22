<%@ page contentType="text/plain;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<c:forEach var="org" items="${orgList}" begin="0" end="1200">
	<c:choose>
		<c:when test="${org.level eq 1}">
			<tr id="tr_${org.id}"  brith="N">
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
			<tr id="tr_${org.id}" name="tr_${org.parent_id}" style="" brith="N">
			      <td height="20" background="/wssmall/mgWebthemes/default/images/xian.gif"></td>
			      <td width="1%" valign="middle" height="20">
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jian.gif" onclick="manyingGlass('${org.id}',1,${org.level})" id="img_${org.id}_1" style="display:none;" >   
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jia.gif" onclick="manyingGlass('${org.id}',0,${org.level})" id="img_${org.id}_0">
			      </td>
			      <td height="20" style="font-size:12px" colspan="4">
			        <label><input ${org_id == org.id ? ' checked="checked" ' : ''} type="checkbox" name="org" depname="${org.dep_name}" level="0" value="${org.id}" id="chk_${org.id}">
			          <b style="font-family:宋体;color:#434343;font-weight:700;font-size:12px;">${org.dep_name}</b>
			        </label>
			      </td>
			</tr>		
		</c:when>
		<c:when test="${org.level eq 3}">
			<tr id="tr_${org.id}" name="tr_${org.parent_id}" style="" brith="N">
			      <td height="20" background="/wssmall/mgWebthemes/default/images/xian.gif"></td>
			      <td height="20" background="/wssmall/mgWebthemes/default/images/xian.gif"></td>
			      <td width="1%" valign="middle" height="20">
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jian.gif" onclick="manyingGlass('${org.id}',1,${org.level})" id="img_${org.id}_1" style="display: none;" >   
			        <img height="20" src="/wssmall/mgWebthemes/default/images/jia.gif" onclick="manyingGlass('${org.id}',0,${org.level})" id="img_${org.id}_0" >
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
