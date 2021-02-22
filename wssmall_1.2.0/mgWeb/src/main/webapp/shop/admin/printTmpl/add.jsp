<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/commons/taglibs.jsp"%>
<script type="text/javascript" src="${staticserver }/js/admin/jqModal1.1.3.1.js"></script>
<script type="text/javascript" src="${staticserver }/js/admin/jqDnR.js"></script>
<script type="text/javascript" src="${staticserver }/js/admin/Ztp.AUI.js"></script>
<style>
.title {
	border-left: 1px solid #FFFFFF;
	border-right: 1px solid #BEC6CE;
	height: 65px;
	padding: 0 3px;
	float: left;
}
#dlg_upload{
	height:300px;
}
</style>
<script type="text/javascript" src="js/swfobject.js"></script>
<script type="text/javascript">
   var xmldata = "";

	var swfVersionStr = "10.0.0";
    var xiSwfUrlStr = "playerProductInstall.swf";
    var flashvars = {};
    flashvars.data = xmldata;
	flashvars.bg = "";
	swfobject : undefined;
    var params = {};
    params.quality = "high";
    params.bgcolor = "#ffffff";
    params.allowscriptaccess = "always";
    params.allowfullscreen = "true";
    params.wmode = "opaque";
    var attributes = {};
    attributes.id = "orderDesign";
    attributes.name = "orderDesign";
    swfobject.embedSWF(
        "js/OrderDesign.swf", "swf", 
        "100%", "100%", 
        swfVersionStr, xiSwfUrlStr, 
        flashvars, params, attributes);



  //function body_load(){
	//document.getElementById("lockbgel").checked=true; 
  //}

  function getFlashObj(movieName) {
	if (navigator.appName.indexOf("Microsoft") != -1) {
		return window[movieName];
	} else {
		return document[movieName];
	}
  }

  function createItem(obj){
	if(obj.value != "--"){
		var spv = obj.value.split("|");
		getFlashObj("orderDesign").createItem(spv[0],spv[1]);
	}
  }

  function delItem(){
	getFlashObj("orderDesign").delItem();
  }

  function uploadOk(data){
		if(data.result==1){
			$("#bgimage").val(data.path);
			getFlashObj("orderDesign").setBG(data.path);
		}else{
			alert(data.message);
		}
		Eop.Dialog.close("upload");	
	}

  function setBG(){
	  Eop.Dialog.open("upload");
	  $("#upload").load('${ctx}/eop/upload.do?subFolder=printTmpl');
  }

  function lockbg(obj){
		getFlashObj("orderDesign").lockBg(obj.checked);
  }

  function setFontSize(obj){
	if(obj.value != "--"){
		getFlashObj("orderDesign").setFontSize(obj.value);
	}
  }

  function setFont(obj){
	if(obj.value != "--"){
		getFlashObj("orderDesign").setFontType(obj.value);
	}
  }

  function setFontSpace(obj){
	if(obj.value != "--"){
		getFlashObj("orderDesign").setFontSpace(obj.value);
	}
  }

  function setFontBold(obj){
	getFlashObj("orderDesign").setFontBold();
  }

  function setFontItalic(obj){
	getFlashObj("orderDesign").setFontItalic();
  }

  function setTextAlign(value){
	getFlashObj("orderDesign").setTextAlign(value);
  }

  function resizeDesign(type,obj){
	if(type == "width"){
		document.getElementById("flashwrap").style.width = obj.value * 3.2;
	}else if(type == "height"){
		document.getElementById("flashwrap").style.height = obj.value * 3.2;
	}
  }

  function saveDesign(){
	  $("#xmldata").val(getFlashObj("orderDesign").exportData());
	//alert(getFlashObj("orderDesign").exportData());
  }
  </script>
<div class="input">
<form method="post" action="printTmpl!saveAdd.do" class="validate"
	name="theForm" id="theForm">
	<div style="height:67px;width:1100px;border:1px solid #ff0000;">
		<div class="title">
			单据名称<br/>
			<input type="text" class="ipttxt"  name="printTmpl.prt_tmpl_title"/><br/>
			<input type="radio" name="printTmpl.shortcut" value="true" checked/>启用&nbsp;
			<input type="radio" name="printTmpl.shortcut" value="false" />停用"
		</div>
		<div class="title">
			单据尺寸<br/>
			宽<input type="text" class="ipttxt"  style="width:30px;" name="printTmpl.prt_tmpl_width" value="250" onchange="resizeDesign('width',this)"/>*高<input type="text" class="ipttxt"  style="width:30px;" value="177" onchange="resizeDesign('height',this)"/>mm
		</div>
		<div class="title">
			单据背景<br/><input type="hidden" name="printTmpl.bgimage" id="bgimage"/>"
			<input type="checkbox" id="lockbgel" checked onchange="lockbg(this)"/>锁定
		</div>
		<div class="title">
			打印单类型<br/>
			<select name="printTmpl.config_id" id="print_type_s" style="width: 120px;">
				<c:forEach items="${configList }" var="cf">
					<option value="${cf.config_id }">${cf.config_name }</option>
				</c:forEach>
			</select>
		</div>
		<div class="title">
			单据打印项<br/>
			<select  class="ipttxt"  onchange="createItem(this);" id="print_items_s" style="width: 120px;">
				<option value="--">选择添加项</option>
				<c:forEach items="${nameList }" var="nl">
					<option value="${nl.c_name }|${nl.e_name}">${nl.c_name }</option>
				</c:forEach>
			</select>
			<button onclick="delItem();">删除</button>
		</div>
		<div class="title">
			<select  class="ipttxt"  onchange="setFontSize(this);">
				<option value="--">大小</option>
				<option value="10">10</option>
				<option value="12">12</option>
				<option value="14">14</option>
				<option value="18">18</option>

				<option value="20">20</option>
				<option value="24">24</option>
				<option value="27">27</option>
				<option value="30">30</option>
				<option value="36">36</option>
			</select>
			<select  class="ipttxt"  onchange="setFont(this);">
				<option value="--">字体</option>
				<option value="宋体">宋体</option>
				<option value="黑体">黑体</option>
				<option value="Arial">Arial</option>
				<option value="Verdana">Verdana</option>
				<option value="Serif">Serif</option>
				<option value="Cursive">Cursive</option>
				<option value="Fantasy">Fantasy</option>
				<option value="Sans-Serif">Sans-Serif</option>
			</select>
			<br/>
			<select  class="ipttxt"  name="jianju" id="jianju" style="height: 20px;" onchange="setFontSpace(this);">
			<option value="--" selected="selected">间距</option>
			<option value="-4">-4</option>
			<option value="-2">-2</option>
			<option value="0">0</option>
			<option value="2">2</option>

			<option value="4">4</option>
			<option value="6">6</option>
			<option value="8">8</option>
			<option value="10">10</option>
			<option value="12">12</option>
			<option value="14">14</option>

			<option value="16">16</option>
			<option value="18">18</option>
			<option value="20">20</option>
			<option value="22">22</option>
			<option value="24">24</option>
			<option value="26">26</option>

			<option value="28">28</option>
			<option value="30">30</option>
		  </select>
		  <button onclick="setFontBold();return false;">&nbsp;B&nbsp;</button>
		  <button onclick="setFontItalic();return false;">&nbsp;I&nbsp;</button>
		</div>
		<div class="title">
		  <button onclick="setTextAlign('left');return false;">左对齐</button>
		  <button onclick="setTextAlign('center');return false;">居中对齐</button><br>
		  <button onclick="setTextAlign('right');return false;">右对齐</button>
		  <input type="button" value="设置背景" onclick="setBG()"/>
		  
		</div>
	</div>
	<input type="hidden" name="printTmpl.prt_tmpl_data" id="xmldata" />
	<div id="flashwrap" style="height:567px;width:945px;border:1px solid #000;">
	<div id="swf"></div>
	<!--object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			id="orderDesign" width="100%" height="100%"
			codebase="http://fpdownload.macromedia.com/get/flashplayer/current/swflash.cab">
			<param name="movie" value="OrderDesign.swf"/>
			<param name="quality" value="high" />
			<param name="bgcolor" value="#ffffff" />
			<param name="allowScriptAccess" value="always" />
			<param name="salign" value="left" />
			<embed src="OrderDesign.swf" quality="high" bgcolor="#ffffff"
				width="100%" height="100%" name="orderDesign" 
				play="true"
				loop="false"
				quality="high"
				allowScriptAccess="always" salign="left"
				type="application/x-shockwave-flash"
				pluginspage="http://www.adobe.com/go/getflashplayer">
			</embed>
	</object-->
	</div>
<div class="submitlist" align="center">
<table>
	<tr>
		<td><input  type="submit" value=" 保存   "
			class="submitBtn" /></td>
	</tr>
</table>
</div>
</form>
</div>

<div id="upload"></div>

<script type="text/javascript">
$("form.validate").validate();
$(function(){
	Eop.Dialog.init({id:'upload', modal:false,width:"500px",height:"700px",title:'上传模板背景图片'});
	$("form").submit(function(){
		saveDesign();
	});
	
	$("#print_type_s").bind("change",function(){
		var cid = $(this).val();
		$("#print_items_s").empty();
		$("#print_items_s").load("printTmpl!listPrintItems.do?ajax=yes&config_id="+cid);
	});
});
</script>