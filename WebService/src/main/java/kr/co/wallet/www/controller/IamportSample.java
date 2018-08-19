package kr.co.wallet.www.controller;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
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
import kr.co.wallet.www.service.IamportClient;
import kr.co.wallet.www.vo.Account;
import kr.co.wallet.www.vo.IamportResponse;
import kr.co.wallet.www.vo.Payment;


@RestController
public class IamportSample {
	
	@Autowired
	IamportClient client;
	
	@Autowired
	AccountDao adao;
	
//	@Autowired
//	UserDao udao;
	
	
	public void testGetToken() throws Exception {
		String token = client.getToken();
		System.out.println("token : " + token);
	}
	
	@RequestMapping(value="/payments/buyer/{uid}", method=RequestMethod.GET)
	public ResponseEntity<Map<String, Object>> getBuyer(@PathVariable("uid") String uid) throws Exception{
		ResponseEntity<Map<String, Object>> param = null;
		
	//	System.out.println("uid 확인중..");
		
		try{
			Map<String, Object> map = new HashMap<String, Object>();
	//		map.put("buyer", udao.selectOne(uid));
			param = new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
			
		}catch(Exception e){
			e.printStackTrace();
			param = new ResponseEntity<Map<String ,Object>>(HttpStatus.BAD_REQUEST);
		}
		return param;
	}
	
	
	
	@RequestMapping(value="/payments/complete", method=RequestMethod.POST)
	public ResponseEntity<String> testGetPaymentByImpUid(Account account, String imp_uid, String uid) throws Exception {	
		ResponseEntity<String> entity = null;
		
		System.out.println("uid : "+uid);
		System.out.println("ImpUid : "+imp_uid);
		
		try{
			IamportResponse<Payment> paymentByimpuid = client.paymentByImpUid(imp_uid);    //  결제 ImpUid Check
			IamportResponse<Payment> paymentByMerchantUid = client.paymentByMerchantUid(paymentByimpuid.getResponse().getMerchantUid());
			
			int amount_ImpUid = ((BigDecimal)paymentByimpuid.getResponse().getAmount()).intValue();
			int amount_to_be_paid = ((BigDecimal)paymentByMerchantUid.getResponse().getAmount()).intValue();
			
			System.out.println("값 비교 : "+amount_ImpUid+" / "+amount_to_be_paid);

			if(paymentByimpuid.getResponse().getStatus().equals("paid") && amount_ImpUid == amount_to_be_paid){
				System.out.println("success 처리중...");
				
				account.setTno(adao.getCno(account.getTitle()));
				
				SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd");
				account.setCreDate(format.format(new Date()));
				adao.addAccount(account);
				
			//	udao.chargePoint(amount_to_be_paid, uid);
			//	model.put("member", mdao.sessionUpdate(mno));
				entity = new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
				
			}else{
				entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			entity = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
		
		return entity;
	}
	
	/*
	public void testCancelPaymentByImpUid() throws Exception {
		//이미 취소된 거래 imp_uid
		CancelData cancel1 = new CancelData("imp_813657628533", true);
		IamportResponse<Payment> cancelpayment1 = client.cancelPayment(cancel1);
		System.out.println(cancelpayment1.getMessage());
	}
	
	
	public void testCancelPaymentByMerchantUid() throws Exception {
		//이미 취소된 거래 merchant_uid
		CancelData cancel2 = new CancelData(" merchant_1499105658375", false);
		IamportResponse<Payment> cancelpayment2 = client.cancelPayment(cancel2);
		System.out.println(cancelpayment2.getMessage());
	}
	*/
}
