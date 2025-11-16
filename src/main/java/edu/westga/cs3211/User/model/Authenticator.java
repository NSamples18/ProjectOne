package edu.westga.cs3211.User.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 
 */
public class Authenticator {
	
    private Collection<User> users = new ArrayList<>();
    
    /**
     * 
     */
    public Authenticator() {
    	this.users.add(new User("Haynes", "CrewMate1", UserRole.Crew));
    	this.users.add(new User("Brent", "QMaster1", UserRole.Quartermaster));
    }
    

    
    /**
     * 
     * @param name
     * @param password
     * @return verifies if the creds a valid.
     */
    public boolean verify(String name, String password) {
        for (User user : users){

           if(user.getName().equals(name) && user.getPassword().equals(password)){
               return true;
           }
        }
        return false;
    }



}
