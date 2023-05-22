package com.cho.login.mapper;

import com.cho.login.vo.LoginVO;

public interface LoginMapper {

	public LoginVO login(LoginVO vo);

	public int join(LoginVO vo);
	
	public int searchId(LoginVO vo);

	public int joinKakao(LoginVO vo);

	public LoginVO loginKakao(LoginVO vo);

}
