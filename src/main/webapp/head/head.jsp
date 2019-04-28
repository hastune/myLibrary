<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="./myCSS/navbar-static-top.css" rel="stylesheet">
</head>
<body>
<%@
	page import="com.sm.beans.user" 
%>
 <%
          	user u = (user)request.getSession().getAttribute("userInfo");
  %>
<!-- Static navbar -->
    <nav class="navbar navbar-inverse navbar-static-top">
      <div class="container">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">超市管理系统</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
        	<%if(u.getPrivilege_id() == 1){%>
        		<%-- 老板导航栏--%> 
        		 <ul class="nav navbar-nav">
	            <li><a href="Backstage.html">主页</a></li>
	            <li><a href="commodity.html">商品</a></li>
	            <li><a href="staff.jsp">员工</a></li>
	            <li class="dropdown">
	              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">报表 <span class="caret"></span></a>
	              <ul class="dropdown-menu">
	                <li><a href="em_bb.jsp">员工报表</a></li>
	                <li><a href="pd_bb.jsp">商品报表</a></li>
	              </ul>
	            </li>
	          </ul>
        	<%}else{%>
        	<%-- dd导航栏--%> 
	        	 <ul class="nav navbar-nav">
	            <li ><a href="employee.html">主页</a></li>
	            <li><a href="sold.html">商品出售</a></li>
	            <li><a href="manage.html">商品管理</a></li>
	             <li><a href="Report_form_em.html">报表管理</a></li>
	          </ul>
        	<%}%>
        
         
          <ul class="nav navbar-nav navbar-right">
         	
            <li><a><%=u.getUsername() %></a></li>
            <li><a id = "signOut">注销</a></li>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <script>
    	$("#signOut").click(function(){
    		$.post("user/signOut.action",function(data){
    			if(data.status == 200){
    				alert(data.msg);
    				window.location.href="login.html";
    			}else if(data.status == 400){
    				alert("因不明原因，注销失败");
    				return false;
    			}
    		});
    	});
    </script>
</body>
</html>