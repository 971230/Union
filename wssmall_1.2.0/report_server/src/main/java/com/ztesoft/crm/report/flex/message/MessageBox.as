package com.ztesoft.crm.report.flex.message {
	import mx.controls.Alert;
	import mx.events.CloseEvent;

	public class MessageBox {

		public function MessageBox() {
			//TODO: implement function
		}

		private static function callalert(s:String):void {
			//flash.external.ExternalInterface.call("alert", s);
			Alert.okLabel="确定";
			Alert.show(s, "系统提示", mx.controls.Alert.OK);
		}

		public static function alert(s:Object):void {
			if (s == null || typeof(s) == "undefined") {
				MessageBox.callalert("null");
				return;
			}
			if (s is String) {
				MessageBox.callalert(String(s));
				return;
			}
			MessageBox.callalert(s.toString());
		}


		public static function confirm(s:String, cb:Function):void {
			Alert.yesLabel="是";
			Alert.noLabel="否";
			Alert.show(s, "确认", Alert.YES | Alert.NO, null, function(e:CloseEvent):void {
				if (e.detail == Alert.YES) {
					cb.call(this);
				}
			});
		}
	}
}