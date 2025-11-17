package edu.westga.cs3211.LandingPage.model;

/**
 * defines the compartment.
 * @author ns00184
 * @version 2025
 */
public class Compartment {

	private Stock stock;
    private String specialQuals;
    private int capacity;

    /**
     * Creates a new Compartment.
     *
     * @precondition stock != null && specialQuals != null && capacity >= 0
     * @postcondition 
     *      getStock() == stock AND
     *      getSpecialQuals() == specialQuals AND
     *      getCapacity() == capacity
     *
     * @param stock        the stock stored in this compartment
     * @param specialQuals special notes or qualifications
     * @param capacity     the maximum capacity for this compartment
     */
    public Compartment(Stock stock, String specialQuals, int capacity) {
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be null");
        }
        if (specialQuals == null) {
            throw new IllegalArgumentException("specialQuals cannot be null");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity cannot be negative");
        }

        this.stock = stock;
        this.specialQuals = specialQuals;
        this.capacity = capacity;
    }

    /**
     * Gets the stock stored in this compartment.
     *
     * @precondition none
     * @postcondition none
     *
     * @return the stock item
     */
    public Stock getStock() {
        return this.stock;
    }

    /**
     * Gets the special qualifications.
     *
     * @precondition none
     * @postcondition none
     *
     * @return the special qualifications string
     */
    public String getSpecialQuals() {
        return this.specialQuals;
    }

    /**
     * Gets the capacity of the compartment.
     *
     * @precondition none
     * @postcondition none
     *
     * @return the capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Computes the remaining free space.
     *
     * @precondition none
     * @postcondition none
     *
     * @return the free space as (capacity - stock.size)
     */
    public int getFreeSpace() {
        return this.capacity - this.stock.getSize();
    }
}
