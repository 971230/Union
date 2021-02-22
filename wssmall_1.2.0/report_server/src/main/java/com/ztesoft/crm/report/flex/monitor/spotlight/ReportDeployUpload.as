package com.ztesoft.crm.report.flex.monitor.spotlight {
	import com.ztesoft.crm.report.flex.admin.ReportExplorer;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.monitor.ReportMonitorApplication;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.events.MouseEvent;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;

	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.Button;
	import mx.controls.Label;
	import mx.controls.TextInput;
	import mx.events.FlexEvent;

	public class ReportDeployUpload extends VBox {
		public function ReportDeployUpload() {
			//TODO: implement function
			super();
			this.percentHeight=100;
			this.percentWidth=100;

			this.setStyle("paddingBottom", 5);
			this.setStyle("paddingLeft", 10);
			this.setStyle("paddingTop", 5);
			this.setStyle("paddingRight", 10);
			this.addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
				init();
			});


		}

		private var fr:FileReference=new FileReference();

		private var body:VBox;
		private var buttonBar:HBox;

		private function init():void {
			body=new VBox();
			body.percentHeight=100;
			body.percentWidth=100;
			buttonBar=new HBox();
			buttonBar.percentWidth=100;
			buttonBar.height=26;
			this.addChild(body);
			this.addChild(buttonBar);
			this.initBody();
			this.initButtonBar();
		}

		private var reporttree:ReportExplorer;
		private var zipfile:TextInput;

		private function initBody():void {
			/**/
			var label:Label=new Label();
			label.text="指定要发布的目录，";
			this.body.addChild(label);
			label.percentWidth=100;

			reporttree=new ReportExplorer();
			reporttree.percentHeight=100;
			reporttree.width=400;
			reporttree.contextMenu=null;
			reporttree.doubleClickEnabled=false;
			reporttree.setAjax(ReportMonitorApplication(this.parentApplication).parameters.ajax);
			reporttree.reload(null);
			this.body.addChild(reporttree);
			/**/
			label=new Label();
			label.text="指定要上载并安装的报表打包文件(.ZIP)。";
			this.body.addChild(label);
			label.percentWidth=100;

			/**/
			var hBox:HBox=new HBox();
			var text:TextInput=new TextInput();
			hBox.addChild(text);
			hBox.percentWidth=100;


			text.height=22;
			text.editable=false;

			text.width=200;
			hBox.addChild(createButton("浏览...", 80, 22, selectZip));
			this.zipfile=text;
			this.body.addChild(hBox);
			/**/
			fr.addEventListener(Event.SELECT, uploadSelected);
			fr.addEventListener(Event.COMPLETE, uploadComplete);
			fr.addEventListener(IOErrorEvent.IO_ERROR, error);

		}

		private function uploadSelected(e:Event):void {
			this.zipfile.text=fr.name;
		}

		private function uploadComplete(e:Event):void {
			MessageBox.alert("上传完成");
		}

		private function error(e:IOErrorEvent):void {
			MessageBox.alert(e.text);
		}


		private function createButton(text:String, w:int, h:int, handler:Function=null):Button {
			var button:Button=new Button();
			button.label=text;
			button.width=w;
			button.height=h;
			if (handler != null) {
				button.addEventListener(MouseEvent.CLICK, handler);
			}
			return button;
		}

		private function initButtonBar():void {
			this.buttonBar.addChild(createButton("发布", 100, 22, nextStep));
		}

		private function selectZip(e:MouseEvent):void {
			fr.browse([new FileFilter("打包文件(*.zip)", "*.zip")]);
		}


		private function nextStep(e:MouseEvent):void {
			var path:String=reporttree.getSelectedPath();
			var file:String=this.zipfile.text;
			if (ObjectUtil.isEmpty(file)) {
				MessageBox.alert("请选择一个文件上传(.ZIP)。");
				return;
			}

			var url:URLRequest=new URLRequest();
			var vars:URLVariables=new URLVariables();
			vars["report_dir"]=path;
			//vars["replyType"]="chart";
			url.url=ReportMonitorApplication(this.parentApplication).ajax + "/VersionBean/deploy.do";
			url.data=vars;
			url.method=URLRequestMethod.POST;
			fr.upload(url);
		}

	}
}