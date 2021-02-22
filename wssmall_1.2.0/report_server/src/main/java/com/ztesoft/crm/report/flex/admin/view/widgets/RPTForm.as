package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;

	public class RPTForm extends RPTComponent {

		public function RPTForm() {
			//TODO: implement function
			super();
			setSides("");
			this.percentWidth=100;
			setMinSize(0, 30);
		}

		override public function getPropertiesConfig():Object {
			var o:Object={cols: 1};
			return {cols: 1, items: [{label: "标题", name: "text"}, {label: "目标面板", name: "target", type: "checkbox", data: getData().getPanelDataWithoutForm()}, {label: "列等宽", name: "columnEqualWidth",
							type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE}, {label: "标签宽度", name: "labelWidth"}, {label: "标签分割符", name: "labelSeparator"}, {label: "显示标签", name: "labelVisiable",
							type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE}, {label: "可以导出？", name: "exportEnable", type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE}]};
		}

		override public function acceptChild(type:String):int {
			if (type == "formrow") {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function load(n:XMLNodeObject):void {
			var arr:Array=n.children();
			var len:int=arr.length;
			for (var i:int=0; i < len; i++) {
				var en:XMLNodeObject=XMLNodeObject(arr[i]);
				var row1:RPTFormRow=new RPTFormRow();
				row1.horizontal();
				row1.render();
				row1.load(en);
				body.addChild(row1);
			}
		}
	}
}