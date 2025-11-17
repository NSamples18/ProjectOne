package edu.westga.cs3211.LandingPage.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class StockTest {

    @Test
    void testConstructorCreatesStockCorrectly() {
        LocalDate exp = LocalDate.of(2030, 1, 1);
        Stock s = new Stock(
                10,
                "Rope",
                SpecialQualities.NONE,
                Condition.PERFECT,
                exp
        );

        assertEquals(10, s.getSize());
        assertEquals("Rope", s.getName());
        assertEquals(SpecialQualities.NONE, s.getSpecialQuals());
        assertEquals(Condition.PERFECT, s.getCondition());
        assertEquals(exp, s.getExpirationDate());
    }

    @Test
    void testConstructorThrowsWhenNameNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Stock(5, null, SpecialQualities.NONE, Condition.USABLE, LocalDate.now())
        );
    }

    @Test
    void testConstructorThrowsWhenSpecialQualsNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Stock(5, "Rope", null, Condition.USABLE, LocalDate.now())
        );
    }

    @Test
    void testConstructorThrowsWhenConditionNull() {
        assertThrows(IllegalArgumentException.class, () -> 
            new Stock(5, "Rope", SpecialQualities.NONE, null, LocalDate.now())
        );
    }

    @Test
    void testConstructorAllowsNullExpirationDate() {
        Stock s = new Stock(
                2,
                "Milk",
                SpecialQualities.PERISHABLE,
                Condition.USABLE,
                null   // allowed
        );

        assertNull(s.getExpirationDate());
    }

    @Test
    void testGettersReturnCorrectValues() {
        LocalDate exp = LocalDate.now().plusDays(10);

        Stock s = new Stock(
                7,
                "Water",
                SpecialQualities.LIQUID,
                Condition.PERFECT,
                exp
        );

        assertEquals(7, s.getSize());
        assertEquals("Water", s.getName());
        assertEquals(SpecialQualities.LIQUID, s.getSpecialQuals());
        assertEquals(Condition.PERFECT, s.getCondition());
        assertEquals(exp, s.getExpirationDate());
    }
}
