package edu.westga.cs3211.ReviewStock.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.StorageCompartments;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;
import edu.westga.cs3211.inventory.InventoryService;
import edu.westga.cs3211.inventory.StockChange;

class ReviewStockViewModelTest {

    private InventoryService service;

    private User userA;
    private User userB;

    private Compartment barrel;
    private Compartment fridge;

    private Stock noneStock;
    private Stock perishableStock;

    @BeforeEach
    void setup() {
        service = InventoryService.getInstance();
        service.getStockChangesInternal().clear();

        userA = new User("Alice", "alice", UserRole.Crew);
        userB = new User("Bob", "bob", UserRole.Crew);

        barrel = service.getInventory().getCompartments().stream()
                .filter(c -> c.getCompartmentType() == StorageCompartments.WOODBARREL)
                .findFirst().orElseThrow();

        fridge = service.getInventory().getCompartments().stream()
                .filter(c -> c.getCompartmentType() == StorageCompartments.FRIDGE)
                .findFirst().orElseThrow();

        noneStock = new Stock(5, "Rope", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now());
        perishableStock = new Stock(3, "Milk", SpecialQualities.PERISHABLE, Condition.USABLE, LocalDate.now());
    }

    private StockChange makeChange(User user, Stock stock, Compartment comp, LocalDateTime time) {
        StockChange change = new StockChange(user, stock, comp, 50, time);
        service.getStockChangesInternal().add(change);
        return change;
    }

    @Test
    void testConstructorLoadsAndSortsChanges() {
        // Newest first is expected
        StockChange c1 = makeChange(userA, noneStock, barrel, LocalDateTime.now().minusDays(1));
        StockChange c2 = makeChange(userB, noneStock, barrel, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        List<StockChange> displayed = vm.getDisplayedChanges();

        assertEquals(2, displayed.size());
        assertEquals(c2, displayed.get(0));
        assertEquals(c1, displayed.get(1));
    }

    @Test
    void testCrewFilterWorks() {
        StockChange c1 = makeChange(userA, noneStock, barrel, LocalDateTime.now());
        makeChange(userB, noneStock, barrel, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.crewFilterProperty().set(userA);
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals(c1, vm.getDisplayedChanges().get(0));
    }


    @Test
    void testSpecialFilterWorks() {
        StockChange c1 = makeChange(userA, perishableStock, fridge, LocalDateTime.now());
        makeChange(userA, noneStock, barrel, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.specialFilterProperty().set(SpecialQualities.PERISHABLE);
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals(c1, vm.getDisplayedChanges().get(0));
    }

    @Test
    void testStartDateFilterWorks() {
        StockChange newC = makeChange(userA, noneStock, barrel, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.startDateFilterProperty().set(LocalDate.now().minusDays(2));
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals(newC, vm.getDisplayedChanges().get(0));
    }

    @Test
    void testEndDateFilterWorks() {
        StockChange earlyC = makeChange(userA, noneStock, barrel, LocalDateTime.now().minusDays(5));
        makeChange(userA, noneStock, barrel, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.endDateFilterProperty().set(LocalDate.now().minusDays(3));
        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals(earlyC, vm.getDisplayedChanges().get(0));
    }

    @Test
    void testInvalidDateRangeThrows() {
        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.startDateFilterProperty().set(LocalDate.now());
        vm.endDateFilterProperty().set(LocalDate.now().minusDays(1));

        assertThrows(IllegalArgumentException.class, vm::applyFilters);
    }


    @Test
    void testMultipleFiltersTogether() {
        StockChange match = makeChange(userA, perishableStock, fridge, LocalDateTime.now());
        makeChange(userA, noneStock, barrel, LocalDateTime.now());
        makeChange(userB, perishableStock, fridge, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.crewFilterProperty().set(userA);
        vm.specialFilterProperty().set(SpecialQualities.PERISHABLE);

        vm.applyFilters();

        assertEquals(1, vm.getDisplayedChanges().size());
        assertEquals(match, vm.getDisplayedChanges().get(0));
    }


    @Test
    void testClearFiltersRestoresAllChanges() {
        makeChange(userA, noneStock, barrel, LocalDateTime.now());
        makeChange(userA, perishableStock, fridge, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.crewFilterProperty().set(userA);
        vm.specialFilterProperty().set(SpecialQualities.PERISHABLE);
        vm.applyFilters();
        assertEquals(1, vm.getDisplayedChanges().size());

        vm.clearFilters();
        assertEquals(2, vm.getDisplayedChanges().size());
    }
    
    @Test
    void testApplyFiltersThrowsOnInvalidDateRange() {
        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.startDateFilterProperty().set(LocalDate.of(2025, 1, 10));
        vm.endDateFilterProperty().set(LocalDate.of(2025, 1, 10)); // equal → invalid

        assertThrows(IllegalArgumentException.class, () -> {
            vm.applyFilters();
        });
    }
    
    @Test
    void testApplyFiltersWithStartDateOnlyDoesNotThrow() {
        ReviewStockViewModel vm = new ReviewStockViewModel();

        vm.startDateFilterProperty().set(LocalDate.now());
        vm.endDateFilterProperty().set(null);  // important branch

        assertDoesNotThrow(() -> vm.applyFilters());
    }
    
    @Test
    void testApplyFiltersWithNullSpecialFilterDoesNotFilterBySpecial() {
        // Create two changes with different special qualities
        StockChange c1 = makeChange(userA, noneStock, barrel, LocalDateTime.now());
        StockChange c2 = makeChange(userA, perishableStock, fridge, LocalDateTime.now());

        ReviewStockViewModel vm = new ReviewStockViewModel();

        // crew filter used so applyFilters does more than nothing,
        // making Jacoco observe the specialFilter null branch
        vm.crewFilterProperty().set(userA);

        // IMPORTANT: ensure specialFilter is null
        vm.specialFilterProperty().set(null);

        vm.applyFilters();

        // Both records match crew filter, so both should appear
        assertEquals(2, vm.getDisplayedChanges().size());
        assertTrue(vm.getDisplayedChanges().contains(c1));
        assertTrue(vm.getDisplayedChanges().contains(c2));
    }
}
