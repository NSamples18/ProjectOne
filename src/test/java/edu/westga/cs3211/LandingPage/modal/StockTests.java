package edu.westga.cs3211.LandingPage.modal;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.LandingPage.modal.Condition;
import edu.westga.cs3211.LandingPage.modal.Stock;

class StockTests {

    @Test
    void testValidConstructor() {
        LocalDate date = LocalDate.now();
        Stock stock = new Stock(10, "Food", "Keep Frozen", Condition.Perfect, date);

        assertEquals(10, stock.getSize());
        assertEquals("Food", stock.getName());
        assertEquals("Keep Frozen", stock.getSpecialQuals());
        assertEquals(Condition.Perfect, stock.getCondition());
        assertEquals(date, stock.getExpirationDate());
    }

    @Test
    void testConstructorNullName() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(5, null, "Notes", Condition.Perfect, LocalDate.now());
        });
    }

    @Test
    void testConstructorNullSpecialQuals() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(5, "Item", null, Condition.Perfect, LocalDate.now());
        });
    }

    @Test
    void testConstructorNullCondition() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(5, "Item", "Notes", null, LocalDate.now());
        });
    }

    @Test
    void testConstructorNullExpirationDate() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Stock(5, "Item", "Notes", Condition.Perfect, null);
        });
    }
}

