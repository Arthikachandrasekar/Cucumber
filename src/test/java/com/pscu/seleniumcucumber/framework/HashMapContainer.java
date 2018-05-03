package com.pscu.seleniumcucumber.framework;

import java.util.Map;

/**
 * @ScriptName    : Utilities
 * @Description   : This class contains HashMap Memory setter and getter 
 * @Author        : Swathin Ratheendren	
 * @Creation Date : September 2016  @Modified Date:                       
 */

public class HashMapContainer {

	static Map<String, String> hm = new java.util.HashMap<String, String>();
	static Map<String, String> hmPO = new java.util.HashMap<String, String>();

	public static void add(String key, String value){
		hm.put(key, value);
	}	
	
	public static void addPO(String key, String value){
		if(hmPO.get(key)!=null){
			System.out.println("KeyValue: "+key);
			System.out.println("Object reference already exists in Hashmap!");
		}else {
		hmPO.put(key, value);
		}
	}	

	public static String get(String key){
		return hm.get(key);
	}
	
	public static String getPO(String key){
		return hmPO.get(key);
	}

	public static void remove(String key)throws NullPointerException{
		if(hm.get(key)!=null){
			hm.remove(key);
		}
	}
	
	public static void removPO(String key)throws NullPointerException{
		if(hmPO.get(key)!=null){
			hmPO.remove(key);
		}
	}
	
	public static void ClearHM(){
		hm.clear();
		hmPO.clear();
	}
}
