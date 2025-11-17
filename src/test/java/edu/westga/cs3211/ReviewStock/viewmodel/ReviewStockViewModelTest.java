package edu.westga.cs3211.ReviewStock.viewmodel;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;
import edu.westga.cs3211.inventory.InventoryService;
import edu.westga.cs3211.inventory.InventoryServiceTestHelper;
import edu.westga.cs3211.inventory.StockChange;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class ReviewStockViewModelTest {

    private User crew1;
    private User crew2;
    private Compartment generalCompartment;
    private Stock foodStock;
    private Stock flammableStock;

    @BeforeEach
    public void setup() {
        InventoryServiceTestHelper.resetService();

        this.crew1 = new User("Alice", "p1", UserRole.Crew);
        this.crew2 = new User("Bob", "p2", UserRole.Crew);

        this.generalCompartment = new Compartment(
            new Stock(1, "dummy", SpecialQualities.NONE, Condition.USABLE, null),
            "NONE",
            100
        );

        this.foodStock = new Stock(5, "Food Crate", SpecialQualities.PERISHABLE, Condition.PERFECT, null);
        this.flammableStock = new Stock(3, "Fuel Tank", SpecialQualities.FLAMMABLE, Condition.USABLE, null);

        InventoryService service = InventoryService.getInstance();

        service.getStockChanges().add(
            new StockChange(crew1, foodStock, generalCompartment, 95, LocalDateTime.of(2025, 1, 1, 10, 0))
        );

        service.getStockChanges().add(
            new StockChange(crew2, flammableStock, generalCompartment, 97, LocalDateTime.of(2025, 1, 2, 9, 0))
        );
    }

    @Test
    public void testFilterByCrew() {
        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.crewFilterProperty().set(this.crew1);
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals("Food Crate", vm.getDisplayedChanges().get(0).getStock().getName());
    }

    @Test
    public void testFilterBySpecialQuality() {
        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.specialFilterProperty().set(SpecialQualities.FLAMMABLE);
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals("Fuel Tank", vm.getDisplayedChanges().get(0).getStock().getName());
    }

    @Test
    public void testFilterByDateRange() {
        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.startDateFilterProperty().set(LocalDateTime.of(2025, 1, 2, 0, 0).toLocalDate());
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals("Fuel Tank", vm.getDisplayedChanges().get(0).getStock().getName());
    }

    @Test
    public void testFilterClearsCorrectly() {
        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.specialFilterProperty().set(SpecialQualities.PERISHABLE);
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());

        vm.clearFilters();

        assertEquals(2, vm.getDisplayedChanges().size());
    }
}
