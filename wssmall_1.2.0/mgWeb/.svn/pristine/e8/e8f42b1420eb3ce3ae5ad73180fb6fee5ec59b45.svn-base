<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<style>
#tagspan {
	position: absolute;
	display: none;
}

#agentspan {
	display: none;
	position: absolute;
}

#searchcbox {
	float: left
}

#searchcbox div {
	float: left;
	margin-left: 10px
}
</style>
<div>
	<form id="gridform" class="grid">
		<grid:grid from="webpage" ajax="yes">
			<grid:header>
				<grid:cell>货位名称</grid:cell>
			</grid:header>

			<grid:body item="obj">
				<grid:cell>${obj.seat_name}</grid:cell>
			</grid:body>
		</grid:grid>
	</form>
	<div style="clear: both; padding-top: 5px;"></div>
</div>