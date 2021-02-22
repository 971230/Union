package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.admin.ReportApplication;
	import com.ztesoft.crm.report.flex.admin.form.XMLNodeForm;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.events.MouseEvent;
	import mx.containers.HBox;
	import mx.containers.TitleWindow;
	import mx.controls.Button;
	import mx.core.UIComponent;
	import mx.events.CloseEvent;
	import mx.managers.PopUpManager;

	public class FormDialog {

		public function FormDialog() {
		}

		public function open(parent:UIComponent, properties:Object, title:String,
			callback:Function):Object {
			var win:TitleWindow=new TitleWindow();
			var form:XMLNodeForm=new XMLNodeForm(properties);
			form.percentHeight=100;
			form.percentWidth=100;
			win.showCloseButton=true;
			win.title=title;
			win.addChild(form);
			win.width=480;
			ControlUtils.setPadding(win, 0, 0, 0, 0);
			win.addEventListener(CloseEvent.CLOSE, function(e:CloseEvent):void {
					PopUpManager.removePopUp(TitleWindow(e.currentTarget));
				});
			var h:HBox=new HBox();
			win.addChild(h);
			h.percentWidth=100;
			h.setStyle("horizontalAlign", "right");
			var ok:Button=new Button();
			ok.label="确定";
			h.addChild(ok);
			ok.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
					var uc:UIComponent=UIComponent(e.currentTarget);
					var win:TitleWindow=TitleWindow(uc.parent.parent);
					var form:XMLNodeForm=XMLNodeForm(win.getChildAt(0));
					if (!ObjectUtil.isEmpty(callback)) {
						if (callback(form.getValues()) != false) {
							PopUpManager.removePopUp(TitleWindow(win));
						}
					}
					else
						PopUpManager.removePopUp(TitleWindow(win));
				});
			var close:Button=new Button();
			close.label="关闭";
			h.addChild(close);
			close.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
					var uc:UIComponent=UIComponent(e.currentTarget);
					var win:TitleWindow=TitleWindow(uc.parent.parent);
					PopUpManager.removePopUp(TitleWindow(win));
				});
			PopUpManager.addPopUp(win, ReportApplication(parent.parentApplication),
				true);
			PopUpManager.centerPopUp(win);
			return null;
		}
	}
}