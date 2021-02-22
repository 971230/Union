<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>

<%
String order_id = request.getParameter("order_id");
%>

<style>
	<!-- .noborder {
		border-style: none;
	}
	
	-->.icoFontlist {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		color: #5f5f5f;
		overflow: hidden;
		text-overflow: ellipsis;
		white-space: nowrap;
	}
	
	.icoFontlist:hover {
		width: 100px;
		font-size: 12px;
		border: 0px solid #ddd;
		overflow: scroll;
		text-overflow: ellipsis;
		white-space: nowrap;
		cursor: pointer;
	}
	
	.second select {
		width: 352px;
		height: 106px;
		margin: 0px;
		outline: none;
		border: 1px solid #999;
		margin-top: 33px;
		background-color: white;
	}
	
	.second select option {
		background-color: inherit;
	}
	
	.op {
		background-color: transparent;
		bacground: tansparent;
		-webkit-appearance: none;
	}
	
	.second input {
		width: 350px;
		top: 9px;
		outline: none;
		border: 0pt;
		position: absolute;
		line-height: 30px;
		/* left: 8px; */
		height: 30px;
		border: 1px solid #999;
	}
	
	.blue {
		background: #1e91ff;
	}
</style>

<body>

<div class="input">
	<div>
		<div style="margin-top: 5px;">
			<table cellspacing="0" cellpadding="0" border="0" width="100%">
				<tbody>
					<tr>
						<th style="width: 150px;text-align: right;" >备注：</th>
						<td>
							<textarea rows="5" cols="63" id="workflow_jump_remark" ></textarea>
						</td>
					</tr>
				</tbody>
			</table>
		</div>

		<div class="pop_btn" align="center">
			<a id="workflow_jump_comfirm_btn" class="blueBtns" style="cursor: pointer;"><span>确定</span></a>
		</div>
	</div>
</div>

</body>