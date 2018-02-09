package com.hpugs.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.hpugs.commons.util.ConstantUtil;
import com.hpugs.commons.util.Utils;

/**
 * @Description 登录拦截器
 * @author 高尚
 * @version 1.0
 * @date 创建时间：2018年2月3日 下午5:20:35
 */
public class LoginFilter implements Filter{
	
	//跳过过滤器的Action
	private static List<String> actionPaths = new ArrayList<String>();

	/**
	 * web容器启动时调用
	 */
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		actionPaths.add("public/indexJsp.action");//首页页面
		actionPaths.add("public/sendSmsCode.action");//发送短信验证码
		actionPaths.add("public/getImageCode.action");//得到图片验证码
		
		actionPaths.add("user/loginJsp.action");//登录页面
		actionPaths.add("user/registerJsp.action");//注册页面
		actionPaths.add("user/checkAccount.action");//账号密码登录接口
		actionPaths.add("user/checkMobile.action");//手机号短信验证码登录接口
		actionPaths.add("user/registerAccount.action");//注册接口
		actionPaths.add("user/forgetPasswd.action");//修改密码接口
		
		actionPaths.add("agreement/registerJsp.action");//注册协议页面
	}
	
	/**
	 * 拦截到请求时执行
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String path = httpServletRequest.getServletPath();
		path = path.indexOf("/") == 0 ? path.substring(1) : path;
		Map<String, Object> staffInfo = (Map<String, Object>) httpServletRequest.getSession().getAttribute("userInfo");
		if(staffInfo == null && !actionPaths.contains(path)){
			if(0 < path.indexOf("Jsp")){//页面请求
				request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
			}else{//接口请求
				Map<String, Object> resultMap = Utils.createResultMap();
				resultMap.put(ConstantUtil.RESULT_STATUS_KEY, ConstantUtil.RESULT_STATUS_NOTLOGIN_STR);
				resultMap.put(ConstantUtil.RESULT_MSG_KEY, ConstantUtil.RESULT_MSG_NOTLOGIN);
				//输出请求结果
				response.setCharacterEncoding("utf-8");
				PrintWriter writer = response.getWriter();
				writer.write(JSON.toJSONString(resultMap));
				writer.flush();
				writer.close();
			}
		}else{
			chain.doFilter(request, response);
		}
	}
	
	/**
	 * web容器关闭时调用
	 */
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
	
}
