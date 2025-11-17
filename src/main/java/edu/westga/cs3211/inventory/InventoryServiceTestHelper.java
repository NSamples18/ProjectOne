package edu.westga.cs3211.inventory;

public class InventoryServiceTestHelper {

    public static void resetService() {
        InventoryService.getInstance().getStockChanges().clear();
    }
}
