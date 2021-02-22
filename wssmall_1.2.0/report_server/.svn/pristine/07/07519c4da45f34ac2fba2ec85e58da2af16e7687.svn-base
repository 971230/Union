package com.ztesoft.crm.report.flex.admin {
	import com.ztesoft.crm.report.flex.admin.elements.Report;
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.admin.view.widgets.RPTComponent;
	import com.ztesoft.crm.report.flex.admin.view.widgets.RPTComponentFactory;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import flash.geom.Rectangle;
	import mx.containers.Box;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.events.ResizeEvent;

	public class ReportView extends RPTComponent {

		public function ReportView() {
			//TODO: implement function
			super();
			addEventListener(FlexEvent.CREATION_COMPLETE, function(e:FlexEvent):void {
					var view:ReportView=ReportView(e.currentTarget);
					var n:Report=ReportApplication(view.parentApplication).reportData;
					if (n != null) {
						var vn:XMLNodeObject=n.getChild("view");
						if (vn != null)
							view.load(vn);
					}
					resizeView(view);
					UIComponent(view.parent.parent).addEventListener(ResizeEvent.RESIZE,
						function(e:ResizeEvent):void {
							var view1:ReportView=ReportView(UIComponent(e.currentTarget).parentApplication.reportview);
							resizeView(view1);
						});
				});
			ControlUtils.setPadding(body, 3, 3, 3, 3);
			body.setStyle("horizontalGap", 4);
			body.setStyle("verticalGap", 4);
		}
		private var node:XMLNodeObject;

		private function resizeView(view:ReportView):void {
			var p:UIComponent=UIComponent(view.parent.parent);
			var r:Rectangle=p.getBounds(p);
			view.parent.height=p.height;
			view.parent.width=p.width - 54 - 10;
		}

		override public function acceptChild(type:String):int {
			if (["vbox", "hbox", "titlebox"].indexOf(type) >= 0) {
				return RPTComponent.CHILD;
			}
			return 0;
		}

		override public function notifyAppend(parent:XMLNodeObject, node:XMLNodeObject):void {
			var rl:RPTComponent=RPTComponentFactory.getFactory().getComponent(node);
			body.addChild(rl);
			rl.percentWidth=100;
			rl.setMinSize(0, 130);
		}

		override public function load(n:XMLNodeObject):void {
			this.node=n;
			var app:Box=body;
			app.removeAllChildren();
			this.node.children().forEach(function(e:*, inx:int, arr:Array):void {
					var en:XMLNodeObject=XMLNodeObject(e);
					var rpt:RPTComponent=RPTComponentFactory.getFactory().getComponent(en);
					rpt.percentWidth=100;
					app.addChild(rpt);
				}, null);
			setData(n);
		}
	}
}