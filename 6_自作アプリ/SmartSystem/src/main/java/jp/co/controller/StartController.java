package jp.co.controller;

import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.database.DaoMethod;
import jp.co.form.LoginForm;

@Controller
public class StartController {
	
	@Autowired
	private DaoMethod dao;
	
	@Autowired
	HttpSession session;
	
	
	@GetMapping("/login")	//http://localhost:8080/login
	public String login() {
		session.invalidate();
		return "login";
	}
	
	
	
	@GetMapping("/menu")
	 public String returnMenu() {
		
		return "menu";
	}
	
	
	
	@PostMapping("/menu")
	public String menu(@Validated @ModelAttribute("login")LoginForm form, BindingResult bindingResult, Model model) {
	
		if (bindingResult.hasErrors()) {
			return "login"; 
		}
		
		boolean check = dao.loginCheck(form.getName(), form.getPassword());
		
		if(check) {
			String authority = dao.usersAuthority(form.getName());
			session.setAttribute("authority", authority);
			
			session.setAttribute("name", form.getName());
			
			if(authority.equals("マネージャー")) {
				boolean userAuthority = true;
				session.setAttribute("userAuthority", userAuthority);
				session.setAttribute("title1", "1. 在庫管理");
				session.setAttribute("title3", "3. ユーザー管理");
				session.setAttribute("insert", "登録");
				session.setAttribute("update", "更新");
				session.setAttribute("delete", "削除");
				return "menu";
				
			}else {
				boolean userAuthority = false;
				session.setAttribute("userAuthority", userAuthority);
				session.setAttribute("title1", "1. 機種検索");
				session.setAttribute("sell", "販売");
				return "menu";
			}
			
		}else {
			model.addAttribute("miss","少しだけ運命の歯車がズレてしまっているようです");
			return "login";
		}
		
	}
	
	
	
	@PostMapping("/logout")
	public String logout() {
		
		session.invalidate();
		return "logout";
	}
	
	
}
