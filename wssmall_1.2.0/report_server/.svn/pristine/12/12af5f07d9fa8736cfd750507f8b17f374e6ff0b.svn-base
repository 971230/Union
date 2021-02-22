package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportApplication;
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObjectFatory;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import mx.containers.Box;
	import mx.containers.BoxDirection;
	import mx.controls.Text;

	public class RPTPanelHeader extends RPTComponent {

		public function RPTPanelHeader() {
			//TODO: implement function
			super();
			this.percentHeight=100;
			this.percentWidth=100;
			this.horizontalScrollPolicy="off";
			this.verticalScrollPolicy="off";
			this.toolTip="表格头";
		}
		private var enableChild:Boolean=true;

		override public function restoreBorder():void {
			ControlUtils.setBorder(this, 1, "solid", 0xE0E0E0);
		}
		private var listtab:Boolean=false;

		public function setListtab(b:Boolean):void {
			this.listtab=b;
		}

		override public function acceptChild(type:String):int {
			if (!this.listtab) {
				if (["textheader", "columnheader"].indexOf(type) >= 0) {
					return RPTComponent.CHILD;
				}
			}
			if (!this.isNullData()) {
				if (["headerevent", "headerreport"].indexOf(type) >= 0) {
					return RPTComponent.SIBLING;
				}
			}
			if ("cncolumnheader" == type && !isNullData() && !getData().isEmptyAttribute("ref")) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function getPropertiesConfig():Object {
			var arr:Array=[{label: "别名", name: "aliasName"}, {label: "空值", name: "emptyValue"}, {label: "宽", name: "width"}, {label: "高", name: "height"}, {label: "水平对齐", name: "align", type: "combo",
					data: ReportConstanst.HEADER_ALIGN}, {label: "格式化", name: "format"}, {label: "格式化类型", name: "formatType", type: "combo", data: ReportConstanst.FORMATTYPE}];
			if (isNullData()) {
				return null;
			}
			if (!getData().isEmptyAttribute("href")) {
				if (getData().getAttribute("href").indexOf("javascript:") == 0) {
					arr.push({label: "事件类型", name: "href", type: "combo", data: ReportConstanst.HEADER_EVENT_TYPE});
					arr.push({label: "目标面板", name: "target", type: "checkbox", data: getData().getPanelDataWithoutForm()});
				} else {
					arr.push({label: "报表路径", name: "href", type: "readOnly"});
					arr.push({label: "窗口模式", name: "target", type: "combo", data: ReportConstanst.WINDOW_TYPE});
				}
			}

			arr.push({label: "合并单元格", name: "mergeCell", type: "combo", data: ReportConstanst.TRUE_OR_FALSE});
			arr.push({label: "表头样式", name: "headerStyle", type: "textarea", height: 40});
			arr.push({label: "内容样式", name: "contentStyle", type: "textarea", height: 40});
			return {cols: 1, linkEnable: true, reportRoot: ReportApplication(this.parentApplication).ajax, panel: getData().getPanelDataWithoutForm(), items: arr};
		}

		override public function forceFocus(b:Boolean):void {
			if (forceFocusFlag == b)
				return;
			forceFocusFlag=b;
			if (b) {
				ControlUtils.setBorder(body, 1, "solid", RPTComponent.FOCUS_BORDER_COLOR);
			} else {
				ControlUtils.setBorder(body, 1, "solid", 0xE0E0E0);
			}
		}

		override public function addChildData(region:String, o:Object):void {
			if (region != "cncolumnheader") {
				var n:XMLNodeObject=XMLNodeObjectFatory.createXMLNodeObject("header");
				if (o is XMLNodeObject) { //关联字段
					var o1:XMLNodeObject=XMLNodeObject(o);
					n.setAttribute("ref", o1.getId());
					n.setAttribute("text", o1.getAttribute("text"));
					n.setAttribute("oldtext", o1.getAttribute("text"));
				} else
					n.setAttributes(o);
				getData().add(n);
			} else {
				var o2:XMLNodeObject=XMLNodeObject(o);
				var dd:Object={text: getData().getAttribute("oldtext") + "(" + o2.getAttribute("text") + ")", labelRef: o2.getId()};
				getData().setAttributes(dd);
			}
		}

		override public function notifyDelete(parent:XMLNodeObject, node:XMLNodeObject):void {
			this.childBox.removeChild(RPTComponent(node.getObserver()));
			if (this.childBox.numChildren == 0) {
				body.removeChild(this.childBox);
				this.l.width=this.wx;
				this.l.height=this.hx;
				this.childBox=null;
			}
			node.release();
		}

		override public function change(node:XMLNodeObject):void {
			load(node);
		}

		override public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			var h:RPTPanelHeader=RPTPanelHeader(RPTComponentFactory.getFactory().getComponent(node));
			h.setSize(wx, hx);
			h.body.direction=this.body.direction;
			if (this.childBox == null) {
				this.childBox=new Box();
				body.addChild(this.childBox);
				this.childBox.horizontalScrollPolicy="off";
				this.childBox.verticalScrollPolicy="off";
				this.childBox.setStyle("horizontalGap", 0);
				this.childBox.setStyle("verticalGap", 0);
			}
			this.childBox.direction=((this.body.direction == BoxDirection.HORIZONTAL) ? BoxDirection.VERTICAL : BoxDirection.HORIZONTAL);
			ControlUtils.setDefaultBorder(this.childBox, (this.childBox.direction == BoxDirection.VERTICAL) ? "left" : "top");
			this.childBox.addChild(h);
			if (this.childBox.direction == BoxDirection.VERTICAL) {
				this.l.percentHeight=100;
				this.childBox.percentHeight=100;
				if (this.childBox.numChildren > 1) {
					ControlUtils.setDefaultBorder(h, "top");
				}
			} else {
				this.l.percentWidth=100;
				this.childBox.percentWidth=100;
				if (this.childBox.numChildren > 1) {
					ControlUtils.setDefaultBorder(h, "left");
				}
			}
		}
		private var childBox:Box=null;
		private var l:Text=null;

		override protected function renderBody(body:Box):void {
			this.l=new Text();
			this.l.enabled=false;
			this.l.setStyle("disabledColor", null);
			this.setStyle("backgroundColor", 0xE0E0E0);
			ControlUtils.setBorder(body, 1, "solid", 0xE0E0E0);
			body.addChild(this.l);
			body.horizontalScrollPolicy="off";
			body.verticalScrollPolicy="off";
			this.horizontalScrollPolicy="off";
			this.verticalScrollPolicy="off";
			this.l.width=wx;
			this.l.height=hx;
		}
		private var wx:int=50;
		private var hx:int=50;

		public function setSize(w:int, h:int):void {
			//this.l.height=h;
			//this.l.width=w;
			//this.wx=50;
			//this.hx=50;
		}

		override public function load(n:XMLNodeObject):void {
			if (ObjectUtil.isEmpty(n.getAttribute("href"))) {
				this.l.text=n.getAttribute("text");
				this.l.setStyle("textDecoration", "none");
			} else {
				this.l.htmlText='<a href="javascript:void(0);" ><font color="red"><b>' + n.getAttribute("text") + "</b></font></a>"
				this.l.setStyle("textDecoration", "underline");
			}
			var len:int=n.children().length;
			if (n.getParent("row") != null)
				this.horizontal();
			if (len > 0) {
				for (var i:int=0; i < len; i++) {
					this.notifyAppend(n, XMLNodeObject(n.children()[i]));
				}
			}
		}
	}
}