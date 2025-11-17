package edu.westga.cs3211.AddStock.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
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

class AddStockViewModelTest {

    private AddStockViewModel vm;

    @BeforeEach
    void setup() {
        vm = new AddStockViewModel();

        InventoryService.getInstance().getStockChangesInternal().clear();
    }

    @Test
    void testSetCurrentUserStoresUser() {
        User user = new User("Bob", "bob123", UserRole.Crew);
        vm.setCurrentUser(user);

        Stock s = new Stock(5, "Test", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now());
        Compartment c = InventoryService.getInstance()
                .findCompatibleCompartments(s).get(0);

        vm.addStockToCompartment(s, c);

        var changes = InventoryService.getInstance().getStockChangesInternal();

        assertEquals(user, changes.get(0).getUser());
    }

    @Test
    void testBuildStockOrNullFailsOnBlankName() {
        vm.nameProperty().set("");
        vm.sizeProperty().set("10");
        vm.conditionProperty().set(Condition.PERFECT);
        vm.specialProperty().set(SpecialQualities.NONE);

        assertNull(vm.buildStockOrNull());
    }

    @Test
    void testBuildStockOrNullFailsOnNullName() {
        vm.nameProperty().set(null);
        vm.sizeProperty().set("10");
        vm.conditionProperty().set(Condition.PERFECT);
        vm.specialProperty().set(SpecialQualities.NONE);

        assertNull(vm.buildStockOrNull());
    }

    @Test
    void testBuildStockOrNullFailsOnNonNumericSize() {
        vm.nameProperty().set("Test");
        vm.sizeProperty().set("abc");
        vm.conditionProperty().set(Condition.PERFECT);
        vm.specialProperty().set(SpecialQualities.NONE);

        assertNull(vm.buildStockOrNull());
    }

    @Test
    void testBuildStockOrNullFailsOnNegativeSize() {
        vm.nameProperty().set("Test");
        vm.sizeProperty().set("-5");  
        vm.conditionProperty().set(Condition.PERFECT);
        vm.specialProperty().set(SpecialQualities.NONE);

        assertNull(vm.buildStockOrNull());
    }

    @Test
    void testBuildStockOrNullFailsOnPerishableWithoutExpiration() {
        vm.nameProperty().set("Milk");
        vm.sizeProperty().set("5");
        vm.conditionProperty().set(Condition.PERFECT);
        vm.specialProperty().set(SpecialQualities.PERISHABLE);
        vm.expirationProperty().set(null);

        assertNull(vm.buildStockOrNull());
    }

    @Test
    void testBuildStockOrNullCreatesValidNonPerishableStock() {
        vm.nameProperty().set("Rope");
        vm.sizeProperty().set("10");
        vm.conditionProperty().set(Condition.USABLE);
        vm.specialProperty().set(SpecialQualities.NONE);
        vm.expirationProperty().set(null); 
        Stock s = vm.buildStockOrNull();

        assertNotNull(s);
        assertEquals("Rope", s.getName());
        assertEquals(10, s.getSize());
        assertEquals(Condition.USABLE, s.getCondition());
        assertEquals(SpecialQualities.NONE, s.getSpecialQuals());
    }

    @Test
    void testBuildStockOrNullCreatesValidPerishableStock() {
        LocalDate exp = LocalDate.now().plusDays(3);

        vm.nameProperty().set("Milk");
        vm.sizeProperty().set("3");
        vm.conditionProperty().set(Condition.PERFECT);
        vm.specialProperty().set(SpecialQualities.PERISHABLE);
        vm.expirationProperty().set(exp);

        Stock s = vm.buildStockOrNull();

        assertNotNull(s);
        assertEquals("Milk", s.getName());
        assertEquals(3, s.getSize());
        assertEquals(SpecialQualities.PERISHABLE, s.getSpecialQuals());
        assertEquals(exp, s.getExpirationDate());
    }

    @Test
    void testLoadCompatibleCompartmentsPopulatesList() {
        Stock s = new Stock(5, "Test", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now());

        vm.loadCompatibleCompartments(s);

        List<Compartment> expected = InventoryService.getInstance()
                .findCompatibleCompartments(s);

        assertEquals(expected.size(), vm.getAvailableCompartments().size());
    }

    @Test
    void testGetAvailableCompartmentsInitiallyEmpty() {
        assertTrue(vm.getAvailableCompartments().isEmpty());
    }

    @Test
    void testSelectedCompartmentSetAndGet() {
        Compartment c = new Compartment(
                new Stock(0, "Empty", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now()),
                StorageCompartments.WOODBARREL,
                100
        );

        vm.setSelectedCompartment(c);

        assertEquals(c, vm.getSelectedCompartment());
        assertEquals(c, vm.selectedCompartmentProperty().get());
    }

    @Test
    void testAddStockToCompartmentCreatesLogEntry() {
        User user = new User("Alice", "a1", UserRole.Crew);
        vm.setCurrentUser(user);

        Stock s = new Stock(5, "Food", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now());
        Compartment c = InventoryService.getInstance()
                .findCompatibleCompartments(s)
                .get(0);

        vm.addStockToCompartment(s, c);

        var logs = InventoryService.getInstance().getStockChangesInternal();

        assertEquals(1, logs.size());
        assertEquals(user, logs.get(0).getUser());
        assertEquals(s, logs.get(0).getStock());
        assertEquals(c, logs.get(0).getCompartment());
    }
}
