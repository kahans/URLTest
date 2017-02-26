package com.url.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		HttpURLConnection conn = null;

		try {
			// 요청 URL
			URL url = new URL("http://loverman85.cafe24.com/bigtower/government/getData");
			// 문자열로 URL 표현
			System.out.println("URL :" + url.toExternalForm());

			conn = (HttpURLConnection) url.openConnection();
			// 요청 방식(GET or POST)
			conn.setRequestMethod("POST");

			conn.setDoInput(true);// 서버에서 통신에서 입력 가능한 상태로 만듬
			conn.setDoOutput(true);// 서버에서 통신에서 출력 가능한 상태로 만듬
			// - Server와 통신을 하고자 할때는 반드시 위 두 method를 true로 설정
			/*
			 * getDoInput() : Server에서 온 데이터를 입력 받을 수 있는 상태인지 (본인 상태-default :
			 * true) getDoOutput() : Server에서 온 데이터를 출력 할수 있는 상태인지 (Client 상태
			 * -default : false)
			 */
			conn.setUseCaches(false);// 캐싱데이터를 받을지 안받을지

			// 요청응답 타임아웃 설정
			conn.setConnectTimeout(3000);
			// 읽기 타임아웃 설정
			conn.setReadTimeout(3000);
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter writer = new OutputStreamWriter(os);

			writer.write("음 테스트");			
			writer.close();
			os.close();

			StringBuffer sb =new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String buf = null;
			
			while ((buf= reader.readLine()) != null) {
				System.out.println(buf);
				sb.append(buf + "\n");
			}
			// 스트림을 닫는다.
			reader.close();

		}  catch(IOException io){
			io.printStackTrace();
			System.out.println("오류 발생");
		}
		finally {
			if (conn != null) {
				conn.disconnect();
				System.out.println("해체");
			}

		}

		logger.info("Welcome home! The client locale is {}.", locale);

		return "home";
	}
	//URL 연결 데이터 받기?
	@RequestMapping(value = "/test01", method = RequestMethod.GET)
	public String Test01() throws IOException{
		URL url = new URL("http://loverman85.cafe24.com/bigtower/government/getData");
		// 문자열로 URL 표현
		System.out.println("URL :" + url.toExternalForm());
		
		URLConnection uc = url.openConnection();
		uc.connect();
		System.out.println("uc : "+uc.toString());
		System.out.println("Interaction : "+uc.getAllowUserInteraction());
		
		//System.out.println("contect :"+uc.getContent());
		System.out.println("encoding : "+uc.getContentEncoding());
		System.out.println("length : "+uc.getContentLength());
		System.out.println("type : "+uc.getContentType());
		System.out.println("data : "+uc.getDate());
		System.out.println("date02 : "+ new Date(uc.getDate()));
		return "home";
	}
//http://blog.naver.com/uj02013/220859803436
}
