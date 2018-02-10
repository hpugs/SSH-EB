<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<base href="<%=basePath%>">
		
		<title>账号绑定_小破孩工作室</title>
		
		<meta charset="UTF-8">
	    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">    
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		<link rel="stylesheet" href="css/public.css" />
		<link rel="stylesheet" href="css/user/account.css" />
		<script type="text/javascript" src="js/jquery-3.2.1.min.js" ></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<script type="text/javascript" src="js/public.js"></script>
		<script type="text/javascript" src="js/validate.js"></script>
		<script type="text/javascript" src="js/user/public.js"></script>
	</head>
	<body>
		<div class="account-bg background-style">
			<span class="logo background-style" onclick='javascipt:document.location.href="public/indexJsp.action"'></span>
			<ul class="account-input">
				<li><h1>账号绑定</h1></li>
				<li><span class="tip">您的账号为第三方合作登录，为了您的账号安全请先绑定手机号和设置登录密码</span></li>
				<li id="account"><input type="text" maxlength="11" placeholder="请输入手机号" /></li>
				<li id="sms-code"><input type="text" maxlength="6" placeholder="请输入短信验证码" /><span id="get-sms-code">获取验证码</span></li>
				<li id="image-code"><input type="text" maxlength="4" placeholder="请输入图片验证码" /><span id="get-image-code"><img src="public/getImageCode.action" alt="点击换一张"></span></li>
				<li id="passwd"><input type="password" maxlength="20" placeholder="请设置登录密码" /></li>
				<li class="agreement">注册默认同意<span>《小破孩工作室注册协议》</span></li>
				<li class="button"><span class="binding">提交</span></li>
				<li class="go-login">已有账号？&nbsp;&nbsp;<span>去登录</span></li>
			</ul>
		</div>
		<input type="hidden" id="thirdType" value='<s:property value="thirdType"/>'>
		<input type="hidden" id="thirdOpenId" value='<s:property value="thirdOpenId"/>'>
	</body>
</html>