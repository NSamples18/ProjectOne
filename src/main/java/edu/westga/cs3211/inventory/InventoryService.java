package edu.westga.cs3211.inventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.Inventory;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;

/**
 * Service for managing inventory operations and logging stock changes.
 * @author ns00184
 * @version 2025
 */
public class InventoryService {

    private static final InventoryService INSTANCE = new InventoryService();

    private final Inventory inventory;
    private final List<StockChange> stockChanges;

    /**
     * Creates a new InventoryService with initialized compartments and change log.
     */
    private InventoryService() {
        this.inventory = new Inventory();
        this.stockChanges = new ArrayList<>();

        LocalDate farFuture = LocalDate.of(9999, 12, 31);
        
        Stock emptyNone = new Stock(
        	    0,
        	    "EMPTY GENERAL",
        	    SpecialQualities.NONE,
        	    Condition.PERFECT,
        	    farFuture
        	);

        Stock emptyPerishable = new Stock(
            0,
            "EMPTY PERISHABLE",
            SpecialQualities.PERISHABLE,
            Condition.PERFECT,
            farFuture
        );

        Stock emptyFlammable = new Stock(
            0,
            "EMPTY FLAMMABLE",
            SpecialQualities.FLAMMABLE,
            Condition.PERFECT,
            farFuture
        );

        Stock emptyLiquid = new Stock(
            0,
            "EMPTY LIQUID",
            SpecialQualities.LIQUID,
            Condition.PERFECT,
            farFuture
        );
        
        Compartment generalStorage = new Compartment(
        	    emptyNone,
        	    "NONE",
        	    200
        	);

        Compartment coldStorage = new Compartment(
            emptyPerishable,
            "PERISHABLE",
            150
        );

        Compartment flammableBay = new Compartment(
            emptyFlammable,
            "FLAMMABLE",
            100
        );

        Compartment liquidBay = new Compartment(
            emptyLiquid,
            "LIQUID",
            120
        );
        
        this.inventory.addCompartment(generalStorage);
        this.inventory.addCompartment(coldStorage);
        this.inventory.addCompartment(flammableBay);
        this.inventory.addCompartment(liquidBay);
    }

    /**
     * Returns the singleton instance of the service.
     *
     * @return the InventoryService instance
     */
    public static InventoryService getInstance() {
        return INSTANCE;
    }

    /**
     * Returns the underlying inventory.
     *
     * @return the inventory
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Returns the list of logged stock changes in chronological order.
     *
     * @return an unmodifiable list of stock changes
     */
    public List<StockChange> getStockChanges() {
        return Collections.unmodifiableList(this.stockChanges);
    }

    /**
     * Finds compartments that can store the specified stock.
     * A compartment is compatible if it has sufficient free space
     * and its special qualities string contains the stock's special quality.
     *
     * @param stock the stock to evaluate
     * @return a list of compatible compartments
     */
    public List<Compartment> findCompatibleCompartments(Stock stock) {
        List<Compartment> compatible = new ArrayList<>();
        for (Compartment compartment : this.inventory.getCompartments()) {
            boolean hasSpace = compartment.getFreeSpace() >= stock.getSize();
            boolean matchesQuals =
                compartment.getSpecialQuals().contains(stock.getSpecialQuals().name());
            if (hasSpace && matchesQuals) {
                compatible.add(compartment);
            }
        }
        return compatible;
    }

    /**
     * Adds the specified stock to the given compartment and logs the operation.
     * This implementation does not mutate the compartment, but computes
     * remaining capacity for logging purposes.
     *
     * @param user the user adding the stock
     * @param stock the stock to add
     * @param compartment the compartment selected for storage
     */
    public void addStockToCompartment(User user, Stock stock, Compartment compartment) {
        int remaining = compartment.getCapacity() - stock.getSize();
        StockChange change = new StockChange(
            user,
            stock,
            compartment,
            remaining,
            LocalDateTime.now()
        );
        this.stockChanges.add(change);
    }
}
