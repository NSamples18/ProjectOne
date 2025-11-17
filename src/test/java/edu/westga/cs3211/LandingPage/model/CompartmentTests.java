package edu.westga.cs3211.LandingPage.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CompartmentTest {

    private Stock testStock;

    @BeforeEach
    void setup() {
        testStock = new Stock(
                10,
                "TestItem",
                SpecialQualities.NONE,
                Condition.PERFECT,
                LocalDate.now()
        );
    }

    @Test
    void testConstructorValid() {
        Compartment compartment = new Compartment(testStock, StorageCompartments.WOODBARREL, 100);

        assertEquals(testStock, compartment.getStock());
        assertEquals(StorageCompartments.WOODBARREL, compartment.getCompartmentType());
        assertEquals(100, compartment.getCapacity());
    }

    @Test
    void testConstructorNullStockThrows() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Compartment(null, StorageCompartments.WOODBARREL, 100)
        );
    }

    @Test
    void testConstructorNullCompartmentTypeThrows() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Compartment(testStock, null, 100)
        );
    }

    @Test
    void testConstructorNegativeCapacityThrows() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Compartment(testStock, StorageCompartments.WOODBARREL, -5)
        );
    }

    @Test
    void testGetFreeSpaceCorrectValue() {
        Compartment compartment = new Compartment(testStock, StorageCompartments.WOODBARREL, 100);
        assertEquals(90, compartment.getFreeSpace());
    }

    @Test
    void testSetStockWorks() {
        Compartment compartment = new Compartment(testStock, StorageCompartments.WOODBARREL, 100);

        Stock newStock = new Stock(
                3,
                "NewItem",
                SpecialQualities.NONE,
                Condition.USABLE,
                LocalDate.now()
        );

        compartment.setStock(newStock);

        assertEquals(newStock, compartment.getStock());
    }

    @Test
    void testSetStockNullThrows() {
        Compartment compartment = new Compartment(testStock, StorageCompartments.WOODBARREL, 100);

        assertThrows(IllegalArgumentException.class, () -> 
            compartment.setStock(null)
        );
    }

    @Test
    void testToStringIncludesTypeCapacityAndFreeSpace() {
        Compartment compartment = new Compartment(testStock, StorageCompartments.WOODBARREL, 100);

        String result = compartment.toString();

        assertTrue(result.contains("WOODBARREL"));
        assertTrue(result.contains("100"));
        assertTrue(result.contains("90"));
    }

    @Test
    void testGettersReturnCorrectValues() {
        Compartment compartment = new Compartment(testStock, StorageCompartments.TANK, 50);

        assertEquals(testStock, compartment.getStock());
        assertEquals(StorageCompartments.TANK, compartment.getCompartmentType());
        assertEquals(50, compartment.getCapacity());
    }
}
