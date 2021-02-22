<script language="javascript">

var CHART_TYPE=[{text:"2D��״ͼ",value:"{left:'column',right:'column'}"},{text:"����ͼ",value:"{left:'line',right:'line'}"},{text:"2D��״ͼ������ͼ",value:"{left:'column',right:'line'}"},{text:"����ͼ������ͼ",value:"{left:'area',right:'line'}"}];
var config=window.dialogArguments;
document.observe("dom:loaded", function(event) {
	
	Report.onDocumentReady(null);


	if(config.data){
		var arr=[];
	  for(var i=0;i<config.data.axisLeft.count;i++){
		  
		arr.push(config.data.axisLeft[i]);
	  }
	  config.data.axisLeft=arr;
      var arr1=[];
	  for(var i=0;i<config.data.axisRight.count;i++){
			arr1.push(config.data.axisRight[i]);
		  }
	  config.data.axisRight=arr1;
	
	  var tpl="{left:'{0}',right:'{1}'}";
	  if(Report.isEmpty(config.data.left)) config.data["left"]="line";
	  if(Report.isEmpty(config.data.right)) config.data["right"]="line";
	  Field.getField("x1").setValue(Report.format(tpl,[config.data.left,config.data.right]));
	  Field.getField("x1").flyback();
	}

	initList();
	$("enter").observe("click",enter);
	$("close").observe("click",function(event){window.returnValue=null;window.close();});
	$("leftClear").observe("click",function(event){
           var s=$("axisLeft").childNodes;
        	    for(var i=0;i<s.length;i++){
        	        var el=s[i];
        	        setSelectLi(el,false);
        	    }
		});
	$("rightClear").observe("click",function(event){
        var s=$("axisRight").childNodes;
     	    for(var i=0;i<s.length;i++){
     	    	  var el=s[i];
      	        setSelectLi(el,false);
     	    }
		});
	
});

function initList(){
   var xmlDoc=null;
   
   if (window.DOMParser){
      xmlDoc=new DOMParser().parseFromString(config.xml,"text/xml");
   }else{// Internet Explorer
     xmlDoc=new ActiveXObject("Microsoft.XMLDOM");
     xmlDoc.async="false";
     xmlDoc.loadXML(config.xml); 
   } 


   var root=xmlDoc.firstChild;
   var nodes=root.childNodes;
   var axisRight=$("axisRight");
   var axisLeft=$("axisLeft");

   for(var i=0;i<nodes.length;i++){
	   var n=nodes[i];
	   var region=n.getAttribute("region");
	   if(n.nodeName!="prepareSeries"){//Ԥ������
		   continue;
	   }
	   var ss=(region=="right")?axisRight:axisLeft;
	
	   var v=n.getAttribute("id");
	   var option=document.createElement("li");
	
	   option.setAttribute("value1",n.getAttribute("id"));//ie6���治��Ϊvalue���Ը�ֵ

	   option.innerHTML=n.getAttribute("label");

	   option.style.padding="2px";

	   Element.extend(option);
	   ss.appendChild(option);

	   option.observe("click",function(event){
              var el=Event.element(event);
              setSelectLi(el,!(el.getAttribute("selected")=="true"));
                  
		   });

	   if(config.data ){
		   if(n.getAttribute("region")=="right"){
			   if(config.data.axisRight && config.data.axisRight.indexOf(v)>=0){
				   setSelectLi(option,true);
			   }
		   }else{
			   if(config.data.axisLeft && config.data.axisLeft.indexOf(v)>=0){
				   setSelectLi(option,true);
			   }
		   }
	   }

	 
   }
 
}

function setSelectLi(li,b){
	if(b)li.addClassName("selected");
	else li.removeClassName("selected");
	li.setAttribute("selected",""+b);
}

function enter(){
	if(Report.isEmpty($("x1").value)){
		alert("ͼ����ʽû��ѡ����ѡ��");
		return;
	}
	
    var o=Report.apply({axisRight:[],axisLeft:[]},eval("("+$("x1").value+")"));
    var axisRight=$("axisRight").childNodes;
    var axisLeft=$("axisLeft").childNodes;
    
    for(var i=0;i<axisRight.length;i++){
        var op=axisRight[i];
        if(op.getAttribute("selected")=="true"){
            o.axisRight.push(op.getAttribute("value1"));
        }
    }

    for(var i=0;i<axisLeft.length;i++){
        var op=axisLeft[i];
        if(op.getAttribute("selected")=="true"){
            o.axisLeft.push(op.getAttribute("value1"));
        }
    }
    o.axisLeft["count"]=o.axisLeft.length;
    o.axisRight["count"]=o.axisRight.length;
    window.returnValue=o;
    window.close();
    
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
<title>ͼ������</title>
<table width="100%" height="100%" cellspacing="0" cellpadding="0">
	<tr>
		<td nowrap class="header">ͼ����ʽ</td>
		<td nowrap width="100%"><input id="x1" style="width: 100%"
			xtype="combo" data="CHART_TYPE"></td>
		<td nowrap>&nbsp;</td>
	</tr>
	<tr>
		<td nowrap height="50%" class="header">ѡ��������<br />
		(����չʾ):</td>
		<td nowrap align="left"><ul id="axisLeft"
			style="width: 100%; height: 100%"></ul></td>
		<td nowrap style="vertical-align: bottom" valign="bottom">
		<button xtype="button" id="leftClear">���</button>
		</td>
	</tr>
	<tr>
		<td nowrap height="50%" class="header">ѡ��������<br />
		(����չʾ):</td>
		<td nowrap><ul id="axisRight"
			style="width: 100%; height: 100%" ></ul></td>
		<td nowrap style="vertical-align: bottom" valign="bottom">
		<button xtype="button" id="rightClear">���</button>
		</td>
	</tr>
	<tr>
		<td nowrap height="50%" colspan="3"
			style="border-bottom: 0px !important"><span
			style="display: block; float: left">��סCTRL����Shift������ѡ���������</span> <span
			style="display: block; float: right">
		<button xtype="button" id="enter">ȷ��</button>
		&nbsp;
		<button xtype="button" id="close">�ر�</button>
		</span></td>
	</tr>
</table>