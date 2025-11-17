package edu.westga.cs3211.LandingPage.viewmodel;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.westga.cs3211.User.model.UserRole;

class LandingPageViewModelTest {

    @Test
    void testConstructorInitializesProperties() {
        LandingPageViewModel vm = new LandingPageViewModel();

        assertNotNull(vm.greetingProperty());
        assertNotNull(vm.viewStockVisibleProperty());

        assertEquals("", vm.greetingProperty().get());
        assertFalse(vm.viewStockVisibleProperty().get());
    }

    @Test
    void testInitializeSetsGreeting() {
        LandingPageViewModel vm = new LandingPageViewModel();

        vm.initialize("Alice", UserRole.Crew);

        assertEquals("Hello, Alice!", vm.greetingProperty().get());
    }

    @Test
    void testInitializeMakesStockVisibleForQuartermaster() {
        LandingPageViewModel vm = new LandingPageViewModel();

        vm.initialize("Bob", UserRole.Quartermaster);

        assertTrue(vm.viewStockVisibleProperty().get());
    }

    @Test
    void testInitializeHidesStockForCrewmate() {
        LandingPageViewModel vm = new LandingPageViewModel();

        vm.initialize("Charlie", UserRole.Crew);

        assertFalse(vm.viewStockVisibleProperty().get());
    }
}
