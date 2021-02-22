package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import mx.containers.Box;
	import mx.containers.Grid;
	import mx.containers.GridItem;
	import mx.containers.GridRow;
	import mx.controls.Label;
	import mx.core.ScrollPolicy;

	public class RPTCrosstab extends RPTComponent {

		public function RPTCrosstab() {
			//TODO: implement function
			super();
			this.percentWidth=100;
			this.setStyle("paddingTop", 5);
			this.setStyle("paddingLeft", 5);
			this.setStyle("paddingBottom", 5);
			this.horizontalScrollPolicy=ScrollPolicy.AUTO;
		}
		protected var hideVisible:Boolean=false;
		private var xHeader:RPTPanelX;
		private var yHeader:RPTPanelY;

		public function hideY():void {
			this.hideVisible=true;
		}

		override public function getPropertiesConfig():Object {
			var arr:Array=getData().getReport().getChilds("model");
			var len:int=arr.length;
			var d:Array=[];
			for (var i:int=0; i < len; i++) {
				var o:XMLNodeObject=XMLNodeObject(arr[i]);
				var l:String=o.getAttribute("text");
				if (ObjectUtil.isEmpty(l))
					l=o.getId();
				d.push({value: o.getId(), label: l});
			}
			return {cols: 1, items: [{label: "关联模型", name: "model", type: "combo", data: d}, {label: "列对齐", name: "columnAlign", type: "combo", data: ReportConstanst.HEADER_ALIGN}, {label: "列宽", name: "columnWidth"}],
					styleRule: true};
		}

		override protected function renderBody(body:Box):void {
			var ly:Grid=new Grid();
			body.addChild(ly);
			ly.setStyle("horizontalGap", 0);
			ly.setStyle("verticalGap", 0);
			ly.percentHeight=100;
			ly.percentWidth=100;
			ly.horizontalScrollPolicy="off";
			ly.verticalScrollPolicy="off";
			/****/
			var row:GridRow=new GridRow();
			ly.addChild(row);
			ControlUtils.setDefaultBorder(row, "left top right");
			/****/
			if (!this.hideVisible) {
				var cell:GridItem=new GridItem();
				row.addChild(cell);
				metric=new RPTPanelMetric();
				metric.render();
				metric.percentHeight=100;
				metric.percentWidth=100;
				ControlUtils.setDefaultBorder(metric, "");
				cell.addChild(metric);
			}
			/****/
			cell=new GridItem();
			this.xHeader=new RPTPanelX();
			cell.addChild(this.xHeader);
			this.xHeader.percentWidth=100;
			this.xHeader.setListtab(this.hideVisible);
			row.addChild(cell);
			ControlUtils.setDefaultBorder(cell, "left");
			/****/
			row=new GridRow();
			ly.addChild(row);
			ControlUtils.setDefaultBorder(row, "left bottom right");
			/****/
			if (!this.hideVisible) {
				cell=new GridItem();
				this.yHeader=new RPTPanelY();
				this.yHeader.percentHeight=100;
				cell.addChild(this.yHeader);
				row.addChild(cell);
			}
			/****/
			cell=new GridItem();
			var label:Label=new Label();
			label.text=" ";
			label.percentHeight=100;
			label.percentWidth=100;
			cell.addChild(label);
			row.addChild(cell);
			ControlUtils.setDefaultBorder(cell, (hideVisible) ? "top" : "left top");
		}
		private var metric:RPTPanelMetric;

		override public function notifyDelete(parent:XMLNodeObject, node:XMLNodeObject):void {
		}

		override public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
		}

		override public function load(n:XMLNodeObject):void {
			var x:XMLNodeObject=n.getChild("x");
			var i:int=0;
			var o:XMLNodeObject=null;
			var h:RPTPanelHeader=null;
			if (ObjectUtil.isEmpty(x)) {
				x=new XMLNodeObject();
				x.setTagName("x");
				getData().add(x);
			}
			this.xHeader.load(x);
			var y:XMLNodeObject=n.getChild("y");
			if (this.yHeader != null) {
				if (ObjectUtil.isEmpty(y)) {
					y=new XMLNodeObject();
					y.setTagName("y");
					getData().add(y);
				}
				this.yHeader.load(y);
			}
			if (this.metric != null) {
				this.metric.setData(n.getChild("metric"));
				this.metric.load(n.getChild("metric"));
			}
		}
	}
}