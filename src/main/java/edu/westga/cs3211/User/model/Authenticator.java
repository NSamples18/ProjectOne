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
     * Returns the UserRole for the given username.
     *
     * @precondition username != null
     * @postcondition none
     *
     * @param username the username to look up
     * @return the user's role, or null if no match
     */
    public UserRole getUserRole(String username) {
        if (username == null) {
            throw new IllegalArgumentException("username cannot be null");
        }

        for (User user : this.users) {
            if (user.getName().equals(username)) {
                return user.getRole();
            }
        }

        return null;
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
