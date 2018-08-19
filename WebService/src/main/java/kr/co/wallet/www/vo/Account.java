package kr.co.wallet.www.vo;

import java.util.Date;

public class Account {
	int ano;
	int tno;
	String title;
	String content;
	int price;
	String creDate;
	
	public void setTno(int tno){
		this.tno = tno;
	}
	public int getTno(){
		return tno;
	}
	public int getAno() {
		return ano;
	}
	public String getTitle() {
		return title;
	}
	public String getContent() {
		return content;
	}
	public int getPrice() {
		return price;
	}
	public String getCreDate() {
		return creDate;
	}
	public void setAno(int ano) {
		this.ano = ano;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public void setCreDate(String creDate) {
		this.creDate = creDate;
	}
}
