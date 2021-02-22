package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.ReportExplorer;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import mx.core.UIComponent;

	public class ReportBrowserDialog extends ModalDialog {

		public function ReportBrowserDialog() {
			//TODO: implement function
			super();
		}
		private var pb:ReportExplorer;

		override public function createContent():UIComponent {
			pb=new ReportExplorer();
			pb.contextMenu=null;
			pb.doubleClickEnabled=true;
			pb.addEventListener(MouseEvent.CLICK, doubleClick);
			return pb;
		}

		private function doubleClick(e:MouseEvent):void {
			var o:Object=getData();
			if (o != null) {
				if (getCallback() != null) {
					getCallback()(o);
				}
				close(null);
			}
		}

		override public function showOkCloseButton():Boolean {
			return false;
		}

		override public function getData():Object {
			var item:Object=pb.selectedItem;
			var o:Object=null;
			if (item != null && ObjectUtil.isTrue(item.leaf)) {
				o={href: item.value.replace(".xml", "")};
			}
			return o;
		}

		override public function load(data:Object):void {
			pb.setAjax(data as String);
			pb.load();
		}
	}
}