package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.message.MessageBox;

	public final class RPTComponentItem {

		public function RPTComponentItem(... args) {
			//TODO: implement function
			if (args != null) {
				if (args is Array) {
					var arr:Array=args as Array;
					var len:int=arr.length;
					if (len > 0) {
						this.uid1=arr[0] as String;
						map[this.uid1]=this;
					}
					if (len > 1) {
						this.tagName1=arr[1] as String;
					}
					if (len > 2) {
						this.attributes1=arr[2];
					}
					if (len > 3) {
						this.clazz1=arr[3] as Class;
					}
				}
				if (args is String) {
					this.uid1=args as String;
				}
			}
		}
		private var uid1:String;
		private var tagName1:String;
		private var properties1:Object;
		private var attributes1:Object;
		private var dialog1:Boolean=false;
		private var clazz1:Class;

		public function get uid():String {
			return this.uid1;
		}

		public function get tagName():String {
			return this.tagName1;
		}

		public function get properties():Object {
			return this.properties1;
		}

		public function get attributes():Object {
			return this.attributes1;
		}

		public function get dialog():Boolean {
			return this.dialog1;
		}

		public function get clazz():Class {
			return this.clazz1;
		}
		public static var map:Object={};
		public static var hbox:RPTComponentItem=new RPTComponentItem("hbox", "box", {horizontal: "true"});
		public static var vbox:RPTComponentItem=new RPTComponentItem("vbox", "box", {});
		public static var crosstab:RPTComponentItem=new RPTComponentItem("crosstab", "panel", {type: "crosstab"});
		public static var listtab:RPTComponentItem=new RPTComponentItem("listtab", "panel", {type: "listtab"});
		public static var chart:RPTComponentItem=new RPTComponentItem("chart", "panel", {type: "chart"});
		public static var form:RPTComponentItem=new RPTComponentItem("form", "panel", {type: "form"});
		public static var formrow:RPTComponentItem=new RPTComponentItem("formrow", "row", {}, {});
		public static var text:RPTComponentItem=new RPTComponentItem("text", "field", {type: "text", label: "文本域"});
		public static var date:RPTComponentItem=new RPTComponentItem("date", "field", {type: "date", label: "日期选择"});
		public static var combo:RPTComponentItem=new RPTComponentItem("combo", "field", {type: "combo", label: "下拉框"});
		public static var checkbox:RPTComponentItem=new RPTComponentItem("checkbox", "field", {type: "checkbox", label: "复选框"});
		public static var treefield:RPTComponentItem=new RPTComponentItem("treefield", "field", {type: "tree"});
		public static var labelfield:RPTComponentItem=new RPTComponentItem("labelfield", "field", {type: "label", label: "文本"});
		public static var radiobox:RPTComponentItem=new RPTComponentItem("radiobox", "field", {type: "radiobox", label: "单选框"});
		public static var submit:RPTComponentItem=new RPTComponentItem("submit", "field", {type: "submit", text: "查询"});
		public static var popupselect:RPTComponentItem=new RPTComponentItem("popupselect", "field", {type: "popup", text: "弹出窗口"});
		public static var excel:RPTComponentItem=new RPTComponentItem("excel", "field", {type: "excel", text: "导出"});
		public static var print:RPTComponentItem=new RPTComponentItem("print", "field", {type: "print", text: "打印"});
		public static var link:RPTComponentItem=new RPTComponentItem("link", "field", {type: "link", content: "超链接"});
		public static var panelrow:RPTComponentItem=new RPTComponentItem("panelrow", "row", {});
		public static var titlebox:RPTComponentItem=new RPTComponentItem("titlebox", "box", {css: "rpt-title"});
		public static var prompt:RPTComponentItem=new RPTComponentItem("prompt", "field", {type: "prompt", text: "预警设置"});
		public static var pie:RPTComponentItem=new RPTComponentItem("simplechart", "panel", {type: "simplechart"});
	}
}