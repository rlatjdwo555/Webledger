package kr.co.wallet.www.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kr.co.wallet.www.dao.AccountDao;
import kr.co.wallet.www.service.Calculator;
import kr.co.wallet.www.vo.Category;

@RestController
@RequestMapping("/board")
public class ChartController {
	
	@Autowired
	Calculator cal;
	
	@RequestMapping(value="/monthChart/{flag}", method=RequestMethod.GET)
	public ResponseEntity<List<Integer>> getMonthChart(@PathVariable("flag") int flag){
		ResponseEntity<List<Integer>> entity = null;
		
		try{
			List<Integer> outlays = cal.monthlyState(flag);
			entity = new ResponseEntity<>(outlays, HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	@RequestMapping(value="/dailyChart/{curDate}/{flag}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getDailyChart(@PathVariable("flag") int flag,
			@PathVariable("curDate") String curDate){
		ResponseEntity<Map<String, Object>> entity = null;
		
		try{
			Map<String, Object> map = new HashMap<>();
			map.put("outlays", cal.weeklyState(flag, curDate));
			map.put("weekAgo", cal.weekAgo(curDate));
			entity = new ResponseEntity<>(map, HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@RequestMapping(value="/categoryChart/{curDate}/{flag}", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getCategoryChart(@PathVariable("flag") int flag,
			@PathVariable("curDate") String curDate){
		ResponseEntity<List<Category>> entity = null;
		
		try{
			List<Category> clist = cal.getCategoryChart(flag, curDate);
			entity = new ResponseEntity<>(clist, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
}
