package com.ztesoft.crm.report.flex.admin.elements {
	import com.ztesoft.crm.report.flex.admin.form.FieldObserver;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	public class XMLNodeObject extends Object implements FieldObserver {

		public function XMLNodeObject(... args) {
			//TODO: implement function
			super();
			if (args == null)
				return;
			if (args is String) {
				this.tagName=args as String;
			} else {
				if (args is Array) {
					if (args.length > 0)
						this.tagName=args[0];
				}
			}
		}

		public function setTagName(tn:String):void {
			this.tagName=tn;
		}
		public var index:int=-1;
		public var error:Boolean=false;

		public function getMaxUUID():int {
			var uid:int=0;
			var id:String=getId();
			if (id != null && id.indexOf("el") >= 0) {
				uid=ObjectUtil.parseInt1(id.replace("el"), 1);
			}
			MessageBox.alert(id);
			var len:int=this.childs.length;
			for (var i:int=0; i < len; i++) {
				var a:int=XMLNodeObject(this.childs[i]).getMaxUUID();
				if (uid < a)
					uid=a;
			}
			return uid;
		}

		public function setContent(ct:String):void {
			this.content=ct;
		}

		public function getId():String {
			return this.getAttribute("id");
		}
		public var parent:XMLNodeObject;

		public function getReport():XMLNodeObject {
			return this.getParent("report");
		}

		public function getPanel():XMLNodeObject {
			return this.getParent("panel");
		}

		public function getModel():XMLNodeObject {
			var report:XMLNodeObject=this.getReport();
			var panel:XMLNodeObject=this.getPanel();
			var r:XMLNodeObject=this.getParent("row");
			var mode:XMLNodeObject=null;
			if (!ObjectUtil.isEmpty(r)) {
				mode=report.getChildById(r.getAttribute("model"));
				if (!ObjectUtil.isEmpty(mode))
					return mode;
			}
			if (panel == null)
				return null;
			return report.getChildById(panel.getAttribute("model"));
		}

		public function replace(oldObj:XMLNodeObject, newObj:XMLNodeObject):void {
			if (oldObj == null) {
				this.childs.push(newObj);
			} else {
				var len:int=this.childs.length;
				var i:int=0;
				for (; i < len; i++) {
					if (XMLNodeObject(this.childs[i]).getId() == oldObj.getId()) {
						break;
					}
				}
				this.childs.splice(i, 1, newObj);
			}
			newObj.parent=this;
		}

		public function insertBefore(n:XMLNodeObject):int {
			var len:int=parent.childs.length;
			var i:int=0;
			for (; i < len; i++) {
				if (XMLNodeObject(parent.childs[i]).getId() == this.getId()) {
					break;
				}
			}
			n.parent=parent;
			n.index=i;
			parent.childs.splice(i, 0, n);
			if (parent.getObserver() != null) {
				parent.getObserver().notifyAppend(parent, n);
			}
			return i;
		}

		public function insertAfter(n:XMLNodeObject):int {
			var len:int=parent.childs.length;
			var i:int=0;
			for (; i < len; i++) {
				if (XMLNodeObject(parent.childs[i]).getId() == this.getId()) {
					break;
				}
			}
			n.parent=parent;
			if (i == (len - 1)) {
				n.index=-1;
				parent.add(n);
			} else {
				n.index=i + 1;
				parent.childs.splice(i + 1, 0, n);
				if (parent.getObserver() != null) {
					parent.getObserver().notifyAppend(parent, n);
				}
			}
			return n.index;
		}

		public function release():void {
			var len:int=this.childs.length;
			for (var i:int=0; i < len; i++) {
				XMLNodeObject(this.childs[i]).release();
			}
			this.childs=null;
			this.observer=null;
			this.parent=null;
			this.attrs=null;
		}

		public function removeChildById(id:String):Boolean {
			if (!id)
				return true;
			var len:int=this.childs.length;
			for (var i:int=len - 1; i >= 0; i--) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.getId() == id) {
					this.childs.splice(i, 1);
					if (!ObjectUtil.isEmpty(this.observer)) {
						this.observer.notifyDelete(this, n);
					}
					return true;
				}
				if (n.removeChildById(id)) {
					return true;
				}
			}
			return false;
		}

		public function getChilds(tagName:String):Array {
			var arr:Array=[];
			for (var i:int=0; i < this.childs.length; i++) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.tagName == tagName)
					arr.push(n);
				arr=arr.concat(n.getChilds(tagName));
			}
			return arr;
		}

		public function getContent():String {
			return this.content;
		}

		public function children():Array {
			return this.childs;
		}

		public function getChildById(id:String):XMLNodeObject {
			if (ObjectUtil.isEmpty(id))
				return null;
			var len:int=this.childs.length;
			for (var i:int=0; i < len; i++) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.getId() == id)
					return n;
				n=n.getChildById(id);
				if (n != null)
					return n;
			}
			return null;
		}

		public function getChild(tagName:String):XMLNodeObject {
			for (var i:int=0; i < this.childs.length; i++) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.tagName == tagName)
					return n;
				n=n.getChild(tagName);
				if (n != null)
					return n;
			}
			return null;
		}

		public function getParent(tagName:String):XMLNodeObject {
			var a:XMLNodeObject=this;
			while (!ObjectUtil.isEmpty(a)) {
				if (a.getTagName() == tagName)
					break;
				a=a.parent;
			}
			return a;
		}

		public function getAttributesBy(tagName:String):Array {
			var arr:Array=[];
			for (var i:int=0; i < this.childs.length; i++) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.tagName == tagName)
					arr.push(n.getAttributes());
				arr=arr.concat(n.getChilds(tagName));
			}
			return arr;
		}

		public function setAttributes(o:Object):void {
			for (var n:String in o) {
				var v:String=o[n];

				if (n == "content") {
					this.content=v;
				} else
					this.setAttribute(n, v);
			}
			if (!ObjectUtil.isEmpty(this.observer)) {
				this.observer.change(this);
			}


		}

		public function removeAttribute(name:String):String {
			var str:String=this.attrs[name];
			delete this.attrs[name];
			return str;
		}

		public function removeChildByTagName(tagName:String):void {
			var len:int=this.childs.length;
			for (var i:int=len - 1; i >= 0; i--) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.tagName == tagName) {
					this.childs.splice(i, 1);
					if (!ObjectUtil.isEmpty(this.observer)) {
						this.observer.notifyDelete(this, n);
					}

				}
			}
		}

		public function setAttribute(name:String, value:String):void {
			var v:String=this.attrs[name];
			if (name == "id" && !this.isEmptyAttribute("id"))
				return;
			if (v == value)
				return;
			if (!ObjectUtil.isEmpty(v) && v != "") {
				var arr:Array=this.observers[name];
				if (arr) {
					var len:int=arr.length;
					for (var i:int=0; i < len; i++) {
						var a:Function=Function(arr[i]);
						a.call(this, this);
					}
				}
				if (!ObjectUtil.isEmpty(this.observer)) {
					this.observer.propertyChange(this, name);
				}
			}
			this.attrs[name]=value;
		}

		public function isEmptyAttribute(name:String):Boolean {
			return ObjectUtil.isEmpty(this.attrs[name]);
		}

		public function getAttribute(name:String):String {
			return this.attrs[name];
		}
		var tagName:String;
		var attrs:Object={};
		var content:String="";
		var childs:Array=[];

		public function emptyChilds():Boolean {
			return this.childs.length == 0;
		}

		public function getTagName():String {
			return this.tagName;
		}

		public function getAttributes():Object {
			var o:Object={};
			for (var n:String in this.attrs) {
				o[n]=this.attrs[n];
			}
			o["content"]=this.content;
			return o;
		}

		public function attributes():Object {
			return this.attrs;
		}

		public function add(c:XMLNodeObject):void {
			this.childs.push(c);
			c.parent=this;
			if (!ObjectUtil.isEmpty(this.observer))
				this.observer.notifyAppend(this, c);
		}

		public function notify(name:String, value:String):void {
			this.setAttribute(name, value);
		}
		private var observers:Object={};

		public function addPropertyObserver(attrName:String, a:Function):void {
			var arr:Array=this.observers[attrName];
			if (!arr) {
				arr=[];
				this.observers[attrName]=arr;
			}
			arr.push(a);
		}

		public function clearPropertyObservers():void {
			this.observers={};
		}
		private var observer:XMLNodeObjectObserver;

		public function setObserver(o:XMLNodeObjectObserver):void {
			this.observer=o;
		}

		public function getObserver():XMLNodeObjectObserver {
			return this.observer;
		}

		public function getChildrenAttributes(tagName:String, b:Boolean):Array {
			var arr:Array=[];
			var len:int=this.childs.length;
			for (var i:int=0; i < len; i++) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.getTagName() != tagName)
					continue;
				arr.push((b == true) ? n.attributes() : n.getAttributes());
			}
			return arr;
		}

		public function getChildAttributes(tagName:String, attrName:String):Array {
			var arr:Array=[];
			var len:int=this.childs.length;
			for (var i:int=0; i < len; i++) {
				var n:XMLNodeObject=XMLNodeObject(this.childs[i]);
				if (n.isEmptyAttribute(attrName) || n.tagName != tagName) {
					continue;
				}
				arr.push(n.getAttribute(attrName));
			}
			return arr;
		}

		public function toXML():String {
			return "<?xml version=\"1.0\" encoding=\"GB2312\"?>\n" + toNodeXML();
		}

		private function urlEncode(v:String):String {
			if (v == null)
				return "";
			v=v.replace("&", "&amp;");
			v=v.replace("<", "&lt;");
			v=v.replace(">", "&gt;");
			return v;
		}

		private function toNodeXML():String {
			var arr:Array=["<", tagName];
			for (var n:String in this.attrs) {
				if (n == "mx_internal_uid")
					continue;
				if (n == "content") {

					continue;
				}
				var v:String=this.attrs[n] as String;
				if (ObjectUtil.isEmpty(v))
					continue;
				arr.push(" ", n, "=\"", urlEncode(v), "\"");
			}
			arr.push(">");
			var len:int=this.childs.length;
			if (len > 0) {
				for (var i:int=0; i < len; i++) {
					var o:XMLNodeObject=XMLNodeObject(this.childs[i]);
					arr.push(o.toNodeXML());
				}
			} else {
				if (!ObjectUtil.isEmpty(getContent())) {
					arr.push("<![CDATA[", getContent(), "]]>");
				}
			}
			arr.push("</", tagName, ">\n");
			return arr.join("");
		}

		public function getPanelDataWithoutForm():Array {
			var r:XMLNodeObject=getReport();
			var p:XMLNodeObject=getPanel();
			var arr:Array=[];
			if (r != null) {
				var ps:Array=r.getChilds("panel");
				var len:int=ps.length;
				for (var i:int=0; i < len; i++) {
					var o:XMLNodeObject=XMLNodeObject(ps[i]);
					if (o.getId() == p.getId())
						continue;
					var l:String="";
					switch (o.getAttribute("type")) {
						case "form":
							l="表单";
							break;
						case "crosstab":
							l="交叉表";
							break;
						case "listtab":
							l="列表";
							break;
						default:
							l="面板";
							break;
					}
					arr.push({value: o.getId(), label: l + o.getId()});
				}
			}
			return arr;
		}

		public function getChildsBefore(tagName:String, id1:String, keyValue:Boolean):Array {
			var chls:Array=this.getChilds(tagName);
			var len:int=chls.length;
			var arr:Array=[];
			for (var i:int=0; i < len; i++) {
				var o:XMLNodeObject=XMLNodeObject(chls[i]);
				if (o.getId() == id1)
					break;
				if (!keyValue) {
					arr.push(o);
				} else {
					arr.push({value: o.getId(), label: o.getAttribute("label")});
				}
			}
			return arr;
		}

		public function setChildren(a:Array):void {
			this.childs=new Array().concat(a);
		}

		public function getSiblingsBefore(keyValue:Boolean):Array {
			if (this.parent == null)
				return [];
			var len:int=this.parent.childs.length;
			var arr:Array=[];
			for (var i:int=0; i < len; i++) {
				var o:XMLNodeObject=XMLNodeObject(this.parent.childs[i]);
				if (o.getId() == this.getId())
					break;
				if (!keyValue) {
					arr.push(o);
				} else {
					arr.push({value: o.getId(), label: o.getAttribute("label")});
				}
			}
			return arr;
		}
	}
}