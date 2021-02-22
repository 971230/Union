package com.ztesoft.crm.report.flex.admin.form {
	import mx.controls.Button;

	public class ButtonEx extends Button implements Field {

		public function ButtonEx() {
			//TODO: implement function
			super();
		}

		public function setName(name:String):void {
			//TODO: implement function
		}

		public function setValue(value:String):void {
			//TODO: implement function
			this.label=value;
		}

		public function getName():String {
			//TODO: implement function
			return null;
		}

		public function getValue():String {
			//TODO: implement function
			return null;
		}

		public function setData(o:Object):void {
			//TODO: implement function
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