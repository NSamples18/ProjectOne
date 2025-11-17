package edu.westga.cs3211.LandingPage.model;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents an inventory that contains multiple compartments.
 * @author ns00184
 * @version 2025
 */
public class Inventory {

	private Collection<Compartment> compartments;

    /**
     * Creates a new empty Inventory.
     * 
     * @precondition none
     * @postcondition getCompartments().isEmpty()
     */
    public Inventory() {
        this.compartments = new ArrayList<>();
    }

    /**
     * Adds a compartment to this inventory.
     * 
     * @precondition compartment != null
     * @postcondition getCompartments().contains(compartment)
     * 
     * @param compartment the compartment to add
     */
    public void addCompartment(Compartment compartment) {
        if (compartment == null) {
            throw new IllegalArgumentException("compartment cannot be null");
        }

        this.compartments.add(compartment);
    }

    /**
     * Returns the list of compartments in the inventory.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return the list of compartments
     */
    public Collection<Compartment> getCompartments() {
        return this.compartments;
    }

    /**
     * Determines whether any compartment in the inventory
     * has free space.
     * 
     * @precondition none
     * @postcondition none
     * 
     * @return true if at least one compartment has free space,
     *         false otherwise
     */
    public boolean hasFreeSpace() {
        for (Compartment compartment : this.compartments) {
            if (compartment.getFreeSpace() > 0) {
                return true;
            }
        }
        return false;
    }
}
