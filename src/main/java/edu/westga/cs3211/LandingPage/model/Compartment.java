package edu.westga.cs3211.LandingPage.model;

/**
 * Represents a storage compartment on the ship.
 *
 *
 *@author ns00184
 * @version 2025
 */
public class Compartment {

    private Stock stock;
    private StorageCompartments type;
    private int capacity;

    /**
     * Creates a new Compartment.
     *
     * @precondition stock != null
     * @precondition type != null
     * @precondition capacity >= 0
     *
     * @postcondition getStock() == stock AND
     *                getCompartmentType() == type AND
     *                getCapacity() == capacity
     *
     * @param stock     the current stock stored in this compartment (can be dummy placeholder)
     * @param type      the compartment type (enum)
     * @param capacity  the maximum capacity for this compartment
     */
    public Compartment(Stock stock, StorageCompartments type, int capacity) {
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be null");
        }
        if (type == null) {
            throw new IllegalArgumentException("compartment type cannot be null");
        }
        if (capacity < 0) {
            throw new IllegalArgumentException("capacity cannot be negative");
        }

        this.stock = stock;
        this.type = type;
        this.capacity = capacity;
    }

    /**
     * Gets the stock stored in this compartment.
     *
     * @return the stock item
     */
    public Stock getStock() {
        return this.stock;
    }

    /**
     * Gets the compartment type (enum).
     *
     * @return the type of storage compartment
     */
    public StorageCompartments getCompartmentType() {
        return this.type;
    }

    /**
     * Gets the capacity of the compartment.
     *
     * @return the storage capacity
     */
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Computes the remaining free space.
     *
     * @return free space = capacity - stored stock size
     */
    public int getFreeSpace() {
        return this.capacity - this.stock.getSize();
    }

    /**
     * Sets the stored stock. Called when new stock is added.
     *
     * @param stock the new stock
     */
    public void setStock(Stock stock) {
        if (stock == null) {
            throw new IllegalArgumentException("stock cannot be null");
        }
        this.stock = stock;
    }

    /**
     * Returns a readable UI-friendly representation of this compartment.

     * @return formatted string
     */
    @Override
    public String toString() {
        return this.type.name()
                + " (Capacity: " + this.capacity
                + ", Free: " + this.getFreeSpace() + ")";
    }
}