package edu.westga.cs3211.LandingPage.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


class InventoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        this.inventory = new Inventory();
    }

    @Test
    void testAddCompartmentAddsToCollection() {
        Stock stock = new Stock(
            5,
            "Rope",
            SpecialQualities.NONE,
            Condition.PERFECT,
            LocalDate.now()
        );

        Compartment compartment = new Compartment(
            stock,
            StorageCompartments.WOODBARREL,
            10   // capacity
        );

        this.inventory.addCompartment(compartment);

        Collection<Compartment> compartments = this.inventory.getCompartments();

        assertEquals(1, compartments.size());
        assertTrue(compartments.contains(compartment));
    }

    @Test
    void testAddCompartmentRejectsNull() {
        assertThrows(IllegalArgumentException.class,
            () -> this.inventory.addCompartment(null));
    }

    @Test
    void testHasFreeSpaceFalseWhenNoCompartments() {
        assertFalse(this.inventory.hasFreeSpace());
    }

    @Test
    void testHasFreeSpaceFalseWhenAllCompartmentsFull() {
        Stock fullStock1 = new Stock(
            10,
            "Full1",
            SpecialQualities.NONE,
            Condition.PERFECT,
            LocalDate.now()
        );
        Compartment full1 = new Compartment(
            fullStock1,
            StorageCompartments.WOODBARREL,
            10
        );

        Stock fullStock2 = new Stock(
            5,
            "Full2",
            SpecialQualities.NONE,
            Condition.PERFECT,
            LocalDate.now()
        );
        Compartment full2 = new Compartment(
            fullStock2,
            StorageCompartments.FRIDGE,
            5
        );

        this.inventory.addCompartment(full1);
        this.inventory.addCompartment(full2);

        assertEquals(0, full1.getFreeSpace());
        assertEquals(0, full2.getFreeSpace());
        assertFalse(this.inventory.hasFreeSpace());
    }

    @Test
    void testHasFreeSpaceTrueWhenAnyCompartmentHasRoom() {
        Stock fullStock = new Stock(
            10,
            "Full",
            SpecialQualities.NONE,
            Condition.PERFECT,
            LocalDate.now()
        );
        Compartment full = new Compartment(
            fullStock,
            StorageCompartments.WOODBARREL,
            10
        );

        Stock partialStock = new Stock(
            7,
            "Partial",
            SpecialQualities.NONE,
            Condition.PERFECT,
            LocalDate.now()
        );
        Compartment partial = new Compartment(
            partialStock,
            StorageCompartments.FRIDGE,
            10
        );

        this.inventory.addCompartment(full);
        this.inventory.addCompartment(partial);

        assertEquals(0, full.getFreeSpace());
        assertEquals(3, partial.getFreeSpace());
        assertTrue(this.inventory.hasFreeSpace());
    }
}
