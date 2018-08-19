package kr.co.wallet.www.dao;

import java.util.Hashtable;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.wallet.www.vo.Account;
import kr.co.wallet.www.vo.Category;
import kr.co.wallet.www.vo.Criteria;

@Repository
public class AccountDao {
	
	@Autowired
	SqlSessionFactory sqlFac;
	
	private final String namespace="kr.co.wallet.www.dao.AccountDao.";
	
	public List<Account> getAlist(Criteria cri) throws Exception{
		SqlSession session = sqlFac.openSession();
		
		try{
			Hashtable<String, Object> params = new Hashtable<>();
			params.put("startPage", cri.getStartPage());
			params.put("endPage", cri.getEndPage());
			
			return session.selectList(namespace+"accList", params);
		}finally{
			session.close();
		}
	}
	
	public int addAccount(Account account) throws Exception{
		SqlSession session = sqlFac.openSession();
		
		try{
			int row =  session.insert(namespace+"addAcc", account);
			session.commit();
			return row;
		}finally{
			session.close();
		}
	}
	
	public int getTotalCount(int flag) throws Exception{
		SqlSession session = sqlFac.openSession();
		
		try{
			Hashtable<String, Integer> param = new Hashtable<>();
			param.put("flag", flag);
			return session.selectOne(namespace+"totalCount", param);
		}finally{
			session.close();
		}
	}
	
	public int delAccount(int ano) throws Exception{
		SqlSession session = sqlFac.openSession();
		
		try{
			return session.delete(namespace+"delAcc", ano);
		}finally{
			session.close();
		}
	}
	
	public String getTitle(int tno) throws Exception{
		SqlSession session = sqlFac.openSession();
		
		try{
			return session.selectOne(namespace+"getCname", tno);
		}finally{
			session.close();
		}
	}
	
	public int deleteAccount(int ano) throws Exception{
		SqlSession session = sqlFac.openSession();
		
		try{
			return session.delete(namespace+"deleteAcc", ano);
		}finally{
			session.close();
		}
	}
	
	public int totalSum(int flag, String sdate, String edate) throws Exception{
		SqlSession session = sqlFac.openSession();
		String result = null;
		
		try{
			Hashtable<String, Object> params = new Hashtable<>();
			params.put("flag", flag);
			params.put("sdate", sdate);
			params.put("edate", edate);
			
			result = session.selectOne(namespace+"totalSum", params);
			
			if(result == null){
				return 0;
			}
			else return Integer.parseInt(result);
			
		}finally{
			session.close();
		}
	}
	
	public int getDailySum(int flag, String date) throws Exception{
		SqlSession session = sqlFac.openSession();
		String result = null;
		
		try{
			Hashtable<String, Object> params = new Hashtable<>();
			params.put("flag", flag);
			params.put("daily", date);
			result = session.selectOne(namespace+"dailySum", params);
			
			if(result == null){
				return 0;
			}else return Integer.parseInt(result);
		}finally{
			session.close();
		}
	}
	
	public int getCno(String title) throws Exception{
		SqlSession session = sqlFac.openSession();
		
		try{
			return session.selectOne(namespace+"getCno", title);
		}finally{
			session.close();
		}
	}
	
	public List<Category> getCategoryTotal(int flag, String sdate, String edate){
		SqlSession session = sqlFac.openSession();
		
		try{
			Hashtable<String, Object> params = new Hashtable<>();
			params.put("flag", flag);
			params.put("sdate", sdate);
			params.put("edate", edate);
			
			return session.selectList(namespace+"totalCategory", params);
			
		}finally{
			session.close();
		}
	}
	
	public List<Category> getRankCategory(int flag, String sdate, String edate){
		SqlSession session = sqlFac.openSession();
		
		try{
			Hashtable<String, Object> params = new Hashtable<>();
			params.put("flag", flag);
			params.put("sdate", sdate);
			params.put("edate", edate);
			
			return session.selectList(namespace+"rankCategory", params);
			
		}finally{
			session.close();
		}
	}
	
}
