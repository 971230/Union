<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>
<body>
	<div id="">
		<div>
			<table width="100%" border="0">
				<tr>
					<th scope="row" width="200"><div align="right"></div></th>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<th scope="row"><div align="right">${invoicePrintInfo.shouhuorenxingming }</div></th>
					<td><div style="padding-left:50px;">${invoicePrintInfo.shouhuorendianhua }</div></td>
				</tr>
				<tr>
					<th scope="row"><div align="right"></div></th>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<th scope="row"><div align="right">实收：${invoicePrintInfo.shishoujine }元</div></th>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<th scope="row"><div align="right"></div></th>
					<td>&nbsp;</td>
				</tr><!-- 20000（号卡） 20001（上网卡）20002（合约机） -->
				<c:if test="${invoicePrintInfo.goods_type=='20002' }">
				<tr>
					<th scope="row"><div align="right">${invoicePrintInfo.zhongduanchuanhao }</div></th>
					<td>&nbsp;</td>
				</tr>
				</c:if>
				<tr>
					<th scope="row"><div align="right">${invoicePrintInfo.daxie }</div></th>
					<td><div style="padding-left:50px;">${invoicePrintInfo.yingshoujine }</div></td>
				</tr>
				<tr>
					<th scope="row"><div align="right">开票人：${invoicePrintInfo.kaipiaoren }</div></th>
					<td>&nbsp;</td>
				</tr>
			</table>
		</div>
		<div>
		<input type="button" name="button" id="button" value="打印"
			class='noprint' onclick="javascript:window.print();"
			style="repeat-x 0 0 #f8f8f8; color:#333; border:1px solid #aaa; height:22px;" />
		</div>
	</div>
</body>
</html>