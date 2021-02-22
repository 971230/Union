<script language="javascript">

var SERIES=[];
var config=window.dialogArguments;
var params={axis:"",series:{}};
document.observe("dom:loaded", function(event) {
	initList();
	
	Report.onDocumentReady(null);

	$("enter").observe("click",enter);
	$("close").observe("click",function(event){window.returnValue=null;window.close();});
	
	$("leftClear").observe("click",function(event){
           var s=$("range").childNodes;
        	    for(var i=0;i<s.length;i++){
        	        var op=s[i];
        	        setSelectLi(op,false);
        	    }
		});
	
});

function loadXML(str){
	   var xmlDoc=null;
	   
	   if (window.DOMParser){
	      xmlDoc=new DOMParser().parseFromString(str,"text/xml");
	   }else{// Internet Explorer
	     xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
	     xmlDoc.async="false";
	     xmlDoc.loadXML(str);
	   } 
	   return xmlDoc;
}

function initList(){
   var xmlDoc=loadXML(config.config);
    
   var root=xmlDoc.firstChild;
   var nodes=root.childNodes;
  
   for(var i=0;i<nodes.length;i++){
	   var n=nodes[i];
	   if(n.nodeName=="series"){
		   var o={};
		   o["field"]=n.getAttribute("field");
		   o["displayName"]=n.getAttribute("displayName");
		   o["text"]=o.displayName;
		   o["value"]=o.field;
		   params.series[o.field]=o;
		   SERIES.push(o);
	   }

	   if(n.nodeName=="axis"){
		   params.axis=n.getAttribute("field");
	   }
      
	 
   }
   //
   xmlDoc=loadXML(config.data);
   root=xmlDoc.firstChild;
   nodes=root.childNodes;
   var range=$("range");
 
   for(var i=nodes.length-1;i>=0;i--){
	   var n=nodes[i];
	   if(n.nodeName=="r"){
		   var option=document.createElement("LI");
		   option.setAttribute("value1",n.getAttribute(params.axis));//ie6下面不能使用这个方法为value属性赋值
		   option.innerHTML=n.getAttribute(params.axis);
		   option.style.padding="2px";
		   range.appendChild(option);
		   Element.extend(option);
		   option.observe("click",function(event){
	              var el=Event.element(event);
	              setSelectLi(el,!(el.getAttribute("selected")=="true"));
	                  
			   });
	   } 
   }
}

function setSelectLi(li,b){
	if(b)li.addClassName("selected");
	else li.removeClassName("selected");
	li.setAttribute("selected",""+b);
}

function enter(){
   var flash=Report.panel.getFlashMovieObject("chart1");
   var s=$("x1").value;
   if(Report.isEmpty(s)){
	    alert("请选择一个指标!");
	    return;
   }

   s=params.series[s];
   var rang=[];
   var el=$("range").childNodes;
   for(var i=0;i<el.length;i++){
       var op=el[i];
       if(op.getAttribute("selected")=="true"){
           rang.push(op.getAttribute("value1"));
       }
   }

   if(flash) flash.load(s,params.axis,config.data,rang.join(","));
    
}
</script>
<style>
<!--
td {
	padding: 4px;
	border-bottom: 3px groove #C0C0C0;
}

td.header {
	border-right: 3px groove #C0C0C0;
}
ul {
	list-style-type: none;
	float:left;
	margin:0;
	cursor:pointer;
	display:block;
	padding:0;
	width:100%;
}
li {
    list-style-type: none;
    width:100%;
    border:1px solid white;
}

li.selected {
    background-color:#C0C0C0;
    border:1px dashed black;
}
-->
</style>
<title>占比查看</title>
<table width="100%" height="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td nowrap class="header">选择指标</td>
		<td nowrap width="100%"><input id="x1" style="width: 100%"
			xtype="combo" data="SERIES"></td>
		<td nowrap>
		<button xtype="button" id="enter">查询</button>
		</td>
	</tr>
	<tr>
		<td nowrap class="header">选择对象</td>
		<td nowrap ><div style="overflow-y:auto;overflow-x:hidden;height:110px;width:100%"><ul id="range" style="width: 100%; height: 100%"
			multiple></ul></div></td>
		<td nowrap style="vertical-align: bottom" valign="bottom">
		<button xtype="button" id="leftClear">清空</button>
		</td>
	</tr>
	<tr>
		<td nowrap height="100%" colspan="3"><object
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab"
			id="chart1" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			style="width: 100%; height: 100%;">
			<param value="transparent" name="wmode">
			</param>
			<param value="high" name="quality">
			</param>
			<param value="#FFFFFF" name="bgcolor">
			</param>
			<param value="always" name="allowScriptAccess">
			</param>
			<param
				value="../reportPie.swf"
				name="movie">
			</param>
			<embed width="100%" wmode="transparent"
				type="application/x-shockwave-flash"
				src="../reportPie.swf"
				pluginspage="http://www.adobe.com/go/getflashplayer" height="100%"
				allowScriptAccess="always" align="middle" play="true"
				bgcolor="#FFFFFF" loop="false" name="chart1" quality="high">
			</embed> </object></td>
	</tr>
	<tr>
		<td nowrap colspan="3"
			style="border-bottom: 0px !important"><span
			style="display: block; float: left">按住CTRL或者Shift键可以选择多行数据</span> <span
			style="display: block; float: right">
		<button xtype="button" id="close">关闭</button>
		</span></td>
	</tr>
</table>