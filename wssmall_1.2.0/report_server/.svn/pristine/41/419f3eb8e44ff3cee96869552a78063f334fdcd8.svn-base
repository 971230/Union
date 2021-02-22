package com.ztesoft.crm.report.flex.admin.dialog {
	import flash.events.MouseEvent;
	import flash.system.System;
	import mx.controls.Button;
	import mx.controls.TextArea;
	import mx.core.UIComponent;

	public class SourceDialog extends ModalDialog {

		public function SourceDialog() {
			//TODO: implement function
			super();
			this.setSize(720, 480);
		}
		private var textArea:TextArea;

		override public function createContent():UIComponent {
			this.textArea=new TextArea();
			this.textArea.percentHeight=100;
			this.textArea.percentWidth=100;
			return textArea;
		}

		override public function load(data:Object):void {
			this.textArea.text=data as String;
		}

		override public function createButtons():Array {
			var b:Button=new Button();
			b.label="复制";
			b.addEventListener(MouseEvent.CLICK, function(e:MouseEvent):void {
					System.setClipboard(textArea.text);
				});
			return [b]
		}
	}
}