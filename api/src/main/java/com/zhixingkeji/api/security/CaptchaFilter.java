package com.zhixingkeji.api.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.zhixingkeji.api.handle.CaptchaException;
import com.zhixingkeji.api.common.Const;

import com.zhixingkeji.api.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CaptchaFilter extends OncePerRequestFilter {

	@Autowired
	RedisUtil redisUtil;

	@Autowired
	LoginFailureHandler loginFailureHandler;

	@Override
	protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

		String url = httpServletRequest.getRequestURI();

		if ("/login".equals(url) && httpServletRequest.getMethod().equals("POST")) {

			try{
				// 校验验证码
				validate(httpServletRequest);
				System.out.println("验证码正确");
			} catch (CaptchaException e) {

				// 交给认证失败处理器
				loginFailureHandler.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
			}
		}

        // 验证通过继续下一步
		filterChain.doFilter(httpServletRequest, httpServletResponse);

	}

	// 校验验证码逻辑
	private void validate(HttpServletRequest httpServletRequest) {

		String code = httpServletRequest.getParameter("verifyCode");
		String key = httpServletRequest.getParameter("randomCode");

		if (StringUtils.isBlank(code) || StringUtils.isBlank(key)) {

			throw new CaptchaException("验证码异常");
		}

		if (!code.equals(redisUtil.hget(Const.CAPTCHA_KEY, key))) {
			throw new CaptchaException("验证码错误");
		}

		// 一次性使用
		redisUtil.hdel(Const.CAPTCHA_KEY, key);
	}
}
