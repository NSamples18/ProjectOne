package edu.westga.cs3211.LandingPage.modal;

import java.time.LocalDate;

/**
 * defines the stock.
 * @author ns00184
 * @version 2025
 */
public class Stock {
	private int size;
	private String name;
	private String specialQuals;
	private Condition condition;
	private LocalDate expirationDate;
	
	/**
    * Creates a new Stock item.
    * 
    * @precondition name != null && specialQuals != null && condition != null && expirationDate != null
    * @postcondition 
    *      getSize() == size AND
    *      getName() == name AND
    *      getSpecialQuals() == specialQuals AND
    *      getCondition() == condition AND
    *      getExpirationDate() == expirationDate
    * 
    * @param size the size of the stock item
    * @param name the name of the stock item
    * @param specialQuals special qualifications or notes
    * @param condition the item's condition
    * @param expirationDate the expiration date
    */
	public Stock(int size, String name, String specialQuals, Condition condition, LocalDate expirationDate) {
        if (name == null) {
            throw new IllegalArgumentException("name cannot be null");
        }
        if (specialQuals == null) {
            throw new IllegalArgumentException("specialQuals cannot be null");
        }
        if (condition == null) {
            throw new IllegalArgumentException("condition cannot be null");
        }
        if (expirationDate == null) {
            throw new IllegalArgumentException("expirationDate cannot be null");
        }

        this.size = size;
        this.name = name;
        this.specialQuals = specialQuals;
        this.condition = condition;
        this.expirationDate = expirationDate;
    }

    /**
     * Gets the size of the stock item.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the size of the item
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Gets the name of the stock item.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the special qualifications for the stock item.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the special qualifications text
     */
    public String getSpecialQuals() {
        return this.specialQuals;
    }

    /**
     * Gets the condition of the stock item.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the condition
     */
    public Condition getCondition() {
        return this.condition;
    }

    /**
     * Gets the expiration date of the stock item.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the expiration date
     */
    public LocalDate getExpirationDate() {
        return this.expirationDate;
    }
}
