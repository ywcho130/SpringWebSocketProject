package com.cho.login.service;

import com.cho.login.vo.LoginVO;

public interface LoginService {

	public LoginVO login(LoginVO vo);

	public int join(LoginVO vo);

	public String getKakaoAccessToken(String code);

	public LoginVO createKakaoUser(String access_token);

	public int searchId(LoginVO vo);

	public LoginVO loginKakao(LoginVO vo);

	public int joinKakao(LoginVO vo);

	public void logoutKakao(String access_token);


	

}
