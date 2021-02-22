package com.ztesoft.crm.report.flex.admin.chart {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.dialog.ModelColumnDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import flash.events.ContextMenuEvent;
	import flash.ui.ContextMenu;
	import mx.controls.List;

	public class ReportChartPrepareSeries extends ReportChartSeries {

		public function ReportChartPrepareSeries() {
			//TODO: implement function
			super();
		}

		override public function deleteSeries(e:ContextMenuEvent):void {
			var a:List=List(e.contextMenuOwner);
			var s:ReportChartSeries=ReportChartSeries(a.parent);
			var cn:XMLNodeObject=XMLNodeObject(a.selectedItem);
			if (!cn) {
				MessageBox.alert("没有选中任何序列，无法操作");
				return ;
			}
			getPanel().removeChildById(cn.getId());
			a.dataProvider=getPanel().getChilds("prepareSeries");
		}

		override public function getProperties():Object {
			return {cols:1, tagName:"prepareSeries", items:[{label:"中文名称", name:"text"}, {label:"数据域", name:"name"}, {label:"显示域", name:"displayName"}, {label:"坐标轴", name:"region", type:"combo", data:ReportConstanst.PREPARE_SERIES_REION}]};
		}

		override public function getContextMenu():ContextMenu {
			return ControlUtils.getContextMenu([{text:"添加序列", handler:addSeries}, {text:"关联左轴字段", handler:addLeftSeries}, {text:"关联右轴字段", handler:addRightSeries}, {text:"设定中文字段", handler:setCnSeries}, {text:"删除序列", handler:deleteSeries}]);
		}

		public function addLeftSeries(e:ContextMenuEvent):void {
			var a:List=List(e.contextMenuOwner);
			var s:ReportChartPrepareSeries=ReportChartPrepareSeries(a.parent);
			var report:XMLNodeObject=XMLNodeObject(a.parentApplication.reportData);
			var model:XMLNodeObject=report.getChildById(s.getModel());
			new ModelColumnDialog().open(a, model, "关联左轴字段", function(n:XMLNodeObject):void {
					var o:XMLNodeObject=createSeries(n);
					o.setAttribute("region", "left");
				});
		}

		override public function draw():void {
			dataList.dataProvider=getPanel().getChilds("prepareSeries");
		}

		public function addRightSeries(e:ContextMenuEvent):void {
			var a:List=List(e.contextMenuOwner);
			var s:ReportChartPrepareSeries=ReportChartPrepareSeries(a.parent);
			var report:XMLNodeObject=XMLNodeObject(a.parentApplication.reportData);
			var model:XMLNodeObject=report.getChildById(s.getModel());
			new ModelColumnDialog().open(a, model, "关联右轴字段", function(n:XMLNodeObject):void {
					var o:XMLNodeObject=createSeries(n);
					o.setAttribute("region", "right");
				});
		}
	}
}