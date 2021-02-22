package com.ztesoft.crm.report.flex.admin {
	import com.adobe.serialization.json.JSON;
	import com.ztesoft.crm.report.flex.URLConnection;
	import com.ztesoft.crm.report.flex.admin.dialog.SourceDialog;
	import com.ztesoft.crm.report.flex.admin.elements.ModelSQLGenerator;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	import mx.core.UIComponent;

	public class ReportGlobalEvent {

		public function ReportGlobalEvent() {
		//TODO: implement function
		}
		public static var VIEW_REPORT:int=1;
		public static var VIEW_MODEL:int=2;
		public static var SAVE_REPORT:int=3;
		public static var PREVIEW_REPORT:int=4;
		public static var CHECK_REPORT:int=5;

		public function buttonClick(e:MouseEvent, type:int):void {
			var b:UIComponent=UIComponent(e.currentTarget);
			var app:ReportApplication=ReportApplication(b.parentApplication);
			if (app.reportData == null) {
				MessageBox.alert("请双击报表目录树上面的报表选择一个报表进行处理!");
				return ;
			}
			switch (type) {
				case ReportGlobalEvent.VIEW_REPORT:
					this.viewReportSource(app);
					break;
				case ReportGlobalEvent.VIEW_MODEL:
					this.viewModelSource(app);
					break;
				case ReportGlobalEvent.SAVE_REPORT:
					this.saveReportToLocal(app);
					break;
				case ReportGlobalEvent.PREVIEW_REPORT:
					this.previewReport(app);
					break;
				case ReportGlobalEvent.CHECK_REPORT:
					this.checkReport(app);
					break;
				default:
					break;
			}
		}

		private function viewModelSource(app:ReportApplication):void {
			var n:XMLNodeObject=XMLNodeObject(app.models.selectedItem);
			if (n != null) {
				var dlg:SourceDialog=new SourceDialog();
				dlg.setTitle("查看模型脚本");
				dlg.open(app, new ModelSQLGenerator().toSQL(n));
			}
		}

		private function viewReportSource(app:ReportApplication):void {
			var d:XMLNodeObject=app.reportData;
			var dlg:SourceDialog=new SourceDialog();
			dlg.setTitle("查看报表源代码");
			dlg.open(app, d.toXML());
		}

		private function saveReportToLocal(app:ReportApplication):void {
			var data:Object=app.explorer.selectedItem;
			if (ObjectUtil.isTrue(data.leaf)) {
				var o:Object={report_name:data.text, report_dir:data.value, report_content:app.reportData.toXML()};
				new URLConnection(function(a:String):void {
						var d:Object=JSON.decode(a, true);
						if (ObjectUtil.isTrue(d.error)) {
							MessageBox.alert(d.message);
						}
						else
							MessageBox.alert("保存报表成功！");
					}).doPostByXML(app.ajaxURL("VersionBean/upload1.do", {}), o);
			}
		}

		private function previewReport(app:ReportApplication):void {
			var data:Object=app.explorer.selectedItem;
			var xml:String=(data.value).replace(".xml", "");
			if (ObjectUtil.isTrue(data.leaf)) {
				var url:String=app.preViewPreffixURL + "/" + xml;
				new URLConnection(function(a:String):void {
						var d:Object=JSON.decode(a, true);
						if (!ObjectUtil.isTrue(d.error)) {
							var request:URLRequest=new URLRequest();
							request.url=url;
							flash.net.navigateToURL(request, "_blank");
						}
					}).open(app.ajaxURL("VersionBean/update.do", {report_path:"/" +
							xml}), null)
			}
		}

		private function checkReport(app:ReportApplication):void {
			if (new ReportChecker(app).check(app.reportData)) {
				MessageBox.alert("报表配置校验通过!");
			}
		}
	}
}