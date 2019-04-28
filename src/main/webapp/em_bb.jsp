<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="./myJS/jquery2.0.js"></script>
    <link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="./bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="./myJS/echarts.js"></script>
<title>商品报表</title>
</head>
<body>
<jsp:include page="./head/head.jsp"></jsp:include>
<div>
	<select  class="form-control col-md-offset-3" style="width:6%" id="time_zone">
		<option>当天</option>
		<option>一个月内</option>
	</select>
</div>
<div class="text-center">
	<table class="table table-hover col-md-offset-3" style="width:50%" id="display_table">
		<tr class="active">
			<td>#</td>
			<td>姓名</td>
			<td>上班打卡时间</td>
			<td>下班打卡时间</td>
		</tr>
		<tr>
			<td>1</td>
			<td>XXX</td>
			<td>123</td>
			<td>123</td>
		</tr>
	</table>
</div>
	<div id="chartmain"  style="width:600px; height: 400px;"></div>
</body>
<script type="text/javascript">
$(document).ready(function () {
	search_num();
});

var day = 0;
var Data = {
	    xAxis: {
	        type: 'category',
	        data: ["店员1","店员3"],
	        name: "姓名"
	    },
	    yAxis: {
	        type: 'value',
	        min: 0,
	        max: 5,
	        name: "旷工次数"
	    },
	    series: [{
	        data: [0,1],
	        type: 'bar'
	    }]
	};
function search_num(){
	var html = "";
	var index = $("#time_zone").get(0).selectedIndex;
	if(index == 0){
		//当天
		day =1;
		$.post("attendance/search_num.action",{day:day},function(data){
			//console.info(data);
			html +="<tbody><tr class='active'>"+
			"<td>#</td>"+
			"<td>姓名</td>"+
			"<td>上班打卡时间</td>"+
			"<td>下班打卡时间</td>"+
		"</tr>";
			if(data.msg.length == 0){
				alert("还没有人上班");
			}else{
				for(var i = 0;i<data.msg.length;i++){
					//Data.xAxis.data.push(data.msg[i].name);
					//Data.series[0].data.push(data.msg[i].num);
					html +="<tr>"+
					"<td>"+(i+1)+"</td>"+
					"<td>"+data.msg[i].name+"</td>"+
					"<td>"+data.msg[i].inTime+"</td>"+
					"<td>"+data.msg[i].outTime+"</td>"+
				"</tr>";
				}
			}
			html+="</tbody>";
			$("#display_table").html(html);
		});
		
	}else if(index == 1){
		//一月
		day =30;
		$.post("attendance/search_num.action",{day:day},function(data){
			//console.info(data);
			
			html +="<tbody><tr class='active'>"+
			"<td>#</td>"+
			"<td>姓名</td>"+
			"<td>旷工次数</td>"+
		"</tr>";
			for(var i = 0;i<data.msg.length;i++){
				Data.xAxis.data.push(data.msg[i].name);
				Data.series[0].data.push(data.msg[i].num);
				html +="<tr>"+
				"<td>"+(i+1)+"</td>"+
				"<td>"+data.msg[i].name+"</td>"+
				"<td>"+data.msg[i].num+"</td>"+
			"</tr>";
			}
			html+="</tbody>";
			$("#display_table").html(html);
		});
		
	}
	
//	init(Data);

}

$("#time_zone").change(function(){
	search_num();
});

function init(Data){
	var option= {
		    xAxis: {
		        type: 'category',
		        data: Data.xAxis.data
		    },
		    yAxis: {
		        type: 'value'
		    },
		    series: [{
		        data: Data.series[0].data,
		        type: 'bar'
		    }]
		};
	//初始化echarts实例
   var dom = document.getElementById("chartmain");
	var myChart = echarts.init(dom);
    //使用制定的配置项和数据显示图表
  if (option && typeof option === "object") {
	  console.info(option);
    myChart.setOption(option, true);
}
}
</script>
</html>