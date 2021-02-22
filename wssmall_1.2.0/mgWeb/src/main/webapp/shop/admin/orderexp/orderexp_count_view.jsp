<%@ page contentType="text/html;charset=UTF-8" %>
<head>
    <title>ECharts</title>
     <script src="<%=request.getContextPath() %>/ecs_ord/js/esl.js"></script>
     <style type="text/css">
     	.tit-item{background:#f9f9fa; border-bottom:1px solid #e6e6e6; padding:10px; color:#666;}
     	.tips-con{text-align:left; color:#000; font-size:14px; padding:10px 10px;}
     	.red{color:#de2200;}
		.ml10{margin-left:10px;}
		.mlr5{margin-left:5px; margin-right:5px;}
		.text-center{text-align:center;}
		.linePicDiv,.pieDivMain{
			float:left;
			font-size:14px;
			color:#f00;;
		}
     </style>
</head>
<body>
<div class="comBtnDiv">
<span style="font-size:14px">${specOuter.text}</span>
</div>
<table style="width:100%">
<tr><td>
<div style="width: 100%;" >
	<div id="barPicDiv" class="barPicDiv" style="width: 80%;height: 350px;"></div>
	</div>
</td></tr>
<tr><td style="text-align:center"><input type="button" class="dobtn" value="返回" onclick="history.back(); "></td></tr>
</table>
	
     <script type="text/javascript">
        // 路径配置  饼图
        require.config({
            paths:{ 
                'echarts' : '/ecs_ord/js/echarts',
                'echarts/chart/bar' : '/ecs_ord/js/echarts'
            }
        });
        
        
        // 使用
        require(
            [
                'echarts',
                'echarts/chart/bar' // 使用柱状图就加载bar模块，按需加载
            ],
            function (ec) {
                // 基于准备好的dom，初始化echarts图表
                var myChart = ec.init(document.getElementById('barPicDiv')); 
                
                option = {
                	    title : {
                	        text: '',
                	        subtext: '${specOuter.subtext}'
                	    },
                	    tooltip : {
                	        trigger: 'axis'
                	    },
                	    legend: {
                	        data:['异常关键字']
                	    },
                	    toolbox: {
                	        show : true,
                	        feature : {
                	            mark : {show: true},
                	            dataView : {show: true, readOnly: false},
                	            magicType : {show: true, type: ['line', 'bar']},
                	            restore : {show: true},
                	            saveAsImage : {show: true}
                	        }
                	    },
                	    calculable : true,
                	    xAxis : [
                	        {
                	            type : 'category',
                	            data : []
                	        }
                	    ],
                	    yAxis : [
                	        {
                	            type : 'value',
                	            max : '${specOuter.maxdata}'
                	        }
                	    ],
                	    series : [
                	        {
                	            name:'异常数',
                	            type:'bar',
                	            data:[],
                	            markPoint : {
                	                data : [
                	                        {type : 'max', name: '最大值'},
                	                        {type : 'min', name: '最小值'}
                	                ]
                	            },
                	            markLine : {
                	                data : [
                	                    
                	                ]
                	            }
                	        }
                	    ]
                	};
        
                // 为echarts对象加载数据 
                option.xAxis[0].data = '${specOuter.xdata}'.split(",");
                var ydata = '${specOuter.ydata}'.split(",");
                var y = new Array(ydata.length);
                for(var i=0;i<ydata.length;i++){
                	y[i]=ydata[i]/1;
                }
                option.series[0].data = y;
                myChart.setOption(option); 
            }
        );
        
        
        

    </script>
</body>