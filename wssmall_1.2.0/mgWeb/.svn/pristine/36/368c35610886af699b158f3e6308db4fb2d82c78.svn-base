<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="js/DlyType.js"></script>
<div class="searchformDiv">
	<a href="dlyType!add_type.do" style="margin-right:10px;" class="graybtn1" ><span>添加</span></a>
    <a href="javascript:;"  id="delBtn" style="margin-right:10px;" class="graybtn1" ><span>删除</span></a>
    
		<div style="clear: both"></div>
</div>
<div class="grid">

	<form method="POST">
		<grid:grid from="webpage">

			<grid:header>
				<grid:cell width="20px">
					<input type="checkbox" id="toggleChk" />
				</grid:cell>
				<grid:cell>配送方式</grid:cell>
				<grid:cell>支持保价</grid:cell>
				<grid:cell>支持货到付款</grid:cell>
				<grid:cell>操作</grid:cell>
			</grid:header>

			<grid:body item="type">
				<grid:cell>
					<input type="checkbox" name="id" value="${type.type_id }" />
				</grid:cell>
				<grid:cell>${type.name} </grid:cell>
				<grid:cell>
					<c:if test="${type.protect==1}">是</c:if>
					<c:if test="${type.protect==0}">否</c:if>
				</grid:cell>
				<grid:cell>
					<c:if test="${type.has_cod==1}">是</c:if>
					<c:if test="${type.has_cod==0}">否</c:if>
				</grid:cell>
				<grid:cell align="center">
					<a href="dlyType!edit.do?type_id=${type.type_id }"><img
							class="modify" src="images/transparent.gif" />
					</a>
				</grid:cell>
			</grid:body>

		</grid:grid>
	</form>
	<DIV style="clear: both; margin-top: 5px;"></DIV>
</div>