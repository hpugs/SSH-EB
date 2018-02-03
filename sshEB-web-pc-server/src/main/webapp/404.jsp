<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>404</title>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<style type="text/css">
		.error{
			width: 100%;
			margin-top: 100px;
			display: inline-block;
			margin-left: 0;
			padding-left: 0;
		}
		.error li{
			width: 100%;
			text-align: center;
			list-style: none;
			float: left;
			margin-bottom: 20px;
		}
		.error li span{
			width: 500px;
			margin: 0 auto;
			font-size: 20px;
			text-align: left;
			display: inline-block;
		}
		.error li:FIRST-CHILD span{
			width: 814px;
			height: 544px;
			line-height: 544px;
			text-align: center;
			background-position: center;
			background-repeat: no-repeat;
			background-size: 80%;
			background-image: url("img/error.png");
			display: inline-block;
			margin-bottom: -50px;
		}
		.error li:FIRST-CHILD span i{
			font-size: 100px;
			font-weight: bold;
			color: #fff;
			font-style: normal;
			margin-left: 60px;
		}
		.error li:LAST-CHILD span{
			color: #fb471d;
			cursor: pointer;
		} 
	</style>
	
  </head>
  
  <body>
  	<ul class="error">
  		<li><span><i>4&nbsp;0&nbsp;4</i></span></li>
  		<li><span>您访问的页面不存在</span></li>
  		<li><span onClick='javascript:document.location.href="public/indexJsp.action"'>返回首页</span></li>
  	</ul>
  </body>
</html>