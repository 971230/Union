<%@page import="zte.net.ecsord.common.AttrConsts"%>
<%@page import="zte.net.ecsord.common.CommonDataFactory"%>
<%@page import="zte.net.ecsord.common.SpecConsts"%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/commons/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>预拣货</title>
<script src="<%=request.getContextPath() %>/ecs_ord/js/autoord.js"></script>
<!-- <script src="<%=request.getContextPath() %>/ecs_ord/js/RwCardExtract.js"></script>
<script src="<%=request.getContextPath() %>/ecs_ord/js/writeCard.js"></script> -->
<%
  String order_id = (String)request.getAttribute("order_id");
%>

</head>
<body>
<form action="javascript:void(0);" id="preDealOrderForm" method="post">

<div class="gridWarp">
	<div class="new_right">
        <div class="right_warp">
             <!-- 顶部公共 -->
        	<jsp:include page="auto_flows.jsp?order_id=${order_id}"/>
        	<div class="grid_n_div">
            	<h2><a href="#" class="closeArrow"></a>订单信息</h2>
                <div class="grid_n_cont">
                	<div class="grid_n_cont_sub">
                    	<h3 id="card_no"></h3>
                        <table width="100%" border="0" cellspacing="0" cellpadding="0" class="grid_form">
                          <tr>
                            <td class="title_desc">
		                    <select id="cardList" style="width:250"></select>
		                    <input type="button" id="selectCard" value="列出卡List"/>
		                    </td>
                          </tr>
                          <tr>
                            <td class="title_desc">
		                    <input type="text" name="cardNo" id="cardNo"/>
		                    <input type="button" id="readCard"  value="输出卡号"/>
		                    </td>
                          </tr>
                          <tr>
                            <th><span>*</span>读卡：</th>
                            <td id="readCard">未开始</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>开户：</th>
                            <td id="openAccount">未开始</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>获取写卡数据：</th>
                            <td id="getCardInfo">未开始</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>写卡：</th>
                            <td id="writeCard">未开始</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                          <tr>
                            <th><span>*</span>完成：</th>
                            <td id="finish">未完成</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                            <th>&nbsp;</th>
                            <td>&nbsp;</td>
                          </tr>
                        </table>
                    </div>
                </div>
            </div>
        	
         </div>
    </div>
</div>
</form>
</body>
<OBJECT id="Ocxtest" classid=clsid:43E4D4FC-3CD8-459A-AAA1-698C1288DE93><PARAM NAME="_Version" VALUE="65536"><PARAM NAME="_ExtentX" VALUE="2646"><PARAM NAME="_ExtentY" VALUE="1323"><PARAM NAME="_StockProps" VALUE="0"></OBJECT>
<script type="text/javascript">
$(function(){
	$("#selectCard").bind("click",function(){
		doListCard();
	});
	function sure(){
		var cardNum=document.getElementById("cardNum").value;
		if(cardNum==null || cardNum=="" || cardNum.length!="20"){
			alert("卡号有误！卡号："+cardNum);
			return;
		}
		window.opener.document.getElementsByName("UserArInfo.SIM")[0].value=cardNum;
		window.close();
	}
	//关闭窗口
	function cancle(){
		window.close();
	}
	//读取卡号
	function getCardNum(){
		var obj=document.getElementById("Ocxtest");//获取对象
		var cardName=document.getElementById("cardList").value;
		if(cardName==null || cardName==""){
			alert("请选择读卡器！");
			return;
		}
		//alert(cardName);
		var retConnect=obj.ConnectCard(cardName);//连接读卡器,报错后要断开，否则再连会失败
		if(!(retConnect=="0" || retConnect=="")){//连接失败
			document.getElementById("errMsg").value=obj.GetErrMsg();
			document.getElementById("retMsg").value=retConnect;
			alert("连接读卡器失败！报错："+obj.GetErrMsg());
			doDisConnectCard();//报错后要断开，否则再连会失败
			return;
		}
		var cmdArray = new Array();
		var cardRet="";
		cmdArray.push("A0A40000023F00");//APDU命令，选择目录
		cmdArray.push("A0A40000022FE2");//APDU命令，选择文件
		cmdArray.push("A0B000000A");//APDU命令，读取卡号
		for(var i=0;i<cmdArray.length;i++){
			var cmd=cmdArray[i];
			var retTrans=obj.TransmitCard(cmd,cardName);//向读卡器发送命令
			var err=obj.GetErrMsg();
			//err=err;
			if(!(err=="0" || err=="")){
				document.getElementById("errMsg").value=obj.GetErrMsg();
				document.getElementById("retMsg").value=retTrans;
				alert(err.length);
				alert(err);
				alert("向读卡器发送命令失败！报错："+obj.GetErrMsg());
				
				doDisConnectCard();//报错后要断开，否则再连会失败
				return;
			}
			cardRet=retTrans;//最后一个命令返回卡号
		}
		//指令返回98 68 10 11 11 11 11 11 11 11 90 00 ，前20位转置后为卡号，后四位为返回码
		alert(cardRet);
		cardRet=trim(cardRet);//去掉空格
		alert(cardRet);
		if(!isnumber(cardRet)){
			alert("读取的卡号只能为数字："+cardRet);
			doDisConnectCard();//报错后要断开，否则再连会失败
			return;
		}
		if(cardRet.length!="24" ){
			alert("读取的卡号格式不对："+cardRet);
			doDisConnectCard();//报错后要断开，否则再连会失败
			return;
		}
		if(cardRet.substr(20,4)!="9000" ){
			alert("读卡失败，返回码："+cardRet.substr(20,4));
			obj.DisconnectCard(cardName);//报错后要断开，否则再连会失败
			return;
		}
		cardRet=cardRet.substr(0,20);
		var cardNum=dealCardNum(cardRet);
		document.getElementById("cardNum").value=cardNum;
	}
	//转换卡号 0123456789>1032547698 第一二位对换、三四位对换...
	function dealCardNum(cardStr){
		var cardLength=cardStr.length;
		var cardNum="";
		for(var i=0;i<cardLength;i++){
			if(i%2==0){
				cardNum+=cardStr.charAt(i+1);
			}
			else{
				cardNum+=cardStr.charAt(i-1);
			}
		}
		return cardNum;
	}
	//列出写卡器
	function doListCard(){
		var obj=document.getElementById("Ocxtest"); 
		alert(obj);
		alert(obj.ListCard());
		var str=obj.ListCard();
		alert(str);
		if(str==null || str==""){
			document.getElementById("errMsg").value=obj.GetErrMsg();
			alert("没有取到读卡器，请检查控件是否注册或者驱动是否安装或者连接是否正常！");
			return;
		}
		//动态加载下拉框，将写卡器列出来
		if(str!=null && str!=""){
			var list = document.getElementById("cardList");
			for(var i = list.options.length-1; i >= 0; i--){
				alert("i===="+i);
				list.remove(i);
			}
			var strArray =str.split(";");
			for(var i = 0; i < strArray.length; i++){
				var newOption = document.createElement("option");
				newOption.setAttribute("value", strArray[i]);
				newOption.appendChild(document.createTextNode(strArray[i]));
				list.appendChild(newOption);
			}
		}
	}
	//连接写卡器
	function doConnectCard(){
		var obj=document.getElementById("Ocxtest");
		var str=document.getElementById("cardList");
		var ret=obj.ConnectCard(str);
		document.getElementById("errMsg").value=obj.GetErrMsg()+ ret;
	}
	//发送指令
	function doSendCard(){
		var obj=document.getElementById("Ocxtest");
		var str=document.sendMsg.value;
		var ret=obj.TransmitCard(str,document.getElementById("cardList"));
		document.getElementById("errMsg").value=obj.GetErrMsg();
		document.getElementById("retMsg").value=ret ;
	}
	//断开连接
	function doDisConnectCard(){
		var obj=document.getElementById("Ocxtest");
		var str=document.getElementById("cardList").value;
		var ret = obj.DisconnectCard(str);
		//document.getElementById("errMsg").value=obj.GetErrMsg()+ret;
	}
	//去掉所有的空格
	function trim(str){
		return str.replace(/\s+/g,"");
	}
	//判断是否为数字
	function isnumber(strs){
		for(var i=0;i<strs.length;i++){
			var str=strs.charAt(i);
			if((str<'0') || (str>'9'))
				return false;
		}
		return true;
	}
});
</script>