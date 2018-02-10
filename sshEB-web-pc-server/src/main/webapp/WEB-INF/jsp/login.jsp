<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html xmlns:wb="http://open.weibo.com/wb">
  <head>
    <base href="<%=basePath%>">
    
    <title>登录_小破孩工作室</title>
    
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
	<!-- 第三方登录 -->
	<script type="text/javascript" src="http://tjs.sjs.sinajs.cn/open/api/js/wb.js?appkey=2096941538" type="text/javascript" charset="utf-8"></script>
	<script type="text/javascript" src="http://qzonestyle.gtimg.cn/qzone/openapi/qc_loader.js" data-callback="true" charset="utf-8"></script>
	<script type="text/javascript" src="js/loginAuth.js"></script>
  </head>
  
  <body>
    <div class="account-bg background-style">
		<span class="logo background-style" onclick='javascipt:document.location.href="public/indexJsp.action"'></span>
		<ul class="account-input">
			<li><h1>登录</h1></li>
			<li id="account"><input type="text" maxlength="50" placeholder="账号/手机号/邮箱" /></li>
			<li id="sms-code" style="display: none;"><input type="text" maxlength="6" placeholder="请输入验证码" /><span id="get-sms-code">获取验证码</span></li>
			<li id="passwd"><input type="password" maxlength="20" placeholder="请输入密码" /></li>
			<li><span class="account-mobile">手机验证码登录</span><span class="forgot">忘记密码</span></li>
			<li><span class="login">登录</span></li>
			<li class="go-register login-show">还没有账号？&nbsp;&nbsp;<span>请注册</span></li>
			<li class="thirdParty login-show"><i></i><span>其他登录方式</span><i></i></li>
			<li class="login-show"><i id="wx_btn" class="background-style"></i><i id="qq_btn" class="background-style"></i><i id="sina_btn" class="background-style"></i></li>
		</ul>
	</div>
  </body>
</html>