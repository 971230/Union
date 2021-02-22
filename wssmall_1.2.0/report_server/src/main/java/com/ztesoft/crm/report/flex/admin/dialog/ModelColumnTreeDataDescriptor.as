package com.ztesoft.crm.report.flex.admin.dialog {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import mx.collections.ArrayCollection;
	import mx.collections.ICollectionView;
	import mx.controls.treeClasses.DefaultDataDescriptor;

	public class ModelColumnTreeDataDescriptor extends DefaultDataDescriptor {

		public function ModelColumnTreeDataDescriptor() {
			super();
		}

		override public function hasChildren(node:Object, model:Object=null):Boolean {
			if (node == null)
				return false;
			var n:XMLNodeObject=XMLNodeObject(node);
			var arr:Array=n.getChilds("column");
			return arr.length > 0;
		}

		override public function isBranch(node:Object, model:Object=null):Boolean {
			if (node == null)
				return false;
			var n:XMLNodeObject=XMLNodeObject(node);
			var arr:Array=n.getChilds("column");
			return arr.length > 0;
		}

		override public function getChildren(node:Object, model:Object=null):ICollectionView {
			if (node == null)
				return null;
			var n:XMLNodeObject=XMLNodeObject(node);
			return new ArrayCollection(n.getChilds("column"));
		}
	}
}