//数字输入插件
//author:


(function($) {
	$.fn.numinput = function(options) {
		
		var opts = $.extend({}, $.fn.numinput.defaults, options);
		return this.each(function(){
			
			createEl($(this));
			bindEvent($(this));
		});		
	
		
		function createEl(target){
			if(!opts.value || opts.value==0) opts.value=1;
			var value = opts.value;
			if(target.attr("value")) value=target.attr("value");
			input=$("<input type=\"text\" value=\""+value+"\" size=\"5\" name=\""+opts.name+"\">");
			incBtn =$('<span class="numadjust increase"></span>');
			decBtn =$('<span class="numadjust decrease"></span>');
			//var unit = $("<span>"+opts.unit+"</span>");
			//
			target.append(input).append(incBtn).append(decBtn);//.append(unit);
			//alert(target.get(0).outerHTML);
		}
		 
		
		function fireEvent(input){
			if(opts.onChange){
				if(input.val()==""){alert("数字格式不正确");input.val("1");}
				opts.onChange(input);
			}			
		}
        
		function bindEvent(target){
			var input,incBtn,decBtn;
			var input =target.children("input");
			var incBtn =target.children("span.increase");
			var decBtn =target.children("span.decrease");
			incBtn
			.mousedown(function(){
				$(this).addClass("active");
			})
			.mouseup(function(){
				$(this).removeClass("active");
				var value = opts.value;
				if(target.attr("ctn")) value = target.attr("ctn");
				input.val(parseInt(input.val())+parseInt(value));
				fireEvent(input);
			});

			decBtn
			.mousedown(function(){
				$(this).addClass("active");
			})
			.mouseup(function(){
				$(this).removeClass("active");
				var value = opts.value;
				if(target.attr("ctn")) value = target.attr("ctn");
				input.val( parseInt(input.val())<=parseInt(value)?parseInt(value) :parseInt(input.val()) -parseInt(value));
				fireEvent(input);
			});
			
			input.keypress(function(event) {  
			         if (!$.browser.mozilla) {  
				             if (event.keyCode && (event.keyCode < 48 || event.keyCode > 57)) {  
				                 event.preventDefault();  
				             }  
				         } else {  
				             if (event.charCode && (event.charCode < 48 || event.charCode > 57)) {  
				                 event.preventDefault();  
				             }  
				         }  
			});
			//console.log($(input));
			//onblur
			input.blur(function(){
				var num = parseInt($(this).val());
				var value = opts.value;
				if(target.attr("ctn")) value = target.attr("ctn");
				var ov = parseInt(value);
				var tmp = num%ov;
				var end = 0;
				if(num==0 || tmp!=0){
					end = num+ov-tmp;
					alert("您输入的数量不符合装箱量，购买数量调整为："+end);
					$(this).val(end);
				}
				fireEvent(input);
			});
			
			input.change(function(){
				fireEvent($(this));
			});
		}
		

		
	};
	
	$.fn.numinput.defaults={value:1};
})(jQuery);