package kr.co.wallet.www.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.wallet.www.dao.AccountDao;
import kr.co.wallet.www.service.Calculator;
import kr.co.wallet.www.service.CreatePage;
import kr.co.wallet.www.service.NaverLoginBO;
import kr.co.wallet.www.vo.Account;
import kr.co.wallet.www.vo.Criteria;

@Controller
public class BoardController {
	
	@Autowired 
	AccountDao adao;
	
	@Autowired
	Calculator cal;
	
	@Autowired
	CreatePage makePage;
	
	@Autowired
	NaverLoginBO naverLoginBO;
	
	@RequestMapping(value = "main", method = { RequestMethod.GET, RequestMethod.POST })
	public String getDashboard(Model model, String sdate) throws Exception{
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
		
		if(sdate == null){
			sdate = format.format(new Date());
			model.addAttribute("curDate", sdate);
		}else{
			model.addAttribute("curDate", sdate);
		}
		
		model.addAttribute("status", cal.userStatus(1, sdate));
		return "dashboard";
	}
	
	@RequestMapping("table")
	public String getAccountList(Model model, Criteria cri) throws Exception{
		
		ArrayList<Account> alist = (ArrayList<Account>)adao.getAlist(cri);
		
		model.addAttribute("alist", alist);
		model.addAttribute("pageMaker", makePage.getPageMaker(cri, adao.getTotalCount(1)));
	
		return "table";
	}
	
}
