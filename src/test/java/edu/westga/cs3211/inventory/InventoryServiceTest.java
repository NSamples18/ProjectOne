package edu.westga.cs3211.inventory;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.Inventory;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.StorageCompartments;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;

class InventoryServiceTest {

    private InventoryService service;

    @BeforeEach
    void setup() {
        this.service = InventoryService.getInstance();
        this.service.getStockChangesInternal().clear();
    }

    @Test
    void testSingletonInstanceIsNotNull() {
        assertNotNull(InventoryService.getInstance());
    }

    @Test
    void testInventoryContainsAllFourCompartments() {
        Inventory inv = this.service.getInventory();

        assertEquals(4, inv.getCompartments().size());

        boolean hasWood = inv.getCompartments().stream()
                .anyMatch(c -> c.getCompartmentType() == StorageCompartments.WOODBARREL);
        boolean hasFridge = inv.getCompartments().stream()
                .anyMatch(c -> c.getCompartmentType() == StorageCompartments.FRIDGE);
        boolean hasHazmat = inv.getCompartments().stream()
                .anyMatch(c -> c.getCompartmentType() == StorageCompartments.HAZMAT_LOCKER);
        boolean hasTank = inv.getCompartments().stream()
                .anyMatch(c -> c.getCompartmentType() == StorageCompartments.TANK);

        assertTrue(hasWood);
        assertTrue(hasFridge);
        assertTrue(hasHazmat);
        assertTrue(hasTank);
    }

    @Test
    void testFindCompatibleForNone() {
        Stock stock = new Stock(10, "Rope", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now());
        List<Compartment> result = this.service.findCompatibleCompartments(stock);

        assertEquals(1, result.size());
        assertEquals(StorageCompartments.WOODBARREL, result.get(0).getCompartmentType());
    }

    @Test
    void testFindCompatibleForPerishable() {
        Stock stock = new Stock(5, "Milk", SpecialQualities.PERISHABLE, Condition.PERFECT, LocalDate.now());
        List<Compartment> result = this.service.findCompatibleCompartments(stock);

        assertEquals(1, result.size());
        assertEquals(StorageCompartments.FRIDGE, result.get(0).getCompartmentType());
    }

    @Test
    void testFindCompatibleForFlammable() {
        Stock stock = new Stock(3, "Fuel", SpecialQualities.FLAMMABLE, Condition.PERFECT, LocalDate.now());
        List<Compartment> result = this.service.findCompatibleCompartments(stock);

        assertEquals(1, result.size());
        assertEquals(StorageCompartments.HAZMAT_LOCKER, result.get(0).getCompartmentType());
    }

    @Test
    void testFindCompatibleForLiquid() {
        Stock stock = new Stock(8, "Water", SpecialQualities.LIQUID, Condition.PERFECT, LocalDate.now());
        List<Compartment> result = this.service.findCompatibleCompartments(stock);

        assertEquals(1, result.size());
        assertEquals(StorageCompartments.TANK, result.get(0).getCompartmentType());
    }

    @Test
    void testFindCompatibleReturnsEmptyWhenCapacityTooSmall() {
        Stock stock = new Stock(1000, "Huge Box", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now());
        List<Compartment> result = this.service.findCompatibleCompartments(stock);

        assertTrue(result.isEmpty());
    }

    @Test
    void testFindCompatibleReturnsEmptyForMismatchSpecialQuality() {
        Stock stock = new Stock(5, "Fuel", SpecialQualities.FLAMMABLE, Condition.PERFECT, LocalDate.now());
        List<Compartment> result = this.service.findCompatibleCompartments(stock);

        assertEquals(1, result.size());
        assertEquals(StorageCompartments.HAZMAT_LOCKER, result.get(0).getCompartmentType());
    }

    @Test
    void testAddStockToCompartmentAddsLogEntry() {
        User user = new User("Bob", "Crew", UserRole.Crew);
        Stock stock = new Stock(5, "Food", SpecialQualities.PERISHABLE, Condition.PERFECT, LocalDate.now());

        Compartment fridge = this.service.getInventory().getCompartments().stream()
                .filter(c -> c.getCompartmentType() == StorageCompartments.FRIDGE)
                .findFirst()
                .orElseThrow();

        this.service.addStockToCompartment(user, stock, fridge);

        assertEquals(1, this.service.getStockChanges().size());

        StockChange change = this.service.getStockChanges().get(0);

        assertEquals(user, change.getUser());
        assertEquals(stock, change.getStock());
        assertEquals(fridge, change.getCompartment());
        assertEquals(fridge.getFreeSpace() - stock.getSize(), change.getRemainingCapacity());
        assertNotNull(change.getTimestamp());
    }

    @Test
    void testGetStockChangesIsUnmodifiable() {
        List<StockChange> readOnlyList = this.service.getStockChanges();
        assertThrows(UnsupportedOperationException.class, () -> readOnlyList.add(null));
    }
}
