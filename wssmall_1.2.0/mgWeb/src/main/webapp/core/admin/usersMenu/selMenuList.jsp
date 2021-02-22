<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<form method="post" id="menuSelSearchForm" >
 <div class="searchformDiv">
 <table>
	<tr>
		<th>菜单名称：</th>
		<td><input type="text" class="ipttxt"  name="menu_title"  value="${menu_title}"/></td>
		<th></th>
		<td>
	    <input class="comBtn" type="button" name="searchBtn" id="menuSelSearchBtn" value="搜索" style="margin-right:10px;"/>
		</td>
   </tr>
  </table>
 </div>
</form>
<div class="grid" >
	<form method="post" action="" id="menuSelForm"></form>

              <form id="gridform" class="grid">
				<grid:grid  from="selMenuPage" ajax='yes'  formId="menuSelSearchForm">
					<grid:header>
					    <grid:cell>选择</grid:cell> 
					  	<grid:cell>菜单名称</grid:cell> 
						<grid:cell>菜单路径</grid:cell>
					</grid:header>
				    <grid:body item="selMenu">
				         <grid:cell><input type="radio" name="selMenuRadio" menu_id = "${selMenu.menu_id }" url="${selMenu.url}"></grid:cell>
					     <grid:cell>${selMenu.title}</grid:cell>
					     <grid:cell>${selMenu.url}</grid:cell>
				  </grid:body>  
				</grid:grid>
				<div align="center" style="margin-top:20px;">
					<input type="button" style="margin-top:10px;" class="comBtn" value="确&nbsp;定"  id="menuSelInsureBtn">
				</div>
		
	</form>
</div>      

     
