package kr.co.wallet.www.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.wallet.www.dao.AccountDao;
import kr.co.wallet.www.vo.Category;

@Service
public class Calculator {
	
	@Autowired
	AccountDao adao;
	
	public List<Integer> monthlyState(int flag) throws Exception{
		ArrayList<String> month = new ArrayList<>();
		ArrayList<Integer> outlay = new ArrayList<>();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
		
		Date today = new Date();
		cal.setTime(today);
		
		for(int i=1; i<=12; i++){
			cal.set(Calendar.MONTH, i - 1);
			cal.set(Calendar.DATE, 1);
			month.add(format.format(cal.getTime()));
		}
		
		for(int i=1; i<month.size(); i++){
			int monthSum = adao.totalSum(flag, month.get(i-1), month.get(i));
			outlay.add(monthSum/10000);
		}
		
		return outlay;
	}
	
	public Map<String, Object> userStatus(int uno, String curdate) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
		HashMap<String, Object> status = new HashMap<>();
		cal.setTime(format.parse(curdate));
		
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)-1, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String sdate = format.format(cal.getTime());
//		System.out.println("sdate: "+sdate);
		
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String edate = format.format(cal.getTime());
//		System.out.println("edate: "+edate);
//		System.out.println("sdate: "+sdate +"edate : "+edate);
		
		int expend = adao.totalSum(1, sdate, edate);
		int income = adao.totalSum(2, sdate, edate);
		
		status.put("budget", (double)100);
		status.put("limit", (double)expend/10000);
		status.put("expend", expend);
		status.put("income", income);
		status.put("margin", income-expend);
		status.put("clist", adao.getRankCategory(1, sdate, edate));
		
		return status;
	}
	
	
	public List<Integer> weeklyState(int flag, String curDate) throws Exception{
		ArrayList<Integer> outlays = new ArrayList<>();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
		
	//	Date today = new Date();
		cal.setTime(format.parse(curDate));
		
		for(int i=1; i<8; i++){
			cal.add(Calendar.DATE, -1);
			int result = adao.getDailySum(flag, format.format(cal.getTime()));
			
			outlays.add(result/10000);
		}
		
		return outlays;
	}
	
	public List<Category> getCategoryChart(int flag, String curDate) throws Exception{
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
		
		cal.setTime(format.parse(curDate));
		
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)-1, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		String sdate = format.format(cal.getTime());
		
		cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		String edate = format.format(cal.getTime());
		
		return adao.getCategoryTotal(flag, sdate, edate);
		
	}
	
	public List<String> weekAgo(String curDate) throws Exception{
		ArrayList<String> week = new ArrayList<>();
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat format = new SimpleDateFormat("yy.MM.dd");
		SimpleDateFormat format2 = new SimpleDateFormat("MM-dd");
		
		cal.setTime(format.parse(curDate));
		
		week.add(format2.format(cal.getTime()));
		
		for(int i=0; i<6; i++){
			cal.add(Calendar.DATE, -1);
			week.add(format2.format(cal.getTime()));
		}
		
		return week;
	}
}
