//package edu.westga.cs3211.LandingPage.modal;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.time.LocalDate;
//
//import org.junit.jupiter.api.Test;
//
//import edu.westga.cs3211.LandingPage.modal.Compartment;
//import edu.westga.cs3211.LandingPage.modal.Condition;
//import edu.westga.cs3211.LandingPage.modal.Stock;
//
//class CompartmentTests {
//
//    private Stock makeStock(int size) {
//        return new Stock(size, "Item", "Quals", Condition.PERFECT, LocalDate.now());
//    }
//
//    @Test
//    void testValidConstructor() {
//        Stock stock = makeStock(5);
//        Compartment c = new Compartment(stock, "Cold", 20);
//
//        assertEquals(stock, c.getStock());
//        assertEquals("Cold", c.getSpecialQuals());
//        assertEquals(20, c.getCapacity());
//    }
//
//    @Test
//    void testConstructorNullStock() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            new Compartment(null, "Cold", 10);
//        });
//    }
//
//    @Test
//    void testConstructorNullSpecialQuals() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            new Compartment(makeStock(5), null, 10);
//        });
//    }
//
//    @Test
//    void testConstructorNegativeCapacity() {
//        assertThrows(IllegalArgumentException.class, () -> {
//            new Compartment(makeStock(5), "Cold", -1);
//        });
//    }
//
//    @Test
//    void testGetFreeSpace() {
//        Stock stock = makeStock(6);
//        Compartment c = new Compartment(stock, "Cold", 10);
//
//        assertEquals(4, c.getFreeSpace());
//    }
//
//    @Test
//    void testGetFreeSpaceZero() {
//        Stock stock = makeStock(10);
//        Compartment c = new Compartment(stock, "Cold", 10);
//
//        assertEquals(0, c.getFreeSpace());
//    }
//
//    @Test
//    void testGetFreeSpaceNegative() {
//        Stock stock = makeStock(15);
//        Compartment c = new Compartment(stock, "Cold", 10);
//
//        assertEquals(-5, c.getFreeSpace());
//    }
//}