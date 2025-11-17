//package edu.westga.cs3211.LandingPage;
//
//import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Test;
//
//import edu.westga.cs3211.LandingPage.viewmodel.LandingPageViewModel;
//
//class LandingPageViewModelTests {
//
//    @Test
//    void testInitialValues() {
//        LandingPageViewModel vm = new LandingPageViewModel();
//
//        assertEquals("Hello, World!", vm.greetingProperty().get());
//        assertEquals("", vm.nameProperty().get());
//        assertEquals("", vm.allNamesProperty().get());
//    }
//
//    @Test
//    void testSayGreetingDoesNothingWhenNameEmpty() {
//        LandingPageViewModel vm = new LandingPageViewModel();
//
//        vm.nameProperty().set("");
//        vm.sayGreeting();
//
//        assertEquals("Hello, World!", vm.greetingProperty().get());
//        assertEquals("", vm.allNamesProperty().get());
//    }
//
//    @Test
//    void testSayGreetingWithFirstName() {
//        LandingPageViewModel vm = new LandingPageViewModel();
//
//        vm.nameProperty().set("Bob");
//        vm.sayGreeting();
//
//        assertEquals("Hello! ", vm.greetingProperty().get());
//        assertEquals("Bob", vm.allNamesProperty().get());
//        assertEquals("", vm.nameProperty().get());
//    }
//
//    @Test
//    void testSayGreetingAppendsMultipleNames() {
//        LandingPageViewModel vm = new LandingPageViewModel();
//
//        vm.nameProperty().set("Bob");
//        vm.sayGreeting();
//
//        vm.nameProperty().set("Alice");
//        vm.sayGreeting();
//
//        String expected = "Bob" + System.lineSeparator() + "Alice";
//        assertEquals(expected, vm.allNamesProperty().get());
//    }
//}
