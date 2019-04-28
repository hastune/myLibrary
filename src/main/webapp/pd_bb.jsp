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
<div class=" col-md-offset-3   row">
<div class="col-md-1">
	<select  class="form-control " style="width:100%" id="time_zone">
		<option>一周内</option>
		<option>一个月内</option>
	</select>
</div>
<div class=" col-md-4">
	<font   id="sum">总价￥</font>
</div>	
<div class="col-md-3" >
	<input class="form-control" type="text" onkeyup="find()" id = "sc" style="width:100%;;" placeholder="请输入要搜索的内容">
</div>
	
</div>
<div class="text-center">
	<table class="table table-hover col-md-offset-3" style="width:50%" id="display_table">
		<tr class="active">
			<td>#</td>
			<td>商品名</td>
			<td>数量</td>
			<td>单价</td>
			<td>总价</td>
			<td>售出时间</td>
		</tr>
		<tr id="none">
			<td>1</td>
			<td>XXX</td>
			<td>123</td>
			<td>1.2</td>
			<td>300</td>
			<td>2018-12-12 12:12:12</td>
		</tr>
	</table>
<div>
	<nav aria-label="Page navigation" >
	  <ul class="pagination" id="page">
	    <li>
	      <a href="#" aria-label="Previous">
	        <span aria-hidden="true">&laquo;</span>
	      </a>
	    </li>
	    <li><a href="#">1</a></li>
	    <li><a href="#">2</a></li>
	    <li><a href="#">3</a></li>
	    <li><a href="#">4</a></li>
	    <li><a href="#">5</a></li>
	    <li>
	      <a href="#" aria-label="Next">
	        <span aria-hidden="true">&raquo;</span>
	      </a>
	    </li>
	  </ul>
	</nav>
</div>
</div>
 
<div id="chartmain" style="width:600px; height: 400px;"></div>
 

</body>
<script type="text/javascript">
var page = 1;
	$(document).ready(function(){
		search();
		//window.location.href +="?page=1";
		
	});
	var day = 0;
	
	function search(){
		var index = $("#time_zone").get(0).selectedIndex;
		if(index == 0){
			//一周
			day =7;
		}else if(index == 1){
			//一月
			day =30;
		}
		
		$.post("sold/count.action",{name:"_sold",day:day},function(resp){
			//console.info(resp)
			var html1 = " <li>"+
			      "<a aria-label='Previous'>"+
	        "<span aria-hidden='true'>&laquo;</span>"+
	      "</a>"+
	    "</li>";
			if(resp.msg == 0){
				html1 += "<li ><span >1</span></li>"
			}else{
				for(var i = 0; i<resp.msg/10;i++){
					if(i == 0){
						html1 += "<li onclick='get("+day+","+(i+1)+")'><a>"+(i+1)+"</a></li>"
					}else{
						html1 += "<li onclick='get("+day+","+(i+1)+")'><a>"+(i+1)+"</a></li>"
					}
					
				}
			}
			html1 += "<li>"+
			      "<a  aria-label='Next'>"+
	       " <span aria-hidden='true'>&raquo;</span>"+
	     " </a>"+
	   " </li>";
			$("#page").html(html1);
		});
		get(day,page);
		setTimeout(function(){
			column = $("#display_table").find('tr');
		},150);
		initData();
	}
	$("#time_zone").change(function(){
		search();
	});
	
	
	function get(day,page_Num){
		page = page_Num;
		$.post("sold/get.action",{day:day,page:page_Num},function(data){
			var html =" ";
			html += "<tbody><tr class='active'>"+
			"<td>#</td>"+
			"<td>商品名</td>"+
			"<td>商品种类</td>"+
			"<td>数量</td>"+
			"<td>单价</td>"+
			"<td>总价</td>"+
			"<td>售出时间</td>"+
		"</tr>";
			for(var i = 0 ;i<data.msg.length;i++){
				html +=("<tr>"+
				"<td>"+(i+1)+"</td>"+
				"<td class='e_target'>"+data.msg[i].product.name+"</td>"+
				"<td class='e_target'>"+data.msg[i].product.kind+"</td>"+
			    "<td>"+data.msg[i].sold.amount+"</td>"+
				"<td>"+data.msg[i].product.type+"</td>"+
				"<td>"+data.msg[i].sold.sum+"</td>"+
				"<td>"+data.msg[i].sold.time+"</td>"+
			"</tr>");
			}
			html+="</tbody>"
			$("#display_table").html(html);
		});
	}
	
	
	//ECharts
	function initData(){
		var data = {
				seriesData:[],
				legendData:[],
				selected:{}
			};
		//var  data.seriesData=[];
		$.post("sold/EChartsData.action",{day:day},function(responce){
			console.info(responce)
			var sum = 0;
			
			if(responce.msg.length == 0){
				$("#chartmain").hide();
			}else{
				$("#chartmain").show();
			}
			for(var i = 0;i<responce.msg.length;i++){
				var temp = {value:(responce.msg[i].sum*1),name:responce.msg[i].kind};
				data.seriesData.push( temp);
				data.legendData.push(responce.msg[i].kind);
				data.selected[responce.msg[i].kind] = i < 6;//显示前6个
				sum +=responce.msg[i].sum*1;
			}
			$("#sum").html("总价￥"+sum);
			 //指定图标的配置和数据
	        var option = {
	            title:{
	                text:'种类_售出总价'
	            },     
	            tooltip : {
	                trigger: 'item',
	                formatter: "{b} : ¥{c} ({d}%)"
	            },
	            legend: {
	                type: 'scroll',
	                orient: 'vertical',
	                right: 10,
	                top: 20,
	                bottom: 20,
	                data: data.legendData,

	                selected: data.selected
	            },
	            series:[{
	                name:'种类_售出总价',
	                type:'pie',    
	                radius:'60%',  //半径
	                data:data.seriesData,
	                itemStyle: {
	                    emphasis: {
	                        shadowBlur: 10,
	                        shadowOffsetX: 0,
	                        shadowColor: 'rgba(0, 0, 0, 0.5)'
	                    }
	                }
	            }]
	        };
	      //初始化echarts实例
	        var myChart = echarts.init(document.getElementById('chartmain'));

	        //使用制定的配置项和数据显示图表
	        myChart.setOption(option);
		});
	}
	
	function find(){
		
		var t = $("#sc").val();
		//console.info(t);
		//console.info(column);
		for(var i = 1 ; i<column.length;i++){
			var td = $(column[i]).find('td');
			var num = 0;
			for(var j = 0;j<td.length;j++){
				if($(td[j]).attr("class") == "e_target" && $(td[j]).text().indexOf(t) > -1 ){
					$(column[i]).show();
					break;
				}else{
					num++;
				}
			}
			if(num == td.length){
				$(column[i]).hide();
			}
		}
	}
	
</script>
</html>