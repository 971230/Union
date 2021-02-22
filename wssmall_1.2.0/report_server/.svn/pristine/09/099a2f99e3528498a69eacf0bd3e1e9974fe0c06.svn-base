package com.ztesoft.crm.report.flex.admin.form {
	import com.ztesoft.crm.report.flex.admin.elements.XMLNodeObject;
	import com.ztesoft.crm.report.flex.message.MessageBox;
	import com.ztesoft.crm.report.flex.utils.ControlUtils;
	import com.ztesoft.crm.report.flex.utils.ObjectUtil;
	
	import flash.events.Event;
	import flash.events.MouseEvent;
	
	import mx.containers.HBox;
	import mx.containers.VBox;
	import mx.controls.List;
	import mx.controls.Text;
	import mx.controls.TextArea;
	import mx.events.FlexEvent;

	public class EventForm extends VBox {
		public function EventForm() {
			//TODO: implement function
			super();
			var vBox:HBox=new HBox();
			this.events=new List();
			vBox.addChild(this.events);
			this.script=new TextArea();

			vBox.addChild(this.script);
			this.addChild(vBox);
			this.events.width=100;
			this.events.percentHeight=100;
			this.script.percentHeight=100;
			this.script.percentWidth=100;
			vBox.percentHeight=100;
			vBox.percentWidth=100;
			this.desc=new Text();
			this.desc.percentWidth=100;
			addChild(this.desc);
			this.events.labelFunction=function(d:Object):String {
				return d.text;
			}
			ControlUtils.setPadding(this, 4, 0, 4, 4);
			this.events.addEventListener(MouseEvent.CLICK, selectEvent);
		this.events.addEventListener(FlexEvent.CREATION_COMPLETE,function(e:FlexEvent):void{
		  	events.selectedIndex=0;
		  	selectEvent(null);
		});
			
			this.script.addEventListener(Event.CHANGE,function(e:Event):void{
				var tx:TextArea=TextArea(e.currentTarget);
				eventData[tx.name]=tx.text;
			});

		} 
		
		private var edited:Boolean=false;

		private var events:List;
		private var script:TextArea;
		private var desc:Text;
		
		
		private var lastRow:Object;
		
		public function getData():Array{
	
			var xns:Array=[];
			for(var n:String in this.eventData){
	
				if(ObjectUtil.isEmpty(this.eventData[n])) continue;
				var xn:XMLNodeObject=new XMLNodeObject("event");
				xn.setAttribute("name",n);
				xn.setContent(this.eventData[n]);
				xns.push(xn);
				
			}
			
			return xns;
		}
		
		private var eventData:Object={};
		
		public function load(data:Array):void{
			if(data==null) return;
			var len:int=data.length;

			for(var i:int=0;i<data.length;i++){
				var x:XMLNodeObject=data[i] as XMLNodeObject;
				this.eventData[x.getAttribute("name")]=x.getContent();
			}
		
		}
		
		

		private function selectEvent(e:MouseEvent):void {
		
			var n:Object=events.selectedItem;
			if(n==null) return
			if(n.desc==null) return;
            this.script.name=n.name;
			if(this.eventData[n.name]!=null)this.script.text=this.eventData[n.name];
			else this.script.text="";
			desc.text=n.desc;
			
		}

		
		

		public function render(eventsName:Array):void {

			this.events.dataProvider=eventsName;
		}


	}
}