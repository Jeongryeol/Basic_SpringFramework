package com.mvc.board;

public class MvcBoardMasterVO {
	private int    b_no     =0;//  
	private String b_title  ="";//  
	private String b_name   ="";//  
	private String b_content="";//  
	private String b_date   ="";//  
	private int    b_hit    =0;//  
	private int    b_group  =0;//  
	private int    b_pos    =0;//  
	private int    b_step   =0;//  
	private String b_pwd    ="";//  
	
	
	private MvcBoardSubVO bsVO = null;
	
	
	public int getB_no() {
		return b_no;
	}
	public void setB_no(int b_no) {
		this.b_no = b_no;
	}
	public String getB_title() {
		return b_title;
	}
	public void setB_title(String b_title) {
		this.b_title = b_title;
	}
	public String getB_name() {
		return b_name;
	}
	public void setB_name(String b_name) {
		this.b_name = b_name;
	}
	public String getB_content() {
		return b_content;
	}
	public void setB_content(String b_content) {
		this.b_content = b_content;
	}
	public String getB_date() {
		return b_date;
	}
	public void setB_date(String b_date) {
		this.b_date = b_date;
	}
	public int getB_hit() {
		return b_hit;
	}
	public void setB_hit(int b_hit) {
		this.b_hit = b_hit;
	}
	public int getB_group() {
		return b_group;
	}
	public void setB_group(int b_group) {
		this.b_group = b_group;
	}
	public int getB_pos() {
		return b_pos;
	}
	public void setB_pos(int b_pos) {
		this.b_pos = b_pos;
	}
	public int getB_step() {
		return b_step;
	}
	public void setB_step(int b_step) {
		this.b_step = b_step;
	}
	public String getB_pwd() {
		return b_pwd;
	}
	public void setB_pwd(String b_pwd) {
		this.b_pwd = b_pwd;
	}
	
	
	public MvcBoardSubVO getBsVO() {
		return bsVO;
	}
	public void setBsVO(MvcBoardSubVO bsVO) {
		this.bsVO = bsVO;
	}
}
