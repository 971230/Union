<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/jquery-1.4.2.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/dragDrop.js"></script>

<script type="text/javascript" src="<%=request.getContextPath() %>/ecs_ord/js/model_edit.js"></script> 

<form  action="invoicePrintAction!editFieldPosition.do" method="post"  id="editForm">
<script type="text/javascript">
<c:forEach  items="${modeSelectFieldVO.fieldVOList}" var="mt">
$(function()
		{
			//限定区域，有回调函数，有多少个打印域，就要写多少个回调函数。
			$('#${mt.field_cd }').dragDrop({fixarea:[0,$('#dragContainer').width()-50,0,$('#dragContainer').height()-50],callback:function(params)
			{
				document.getElementById('hmf${mt.field_cd}').value=params.moveX+'#'+params.moveY;
			}	
			});
			
			
		});
</c:forEach>


</script>

<input type="hidden" name="model_cd" id="mode_id" value="${modeSelectFieldVO.model_cd }"/>

 <c:forEach  items="${modeSelectFieldVO.fieldVOList}" var="mt1">
    <input attr="field_V" type="hidden" id="hmf${mt1.field_cd }" name="hmf${mt1.field_cd }" />    
</c:forEach> 


<input type="hidden" name="modeSelectFieldVO.model_cd" value="${modeSelectFieldVO.model_cd}"/>
 <div id="dragContainer" style='background-position:center center;margin:5px;position:relative;left:10px;top:10px;border:1px dashed blue;width:${modeSelectFieldVO.paper_width}px;height:${modeSelectFieldVO.paper_height}px;background-image:url("${ctx }${modeSelectFieldVO.img_url}");'>
 <c:forEach  items="${modeSelectFieldVO.fieldVOList}" var="mt">
		 <div id="${mt.field_cd }" style='float:left; border:1px dashed #812c0a;padding:4px 6px;font-size:12px; color:#000; font- weight:bold;FILTER: progid:DXImageTransform.Microsoft.Gradient(startColorStr=#ed845a, endColorStr=#f2a689); padding-left:10px;position:absolute;top:${mt.pos_y}px;left:${mt.pos_x }px;'>${mt.field_nam }</div>
 </c:forEach>
</div>
<div class="inputBox"  style="margin-top:40px;margin-left:300px;" >
	 <table>
		 <tr>
			 <td >
		           <input  type="button"  value=" 保存 " class="greenbtnbig" id="doSave"  style="cursor:pointer;"/>
		          <input name="button" type="button" value=" 取消 " class="greenbtnbig" id="backBtn"  style="cursor:pointer;" />
			 </td>
		 </tr>
	 </table> 
 </div>
</form>
<script type="text/javascript">
$(function(){
	ActivityAddEdit.init();
	$("#backBtn").click(function(){history.go(-1);});
});
</script>
