package edu.westga.cs3211.inventory;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Inventory;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.LandingPage.model.StorageCompartments;
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

    private InventoryService() {
        this.inventory = new Inventory();
        this.stockChanges = new ArrayList<>();

        LocalDate farFuture = LocalDate.of(9999, 12, 31);

        Stock emptyGeneral = new Stock(0, "EMPTY_GENERAL",
                SpecialQualities.NONE, edu.westga.cs3211.LandingPage.model.Condition.PERFECT, farFuture);

        Stock emptyPerishable = new Stock(0, "EMPTY_PERISHABLE",
                SpecialQualities.PERISHABLE, edu.westga.cs3211.LandingPage.model.Condition.PERFECT, farFuture);

        Stock emptyFlammable = new Stock(0, "EMPTY_FLAMMABLE",
                SpecialQualities.FLAMMABLE, edu.westga.cs3211.LandingPage.model.Condition.PERFECT, farFuture);

        Stock emptyLiquid = new Stock(0, "EMPTY_LIQUID",
                SpecialQualities.LIQUID, edu.westga.cs3211.LandingPage.model.Condition.PERFECT, farFuture);

        Compartment woodBarrel = new Compartment(emptyGeneral, StorageCompartments.WOODBARREL, 200);
        Compartment fridge = new Compartment(emptyPerishable, StorageCompartments.FRIDGE, 150);
        Compartment metalBarrelFlammable = new Compartment(emptyFlammable, StorageCompartments.HAZMAT_LOCKER, 100);
        Compartment metalBarrelLiquid = new Compartment(emptyLiquid, StorageCompartments.HAZMAT_LOCKER, 120);

        this.inventory.addCompartment(woodBarrel);
        this.inventory.addCompartment(fridge);
        this.inventory.addCompartment(metalBarrelFlammable);
        this.inventory.addCompartment(metalBarrelLiquid);
    }

    /**
     * Returns the singleton InventoryService instance.
     *
     * @return the InventoryService instance
     */
    public static InventoryService getInstance() {
        return INSTANCE;
    }
    
    /**
     * Returns the ship's inventory, which includes all storage compartments.
     *
     * @return the Inventory object containing all compartments
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Returns a read-only list of all logged stock changes.
     * Most recent changes appear last.
     *
     * @return unmodifiable list of StockChange records
     */
    public List<StockChange> getStockChanges() {
        return Collections.unmodifiableList(this.stockChanges);
    }

    /**
     * Determines which storage compartments can hold a given stock item.
     * @param stock the stock item to check (must not be null)
     * @return a list of compatible compartments
     *
     * @precondition stock != null
     * @postcondition result contains all compartments that meet compatibility rules
     */
    public List<Compartment> findCompatibleCompartments(Stock stock) {
        List<Compartment> compatible = new ArrayList<>();

        for (Compartment compartment : this.inventory.getCompartments()) {

            boolean hasSpace = compartment.getFreeSpace() >= stock.getSize();
            if (!hasSpace) {
                continue;
            }

            boolean matches = switch (compartment.getCompartmentType()) {

                case WOODBARREL ->
                        stock.getSpecialQuals() == SpecialQualities.NONE;

                case FRIDGE ->
                        stock.getSpecialQuals() == SpecialQualities.PERISHABLE;

                case HAZMAT_LOCKER ->
                        stock.getSpecialQuals() == SpecialQualities.FLAMMABLE;

                case TANK ->
                        stock.getSpecialQuals() == SpecialQualities.LIQUID;
            };

            if (matches) {
                compatible.add(compartment);
            }
        }

        return compatible;
    }

    /**
     * Logs the addition of a stock item to a compartment.
     * 
     * @param user the user who added the stock
     * @param stock the stock item being added
     * @param compartment the compartment chosen to store the stock
     *
     * @precondition user != null AND stock != null AND compartment != null
     * @postcondition stockChanges contains exactly one additional StockChange entry
     */
    public void addStockToCompartment(User user, Stock stock, Compartment compartment) {

        int remainingCapacity = compartment.getFreeSpace() - stock.getSize();
        StockChange change = new StockChange(
                user,
                stock,
                compartment,
                remainingCapacity,
                LocalDateTime.now()
        );

        this.stockChanges.add(change);
    }
    
    /**
     * Internal accessor used only in unit tests to modify the change history.
     *
     * @return the internal list of StockChange objects
     */
    public List<StockChange> getStockChangesInternal() {
        return this.stockChanges;
    }
}
