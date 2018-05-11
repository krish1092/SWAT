package com.meteorology.swat.bean;

import org.json.JSONObject;

public class Rectangle {
	
	public Rectangle(){
		
	}
	
	public Rectangle(JSONObject jobj){
		
		this.left = jobj.getInt("rectLeft");
		this.right = jobj.getInt("rectRight");
		this.top = jobj.getInt("rectTop");
		this.bottom = jobj.getInt("rectBottom");
		
	}

	private int left,right,top,bottom;

	public int getLeft() {
		return left;
	}

	public void setLeft(int left) {
		this.left = left;
	}

	public int getRight() {
		return right;
	}

	public void setRight(int right) {
		this.right = right;
	}

	public int getTop() {
		return top;
	}

	public void setTop(int top) {
		this.top = top;
	}

	public int getBottom() {
		return bottom;
	}

	public void setBottom(int bottom) {
		this.bottom = bottom;
	}

	
		
	
}
