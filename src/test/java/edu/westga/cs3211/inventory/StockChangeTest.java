package edu.westga.cs3211.inventory;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.StorageCompartments;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.User.model.UserRole;

class StockChangeTest {

    @Test
    void testConstructorAndGettersWorkCorrectly() {
        User user = new User("Bob", "bob123", UserRole.Crew);

        Stock stock = new Stock(
                5,
                "Rope",
                SpecialQualities.NONE,
                Condition.PERFECT,
                LocalDate.now()
        );

        Compartment comp = new Compartment(
                new Stock(0, "Empty", SpecialQualities.NONE, Condition.PERFECT, LocalDate.now()),
                StorageCompartments.WOODBARREL,
                100
        );

        LocalDateTime timestamp = LocalDateTime.now();

        StockChange change = new StockChange(user, stock, comp, 95, timestamp);

        assertEquals(user, change.getUser());
        assertEquals(stock, change.getStock());
        assertEquals(comp, change.getCompartment());
        assertEquals(95, change.getRemainingCapacity());
        assertEquals(timestamp, change.getTimestamp());
    }

    @Test
    void testConstructorAllowsNullValues() {
        StockChange change = new StockChange(
                null,
                null,
                null,
                0,
                null
        );

        assertNull(change.getUser());
        assertNull(change.getStock());
        assertNull(change.getCompartment());
        assertEquals(0, change.getRemainingCapacity());
        assertNull(change.getTimestamp());
    }
}
