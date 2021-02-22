package com.ztesoft.crm.report.flex.admin.form {
	import com.ztesoft.crm.report.flex.admin.ReportExplorer;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import mx.containers.VBox;

	public class ReportTreeEx extends VBox implements Field {
		public function ReportTreeEx() {
			//TODO: implement function
			super();
			pb=new ReportExplorer(false);
			this.addChild(pb);

			pb.percentHeight=100;
			pb.percentWidth=100;
		}

		private var pb:ReportExplorer;
		private var name1:String;

		public function setName(name:String):void {
			//TODO: implement function
			this.name1=name;
		}

		public function setValue(value:String):void {
			//TODO: implement function


			pb.select(value);

		}

		public function getName():String {
			//TODO: implement function
			return this.name1;
		}

		public function getValue():String {
			//TODO: implement function
			var item:Object=pb.selectedItem;
			if (item != null && ObjectUtil.isTrue(item.leaf)) {
				return item.value.replace(".xml", "");
			}
			return "";
		}

		public function setObserver(o:FieldObserver):void {
			//TODO: implement function
		}

		public function setData(o:Object):void {
			//TODO: implement function

			pb.setAjax(o as String);
			//pb.shrinkAll();
			//pb.load();
		}

		public function getObserver():FieldObserver {
			//TODO: implement function
			return null;
		}

		public function addChangeListener(e:Function):void {
			//TODO: implement function
		}

	}
}