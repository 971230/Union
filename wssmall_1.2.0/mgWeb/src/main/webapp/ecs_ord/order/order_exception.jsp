<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<head>
    <title>ECharts</title>
</head>
<body>
	<div style="width: 100%;" >
		<iframe src="${url }" style="width: 100%;height: 100%;" id ="order_excep_frame" onLoad="iFrameHeight()"></iframe>
	</div>
</body>
<script type="text/javascript">
function iFrameHeight(){
	var parentDocument;
	try{
		parentDocument = parent.document;
	}catch(e){
		parentDocument = $(document).context;
	}
	var height = $(".auto_frame",parentDocument).height();

	//计算顶部菜单高度，不同环境顶部不同
	$("#order_excep_frame").css("height",height-3+"px");
}
</script>
