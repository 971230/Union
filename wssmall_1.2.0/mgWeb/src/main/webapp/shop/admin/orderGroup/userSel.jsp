<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="orderGroup/userSel.js"></script>
<style>

.tableform {
background:none repeat scroll 0 0 #EFEFEF;
border-color:#DDDDDD #BEC6CE #BEC6CE #DDDDDD;
border-style:solid;
border-width:1px;
margin:10px;
padding:5px;
}


.division  {
background:none repeat scroll 0 0 #FFFFFF;
border-color:#CCCCCC #BEC6CE #BEC6CE #CCCCCC;
border-style:solid;
border-width:1px 2px 2px 1px;
line-height:150%;
margin:10px;
padding:5px;
white-space:normal;
}

h4  {
font-size:1.2em;
font-weight:bold;
line-height:1.25;
}
h1, h2, h3, h4, h5, h6 {
clear:both;
color:#111111;
margin:0.5em 0;
}

</style>
<div class="comBtnDiv">
	<a href="javascript:;" style="margin-right:10px;" id="addUserBtn" class="graybtn1" ><span>添加</span></a>
</div>
<form id="userForm">
<div class="grid" id="goodslist">
    <input name="orderGroupRel.group_id" type="hidden" value = "${group_id}">
     <table class="form-table"  cellspacing="0" cellpadding="0" border="0" width="100%">
			<tbody id="userRow">
			    
			    <tr>
                    <td><div align="center"><b>员工工号</b></div></td>
                    <td><div align="center"><b>员工名称</b></div></td>
                    <td><div align="center"><b>操作</b></div></td>
                </tr> 
             
			    <c:forEach var ="orderGroupUser" items="${orderGroupUser}">
                  <tr id="sel_${orderGroupUser.userid}">
                    <td width="20%">
                        <div align="center">${orderGroupUser.username}
                            <input type='hidden' name='userIds' username="${orderGroupUser.username}" user_id='${orderGroupUser.userid}' value="${orderGroupUser.userid}" />
                            
                        </div>
                    </td>
                    <td>${orderGroupUser.realname}</td>
                    <td width="20%"><div align="center"><a href='javascript:;' name='delContract'>删除</a></div></td>
                </tr> 
               </c:forEach>
              
              
			</tbody>
			
		</table>
		
		<div>
		 <tr></tr>
	     <tr id="btnTr">
	         
	         <td colspan='2'>
		        <div  style="margin-left:90%"> 
		           <input type="button" id='saveUserBtn' class="graybtn1" value="确  定" />
		         </div>
	          </td>
	     </tr>
     </div>
</div>
<input type="hidden" name="userIdStr" id ="userIdStr">
</form>
<div id="addUserDlg"></div>




<script type="text/javascript">
  $(function(){
      $("[name='delContract']").each(function(){ 
                 $(this).click(function(){
                 $(this).closest("tr").remove();
               });
       });
      
      
  });
</script>
