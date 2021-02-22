package com.ztesoft.crm.report.flex.admin.form {
	import flash.events.Event;

	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.CheckBox;

	public class CheckBoxEx extends VBox implements Field {

		public function CheckBoxEx() {
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
			if (value == null)
				return;
			var len:int=this.checkboxs.length;
			var dd:Array=value.split(",");
			for (var i:int=0; i < len; i++) {
				var check:CheckBox=CheckBox(this.checkboxs[i]);
				if (dd.indexOf(check.data) >= 0) {
					check.selected=true;
				}
			}
		}

		public function getName():String {
			//TODO: implement function
			return this.name1;
		}

		public function getValue():String {
			//TODO: implement function
			var len:int=this.checkboxs.length;
			var dd:Array=[];
			for (var i:int=0; i < len; i++) {
				var check:CheckBox=CheckBox(this.checkboxs[i]);
				if (check.selected == true) {
					dd.push(check.data);
				}
			}
			return dd.join(",");
		}
		private var observer:FieldObserver=null;

		public function setObserver(o:FieldObserver):void {
			//TODO: implement function
			this.observer=o;
		}
		private var checkboxs:Array=[];

		public function setData(o:Object):void {
			//TODO: implement function
			var arr:Array=o as Array;
			var len:int=arr.length;
			var row:HBox=null;

			for (var i:int=0; i < len; i++) {
				var check:CheckBox=new CheckBox();
				var item:Object=arr[i];
				if (i % 3 == 0) {
					row=new HBox();
					row.percentWidth=100;
					addChild(row);
				}
				check.label=item.label;
				check.data=item.value;
				row.addChild(check);
				this.checkboxs.push(check);
				check.addEventListener(Event.CHANGE, function(e:Event):void {
					var c:CheckBox=CheckBox(e.currentTarget);
					var f:Field=Field(c.parent);
					if (f.getObserver() != null)
						f.getObserver().notify(f.getName(), f.getValue());
				});
			}
		}

		public function getObserver():FieldObserver {
			//TODO: implement function
			return this.observer;
		}

		public function addChangeListener(e:Function):void {
		}
	}
}