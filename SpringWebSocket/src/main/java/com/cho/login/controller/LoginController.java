package com.cho.login.controller;


import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cho.login.service.LoginService;
import com.cho.login.vo.LoginVO;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
	
	@Autowired
	@Qualifier("LoginServiceImpl")
	private LoginService service;
	
	// 로그인폼
	@GetMapping("/loginform")
	public String loginform() {
		return "login";
	}
	
	// 로그인
	@PostMapping("/login")
	public String login(HttpSession session, LoginVO vo) {
		System.out.println(vo);
		
		LoginVO login = service.login(vo);
		
		System.out.println(login);
		if(login == null) {
			System.out.println("로그인 안됨");
			return "redirect:loginform";
		}else {
			System.out.println("로그인 됨");
			session.setAttribute("login", login);
			return "redirect:/";
		}
		
	}
	
	// 로그아웃
		@GetMapping("/logout")
		public String logOut(HttpSession session) throws IOException {
			service.logoutKakao((String)session.getAttribute("access_token"));
			session.setAttribute("login", null);
			System.out.println(session.getAttribute("login"));
			System.out.println("로그아웃 완료");
			return "redirect:/";
		}
		
	// 가입폼
		@GetMapping("/joinform")
		public String joinForm() {
	
			return "join";
		}

	// 가입
		@PostMapping("/join")
		public String login(LoginVO vo) {
			System.out.println(vo);
			service.join(vo);

			return "redirect:/";
		}
		
		//카카오관련---------------------------------------------------------------------------------------------------
		
		//카카오 로그인
		@GetMapping("/kakaoLogin")
		public String kakaoLogin(@RequestParam String code, Model model, HttpSession session,RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {//서버로 코드 가져오기
			int searchId = 0;
			LoginVO vo = null;
			System.out.println(code);
			
			String tokken = service.getKakaoAccessToken(code);//코드로 토큰
			
			vo = service.createKakaoUser(tokken);//토큰으로 유저정보
			
			System.out.println("카카오에서 유저정보 가져오기" + vo);
			
			searchId = service.searchId(vo);
			
			if (searchId == 0) {
				System.out.println(vo);
				model.addAttribute("vo", vo);

				String encodedName = URLEncoder.encode(vo.getName(), "UTF-8");
				return "redirect:joinKakaoForm.do?id=" + vo.getId() + "&email=" + vo.getEmail() + "&name=" + encodedName;

				}else {
					LoginVO login =service.loginKakao(vo);
				session.setAttribute("login", login);
				session.setAttribute("access_token", tokken);
				}
			return "redirect:/";
			}
		

		// 카카오 가입 폼
		@GetMapping("/joinKakaoForm")
		public String joinKakaoForm(@ModelAttribute("vo") LoginVO vo,Model model){
				
			model.addAttribute("vo", vo);
			return "/joinKakaoForm";
			
		}

		// 카카오 가입
		@PostMapping("/joinKakao")
		public String joinKakao(LoginVO vo) {
			System.out.println(vo);
			service.joinKakao(vo);
			return "redirect:/";
		}
		
		}
		
