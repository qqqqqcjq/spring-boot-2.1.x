package sample.tomcat.jsp;

import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @date 2020/12/24 10:39
 * @author chengjiaqing
 * @version : 0.1
 */

// 过滤器
//@Component
 @WebFilter

public class RequestBasicFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;
		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			}
			else {
				stringBuilder.append("");
			}
		}
		catch (IOException ex) {
			throw ex;
		}
		finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				}
				catch (IOException ex) {
					throw ex;
				}
			}
		}
		String body = stringBuilder.toString();
		System.out.println("basicfilter " + body);

		chain.doFilter(request, response);// requestWrapper中保存着供二次使用的请求数据

	}

	@Override
	public void destroy() {

	}

}