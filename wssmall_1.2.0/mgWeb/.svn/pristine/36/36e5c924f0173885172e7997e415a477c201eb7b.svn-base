<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<%
	String UPTRANSEQ = request.getParameter("UPTRANSEQ")==null?"":request.getParameter("UPTRANSEQ");
	String RETNCODE = request.getParameter("RETNCODE")==null?"":request.getParameter("RETNCODE");
	String RETNINFO = request.getParameter("RETNINFO")==null?"":request.getParameter("RETNINFO");
	String ORDERSEQ = request.getParameter("ORDERSEQ")==null?"":request.getParameter("ORDERSEQ");
	String ORDERAMOUNT = request.getParameter("ORDERAMOUNT")==null?"":request.getParameter("ORDERAMOUNT");
	String ENCODETYPE = request.getParameter("ENCODETYPE")==null?"":request.getParameter("ENCODETYPE");
	String TRANDATE = request.getParameter("TRANDATE")==null?"":request.getParameter("TRANDATE");
	String SIGN = request.getParameter("SIGN")==null?"":request.getParameter("SIGN");
%>
<script type="text/javascript">
	$(function() {
		var url = ctx+ "/shop/admin/pay!payShow.do?ajax=yes";
		Cmp.ajaxSubmit('payNotify', '', url, {'insert':'yes'}, callBack,'html');
		
		function callBack(responseText){
			if(responseText.indexOf("{result:")>-1){
				var responseText = eval('(' + responseText + ')');   
				if(responseText.result == 0){
					alert(responseText.message);
				}
			}else {
			
				$("#wrapper").empty().append($(responseText));
			}
		}
	});
	
</script>
<div id="wrapper">
	
</div>
<div>
	<form method="post" id="payNotify"  >
 		<input type="hidden" name="uptranSeq" id="uptranSeq" value="<%=UPTRANSEQ %>" >
 		<input type="hidden" name="retnCode" id="retnCode" value="<%=RETNCODE %>" >
 		<input type="hidden" name="retnInfo" id="retnInfo" value="<%=RETNINFO %>" >
 		<input type="hidden" name="ordeSeq" id="ordeSeq" value="<%=ORDERSEQ %>" >
 		<input type="hidden" name="orderAmount" id="orderAmount" value="<%=ORDERAMOUNT %>" >
 		<input type="hidden" name="encodeType" id="encodeType" value="<%=ENCODETYPE %>" >
 		<input type="hidden" name="tranDate" id="tranDate" value="<%=TRANDATE %>" >
 		<input type="hidden" name="sign" id="sign" value="<%=SIGN %>" >
	</form>
</div>