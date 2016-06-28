package com.example.sqlitedbpractice;

public class Sales {
	
	int _id;
	String _amount;
	int _top;
	
	public Sales(){
		
	}
	
	public Sales(int id, String amount, int top){
		this._id = id;
		this._amount = amount;
		this._top = top;
	}
	
	public Sales(String amount, int top){
		this._amount = amount;
		this._top = top;
	}
	
	public int getId(){
		return this._id;
	}
	
	public void setId(int id){
		this._id = id;
	}
	
	public String getAmount(){
		return this._amount;
	}
	
	public int getTop(){
		return this._top;
	}

	public void setAmount(String string) {
		// TODO Auto-generated method stub
		this._amount = string;
		
	}

	public void setTop(int parseInt) {
		// TODO Auto-generated method stub
		this._top = parseInt;
		
	}
	

}
