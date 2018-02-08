var netLoading = true;
$(function(){
	init();
})
function init(){
	height = $(window).height();
	$(".account-bg").css({"height":height});
	//输入框是否为空
	$("input").change(function(){
		$(".login").html("登录");
		if(0 < $(this).val().length){
			$(this).addClass("not-null");
		}else{
			$(this).removeClass("not-null");
		}
	});
	//账号、手机号登录切换
	$(".account-mobile").click(function(){
		switchLogin(this);
	});
	//重置密码
	$(".forgot").click(function(){
		$("h1").html("重置密码");
		$("input").val("");
		$(".account-mobile").text("账号密码登录");
		$("#account input").attr('placeholder', "请输入手机号");
		$("#account input").attr('maxlength', "11");
		$("#passwd input").attr('placeholder', "请输入6位以上密码");
		$("#passwd").show();
		$("#sms-code").show();
		$(this).hide();
		$(".login").html("确认");
		$(".login-show").hide();
	});
	//获取短信验证码
	$("#get-sms-code").click(function(){
		if("注册" == $("h1").html()){
			getSmsCode("pcRegisterCode");
		}else if($(".forgot").css("display") == "none"){
			getSmsCode("pcForgetCode");
		}else{
			getSmsCode("pcLoginCode");
		}
	});
	//更新图片验证码
	$("#get-image-code").click(function(){
		$(this).children("img").attr("src", "public/getImageCode.action?t="+new Date().getTime());
	});
	//登录
	$(".login").click(function(){
		//判断是否为找回密码
		if($(".forgot").css("display") == "none"){
			forgotPasswd();
		}else{
			//判断是否为账号密码登录
			if($("#passwd").css("display") == "none"){
				checkMobileLogin();
			}else{
				checkAccountLogin();
			}
		}
	});
	//回车事件处理
	document.onkeydown = function(e){
		// 兼容FF和IE和Opera    
	    var theEvent = e || window.event;    
	    var code = theEvent.keyCode || theEvent.which || theEvent.charCode;    
	    if (code == 13) {
	    	if(null == $(".register").html()){
	    		//判断是否为找回密码
	    		if($(".forgot").css("display") == "none"){
	    			forgotPasswd();
	    		}else{
	    			//判断是否为账号密码登录
	    			if($("#passwd").css("display") == "none"){
	    				checkMobileLogin();
	    			}else{
	    				checkAccountLogin();
	    			}
	    		}
	    	}else{
	    		//回车事件注册
	    		userRegister();
	    	}
        }
   	};
	//注册协议
	$(".agreement span").click(function(){
		layer.open({
			type: 2,
			area: ['700px', '450px'],
			shade: [0.3, '#323232'], // 遮罩
  			shadeClose: true, // 是否点击遮罩关闭
  			zIndex: 19930912, //层叠顺序
			title: "《艺鲸版库注册协议》",
			content: 'agreement/registerJsp.action'
		});
	});
	//注册
	$(".register").click(function(){
		userRegister();
	});
	//去注册
	$(".go-register span").click(function(){
		document.location.href = "user/registerJsp.action";
	});
	//去登录
	$(".go-login span").click(function(){
		document.location.href = "user/loginJsp.action";
	});
}
//账号登录
function checkAccountLogin(){
	if(0 < $("#account input").val().length){
		if(0 < $("#passwd input").val().length){
			if(netLoading){
				netLoading = false;
				$.ajax({
			        url: "user/checkAccount.action",
			        type: "post",
			        data: {"account": $("#account input").val(), "passwd": $("#passwd input").val()},
			        dataType: "json",
			        async: false,
			        success: function (jsonData) {
			            if("1" == jsonData.status){
			            	var url = jsonData.source;
			            	if(null != url){
			            		document.location.href = url;
			            	}else{
			            		document.location.href = "indexJsp.action";
			            	}
			            }else{
			            	 myAlert(jsonData.msg, 5);
			            }
			        },
			        complete: function () {
			            netLoading = true;
			        }
			    });
			}else{
				myAlert("正在操作...", 4);	
			}
		}else{
			myAlert("密码不能为空", 2);
		}
	}else{
		myAlert("账号不能为空", 2);
	}
}
//短信验证码登录
function checkMobileLogin(){
	if(0 < $("#account input").val().length){
		if(checkMobile($("#account input").val())){
			if(0 < $("#sms-code input").val().length){
				if(checkInteger($("#sms-code input").val())){
					if(netLoading){
						netLoading = false;
						$.ajax({
					        url: "user/checkMobile.action",
					        type: "post",
					        data: {"account": $("#account input").val(), "smsCode": $("#sms-code input").val()},
					        dataType: "json",
					        async: false,
					        success: function (jsonData) {
					            if("1" == jsonData.status){
					            	var url = jsonData.source;
					            	if(null != url){
					            		document.location.href = url;
					            	}else{
					            		document.location.href = "indexJsp.action";
					            	}
					            }else{
					            	myAlert(jsonData.msg, 5);
					            }
					        },
					        complete: function () {
					            netLoading = true;
					        }
					    });
					}else{
						myAlert("正在操作...", 4);	
					}
				}else{
					myAlert("短信验证码非法", 2);
				}
			}else{
				myAlert("短信验证码不能为空", 2);
			}
		}else{
			myAlert("手机号非法", 2);
		}
	}else{
		myAlert("手机号不能为空", 2);
	}
}
//重置密码
function forgotPasswd(){
	if(0 < $("#account input").val().length){
		if(checkMobile($("#account input").val())){
			if(0 < $("#sms-code input").val().length){
				if(checkInteger($("#sms-code input").val())){
					if(0 < $("#passwd input").val().length){
						if(checkPassword($("#passwd input").val())){
							if(netLoading){
								netLoading = false;
								$.ajax({
							        url: "user/forgetPasswd.action",
							        type: "post",
							        data: {"account": $("#account input").val(), "passwd": $("#passwd input").val(), "smsCode": $("#sms-code input").val()},
							        dataType: "json",
							        async: false,
							        success: function (jsonData) {
							            if("1" == jsonData.status){
							            	switchLogin($(".account-mobile"));
							            }else{
							            	myAlert(jsonData.msg, 5);
							            }
							        },
							        complete: function () {
							            netLoading = true;
							        }
							    });
							}else{
								myAlert("正在操作...", 4);	
							}
						}else{
							myAlert("为了您的账号安全，请设置6~20位以数字、字母、符号至少两种，且以字母开头的密码", 2);
						}
					}else{
						myAlert("密码不能为空", 2);
					}
				}else{
					myAlert("短信验证码非法", 2);
				}
			}else{
				myAlert("短信验证码不能为空", 2);
			}
		}else{
			myAlert("手机号非法", 2);
		}
	}else{
		myAlert("手机号不能为空", 2);
	}
}
//注册账号
function userRegister(){
	if(0 < $("#account input").val().length){
		if(checkMobile($("#account input").val())){
			if(0 < $("#sms-code input").val().length){
				if(checkInteger($("#sms-code input").val())){
					if(0 < $("#image-code input").val().length){
						if(0 < $("#passwd input").val().length){
							if(checkPassword($("#passwd input").val())){
								if(netLoading){
									netLoading = false;
									$.ajax({
								        url: "user/registerAccount.action",
								        type: "post",
								        data: {"account": $("#account input").val(), "passwd": $("#passwd input").val(), "smsCode": $("#sms-code input").val(), "imageCode": $("#image-code input").val()},
								        dataType: "json",
								        async: false,
								        success: function (jsonData) {
								            if("1" == jsonData.status){
								            	document.location.href = "user_loginJsp.action";
								            }else{
								            	myAlert(jsonData.msg, 5);
								            }
								        },
								        complete: function () {
								            netLoading = true;
								        }
								    });
								}else{
									myAlert("正在操作...", 4);	
								}
							}else{
								myAlert("为了您的账号安全，请设置6~20位以数字、字母、符号至少两种，且以字母开头的密码", 2);
							}
						}else{
							myAlert("密码不能为空", 2);
						}
					}else{
						myAlert("图片验证码不能为空", 2);
					}
				}else{
					myAlert("短信验证码非法", 2);
				}
			}else{
				myAlert("短信验证码不能为空", 2);
			}
		}else{
			myAlert("手机号非法", 2);
		}
	}else{
		myAlert("手机号不能为空", 2);
	}
}
//获取短信验证码
function getSmsCode(codType){
	if(0 < $("#account input").val().length){
		if(checkMobile($("#account input").val())){
			if(netLoading){
				netLoading = false
				$.ajax({
			        url: "public/sendSmsCode.action",
			        type: "post",
			        data: {"mobile": $("#account input").val(), "codeType": codType},
			        dataType: "json",
			        async: false,
			        success: function (jsonData) {
			            if("1" == jsonData.status){
			            	myAlert(jsonData.msg, 6);
			            	setCodeOutTime();
			            }else{
			            	myAlert(jsonData.msg, 5);
			            }
			        },
			        complete: function () {
			            netLoading = true;
			        }
			    });
			}else{
				myAlert("正在操作...", 4);	
			}
		}else{
			myAlert("手机号非法", 2);
		}
	}else{
		myAlert("手机号不能为空", 2);
	}
}
//短信验证码获取开始计时
function setCodeOutTime() {
    setTimeout(function () {
        var codeOutTime = $("#get-sms-code").text();
        if ("获取验证码" != codeOutTime) {
            $("#get-sms-code span").html(parseInt($("#get-sms-code span").html()) - 1);
        } else {
        	$("#get-sms-code").addClass("not-null");
            $("#get-sms-code").html("重新发送(<span>60</span>s)");
        }
        if (0 < parseInt($("#get-sms-code span").html())) {
            setTimeout(setCodeOutTime(), 1000);
        } else {
            sendMsg = true;
            $("#get-sms-code").removeClass("not-null");
            $("#get-sms-code").html("获取验证码");
        }
    }, 1000);
}
/**
 * 切换登录方式
 */
function switchLogin(obj){
	$("h1").html("登录");
	$(".forgot").show();
	$(".login").html("登录");
	$(".login-show").show();
	
	$("input").val("");
	if("账号密码登录" == $(obj).text()){
		$(obj).text("手机验证码登录");
		$("#account input").attr('placeholder',"账号/手机号/邮箱");
		$("#account input").attr('maxlength', "50");
		$("#passwd input").attr('placeholder', "请输入密码");
		$("#passwd").show();
		$("#sms-code").hide();
	}else{
		$(obj).text("账号密码登录");
		$("#account input").attr('placeholder', "请输入手机号");
		$("#account input").attr('maxlength', "11");
		$("#passwd").hide();
		$("#sms-code").show();
	}
}