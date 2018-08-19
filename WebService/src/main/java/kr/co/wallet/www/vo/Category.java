package kr.co.wallet.www.vo;

public class Category {
	int cno;
	String cname;
	int count;
	int totalPrice;
	
	public int getTotalPrice(){
		return totalPrice;
	}
	
	public void setTotalPrice(int totalPrice){
		this.totalPrice = totalPrice;
	}
	
	public int getCount(){
		return count;
	}
	
	public void setCount(int count){
		this.count = count;
	}
	
	public int getCno() {
		return cno;
	}
	public String getCname() {
		return cname;
	}
	public void setCno(int cno) {
		this.cno = cno;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
}
