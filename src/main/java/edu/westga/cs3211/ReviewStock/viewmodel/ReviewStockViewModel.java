package edu.westga.cs3211.ReviewStock.viewmodel;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.inventory.InventoryService;
import edu.westga.cs3211.inventory.StockChange;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * ViewModel for the Review Stock page.
 * Retrieves stock change history and applies user-selected filters.
 *
 *@author ns00184
 * @version 2025
 */
public class ReviewStockViewModel {

    private final InventoryService inventoryService;

    private final ObservableList<StockChange> displayedChanges;

    private final ObjectProperty<User> crewFilter;
    private final ObjectProperty<SpecialQualities> specialFilter;
    private final ObjectProperty<LocalDate> startDateFilter;
    private final ObjectProperty<LocalDate> endDateFilter;

    private  Comparator<StockChange> SORT_BY_TIMESTAMP_DESC =
        Comparator.comparing(StockChange::getTimestamp).reversed();

    /**
     * Creates a new ViewModel for reviewing stock.
     */
    public ReviewStockViewModel() {
        this.inventoryService = InventoryService.getInstance();
        this.displayedChanges = FXCollections.observableArrayList();

        this.crewFilter = new SimpleObjectProperty<>(null);
        this.specialFilter = new SimpleObjectProperty<>(null);
        this.startDateFilter = new SimpleObjectProperty<>(null);
        this.endDateFilter = new SimpleObjectProperty<>(null);

        this.loadAllChanges();
    }

    /**
     * Returns the ObservableList representing the current filtered stock changes.
     *
     * @return displayed stock changes
     */
    public ObservableList<StockChange> getDisplayedChanges() {
        return this.displayedChanges;
    }

    public ObjectProperty<User> crewFilterProperty() { 
    	return this.crewFilter; }
    
    public ObjectProperty<SpecialQualities> specialFilterProperty() { 
    	return this.specialFilter; }
    
    public ObjectProperty<LocalDate> startDateFilterProperty() { 
    	return this.startDateFilter; }
    
    public ObjectProperty<LocalDate> endDateFilterProperty() { 
    	return this.endDateFilter; }

    /**
     * Loads all stock changes and sorts them newest → oldest.
     *
     * @postcondition displayedChanges contains all changes sorted by timestamp
     */
    public void loadAllChanges() {
        List<StockChange> sorted = this.inventoryService.getStockChanges()
            .stream()
            .sorted(SORT_BY_TIMESTAMP_DESC)
            .collect(Collectors.toList());

        this.displayedChanges.setAll(sorted);
    }

    /**
     * Applies all user-selected filters to the stock changes.
     *
     * Filters:
     * - User (crewFilter)
     * - SpecialQualities (specialFilter)
     * - Start date
     * - End date
     *
     * @postcondition displayedChanges contains only matching entries
     */
    public void applyFilters() {
        List<StockChange> filtered = this.inventoryService.getStockChanges();

        if (this.crewFilter.get() != null) {
            filtered = filtered.stream()
                .filter(change -> change.getUser().equals(this.crewFilter.get()))
                .collect(Collectors.toList());
        }

        if (this.specialFilter.get() != null) {
            filtered = filtered.stream()
                .filter(change -> change.getStock().getSpecialQuals() == this.specialFilter.get())
                .collect(Collectors.toList());
        }

        if (this.startDateFilter.get() != null) {
            filtered = filtered.stream()
                .filter(change -> change.getTimestamp().toLocalDate()
                        .isAfter(this.startDateFilter.get().minusDays(1)))
                .collect(Collectors.toList());
        }

        if (this.endDateFilter.get() != null) {
            filtered = filtered.stream()
                .filter(change -> change.getTimestamp().toLocalDate()
                        .isBefore(this.endDateFilter.get().plusDays(1)))
                .collect(Collectors.toList());
        }

        filtered = filtered.stream()
            .sorted(SORT_BY_TIMESTAMP_DESC)
            .collect(Collectors.toList());

        this.displayedChanges.setAll(filtered);
    }

    /**
     * Clears all filters and reloads the complete change list.
     *
     * @postcondition all filters reset; displayedChanges contains all changes
     */
    public void clearFilters() {
        this.crewFilter.set(null);
        this.specialFilter.set(null);
        this.startDateFilter.set(null);
        this.endDateFilter.set(null);

        this.loadAllChanges();
    }
}
