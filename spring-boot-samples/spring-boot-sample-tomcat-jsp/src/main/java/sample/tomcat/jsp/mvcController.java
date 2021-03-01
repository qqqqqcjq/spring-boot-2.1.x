package sample.tomcat.jsp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;

/**
 * @date 2020/2/23 13:16
 * @author chengjiaqing
 * @version : 0.1
 */

// @Controller
// @RequestMapping("/mvc")
// public class mvcController {
//
// @RequestMapping("/hello.do")
// public String hello(){
// System.out.println("hello");
// return "hello";
// }
// }

@Controller
@RequestMapping("/mvc")
@PropertySource("classpath:application.yml")
public class mvcController {

    @Value("${user.name}")
    private String name;
    @Value("${user.age}")
    private int age;


    @RequestMapping("/hello")
	public String hello() {
		System.out.println("hello");
		return "/hello";
	}

	@RequestMapping("/helloreturn")
	public String helloreturn(HttpServletRequest httpServletRequest, String value) {
		try {
			StringBuilder stringBuilder = new StringBuilder();
			BufferedReader bufferedReader = null;
			try {
				InputStream inputStream = null;// httpServletRequest.getInputStream();
				// if (inputStream != null) {
				// bufferedReader = new BufferedReader(new
				// InputStreamReader(inputStream));
				// char[] charBuffer = new char[128];
				// int bytesRead = -1;
				// while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
				// stringBuilder.append(charBuffer, 0, bytesRead);
				// }
				// } else {
				// stringBuilder.append("");
				// }
			}
			catch (Exception ex) {
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
			System.out.println(body);
			System.out.println("-----------");
			System.out.println(httpServletRequest.getParameter("name"));
			System.out.println(httpServletRequest.getParameter("value"));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return "/hello";
	}

}