var VersionChecker={
		init:function(){
			var self = this;
			$("#check_new_btn").click(function(){
				self.check();
			});
		},
		check:function(auto){
	
			var self = this;
			if(!auto){
				$.Loading.show('正在检查新版本，请稍候...');
			}
		},
		/**
		 * 生成日志的html
		 */
		createLogHtml:function(updateLogList){
			var logBox = $("<ul></ul>");
			$.each(updateLogList,function(app_index,app){
				if(app.logList.length>0){
					var appbox=$("<li class='app'>"+app.appId+"<ul ></ul></li>");
					$.each(app.logList,function(log_index,log){
						appbox.children("ul").append("<li class='log'>"+log+"</li>");
					});
					logBox.append(appbox);
				}
			});
			
			return logBox.html();
		}
		,
		skipVersion:function(){
			  var date = new Date();
              date.setTime(date.getTime() + (360 * 24 * 60 * 60 * 1000));
			  var options = { path: '/', expires: date };
			  $.cookie("ver_"+this.version, 'skip', options);
			  Eop.Dialog.close("version_box");
		}
		
		
	};

$(function(){ 
	
});