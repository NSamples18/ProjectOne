//package edu.westga.cs3211.LandingPage.modal;
//
//import static org.junit.jupiter.api.Assertions.*;
//import java.time.LocalDate;
//import org.junit.jupiter.api.Test;
//
//import edu.westga.cs3211.LandingPage.modal.Compartment;
//import edu.westga.cs3211.LandingPage.modal.Condition;
//import edu.westga.cs3211.LandingPage.modal.Inventory;
//import edu.westga.cs3211.LandingPage.modal.Stock;
//
//import java.util.Collection;
//
//class InventoryTests {
//
//    private Stock makeStock(int size) {
//        return new Stock(size, "Item", "Notes", Condition.PERFECT, LocalDate.now());
//    }
//
//    private Compartment makeCompartmentWith(int size, int capacity) {
//        return new Compartment(makeStock(size), "Cold", capacity);
//    }
//
//    @Test
//    void testConstructorCreatesEmptyInventory() {
//        Inventory inv = new Inventory();
//
//        assertTrue(inv.getCompartments().isEmpty());
//    }
//
//    @Test
//    void testAddCompartmentValid() {
//        Inventory inv = new Inventory();
//        Compartment c = makeCompartmentWith(5, 10);
//
//        inv.addCompartment(c);
//
//        assertTrue(inv.getCompartments().contains(c));
//    }
//
//    @Test
//    void testAddCompartmentNull() {
//        Inventory inv = new Inventory();
//
//        assertThrows(IllegalArgumentException.class, () -> {
//            inv.addCompartment(null);
//        });
//    }
//
//    @Test
//    void testHasFreeSpaceTrueWhenAtLeastOneHasSpace() {
//        Inventory inv = new Inventory();
//        inv.addCompartment(makeCompartmentWith(5, 10)); // free space = 5
//        inv.addCompartment(makeCompartmentWith(10, 10)); // free space = 0
//
//        assertTrue(inv.hasFreeSpace());
//    }
//
//    @Test
//    void testHasFreeSpaceFalseWhenNoneHaveSpace() {
//        Inventory inv = new Inventory();
//        inv.addCompartment(makeCompartmentWith(10, 10)); 
//        inv.addCompartment(makeCompartmentWith(15, 10)); 
//
//        assertFalse(inv.hasFreeSpace());
//    }
//
//    @Test
//    void testGetCompartmentsReturnsSameCollection() {
//        Inventory inv = new Inventory();
//        Compartment c = makeCompartmentWith(5, 10);
//        inv.addCompartment(c);
//
//        Collection<Compartment> result = inv.getCompartments();
//        assertEquals(1, result.size());
//        assertTrue(result.contains(c));
//    }
//}
//
