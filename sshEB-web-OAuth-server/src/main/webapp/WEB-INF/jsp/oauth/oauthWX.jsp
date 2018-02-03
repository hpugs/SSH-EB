<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>微信授权</title>
    
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0,user-scalable=no">
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">

  </head>
  
  <body>
    <script>
        var GWC = {
            urlParams: {},
            appendParams: function(url, params) {
                if (params) {
                    var baseWithSearch = url.split('#')[0];
                    var hash = url.split('#')[1];
                    for (var key in params) {
                        var attrValue = params[key];
                        if (attrValue !== undefined) {
                            var newParam = key + "=" + attrValue;
                            if (baseWithSearch.indexOf('?') > 0) {
                                var oldParamReg = new RegExp('^' + key + '=[-%.!~*\'\(\)\\w]*', 'g');
                                if (oldParamReg.test(baseWithSearch)) {
                                    baseWithSearch = baseWithSearch.replace(oldParamReg, newParam);
                                } else {
                                    baseWithSearch += "&" + newParam;
                                }
                            } else {
                                baseWithSearch += "?" + newParam;
                            }
                        }
                    }

                    if (hash) {
                        url = baseWithSearch + '#' + hash;
                    } else {
                        url = baseWithSearch;
                    }
                }
                return url;
            },
            getUrlParams: function() {
                var pairs = location.search.substring(1).split('&');
                for (var i = 0; i < pairs.length; i++) {
                    var pos = pairs[i].indexOf('=');
                    if (pos === -1) {
                        continue;
                    }
                    GWC.urlParams[pairs[i].substring(0, pos)] = decodeURIComponent(pairs[i].substring(pos + 1));
                }
            },
            doRedirect: function() {
                var appId = GWC.urlParams['appid'];
                var response_type = GWC.urlParams['response_type'];
                var scope = GWC.urlParams['scope'] || 'snsapi_base';
                var redirect_uri = GWC.urlParams['redirect_uri'];
                var code = GWC.urlParams['code'];
                var state = GWC.urlParams['state'];
                var isMp = GWC.urlParams['isMp']; //isMp为true时使用开放平台作授权登录，false为网页扫码登录
                var baseUrl;
                var redirectUri;
                if (!code) {
                    baseUrl = "https://open.weixin.qq.com/connect/oauth2/authorize#wechat_redirect";
                    if(scope == 'snsapi_login' && 'false' == isMp){
                        baseUrl = "https://open.weixin.qq.com/connect/qrconnect";
                    }
                    //第一步，没有拿到code，跳转至微信授权页面获取code
                    redirectUri = GWC.appendParams(baseUrl, {
                        'appid': appId,
                        'redirect_uri': encodeURIComponent(location.href),
                        'response_type': response_type,
                        'scope': scope,
                        'state': state,
                    });
                } else {
                    //第二步，从微信授权页面跳转回来，已经获取到了code，再次跳转到实际所需页面
                    redirectUri = GWC.appendParams(redirect_uri, {
                        'code': code,
                        'state': state
                    });
                }
	
                location.href = redirectUri;
            }
        };

        GWC.getUrlParams();
        GWC.doRedirect();
    </script>
  </body>
</html>