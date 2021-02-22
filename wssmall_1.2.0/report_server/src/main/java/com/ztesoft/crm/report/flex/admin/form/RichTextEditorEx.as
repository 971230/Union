package com.ztesoft.crm.report.flex.admin.form {
	import com.ztesoft.crm.report.flex.message.MessageBox;

	import mx.controls.RichTextEditor;

	public class RichTextEditorEx extends RichTextEditor implements Field {

		public function RichTextEditorEx() {
			//TODO: implement function
			super();
		}
		private var name1:String;

		public function setName(name:String):void {
			//TODO: implement function
			this.name1=name;
		}

		public function setValue(value:String):void {
			//TODO: implement function

			this.htmlText=value;
		}

		public function getName():String {
			//TODO: implement function
			return this.name1;
		}

		public function getValue():String {
			//TODO: implement function
			return this.htmlText;
		}
		private var observer:FieldObserver;

		public function setObserver(o:FieldObserver):void {
			//TODO: implement function
			this.observer=o;
		}

		public function setData(o:Object):void {
			//TODO: implement function
		}

		public function getObserver():FieldObserver {
			//TODO: implement function
			return this.observer;
		}

		public function addChangeListener(e:Function):void {
		}
	}
}