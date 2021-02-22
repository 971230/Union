var spec_imgs={};
function getProduct(){
	return Product;
}

function getPicNames(){
	return $("[name=picnames]");
}

var Product= {
	init:function(){
		var self=this;
 
		Eop.Dialog.init({id:"specdlg",title:"规格",width:"500px",height:'495px'});
		
		//编辑时货品的删除事件
		$("#specNode .delete").click(function(){
			self.deleteProRow($(this));
		});
		
		if($("#haveSpec").val()=='0'){
			$("#specbody2").hide();
			$("#body1").show();			

		}else{
			$("#specbody2").show();
			$("#body1").hide();
		}
	}
};

$(function(){
	
	Product.init();
});



/**
* 将一个值放在一个数组未尾，形成新的数据
*/
function putAr(ar1,obj){
	var newar =[];
	for(var i=0;i<ar1.length;i++){
	    	newar[i] =ar1[i];
	}
	newar[ar1.length] = obj;
	return newar;
};


/*
*
* 组合两个数组
* 如果第一个数组是二维数组，则调用putAr分别组合
* 如果第一个数组是一维数组，则直接和ar2组合
*/
function combination(ar1,ar2){
	var ar = new Array();
	var k=0;
	if(!ar2) { //数组只有一唯的情况
	 
		for(var i=0;i<ar1.length;i++){
			ar[k] = [ar1[i]];
			k++;
		}	
		return ar;
	}

	
 
	
 
	for(var i=0;i<ar1.length;i++){
	 
		
		for(var j=0;j<ar2.length;j++){
 
			if(ar1[i].constructor == Array ){
				ar[k]= putAr(ar1[i],ar2[j]);
				 
			}else{
				ar[k] = [ar1[i],ar2[j]];
			}	 
		  
			k++;
		}
	}
	
	return  ar;
};



function  combinationAr(spec_ar){
	var ar;
	var m =0 ;

	if(spec_ar.length==1){ return combination(spec_ar[0]);}
	while(m<spec_ar.length-1){
		if(m==0){
			ar = spec_ar[0];
		}
		
		ar = combination(ar,spec_ar[m+1]);
		m++;
	};
	
	return ar;
	
};