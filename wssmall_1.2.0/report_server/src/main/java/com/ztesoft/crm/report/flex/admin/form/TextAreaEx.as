package com.ztesoft.crm.report.flex.admin.form {
	import flash.events.Event;

	import mx.controls.TextArea;

	public class TextAreaEx extends TextArea implements Field {

		public function TextAreaEx() {
			//TODO: implement function
			super();
		}

		public function setName(name:String):void {
			//TODO: implement function
			this.name=name;
		}

		public function setData(o:Object):void {
		}

		public function setValue(value:String):void {
			//TODO: implement function
			this.text=value;
		}

		public function getName():String {
			//TODO: implement function
			return this.name;
		}

		public function getValue():String {
			//TODO: implement function
			return this.text;
		}
		private var observer:FieldObserver;

		public function setObserver(o:FieldObserver):void {
			//TODO: implement function
			this.observer=o;
		}

		public function getObserver():FieldObserver {
			return this.observer;
		}

		public function addChangeListener(e:Function):void {
		}
	}
}