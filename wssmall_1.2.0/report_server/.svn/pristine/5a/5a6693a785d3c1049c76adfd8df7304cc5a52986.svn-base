package com.ztesoft.crm.report.flex.admin.view.widgets {
	import com.ztesoft.crm.report.flex.admin.ReportConstanst;
	import com.ztesoft.crm.report.flex.admin.dialog.FormDialog;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.ContextMenuEvent;

	import mx.containers.BoxDirection;
	import mx.controls.Text;

	public class RPTBox extends RPTComponent {

		public function RPTBox() {
			//TODO: implement function
			super();
			this.verticalScrollPolicy="off";
			this.horizontalScrollPolicy="off";
			ControlUtils.setPadding(body, 6, 6, 6, 6);
			body.setStyle("horizontalGap", 6);
			body.setStyle("verticalGap", 6);
			restoreBorder();
			this.contextMenu=ControlUtils.getContextMenu([{text: "编辑BOX属性", handler: modifyProperties}]);
		}
		private var content:Text;

		private function modifyProperties(e:ContextMenuEvent):void {
			if (isNullData())
				return;
			var p:XMLNodeObject=getData().parent;
			var o:Object=null;
			if (p.getTagName() == "box" && ObjectUtil.isTrue(p.getAttribute("horizontal"))) {
				o={cols: 1, items: [{label: "宽度", name: "width"}, {label: "最小宽度", name: "minWidth"}, {label: "水平撑满", name: "horizontalFill", type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE}]}
			} else {
				o={cols: 1, items: [{label: "高度", name: "height"}, {label: "最小高度", name: "minHeight"}, {label: "垂直撑满", name: "verticalFill", type: "radiobox", data: ReportConstanst.TRUE_OR_FALSE}]}
			}
			o["data"]=getData();
			new FormDialog().open(this, o, "BOX属性", function(d:XMLNodeObject):void {
				getData().setAttributes(d.attributes());
			});
		}

		private function addPanel(o:XMLNodeObject):RPTComponent {
			var rl:RPTComponent=null;
			if (o.parent.getAttribute("type") == "form") {
				rl=RPTComponentFactory.getFactory().getComponent(o, RPTFormRow);
			} else
				rl=RPTComponentFactory.getFactory().getComponent(o);
			if (o.getTagName() == "panel") {
				switch (o.getAttribute("type")) {
					case "crosstab":
						rl.setLText("交叉表：" + o.getId());
						break;
					case "listtab":
						rl.setLText("列表：" + o.getId());
						break;
					case "chart":
						rl.setLText("图表：" + o.getId());
						break;
					case "simplechart":
						rl.setLText("简单：" + o.getId());
						break;
					case "form":
						rl.setLText("表单：" + o.getId());
						break;
					default:
						break;
				}
			}
			body.addChild(rl);
			return rl;
		}

		override public function acceptChild(type:String):int {
			if (getData().getChild("panel") != null)
				return 0;
			if (["vbox", "hbox", "titlebox"].indexOf(type) >= 0) {
				return RPTComponent.CHILD;
			}
			if (!ObjectUtil.isEmpty(getData().getContent())) {
				return 0;
			}
			if (["chart", "crosstab", "listtab", "form", "simplechart"].indexOf(type) >= 0)
				return RPTComponent.CHILD;
			return 0;
		}

		override public function load(n:XMLNodeObject):void {
			var len:int=n.children().length;

			if (n.getAttribute("horizontal") == "true") {
				this.body.direction=BoxDirection.HORIZONTAL;
			}

			else
				this.body.direction=BoxDirection.VERTICAL;
			if (len > 0) {
				for (var i:int=0; i < len; i++) {
					var en:XMLNodeObject=n.children()[i];
					addPanel(en);
				}
			} else {

				if (n.getTagName() == "box" && !ObjectUtil.isEmpty(n.getContent())) {
					if (this.content == null) {
						this.content=new Text();
						this.content.percentWidth=100;
						body.addChild(this.content);
					}
					this.content.htmlText=n.getContent();
				}
			}
			if (n != null) {
				this.setData(n);
			}
		}

		override public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			var rc:RPTComponent=addPanel(node);
			if (node.index < 0)
				body.addChild(rc);
			else {
				body.addChildAt(rc, node.index);
			}
			if (ObjectUtil.isTrue(getData().getAttribute("horizontal"))) {
				rc.percentHeight=100;
				rc.setMinSize(200, 100);
			} else {
				rc.percentWidth=100;
				rc.setMinSize(0, 100);
			}
		}

		override public function getPropertiesConfig():Object {
			var d:Object={};
			if (isNullData())
				return null;
			if (getData().children().length > 0) {
				return null;
			}

			return {cols: 1, items: [{name: "content", type: "htmeditor"}], data: getData(), callback: function(d:XMLNodeObject):void {

						getData().setContent(d.getContent());
						load(getData());
					}};
		}

		override public function restoreBorder():void {
			ControlUtils.setDefaultBorder(this, null);
		}
	}
}