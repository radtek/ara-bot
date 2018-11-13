package com.zhuiyi.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import java.io.IOException;


@Slf4j
//@WebFilter(filterName="ParamServletFilter",urlPatterns="/yibot/*")
public class ParamFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
//		try {
//			String params = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
//			log.info("[params is] {}",params);
//		} catch (Exception e) {
//			log.error("get params failed {}",e);
//		}
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
	}
}
