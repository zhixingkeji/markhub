package com.zhixingkeji.api.security;

import cn.hutool.json.JSONUtil;
import com.zhixingkeji.api.common.ResultTemplate;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

		response.setContentType("application/json;charset=UTF-8");
		ServletOutputStream outputStream = response.getOutputStream();

		ResultTemplate resultTemplate = ResultTemplate.fail(exception.getMessage());

		outputStream.write(JSONUtil.toJsonStr(resultTemplate).getBytes("UTF-8"));

//        推出流
		outputStream.flush();
//        退出流
		outputStream.close();
	}
}
