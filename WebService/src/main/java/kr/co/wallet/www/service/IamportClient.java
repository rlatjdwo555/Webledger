package kr.co.wallet.www.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import kr.co.wallet.www.vo.AccessToken;
import kr.co.wallet.www.vo.AuthData;
import kr.co.wallet.www.vo.CancelData;
import kr.co.wallet.www.vo.IamportResponse;
import kr.co.wallet.www.vo.Payment;


@Service
public class IamportClient {
	private static final String API_URL = "https://api.iamport.kr";
//	private static final String API_URL = "http://localhost:8088";
	private String api_key = "API KEY";
	private String api_secret = "API Secrete KEY";
	private HttpClient client = HttpClientBuilder.create().build();
	private Gson gson = new Gson();
	
	private IamportResponse<AccessToken> getAuth() throws Exception{
		AuthData authData = new AuthData(api_key, api_secret);
				
		String authJsonData = gson.toJson(authData);
	
		
		try {
			StringEntity data = new StringEntity(authJsonData);
			
			HttpPost postRequest = new HttpPost(API_URL+"/users/getToken");
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection","keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			
			postRequest.setEntity(data);
			
			HttpResponse response = client.execute(postRequest);
		    
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
			
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			
			Type listType = new TypeToken<IamportResponse<AccessToken>>(){}.getType();
			IamportResponse<AccessToken> auth = gson.fromJson(body, listType);
			
			return auth;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		return null;
	} 

	private String postRequest(String path, String token, StringEntity postData) throws URISyntaxException{
		
		try {
			
			HttpPost postRequest = new HttpPost(API_URL+path);
			postRequest.setHeader("Accept", "application/json");
			postRequest.setHeader("Connection","keep-alive");
			postRequest.setHeader("Content-Type", "application/json");
			postRequest.addHeader("Authorization", token);

			postRequest.setEntity(postData);
					
			HttpResponse response = client.execute(postRequest);
			
			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
			
			ResponseHandler<String> handler = new BasicResponseHandler();
			String body = handler.handleResponse(response);
			
			return body;
			
		} catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
				
		return null;
	}
	
	private String getRequest(String path, String token) throws URISyntaxException {
		
		try {

			HttpGet getRequest = new HttpGet(API_URL+path);
			getRequest.addHeader("Accept", "application/json");
			getRequest.addHeader("Authorization", token);

			HttpResponse response = client.execute(getRequest); // HttpClient 방식으로는 한글 인코딩(utf-8)이 안된다 !

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
				   + response.getStatusLine().getStatusCode());
			}
			
			ResponseHandler<String> handler = new BasicResponseHandler();
			
			String body = handler.handleResponse(response);

			return body;
			
		  } catch (ClientProtocolException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();
		  }
		
		return null;
	}
	
	public String getToken() throws Exception {
		
		IamportResponse<AccessToken> auth = this.getAuth();
		
		if( auth != null) {
			String token = auth.getResponse().getToken();
			return token;
		}
		
		return null;		
	}
	
	public IamportResponse<Payment> paymentByImpUid(String impUid) throws Exception {
		
		String token = this.getToken();
		
		if(token != null) {
			String path = "/payments/"+impUid;
			String response = this.getRequest(path, token);
			System.out.println("test: "+response);
			
			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);
			// json으로 온 데이터를 자바 객체에 담음 
			
			return payment;
		}		
		return null;		
	}	
	
	public IamportResponse<Payment> paymentByMerchantUid(String merchantUid) throws Exception {
		
		String token = this.getToken();
		
		if(token != null){
			String path = "/payments/find/"+merchantUid;
			String response = this.getRequest(path, token);
		//	System.out.println(response);
			
			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);
			
			return payment;
		}
		
		return null;
	}
	
	public IamportResponse<Payment> cancelPayment(CancelData cancelData) throws Exception {
		
		String token = this.getToken();
		
		if(token != null){
			String cancelJsonData = gson.toJson(cancelData); //json 객체를 스트링으로
			StringEntity data = new StringEntity(cancelJsonData);
			
			String response = this.postRequest("/payments/cancel", token, data);
						
			Type listType = new TypeToken<IamportResponse<Payment>>(){}.getType();
			IamportResponse<Payment> payment = gson.fromJson(response, listType);
			
			return payment;
		}		
		return null;
	}	
}
