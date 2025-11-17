package edu.westga.cs3211.AddStock.viewmodel;

import java.time.LocalDate;

import edu.westga.cs3211.LandingPage.modal.Condition;
import edu.westga.cs3211.LandingPage.modal.Stock;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * ViewModel for the Add Stock page.
 */
public class AddStockViewModel {

    private StringProperty name;
    private StringProperty size;
    private StringProperty special;
    private ObjectProperty<Condition> condition;
    private ObjectProperty<LocalDate> expiration;

    private BooleanProperty canSubmit;

    /**
     * Creates a new AddStockViewModel.
     */
    public AddStockViewModel() {
        this.name = new SimpleStringProperty("");
        this.size = new SimpleStringProperty("");
        this.special = new SimpleStringProperty("");
        this.condition = new SimpleObjectProperty<>();
        this.expiration = new SimpleObjectProperty<>();

        this.canSubmit = new SimpleBooleanProperty(false);

        this.canSubmit.bind(
            this.name.isNotEmpty()
                .and(this.size.isNotEmpty())
                .and(this.condition.isNotNull())
                .and(this.expiration.isNotNull())
        );
    }

    public boolean submitStock() {
        try {
            int parsedSize = Integer.parseInt(this.size.get());

            new Stock(
                parsedSize,
                this.name.get(),
                this.special.get(),
                this.condition.get(),
                this.expiration.get()
            );

            // TODO: add stock to Inventory when you hook that up
            return true;
        } catch (NumberFormatException ex) {
            // size was not a valid int
            return false;
        } catch (IllegalArgumentException ex) {
            // one of the Stock preconditions failed
            return false;
        }
    }

    public StringProperty nameProperty() {
        return this.name;
    }

    public StringProperty sizeProperty() {
        return this.size;
    }

    public StringProperty specialProperty() {
        return this.special;
    }

    public ObjectProperty<Condition> conditionProperty() {
        return this.condition;
    }

    public ObjectProperty<LocalDate> expirationProperty() {
        return this.expiration;
    }

    public BooleanProperty canSubmitProperty() {
        return this.canSubmit;
    }
}

