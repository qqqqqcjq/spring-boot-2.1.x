package sample.tomcat.jsp;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @date 2020/12/24 10:39
 * @author chengjiaqing
 * @version : 0.1
 */

// 过滤器
//@Component
@WebFilter
public class RequestAValidFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		ServletRequest requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);

		chain.doFilter(requestWrapper, response);// requestWrapper中保存着供二次使用的请求数据

	}

	@Override
	public void destroy() {

	}

}