package edu.westga.cs3211.AddStock.viewmodel;

import java.time.LocalDate;
import java.util.List;

import edu.westga.cs3211.LandingPage.model.Compartment;
import edu.westga.cs3211.LandingPage.model.Condition;
import edu.westga.cs3211.LandingPage.model.SpecialQualities;
import edu.westga.cs3211.LandingPage.model.Stock;
import edu.westga.cs3211.User.model.User;
import edu.westga.cs3211.inventory.InventoryService;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel for the Add Stock page.
 * @author ns00184
 * @version 2025
 */
public class AddStockViewModel {

    private final InventoryService inventoryService;
    private final StringProperty name;
    private final StringProperty size;
    private final ObjectProperty<SpecialQualities> special;
    private final ObjectProperty<Condition> condition;
    private final ObjectProperty<LocalDate> expiration;
    private final BooleanProperty canSubmit;
    private User currentUser;

    /**
     * Creates a new AddStockViewModel.
     */
    public AddStockViewModel() {
        this.inventoryService = InventoryService.getInstance();
        this.name = new SimpleStringProperty("");
        this.size = new SimpleStringProperty("");
        this.special = new SimpleObjectProperty<>();
        this.condition = new SimpleObjectProperty<>();
        this.expiration = new SimpleObjectProperty<>();
        this.canSubmit = new SimpleBooleanProperty(false);
        this.canSubmit.bind(
        	    this.name.isNotEmpty()
        	        .and(this.size.isNotEmpty())
        	        .and(this.special.isNotNull())
        	        .and(this.condition.isNotNull())
        	        .and(this.special.isNotEqualTo(SpecialQualities.PERISHABLE)
        	                .or(this.expiration.isNotNull())
        	        )
        	);
    }

    /**
     * Sets the user performing the add stock operation.
     *
     * @param user the current user
     */
    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    /**
     * Returns the name property for binding.
     *
     * @return the name property
     */
    public StringProperty nameProperty() {
        return this.name;
    }

    /**
     * Returns the size property for binding.
     *
     * @return the size property
     */
    public StringProperty sizeProperty() {
        return this.size;
    }

    /**
     * Returns the special quality property for binding.
     *
     * @return the special quality property
     */
    public ObjectProperty<SpecialQualities> specialProperty() {
        return this.special;
    }

    /**
     * Returns the condition property for binding.
     *
     * @return the condition property
     */
    public ObjectProperty<Condition> conditionProperty() {
        return this.condition;
    }

    /**
     * Returns the expiration date property for binding.
     *
     * @return the expiration date property
     */
    public ObjectProperty<LocalDate> expirationProperty() {
        return this.expiration;
    }

    /**
     * Returns the property indicating whether the submit button should be enabled.
     *
     * @return the can-submit property
     */
    public BooleanProperty canSubmitProperty() {
        return this.canSubmit;
    }

    /**
     * Constructs a Stock object based on current field values.
     *
     * @return the created Stock object or null if validation fails
     */
    public Stock buildStockOrNull() {
        try {
            int parsedSize = Integer.parseInt(this.size.get());

            LocalDate expirationDate = this.expiration.get();

            if (this.special.get() == SpecialQualities.PERISHABLE
                    && this.expiration.get() == null) {
                return null;
            }

             expirationDate = this.expiration.get();

            return new Stock(
                parsedSize,
                this.name.get(),
                this.special.get(),
                this.condition.get(),
                expirationDate
            );
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * Finds the list of compartments capable of storing the specified stock.
     *
     * @param stock the stock to evaluate
     * @return a list of compatible compartments
     */
    public List<Compartment> findCompatibleCompartments(Stock stock) {
        return this.inventoryService.findCompatibleCompartments(stock);
    }

    /**
     * Adds the specified stock to the given compartment and logs the operation.
     *
     * @param stock the stock to add
     * @param compartment the selected compartment
     */
    public void addStockToCompartment(Stock stock, Compartment compartment) {
        this.inventoryService.addStockToCompartment(this.currentUser, stock, compartment);
    }
}
