package edu.westga.cs3211.AddStock.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;

public class AddStockViewModelTest {

    private AddStockViewModel viewModel;

    @BeforeEach
    public void setup() {
        this.viewModel = new AddStockViewModel();
    }

    @Test
    public void testBuildStockNonPerishableNoExpiration() {
        this.viewModel.nameProperty().set("Rope");
        this.viewModel.sizeProperty().set("10");
        this.viewModel.specialProperty().set(SpecialQualities.FLAMMABLE);
        this.viewModel.conditionProperty().set(Condition.USABLE);
        this.viewModel.expirationProperty().set(null);

        Stock stock = this.viewModel.buildStockOrNull();

        assertNotNull(stock);
        assertEquals(10, stock.getSize());
        assertEquals("Rope", stock.getName());
        assertEquals(SpecialQualities.FLAMMABLE, stock.getSpecialQuals());
        assertNull(stock.getExpirationDate());
    }

    @Test
    public void testBuildStockPerishableRequiresExpiration() {
        this.viewModel.nameProperty().set("Meat");
        this.viewModel.sizeProperty().set("5");
        this.viewModel.specialProperty().set(SpecialQualities.PERISHABLE);
        this.viewModel.conditionProperty().set(Condition.PERFECT);

        // No expiration provided
        this.viewModel.expirationProperty().set(null);

        Stock stock = this.viewModel.buildStockOrNull();
        assertNull(stock);
    }

    @Test
    public void testBuildStockPerishableWithExpiration() {
        this.viewModel.nameProperty().set("Milk");
        this.viewModel.sizeProperty().set("3");
        this.viewModel.specialProperty().set(SpecialQualities.PERISHABLE);
        this.viewModel.conditionProperty().set(Condition.USABLE);
        this.viewModel.expirationProperty().set(LocalDate.of(2025, 1, 1));

        Stock stock = this.viewModel.buildStockOrNull();

        assertNotNull(stock);
        assertEquals("Milk", stock.getName());
        assertEquals(SpecialQualities.PERISHABLE, stock.getSpecialQuals());
        assertEquals(LocalDate.of(2025, 1, 1), stock.getExpirationDate());
    }

    @Test
    public void testBuildStockInvalidSizeNonNumeric() {
        this.viewModel.nameProperty().set("Oil");
        this.viewModel.sizeProperty().set("x10");
        this.viewModel.specialProperty().set(SpecialQualities.LIQUID);
        this.viewModel.conditionProperty().set(Condition.USABLE);

        Stock stock = this.viewModel.buildStockOrNull();
        assertNull(stock);
    }

    @Test
    public void testCanSubmitNonPerishableNoExpiration() {
        this.viewModel.nameProperty().set("Wire");
        this.viewModel.sizeProperty().set("2");
        this.viewModel.specialProperty().set(SpecialQualities.FLAMMABLE);
        this.viewModel.conditionProperty().set(Condition.PERFECT);

        assertTrue(this.viewModel.canSubmitProperty().get());
    }

    @Test
    public void testCanSubmitPerishableRequiresExpiration() {
        this.viewModel.nameProperty().set("Veggies");
        this.viewModel.sizeProperty().set("4");
        this.viewModel.specialProperty().set(SpecialQualities.PERISHABLE);
        this.viewModel.conditionProperty().set(Condition.USABLE);

        // No expiration → should NOT allow submit
        assertFalse(this.viewModel.canSubmitProperty().get());

        // Add expiration → now can submit
        this.viewModel.expirationProperty().set(LocalDate.now());
        assertTrue(this.viewModel.canSubmitProperty().get());
    }
}
