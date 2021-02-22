package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.display.DisplayObject;
	import flash.events.MouseEvent;

	import mx.containers.ControlBar;
	import mx.containers.TitleWindow;
	import mx.controls.Button;
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.managers.PopUpManager;

	public class ModalDialog {

		public function ModalDialog() {
		}
		private var window:TitleWindow;

		protected function getWindow():TitleWindow {
			return window;
		}

		public final function open(parent:DisplayObject, o:Object):void {
			if (parent is Application) {
				window=TitleWindow(PopUpManager.createPopUp(parent, TitleWindow, true));
			} else
				window=TitleWindow(PopUpManager.createPopUp(Application(UIComponent(parent).parentApplication), TitleWindow, true));
			window.width=w;
			window.alpha=1;
			window.height=h;
			window.title=titleStr;
			PopUpManager.centerPopUp(window);
			window.showCloseButton=true;
			var body:UIComponent=createContent();
			window.addChild(body);
			body.percentHeight=100;
			body.percentWidth=100;
			this.renderButtons();
			if (o != null)
				load(o);
			window.createComponentsFromDescriptors();
		}
		private var titleStr:String;
		private var w:int=720;
		private var h:int=540;

		public function setTitle(s:String):void {
			titleStr=s;
		}

		public function setSize(width:int, height:int):void {
			this.w=width;
			this.h=height;
		}

		private function renderButtons():void {
			var buttons:Array=createButtons();
			var len:int=buttons.length;
			var bbar:ControlBar=new ControlBar();
			window.addChild(bbar);
			ControlUtils.setPadding(bbar, 0, 2, 7, 3);

			for (var i:int=0; i < len; i++) {
				if (buttons[i] is Button) {
					bbar.addChild(Button(buttons[i]));
				} else {
					var b:Button=new Button();
					var c:Object=buttons[i];
					b.label=c.label;
					b.data=c;
					b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
						var b1:Button=Button(e.currentTarget);
						var cfg:Object=b1.data;
						if (cfg.close == true) {
							close(cfg.cb);
						} else {
							if (!ObjectUtil.isEmpty(cfg.cb)) {
								cfg.cb(getData());
							}
						}
					});
					bbar.addChild(b);
				}
			}
			if (showOkCloseButton() == true) {
				var b2:Button=null;
				b2=new Button();
				b2.label="确定";
				b2.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
					close(cb);
				});
				bbar.addChild(b2);
				b2=new Button();
				b2.label="关闭";
				b2.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
					close(null);
				});
				bbar.addChild(b2);
			}
			bbar.setStyle("horizontalAlign", "right");
		}

		public final function close(cb:Function):void {
			PopUpManager.removePopUp(window);
			if (!ObjectUtil.isEmpty(cb))
				cb.call(this, getData());
			window=null;
		}

		public function getData():Object {
			return null;
		}
		private var cb:Function;

		public function setCallback(cb1:Function):void {
			this.cb=cb1;
		}

		public function getCallback():Function {
			return this.cb;
		}

		public function load(data:Object):void {
		}

		public function createContent():UIComponent {
			return null;
		}

		public function createButtons():Array {
			return [];
		}

		public function showOkCloseButton():Boolean {
			return true;
		}
	}
}