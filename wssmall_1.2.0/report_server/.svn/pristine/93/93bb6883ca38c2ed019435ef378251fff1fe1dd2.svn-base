package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.message.MessageBox;

	import flash.events.Event;
	import flash.events.IOErrorEvent;
	import flash.external.ExternalInterface;
	import flash.net.FileFilter;
	import flash.net.FileReference;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;
	import flash.net.navigateToURL;

	public class ReportDownload {

		public function ReportDownload() {
			//TODO: implement function
		}
		private var fr:FileReference=new FileReference();

		public function downloadAll(s:String):void {
			var url:URLRequest=new URLRequest();
			url.url=s;
			flash.net.navigateToURL(url, "_blank");


			//MessageBox.alert(url.url);
			//fr.addEventListener(Event.COMPLETE, complete);
			//fr.download(url, "报表包.zip");
		}
		private var requestURL:String;
		private var reportDir:String;
		private var callback:Function;

		public function upload(url1:String, dir:String, cb:Function):void {
			this.requestURL=url1;
			this.reportDir=dir;
			this.callback=cb;
			fr.addEventListener(Event.SELECT, uploadSelected);
			fr.addEventListener(Event.COMPLETE, uploadComplete);
			fr.addEventListener(IOErrorEvent.IO_ERROR, error);
			fr.browse([new FileFilter("Report Files(*.xml)", "*.xml")]);
		}

		private function uploadComplete(e:Event):void {
			var file:FileReference=FileReference(e.target);
			if (this.callback == null) {
				MessageBox.alert("成功上传文件.");
			} else {
				this.callback(file.name);
			}
			this.callback=null;
		}

		private function error(e:IOErrorEvent):void {
			MessageBox.alert(e.text);
			this.callback=null;
		}

		private function uploadSelected(e:Event):void {
			var file:FileReference=FileReference(e.target);
			var url:URLRequest=new URLRequest();
			var vars:URLVariables=new URLVariables();
			vars["report_dir"]=this.reportDir;
			//vars["replyType"]="chart";
			url.url=this.requestURL;
			url.data=vars;
			url.method=URLRequestMethod.POST;
			file.upload(url);
		}
	}
}