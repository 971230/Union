package com.ztesoft.crm.report.flex.admin.chart {
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.dialog.ModelColumnDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.ContextMenuEvent;
	import flash.ui.ContextMenu;
	import mx.collections.ArrayCollection;
	import mx.controls.List;

	public class ReportChartSeries extends ReportPanelField {

		public function ReportChartSeries() {
			//TODO: implement function   
			super();
			this.dataList=new List();
			this.dataList.dataProvider=[];
			this.dataList.contextMenu=getContextMenu();
			addChild(this.dataList);
			this.dataList.percentHeight=100;
			this.dataList.percentWidth=100;
			this.dataList.labelFunction=function(o:Object):String {
				var r:String=XMLNodeObject(o).getAttribute("region");
				if (ObjectUtil.isEmpty(r))
					r="";
				if (r == "left")
					r="--左轴  ";
				if (r == "right")
					r="--右轴  ";
				return r + XMLNodeObject(o).getAttribute("text");
			};
		}

		public function getContextMenu():ContextMenu {
			return ControlUtils.getContextMenu([{text:"添加序列", handler:addSeries}, {text:"关联字段", handler:releatSeries}, {text:"设定中文字段", handler:setCnSeries}, {text:"删除序列", handler:deleteSeries}]);
		}

		public function addSeries(e:ContextMenuEvent):void {
			var a:List=List(e.contextMenuOwner);
			new FormDialog().open(a, getProperties(), "添加序列", createSeries);
		}

		public function releatSeries(e:ContextMenuEvent):void {
			var a:List=List(e.contextMenuOwner);
			var s:ReportChartSeries=ReportChartSeries(a.parent);
			var report:XMLNodeObject=XMLNodeObject(a.parentApplication.reportData);
			var model:XMLNodeObject=report.getChildById(s.getModel());
			new ModelColumnDialog().open(a, model, "关联字段", createSeries);
		}

		public function setCnSeries(e:ContextMenuEvent):void {
			var a:List=List(e.contextMenuOwner);
			var s:ReportChartSeries=ReportChartSeries(a.parent);
			var report:XMLNodeObject=XMLNodeObject(a.parentApplication.reportData);
			var model:XMLNodeObject=report.getChildById(s.getModel());
			var cn:XMLNodeObject=XMLNodeObject(a.selectedItem);
			if (!cn) {
				MessageBox.alert("没有选中任何序列，不允许设定中文字段");
				return ;
			}
			if (!cn.getAttribute("column") || cn.getAttribute("column") == "") {
				MessageBox.alert("这个字段不是表字段，不能设定中文字段");
				return ;
			}
			new ModelColumnDialog().open(a, model, "设定中文字段", function(o:XMLNodeObject):void {
					cn.setAttribute("text", cn.getAttribute("oldtext") + "(" + o.getAttribute("text") + ")");
					dataList.dataProvider=dataList.dataProvider;
				});
		}

		public function createSeries(o:XMLNodeObject):XMLNodeObject {
			if (o.getTagName() == "column") {
				var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject();
				n.setTagName("series");
				n.setAttribute("column", o.getAttribute("id"));
				n.setAttribute("oldtext", o.getAttribute("text"));
				n.setAttribute("text", o.getAttribute("text"));
				add(n);
				return n;
			}
			else {
				add(o);
				return o;
			}
		}

		public function getProperties():Object {
			return {cols:1, tagName:"series", items:[{label:"中文名称", name:"text"}, {label:"数据域", name:"name"}, {label:"显示域", name:"displayName"}]};
		}

		public function deleteSeries(e:ContextMenuEvent):void {
			var a:List=List(e.contextMenuOwner);
			var s:ReportChartSeries=ReportChartSeries(a.parent);
			var cn:XMLNodeObject=XMLNodeObject(a.selectedItem);
			if (!cn) {
				MessageBox.alert("没有选中任何序列，无法操作");
				return ;
			}
			getPanel().removeChildById(cn.getId());
			a.dataProvider=getPanel().getChilds("series");
		}

		override public function draw():void {
			this.dataList.dataProvider=getPanel().getChilds("series");
		}

		private function add(n:Object):void {
			var arr:Array=ArrayCollection(dataList.dataProvider).source;
			arr.push(n);
			getPanel().add(XMLNodeObject(n));
			dataList.dataProvider=arr;
		}
		var dataList:List;
	}
}