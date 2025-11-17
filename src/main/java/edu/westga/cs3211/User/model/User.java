package edu.westga.cs3211.User.model;

/**
 * Defines a person
 * 
 * @author CS 3211
 * @version Fall 2025
 */
public class User {

	private static final String NAME_CANNOT_BE_NULL = "name cannot be null.";
	private String name;
	private String password;
	private UserRole role;
	
	/**
	 * Creates a new Person with the specified name.
	 * 
	 * @precondition name!=null 
	 * @postcondition getName() == name 
	 * 
	 * @param name
	 *            name of the person
	 */
	public User(String name, String password, UserRole role){
		if (name == null) {
			throw new IllegalArgumentException(NAME_CANNOT_BE_NULL);
		}
		
		if (password == null) {
			throw new IllegalArgumentException(NAME_CANNOT_BE_NULL);
		}
		
		if (role == null) {
			throw new IllegalArgumentException(NAME_CANNOT_BE_NULL);
		}

		this.name = name;
		this.password = password;
		this.role = role;
		
	}
	
	/**
	 * Returns the user name
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the Person's name
	 */
	public String getName() {
		return this.name;
	}
	

	/**
	 * Returns the users password
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the Person's name
	 */
	public String getPassword() {
		return this.password;
	}
	

	/**
	 * Returns the users role
	 * 
	 * @precondition none
	 * @postcondition none
	 * 
	 * @return the Person's name
	 */
	public UserRole getRole() {
		return this.role;
	}
	
	@Override
	public String toString() {
	    return this.name;
	}
}
