package com.chatweb.service;

import java.util.ArrayList;

public class FriendService {
   public static ArrayList<String> getFriendList(String userName){
	   ArrayList<String> friendl=new ArrayList<String>();
	   if(userName.equals("user1")){
			friendl.add("user2");
			friendl.add("user3");
			friendl.add("user4");
		}else if(userName.equals("user2")){
			friendl.add("user1");
			friendl.add("user3");
			friendl.add("user4");
		}else if(userName.equals("user3")){
			friendl.add("user2");
			friendl.add("user1");
			friendl.add("user4");
		}else if(userName.equals("user4")){
			friendl.add("user2");
			friendl.add("user3");
			friendl.add("user1");
		}
	return friendl;
   }
}
