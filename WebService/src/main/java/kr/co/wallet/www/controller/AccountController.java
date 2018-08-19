package kr.co.wallet.www.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import kr.co.wallet.www.dao.AccountDao;
import kr.co.wallet.www.service.CreatePage;
import kr.co.wallet.www.vo.Account;
import kr.co.wallet.www.vo.Criteria;
import kr.co.wallet.www.vo.PageMaker;

@RestController
@RequestMapping("/table")
public class AccountController {
	
	@Autowired
	AccountDao adao;
	
	@Autowired
	CreatePage makePage;
	
	@RequestMapping(value="/list/{page}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> ListCon(
			@PathVariable("page") int page) throws Exception {
		
		ResponseEntity<Map<String, Object>> entity = null;
		
		try{
			Criteria cri = new Criteria();
			cri.setPage(page);
			
			PageMaker pageMaker = new PageMaker();
			pageMaker.setCri(cri);
			
			Map<String, Object> map = new HashMap<String, Object>();
			
			ArrayList<Account> alist = (ArrayList<Account>)adao.getAlist(cri);
			map.put("alist", alist);
			map.put("pageMaker", makePage.getPageMaker(cri, adao.getTotalCount(1)));
				
			entity = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<Map<String, Object>>(HttpStatus.BAD_REQUEST);
		}
		return entity;
		
	}
	
	@RequestMapping(value="/delete/{ano}", method=RequestMethod.DELETE)
	public ResponseEntity<String> delAccount(@PathVariable("ano") int ano){
		ResponseEntity<String> entity = null;
		
		try{
			adao.deleteAccount(ano);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
				
		return entity;
	}
	
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public ResponseEntity<String> addAccount(@RequestBody Account account){
		
		System.out.println("결제 연동 테스트중");
		
		ResponseEntity<String> entity = null;
		
		try{
			account.setTno(adao.getCno(account.getTitle()));
			adao.addAccount(account);
			entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
}
