package com.example.wonderfullindonesia.model;

public class Response{
	private String password;
	private String full_name;
	private String address;
	private String contact;
	private String id;
	private String username;

	public void setPassword(String password){
		this.password = password;
	}

	public String getPassword(){
		return password;
	}

	public void setFull_name(String full_name){
		this.full_name = full_name;
	}

	public String getFull_name(){
		return full_name;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setContact(String contact){
		this.contact = contact;
	}

	public String getContact(){
		return contact;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}
}
