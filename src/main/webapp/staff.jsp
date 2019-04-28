<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="./myJS/jquery2.0.js"></script>
    <link href="./bootstrap/css/bootstrap.min.css" rel="stylesheet">
    <script src="./bootstrap/js/bootstrap.min.js"></script>
<title>员工管理页</title>
</head>
<body>
<jsp:include page="./head/head.jsp"></jsp:include>
<!-- 
	<div id="head"></div>
		
		<script type="text/javascript">
		 
			$("#head").load("./head/head.jsp");
		</script>
	 -->	
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">添加员工</h4>
	            </div>
	            <div class="modal-body">
	            	 <form>
			         	<div class="form-group">
			         		<label for="recipient-name" class="control-label">员工uid</label>
		         			<input type="text" class="form-control" id="em_id">
			            </div>
			          	<div class="form-group">
			            	<label for="message-text" class="control-label">月薪</label>
			            	<input type="text" class="form-control" id="em_pay">
			         	</div>
			        </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" id="add_btn">提交</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	<!-- 模态框（Modal） -->
	<div class="modal fade" id="change" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	    <div class="modal-dialog">
	        <div class="modal-content">
	            <div class="modal-header">
	                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
	                <h4 class="modal-title" id="myModalLabel">修改</h4>
	            </div>
	            <div class="modal-body">
	            	 <form>
			         	<div class="form-group">
			         		<label for="recipient-name" class="control-label">员工姓名</label>
		         			<input type="text" class="form-control" id="em_c_name">
			            </div>
			          	<div class="form-group">
			            	<label for="message-text" class="control-label">月薪</label>
			            	<input type="text" class="form-control" id="em_c_pay">
			         	</div>
			         	<input type="text" id="em_c_id"/>
			        </form>
	            </div>
	            <div class="modal-footer">
	                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
	                <button type="button" class="btn btn-primary" id="change_btn">提交</button>
	            </div>
	        </div><!-- /.modal-content -->
	    </div><!-- /.modal -->
	</div>
	
	<!-- 显示商品详情 -->
	<hr style="filter: alpha(opacity=100,finishopacity=0,style=3)" width="80%" color="#6f5499" size="3" />
	<div class="table-responsive row text-center" >
		<input type="text" class="form-control col-md-offset-3" onkeyup="find()" id = "sc" style="width:15%" placeholder="请输入要搜索的内容">
		<table class="table table-hover col-md-offset-3" style="width:1125px;" id="display_table">
		<tr>
			<td>#</td>
			<td>员工姓名</td>
			<td>员工薪资</td>
			<td>上班时间</td>
			<td>下班时间</td>
			<td>   </td>
		</tr>
		<tr id="none">
			<td>还</td>
			<td>没</td>
			<td>有</td>
			<td>员</td>
			<td>工</td>
			<td>哦</td>
		</tr>
		</table>
	</div>
	<div class="row">
		<button class="btn btn-default col-md-7 col-md-offset-3" data-toggle="modal" data-target="#myModal">+</button>
	</div>
	

<script type="text/javascript">

$(document).ready(function(){
	search();
});
function search(){
	$.post("employee/search.action",function(data){
		//console.info(data)
		//d=data;
		if(data.status == 201){
			$("#none").hide();
			$("#display_table").html("<tr><td>#</td><td>员工姓名</td><td>员工薪资</td><td>   </td></tr>");
			for(var i = 0; i<data.msg.length;i++){
				var tr = $("<tr></tr>");
				
				var td1 = $("<td></td>");
				var td2 = $("<td class='e_name'></td>");
				var td3 = $("<td></td>");
				//var td4 = $("<td></td>");
				//var td5 = $("<td></td>");
				var td6 = $("<td></td>");
				var btn1 = $("<button class='change' onclick=addinfo("+i+","+data.msg[i].employee.em_id+") cid="+data.msg[i].employee.em_id+">修改</button>");
				var btn2 = $("<button class='delect' onclick= delect("+data.msg[i].employee.em_id+") cid="+data.msg[i].employee.id+">辞退</button>");
				
				td1.text(i+"");
				td2.text(data.msg[i].username);
				td3.text("¥"+data.msg[i].employee.pay);
				//td4.text(data.msg[i].attendance.inTime);
				//td5.text(data.msg[i].attendance.outTime);
				td6.append(btn1);
				td6.append(btn2);
				
				tr.append(td1);
				tr.append(td2);
				tr.append(td3);
				//tr.append(td4);
				//tr.append(td5);
				tr.append(td6);
				
				$("#display_table").append(tr);
			}
			
		}else if(data.status == 400){
			alert("获取数据失败");
		}
	});
	
	setTimeout(function(){
		column = $("#display_table").find('tr');
	},150);
	
}

	$("#add_btn").click(function(){
		var id = $("#em_id").val();
		var pay = $("#em_pay").val();
		if(id == "" || pay == ""){
			alert("不能为空");
			return false;
		}
		//console.info(id);
		//console.info(pay);
		$.post("employee/add.action",{id:id,pay:pay},function(data){
			$('#myModal').modal('hide');
			alert(data.msg);
			if(data.status==200){
				search();
			}
		});
	});
	
	$("#change_btn").click(function(){
		var id = $("#em_c_id").val();
		var pay = $("#em_c_pay").val();
		if(id == "" || pay == ""){
			alert("不能为空");
			return false;
		}
		console.info(id);
		console.info(pay);
		$.post("employee/update.action",{id:id,pay:pay},function(data){
			$('#change').modal('hide');
			alert(data.msg);
			if(data.status==200){
				search();
			}
		});
	});
	
	function delect(id){
		$.post("employee/delete.action",{id:id},function(data){
			if(data.status == 200){
				alert("删除成功");
				search();
			}else{
				alert("删除失败");
			}
		});
	}
	
	function find(){
		var t = $("#sc").val();
		for(var i = 1 ; i<column.length;i++){
			var td = $(column[i]).find('td');
			var num = 0;
			for(var j = 0;j<td.length;j++){
				if($(td[j]).attr("class") == "e_name" && $(td[j]).text().indexOf(t) > -1 ){
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
	
	function addinfo(col,id){
		var tr = $("#display_table tr:eq("+(col+1)+")").clone();
		var name = tr[0].cells[1].textContent;
		var pay = tr[0].cells[2].textContent;
		var id = id;
		/*console.info(name);
		console.info(pay);
		console.info(id);*/
		$("#em_c_name").val(name);
		$("#em_c_pay").val(pay.substring(1,pay.length));
		$("#em_c_id").val(id);
		$('#change').modal('show');
	}
</script>
</body>
</html>