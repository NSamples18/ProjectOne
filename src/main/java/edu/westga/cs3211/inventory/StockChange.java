package edu.westga.cs3211.inventory;

import java.time.LocalDateTime;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;

/**
 * Represents a single change to the inventory when stock is added.
 * @author ns00184
 * @version 2025
 */
public class StockChange {

    private final User crewMate;
    private final Stock stock;
    private final Compartment compartment;
    private final int remainingCapacity;
    private final LocalDateTime timestamp;

    /**
     * Creates a new StockChange record.
     *
     * @param crewMate the user who added the stock
     * @param stock the added stock
     * @param compartment the compartment used to store stock
     * @param remainingCapacity capacity left in the compartment after adding stock
     * @param timestamp the time the stock was added
     */
    public StockChange(User crewMate, Stock stock, Compartment compartment,
                       int remainingCapacity, LocalDateTime timestamp) {

        this.crewMate = crewMate;
        this.stock = stock;
        this.compartment = compartment;
        this.remainingCapacity = remainingCapacity;
        this.timestamp = timestamp;
    }

    /**
     * Returns the user who added the stock.
     *
     * @return the user
     */
    public User getCrewMate() {
        return this.crewMate;
    }

    /**
     * Returns the stock that was added.
     *
     * @return the stock
     */
    public Stock getStock() {
        return this.stock;
    }

    /**
     * Returns the compartment where the stock was placed.
     *
     * @return the compartment
     */
    public Compartment getCompartment() {
        return this.compartment;
    }

    /**
     * Returns the remaining capacity after adding stock.
     *
     * @return the remaining capacity
     */
    public int getRemainingCapacity() {
        return this.remainingCapacity;
    }

    /**
     * Returns the timestamp for when the stock was added.
     *
     * @return the timestamp
     */
    public LocalDateTime getTimestamp() {
        return this.timestamp;
    }
}
