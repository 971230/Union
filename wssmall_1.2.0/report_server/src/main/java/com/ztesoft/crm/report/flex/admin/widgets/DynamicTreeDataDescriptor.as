package com.ztesoft.crm.report.flex.admin.widgets {
	import com.adobe.serialization.json.JSON;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import mx.controls.treeClasses.DefaultDataDescriptor;

	public class DynamicTreeDataDescriptor extends DefaultDataDescriptor {

		public function DynamicTreeDataDescriptor() {
			//TODO: implement function
			super();
		}

		override public function hasChildren(node:Object, model:Object=null):Boolean {
			if (node == null)
				return false;
			if (node.leaf == "true" || node.leaf == true)
				return false;
			return true;
		}

		override public function isBranch(node:Object, model:Object=null):Boolean {
			if (node == null)
				return false;
			if (node.leaf == "true" || node.leaf == true)
				return false;
			return true;
		}
	}
}