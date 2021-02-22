package com.ztesoft.crm.report.flex {
	import com.adobe.serialization.json.JSON;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;

	import flash.events.*;
	import flash.external.ExternalInterface;
	import flash.net.URLLoader;
	import flash.net.URLLoaderDataFormat;
	import flash.net.URLRequest;
	import flash.net.URLRequestMethod;
	import flash.net.URLVariables;

	public class URLConnection extends URLLoader {

		public function URLConnection(callback:Function) {
			//TODO: implement function
			super(null);
			addEventListener(Event.COMPLETE, function(e:Event):void {
				var loader:URLLoader=URLLoader(e.target);
				callback(loader.data);
			});
		}

		public static function doPostJSON(url:String, param:Object, cb:Function):void {
			if (param == null)
				param={};
			param["replyEncoding"]="utf8";
			new URLConnection(function(str:String):void {
				var o:Object=null;
				if (!ObjectUtil.isEmpty(str)) {
					o=JSON.decode(str, true);
				}
				if (cb != null)
					cb(o);
			}).open(url, param);
		}

		public static function doGet(url:String, param:Object, cb:Function):void {
			if (param == null)
				param={};
			param["replyEncoding"]="utf8";
			new URLConnection(function(str:String):void {
				if (cb != null)
					cb(str);
			}).open(url, param);
		}

		public static function doPostByXML(url:String, param:Object, cb:Function):void {
			new URLConnection(cb).doPostByXML(url, param);
		}

		private function toURLVariables(p:Object):URLVariables {
			var vars:URLVariables=new URLVariables();
			if (p != null) {
				for (var n:String in p) {
					var o:Object=p[n];
					if (!o || o == "")
						continue;
					vars[n]=o;
				}
			}
			return vars;
		}

		/**
		 * 数据读取
		 */
		public function open(url:String, param:Object):void {
			var request:URLRequest=new URLRequest();
			request.url=url;
			request.data=toURLVariables(param);
			this.dataFormat=URLLoaderDataFormat.TEXT;
			try {
				this.load(request);
			} catch (error:Error) {
				flash.external.ExternalInterface.call("alert", error.message);
			}
		}

		public function doPostByXML(url:String, param:Object):void {
			var request:URLRequest=new URLRequest();
			request.method=URLRequestMethod.POST;
			request.contentType="text/xml";
			request.url=url;
			request.data=toXML(param);
			this.dataFormat=URLLoaderDataFormat.TEXT;
			try {
				this.load(request);
			} catch (error:Error) {
				flash.external.ExternalInterface.call("alert", error.message);
			}
		}



		private function toXML(o:Object):String {
			var arr:Array=["<?xml version=\"1.0\" encoding=\"GB2312\"?><request>"];
			o["replyType"]="chart";
			o["replyEncoding"]="utf-8";
			o["requestEncoding"]="utf-8";
			arr.push(objectToXML(null, o));
			arr.push("</request>");
			return arr.join("");
		}

		private function objectToXML(name:String, o:Object):String {
			var node:Array=[];
			if (o is Array) {
				var arr:Array=o as Array;
				var len:int=arr.length;
				if (ObjectUtil.isEmpty(name))
					node.push("<element type=\"array\">");
				else
					node.push("<element name=\"", name, "\" type=\"array\">");
				for (var i:int=0; i < len; i++) {
					node.push("<row>");
					node.push(objectToXML(null, arr[i]));
					node.push("</row>");
				}
				node.push("</element>");
				return node.join("");
			}
			if (o is String || o is int || o is Boolean || o is Number) {
				return "<element name=\"" + name + "\" type=\"string\"><![CDATA[" + encodeURIComponent(o.toString()) + "]]></element>";
			}
			if (o is Object) {
				for (var n:String in o) {
					node.push(objectToXML(n, o[n]));
				}
				return node.join("");
			}
			return "";
		}
	}
}