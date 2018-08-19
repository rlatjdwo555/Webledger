package kr.co.wallet.www.controller;

import java.io.IOException;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.github.scribejava.core.model.OAuth2AccessToken;

import kr.co.wallet.www.service.NaverLoginBO;
import kr.co.wallet.www.vo.User;

@Controller
@SessionAttributes("userName")
public class LoginController {
	
	@Autowired
	NaverLoginBO naverLoginBO;
	
	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	public String getTemp(Model model, HttpSession session) throws Exception{
		
		String naverAuthUrl = naverLoginBO.getAuthorizationUrl(session);
		System.out.println(naverAuthUrl);
		model.addAttribute("url", naverAuthUrl);
		
		return "home";
	}
	
	@RequestMapping(value = "/callback", method = { RequestMethod.GET, RequestMethod.POST })
	public String callback(Model model, @RequestParam String code, @RequestParam String state, HttpSession session)
			throws IOException {
		System.out.println("여기는 callback");
		OAuth2AccessToken oauthToken;
        oauthToken = naverLoginBO.getAccessToken(session, code, state);
        
        //로그인 사용자 정보를 읽어온다.
	    String apiResult = naverLoginBO.getUserProfile(oauthToken);
		System.out.println(apiResult);
		
		String user = apiResult.substring(apiResult.indexOf("email")+8, apiResult.indexOf("@"));
		session.setAttribute("userName", user);
	    model.addAttribute("result", apiResult);
	    
        /* 네이버 로그인 성공 페이지 View 호출 */
		return "redirect:main";
	}

	@RequestMapping("/")
	public String getHome() throws Exception{
		return "redirect:login";
	}
	
	@RequestMapping("login")
	public String getLogin() throws Exception{
		return "loginForm";
	}
	
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String getLogin(Model model, User user) throws Exception{
		
		return "redirect:dashBoard";
	}
	
	
	
	/* 임시 매핑 */
	
	
	@RequestMapping("/test")
	public void getTestPage() throws Exception{
		
	}
	
	@RequestMapping("/addRecord")
	public void toAddRecord() throws Exception{
		
	}
}
