package com.cho.login.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cho.login.mapper.LoginMapper;
import com.cho.login.vo.LoginVO;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

@Service
@Qualifier("LoginServiceImpl")
public class LoginServiceImpl implements LoginService {

	@Inject
	private LoginMapper mapper;

	// 로그인
	@Override
	public LoginVO login(LoginVO vo) {
		System.out.println(vo);
		return mapper.login(vo);
	}

	// 회원가입
	@Override
	public int join(LoginVO vo) {
		return mapper.join(vo);
	}

	// ------------- 카카오 -------------

	// 카카오 토큰 가져오기
	@Override
	public String getKakaoAccessToken(String code) {
		String access_Token = "";
		String refresh_Token = "";
		String reqURL = "https://kauth.kakao.com/oauth/token";

		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");
			sb.append("&client_id=51950f57073f192bf1a30618e254a8d1"); // TODO REST_API_KEY 입력
			sb.append("&redirect_uri=http://localhost/kakaoLogin"); // TODO 인가코드 받은 redirect_uri 입력
			sb.append("&code=" + code);
			bw.write(sb.toString());
			bw.flush();

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);

			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

			System.out.println("access_token : " + access_Token);
			System.out.println("refresh_token : " + refresh_Token);

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return access_Token;
	}

	// 카카오 토큰으로 유저 정보 가져오기
	@Override
	public LoginVO createKakaoUser(String access_token) {
		 //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
		String reqURL = "https://kapi.kakao.com/v2/user/me";

		LoginVO vo = new LoginVO();
		// access_token을 이용하여 사용자 정보 조회
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "Bearer " + access_token); // 전송할 header 작성, access_token전송

			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode : " + responseCode);

			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";

			while ((line = br.readLine()) != null) {
				result += line;
			}
			System.out.println("response body : " + result);

			// Gson 라이브러리로 JSON파싱
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
			
			JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
	        JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

	        String nickname = properties.getAsJsonObject().get("nickname").getAsString();
	        String email = kakao_account.getAsJsonObject().get("email").getAsString();
	        
			long id = element.getAsJsonObject().get("id").getAsLong();

			System.out.println("id : " + id);
			String strId = Long.toString(id);
			vo.setId(strId);
			vo.setEmail(email);
			vo.setName(nickname);
			br.close();

			System.out.println("id : " + id);

			br.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return vo;
	}

	// 가입 여부
	@Override
	public int searchId(LoginVO vo) {
		// TODO Auto-generated method stub
		return mapper.searchId(vo);
	}

	// 카카오 로그인
	@Override
	public LoginVO loginKakao(LoginVO vo) {
		// TODO Auto-generated method stub
		return mapper.loginKakao(vo);
	}

	// 카카오 가입
	@Override
	public int joinKakao(LoginVO vo) {
		// TODO Auto-generated method stub

		return mapper.joinKakao(vo);
	}

	@Override
	public void logoutKakao(String access_token) {
		// TODO Auto-generated method stub
		String reqURL = "https://kapi.kakao.com/v1/user/logout";
		try {
			URL url = new URL(reqURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Authorization", "Bearer " + access_token);
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
 
            if(responseCode ==400)
                throw new RuntimeException("카카오 로그아웃 도중 오류 발생");
            
            
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            
            String br_line = "";
            String result = "";
            while ((br_line = br.readLine()) != null) {
                result += br_line;
            }
            System.out.println("결과");
            System.out.println(result);
        }catch(IOException e) {
            
        }
	}
	


}
