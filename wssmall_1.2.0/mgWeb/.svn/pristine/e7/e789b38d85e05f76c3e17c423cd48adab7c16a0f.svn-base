<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="despost/js/changeLogF.js"></script>
<h3>
	<div class="stat_graph">
		<h3>
			<div class="graph_tab">
				<ul>
					<li id="show_click_2" class="selected">
						<span class="word">预存款信息</span><span class="bg"></span>
					</li>
					<div class="clear"></div>
				</ul>
			</div>
		</h3>
	</div>
</h3>
<div class="searchformDiv">
	<form method="post" id="serchform" action='freeze!changeLogF.do'>
		<table class="form-table">
			<tr>		
				<th>开始时间</th>
				<td>
					<input type="text" class="ipttxt dateinput"   name="beginTime" id="beginTime" readonly="readonly"   value='${beginTime}'  maxlength="60"  dataType="date" />
				</td>
				<th>结束时间</th>
				<td>
					<input type="text" class="ipttxt dateinput"  name="endTime" id="endTime" readonly="readonly"   value='${endTime}'  maxlength="60"  dataType="date" />
				</td>	
				<th></th>
				<td>
			    	<input class="comBtn" style="display:none" type="text" name="userid" id="userid" value='${userid}'/>
			    </td>
				<th></th>
				<td>
			    	<input class="comBtn" type="button" name="searchBtn" id="searchBtn" value="搜索" style="margin-right:10px;"/>
			    </td>
			</tr>
			<div style="clear:both"></div>
		</table>		
	</form>
</div>
<div id="changeLogList">
	<jsp:include page="changeLogList.jsp"></jsp:include>
</div>