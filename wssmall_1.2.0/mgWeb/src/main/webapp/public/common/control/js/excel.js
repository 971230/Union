/**
 * 
 * add by wui
 * 上传下载 控件工具使用说明
 * 
 * 页面定义部分
 * <span onclick="downLoadExcel();" style='cursor:hand;font-weight: bold;'><font color="#FCD410;">点击下载号码模板..</font></span>
   <span id='uploadDiv' style='width:40%;padding-right:10px;'></span>
   <input type="button" value="导入号码..." id="close_Win" onMouseOver=this.className='btn04' onMouseOut=this.className='btn02'  class="btn02"  onclick="uploadExcel();" >
	
   对象创建部分
   config["titles"] =['产品号码'];
   config["fieldnames"] =['acc_nbr'];
   excelInst = new acceptExcel(config);
   
   子类覆盖父类重写实现
   
	
 */
(function(scope) {
	var ExcelBase = Base.extend({
		dConfig :{},
		exportcNamesArr:[],
		exportFieldNamesArr:[],
		uploadDiv:"uploadDiv",
		default_upload_id :'uploadIframe',
		default_down_id :'downLoadIframe',
		constructor : function(pconfig) {
			this._init(pconfig);
		},
		_init:function(pconfig){
			this._initConfig(pconfig);
			var iframeNames=pconfig && pconfig['iframeName'] ||[this.default_upload_id]; //传递上传ids过来
			for(var i=0;i<iframeNames.length;i++)
				this._initUIframe(iframeNames[i]);
			iframeName=pconfig && pconfig['iframeName'] || this.default_down_id;
			this._initConfig(pconfig);
			this._initDIframe(iframeName);
			
			
		},
		_initDIframe:function(iframeName){
			if($("#"+iframeName).length==0){
				var formHtmlArr = [];
				formHtmlArr.push("<iframe name='"+iframeName+"' id='"+iframeName+"' src='/public/v4/common/control/excel/downLoadExcelMb.jsp'  frameborder='0' scrolling='no' ></iframe>");
				$("body").append($(formHtmlArr.join("")));
			
			}
		},
		_initUIframe:function(iframeName){
			if($("#"+iframeName).length==0){
				var htmlContentArr = [];
				htmlContentArr.push("<iframe name='"+iframeName+"' id='"+iframeName+"' src='/public/v4/common/control/excel/UpLoadExcelFile.jsp' width='100%' height='21px' frameborder='0' scrolling='no' style='margin:0px;padding:0px;overflow:hidden;'></iframe>");
				$("#"+this.uploadDiv).append(htmlContentArr.join(""));
			}
		},
		uploadExcel:function (pconfig){ //上传
			pconfig= pconfig|| {};
			var iframeName=pconfig['iframeName'] || this.default_upload_id;
			this._initConfig(pconfig);
			if(!this.upValidte(iframeName))
				return;
			var iframeDocument = this._getIframe(iframeName);
			var url ="/servlet/excelServlet?url=/public/v4/common/control/excel/UpLoadExcelFile.jsp&lx=sUploadToSession"+this.upAssembleData();
			iframeDocument.find("form").attr("action",url);
			iframeDocument.find("form").submit();
		},
		upValidte:function(iframeName){ //上传验证
			var iframeDocument =this._getIframe(iframeName);
			var fileName = iframeDocument.find("[id='uploadFile']").val();
			if(!fileName)
			{
				alert("请选择需要上传的文件！");
				return false;
			}
			return true;
		},
		upAssembleData:function(){//上传组装数据
			var paramArr = [];
			paramArr.push("&colCount=" + this.exportcNamesArr.length);
			paramArr.push("&cols=" + this.exportFieldNamesArr.join(","));
			return paramArr.join("");
		},
		upCallBack:function(msg){ //上传后回调函数,子类可以重写
			if(msg =='succ')
				this.loadImportDataFromDb();
			else{
				msg && alert(msg);
			}
		},
		loadImportDataFromDb:function(){	//从数据库获取导入的数据
			alert("请子类继承父类重写_loadImportDataFromDb函数。");
		},
		_getIframe:function(iframeName){ //获取iframe
			var ifDoc = $($("body").find("iframe[id='"+iframeName+"']").get(0).contentWindow.document.body);
			return ifDoc
		},
		//下载处理器
		downLoadExcel:function (pconfig){ //下载
			var iframeName=pconfig && pconfig['iframeName'] || this.default_down_id;
			this._initConfig(pconfig);
			this._initDIframe(iframeName);
			var iframeDocument = this._getIframe(iframeName);
			var formJq = $(iframeDocument).find("form");
			this._setDAction(formJq);
			formJq.submit();
		},
		_setDAction:function(formJq){
			var action =formJq.attr("tempAction")+"?lx=export_excel&title="+this.exportcNamesArr.join(",")+"&prop="+this.exportFieldNamesArr.join(",")+"&"+ jQuery.param(this.req_param);
			
			formJq.attr("action",action);
		},
		_initConfig:function(pconfig){
			this.exportcNamesArr = pconfig && pconfig['titles'] || this.exportcNamesArr;
			this.exportFieldNamesArr = pconfig && pconfig['fieldnames'] || this.exportFieldNamesArr;
			this.req_param = pconfig && pconfig['req_param'] || {};
			
		}
	});
	window.excelBase = new ExcelBase();
}(window));

