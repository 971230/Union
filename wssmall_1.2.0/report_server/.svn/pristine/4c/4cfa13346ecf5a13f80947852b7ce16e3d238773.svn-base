package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.admin.dialog.ModalDynamicConditionDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.grid.DataGridColumnEx;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;

	import flash.events.ContextMenuEvent;

	import mx.controls.DataGrid;

	public class ModelDynamicCondition extends DataGrid {
		public function ModelDynamicCondition() {
			//TODO: implement function
			super();

			var arr:Array=[];
			arr.push(new DataGridColumnEx({name: "tableLabel", label: "表名", width: 2}));
			arr.push(new DataGridColumnEx({name: "columnLabel", label: "字段名", width: 2}));
			arr.push(new DataGridColumnEx({name: "operator", label: "操作符", width: 1, data: ReportConstanst.OPERATOR}));
			arr.push(new DataGridColumnEx({name: "startValue", label: "开始值", width: 1}));
			arr.push(new DataGridColumnEx({name: "endValue", label: "结束值", width: 1}));

			this.columns=arr;


		}

		private function setContextMenu():void {
			var arr:Array=[];
			arr.push({text: "设定动态条件", handler: setDynamicCondition});
			this.contextMenu=ControlUtils.getContextMenu(arr);
		}

		private var model:XMLNodeObject;

		public function setModel(m:XMLNodeObject):void {
			this.model=m;
			this.dataProvider=this.model.getChilds("condition");
			this.setContextMenu();
		}


		private function setDynamicCondition(cx:ContextMenuEvent):void {
			if (this.model == null)
				return;
			var arr:Array=this.model.getChilds("table");
			var len:int=arr.length;
			var dd:Array=[];
			for (var i:int=0; i < len; i++) {
				var tab:XMLNodeObject=XMLNodeObject(arr[i]);
				var o:Object=tab.getAttributes();
				o["children"]=tab.getAttributesBy("column");
				o["table"]=true;
				dd.push(o);
			}
			var dlg:ModalDynamicConditionDialog=new ModalDynamicConditionDialog();
			var app:ModelDynamicCondition=this;
			dlg.setCallback(function(d:Object):void {
				var c:XMLNodeObject=XMLNodeObject(d);
				if (model.getChild("conditions") == null) {
					model.add(c);
				}
				dataProvider=c.getChilds("condition");

			});
			dlg.open(this, {"tree": dd, "conditions": this.model.getChild("conditions")});
		}


	}
}