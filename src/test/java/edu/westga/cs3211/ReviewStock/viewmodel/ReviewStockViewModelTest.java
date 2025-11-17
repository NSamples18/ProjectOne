package edu.westga.cs3211.ReviewStock.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;
import edu.westga.cs3211.inventory.InventoryService;
import edu.westga.cs3211.inventory.StockChange;

public class ReviewStockViewModelTest {

    private ReviewStockViewModel viewModel;

    private User userHaynes;
    private User userBrent;

    private Stock nonPerishableStock;
    private Stock perishableStock;

    private Compartment normalComp;
    private Compartment perishableComp;

    private LocalDateTime t1;
    private LocalDateTime t2;
    private LocalDateTime t3;

    @BeforeEach
    void setup() {

        this.viewModel = new ReviewStockViewModel();
        InventoryService service = InventoryService.getInstance();
        service.getStockChangesInternal().clear();

        this.userHaynes = new User("Haynes", "pw", UserRole.Crew);
        this.userBrent = new User("Brent", "pw", UserRole.Quartermaster);

        this.nonPerishableStock = new Stock(
            5, "Canned Beans", SpecialQualities.NONE, Condition.PERFECT, null
        );

        this.perishableStock = new Stock(
            3, "Milk", SpecialQualities.PERISHABLE, Condition.PERFECT, LocalDate.now().plusDays(7)
        );

        Stock dummyNone = new Stock(0, "dummy-none", SpecialQualities.NONE, Condition.PERFECT, null);
        Stock dummyPer = new Stock(0, "dummy-per", SpecialQualities.PERISHABLE, Condition.PERFECT, null);

//        this.normalComp = new Compartment(dummyNone, "NONE", 100);
//        this.perishableComp = new Compartment(dummyPer, "PERISHABLE", 100);

        this.t1 = LocalDateTime.now().minusDays(2);
        this.t2 = LocalDateTime.now().minusDays(1);
        this.t3 = LocalDateTime.now();

        service.getStockChangesInternal().add(new StockChange(
            this.userHaynes, this.nonPerishableStock, this.normalComp, 95, this.t1
        ));

        service.getStockChangesInternal().add(new StockChange(
            this.userBrent, this.perishableStock, this.perishableComp, 97, this.t2
        ));

        service.getStockChangesInternal().add(new StockChange(
            this.userHaynes, this.perishableStock, this.perishableComp, 90, this.t3
        ));

        this.viewModel.loadAllChanges();
    }


    @Test
    void testLoadAllChangesSortedNewestFirst() {
        assertEquals(3, this.viewModel.getDisplayedChanges().size());
        assertEquals(this.t3, this.viewModel.getDisplayedChanges().get(0).getTimestamp());
        assertEquals(this.t2, this.viewModel.getDisplayedChanges().get(1).getTimestamp());
        assertEquals(this.t1, this.viewModel.getDisplayedChanges().get(2).getTimestamp());
    }

    @Test
    void testFilterBySpecialQuality() {
        this.viewModel.specialFilterProperty().set(SpecialQualities.PERISHABLE);
        this.viewModel.applyFilters();

        assertEquals(2, this.viewModel.getDisplayedChanges().size());
        assertTrue(this.viewModel.getDisplayedChanges()
            .stream()
            .allMatch(sc -> sc.getStock().getSpecialQuals() == SpecialQualities.PERISHABLE));
    }

    @Test
    void testFilterByCrewMate() {
        this.viewModel.crewFilterProperty().set(this.userBrent);
        this.viewModel.applyFilters();

        assertEquals(1, this.viewModel.getDisplayedChanges().size());
        assertEquals("Brent", this.viewModel.getDisplayedChanges().get(0).getUser().getName());
    }

    @Test
    void testFilterByValidTimeRange() {

        this.viewModel.startDateFilterProperty().set(LocalDate.now().minusDays(1));
        this.viewModel.endDateFilterProperty().set(LocalDate.now());

        this.viewModel.applyFilters();

        assertEquals(2, this.viewModel.getDisplayedChanges().size());
        assertTrue(this.viewModel.getDisplayedChanges()
            .stream()
            .allMatch(sc -> sc.getTimestamp().toLocalDate().isAfter(LocalDate.now().minusDays(2))));
    }

    @Test
    void testFilterByInvalidTimeRange() {
        this.viewModel.startDateFilterProperty().set(LocalDate.now());
        this.viewModel.endDateFilterProperty().set(LocalDate.now().minusDays(1));

        assertThrows(IllegalArgumentException.class, () -> {
            this.viewModel.applyFilters();
        });
    }

    @Test
    void testClearFilters() {
        this.viewModel.specialFilterProperty().set(SpecialQualities.PERISHABLE);
        this.viewModel.applyFilters();
        assertEquals(2, this.viewModel.getDisplayedChanges().size());

        this.viewModel.clearFilters();
        assertEquals(3, this.viewModel.getDisplayedChanges().size());
    }
}
