package com.ztesoft.crm.report.flex.admin.elements {
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	import flash.xml.XMLDocument;
	import flash.xml.XMLNode;
	import flash.xml.XMLNodeType;

	public class Report extends XMLNodeObject {

		public function Report(n:String) {
			//TODO: implement function
			super();
			this.name=n;
			setTagName("report");
		}

		public function parseXML(xml:String):void {
			var xmlDoc:XMLDocument=new XMLDocument();
			xmlDoc.ignoreWhite=true;
			xmlDoc.parseXML(xml);
			var root:XMLNode=xmlDoc.firstChild;
			var o:XMLNodeObject=toObject(root);
			if (o) {
				this.tagName=o.tagName;
				this.attrs=o.attrs;
				this.childs=o.childs;
				this.content=o.content;
			}
			o=null;
			root=null;
			xmlDoc=null;
			//XMLNodeObjectFatory.index=this.getMaxUUID();
			//MessageBox.alert(this.getMaxUUID());
			this.initField(this);
		}

		private function initField(o:XMLNodeObject):void {
			var text:String=null;
			var lText:String=null;
			switch (o.getTagName()) {
				case "axis":
					text=XMLNodeObjectFatory.textMap[o.getAttribute("column")];
					break;
				case "metrix":
					text=XMLNodeObjectFatory.textMap[o.getAttribute("column")];
					break;
				case "series":
					text=XMLNodeObjectFatory.textMap[o.getAttribute("column")];
					lText=XMLNodeObjectFatory.textMap[o.getAttribute("labelColumn")]
					break;
				case "header":
					text=XMLNodeObjectFatory.textMap[o.getAttribute("ref")];
					lText=XMLNodeObjectFatory.textMap[o.getAttribute("labelRef")]
					break;
				case "prepareSeries":
					text=XMLNodeObjectFatory.textMap[o.getAttribute("column")];
					lText=XMLNodeObjectFatory.textMap[o.getAttribute("labelColumn")]
					break;
				case "panel":
					break;
				case "field":
					break;
				default:
					break;
			}
			if (ObjectUtil.isEmpty(o.getId())) {
				o.setAttribute("id", XMLNodeObjectFatory.getNextId());
			}
			if (!ObjectUtil.isEmpty(text) && text != "") {
				o.setAttribute("oldtext", text);
				if (ObjectUtil.isEmpty(lText) || lText == "") {
					lText="";
				}
				else {
					lText="(" + lText + ")";
				}
				o.setAttribute("text", text + lText);
			}
			var len:int=o.childs.length;
			for (var i:int=0; i < len; i++) {
				initField(XMLNodeObject(o.childs[i]));
			}
		}
		private var name:String;

		public function getName():String {
			return this.name;
		}

		private function toObject(node:XMLNode):XMLNodeObject {
			var o:XMLNodeObject=new XMLNodeObject();
			var attrs:Object=node.attributes;
			o.tagName=node.nodeName;
			for (var n:String in attrs) {
				o.attrs[n]=attrs[n];
			}
			XMLNodeObjectFatory.setId(o.getId());
			if (!ObjectUtil.isEmpty(o.getId()))
				XMLNodeObjectFatory.textMap[o.getId()]=o.getAttribute("text");
			var nodes:Array=node.childNodes;
			for (var i:int=0; i < nodes.length; i++) {
				var node1:XMLNode=nodes[i];
				if (node1.nodeType == XMLNodeType.TEXT_NODE) {
					o.content=o.content + node1.nodeValue;
					continue;
				}
				if (node1.nodeType == XMLNodeType.CDATA_NODE) {
					o.content=o.content + node1.nodeValue;
					continue;
				}
				if (node1.nodeType == XMLNodeType.ELEMENT_NODE) {
					o.add(toObject(node1));
					continue;
				}
			}
			return o;
		}
	}
}