package model;

import com.example.hotel.AlertHelper;
import com.example.hotel.DbConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

public class Guest {
    private final SimpleIntegerProperty guestIDProperty;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private static ObservableList<Guest> originalGuestList;



    public Guest(int guestID, String firstName, String lastName, String email, String password) {
        this.guestIDProperty = new SimpleIntegerProperty(guestID);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public int getGuestID() {
        return guestIDProperty.get();
    }

    public static Guest getGuestById(int guestId) {
        ObservableList<Guest> allGuests = getAllGuests();

        for (Guest guest : allGuests) {
            if (guest.getGuestID() == guestId) {
                return guest;
            }
        }

        return null; // Guest with the given ID not found
    }

        @Override
        public String toString() {
            return getFirstName() + " " + getLastName();
        }



    public void setGuestID(int guestID) {
        this.guestIDProperty.set(guestID);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public static ObservableList<Guest> getAllGuests() {
        return DbConnection.getAllGuests(DbConnection.getConnection());
    }

    public static class GuestTable {

        private static TableView<Guest> guestTable;
        private static TableColumn<Guest, Integer> guestIdColumn;
        private static TableColumn<Guest, String> firstNameColumn;
        private static TableColumn<Guest, String> lastNameColumn;
        private static TableColumn<Guest, String> emailColumn;
        private static TableColumn<Guest, String> passwordColumn;
        private static ObservableList<Guest> originalGuestList;
        private static TableColumn<Guest, Void> actionsColumn;

        public static void initialize() {
            guestTable = new TableView<>();

            guestIdColumn = new TableColumn<>("Guest ID");
            guestIdColumn.setCellValueFactory(cellData -> cellData.getValue().guestIDProperty().asObject());

            firstNameColumn = new TableColumn<>("First Name");
            firstNameColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());

            lastNameColumn = new TableColumn<>("Last Name");
            lastNameColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

            emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(cellData -> cellData.getValue().emailProperty());

            passwordColumn = new TableColumn<>("Password");
            passwordColumn.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
            passwordColumn.setCellFactory(param -> new PasswordTableCell());

            guestTable.getColumns().addAll(guestIdColumn, firstNameColumn, lastNameColumn, emailColumn, passwordColumn);

            actionsColumn = new TableColumn<>("Actions");
            actionsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button modifyButton = new Button("Modify");
                private final Button detailsButton = new Button("Show Details");
                private final Button deleteButton = new Button("Delete");

                {
                    modifyButton.setOnAction(event -> {
                        Guest guest = getTableView().getItems().get(getIndex());
                        handleModification(guest);
                    });

                    detailsButton.setOnAction(event -> {
                        Guest guest = getTableView().getItems().get(getIndex());
                        showDetails(guest);
                    });

                    deleteButton.setOnAction(event -> {
                        Guest guest = getTableView().getItems().get(getIndex());
                        handleDeletion(guest);
                    });

                    modifyButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    modifyButton.setOnMouseEntered(e -> modifyButton.setCursor(Cursor.HAND));
                    modifyButton.setOnMouseExited(e -> modifyButton.setCursor(Cursor.DEFAULT));

                    detailsButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    detailsButton.setOnMouseEntered(e -> detailsButton.setCursor(Cursor.HAND));
                    detailsButton.setOnMouseExited(e -> detailsButton.setCursor(Cursor.DEFAULT));

                    deleteButton.setStyle("-fx-background-color: #ff0000; -fx-text-fill: #f0f0f0;");
                    deleteButton.setOnMouseEntered(e -> deleteButton.setCursor(Cursor.HAND));
                    deleteButton.setOnMouseExited(e -> deleteButton.setCursor(Cursor.DEFAULT));

                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        HBox buttonsBox = new HBox(modifyButton, detailsButton, deleteButton);
                        buttonsBox.setSpacing(5);
                        setGraphic(buttonsBox);
                    }
                }
            });

            guestTable.getColumns().add(actionsColumn);
        }


        // Other methods like handleModification, handleDeletion, showDetails, createGuestRow, etc.

        // Getter methods for columns and table
        public static TableView<Guest> getGuestTable() {
            return guestTable;
        }

        public static TableColumn<Guest, Integer> getGuestIdColumn() {
            return guestIdColumn;
        }

        public static TableColumn<Guest, String> getFirstNameColumn() {
            return firstNameColumn;
        }

        public static TableColumn<Guest, String> getLastNameColumn() {
            return lastNameColumn;
        }

        public static TableColumn<Guest, String> getEmailColumn() {
            return emailColumn;
        }

        public static TableColumn<Guest, String> getPasswordColumn() {
            return passwordColumn;
        }

        public static void filterGuests(String searchText) {
            if (originalGuestList == null) {
                originalGuestList = getAllGuests();
            }

            if (searchText == null || searchText.trim().isEmpty()) {
                getGuestTable().getItems().setAll(originalGuestList);
            } else {
                ObservableList<Guest> filteredList = originalGuestList.stream()
                        .filter(guest -> guest.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                                || guest.getLastName().toLowerCase().contains(searchText.toLowerCase())
                                || guest.getEmail().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                getGuestTable().getItems().setAll(filteredList);
            }
        }

    }

    private static class PasswordTableCell extends TableCell<Guest, String> {
        private final Button showPasswordButton = new Button("Show");

        public PasswordTableCell() {
            showPasswordButton.setOnAction(event -> showPassword());
        }

        private void showPassword() {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Guest Password");
            alert.setHeaderText(null);
            alert.setContentText("Password: " + getItem());

            alert.showAndWait();
        }

        @Override
        protected void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setGraphic(null);
            } else {
                setGraphic(showPasswordButton);
            }
        }
    }

    public void makeReservation(int numberOfGuests, Date date, String time, String reservationNotes) {
        // implement make reservation functionality
    }

    public void viewReservations() {
        // implement view reservations functionality
    }

    public void updateReservation(int reservationID, String newDate, String newTime) {
        // implement update reservation functionality
    }

    public void cancelReservation(int reservationID) {
        // implement cancel reservation functionality
    }

    // Property methods for JavaFX properties

    public SimpleIntegerProperty guestIDProperty() {
        return guestIDProperty;
    }

    // Other property methods for JavaFX properties
    public SimpleStringProperty firstNameProperty() {
        return new SimpleStringProperty(firstName);
    }

    public SimpleStringProperty lastNameProperty() {
        return new SimpleStringProperty(lastName);
    }

    public SimpleStringProperty emailProperty() {
        return new SimpleStringProperty(email);
    }

    public SimpleStringProperty passwordProperty() {
        return new SimpleStringProperty(password);
    }
    public static void handleModification(Guest guest) {
        Stage modifyStage = new Stage();
        modifyStage.initModality(Modality.APPLICATION_MODAL);
        modifyStage.setTitle("Modify Guest");

        GridPane gridPane = createGuestModification(guest, modifyStage);

        Scene scene = new Scene(gridPane, 400, 300);
        modifyStage.setScene(scene);

        modifyStage.showAndWait();
    }


    public static void handleDeletion(Guest guest) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Confirm Deletion");
        alert.setContentText("Are you sure you want to delete the guest: " + guest.getFirstName() + " " + guest.getLastName() + "?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            DbConnection.deleteGuest(DbConnection.getConnection(), guest);

            // Update originalGuestList after deletion
            originalGuestList = getAllGuests();

            GuestTable.getGuestTable().getItems().remove(guest);
            AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Guest deleted successfully!");
        }
    }




    public static void showDetails(Guest guest) {
        Stage detailsStage = new Stage();
        detailsStage.initModality(Modality.APPLICATION_MODAL);
        detailsStage.setTitle("Guest Details");

        GridPane gridPane = createGuestDetails(guest);

        Scene scene = new Scene(gridPane, 400, 200);
        detailsStage.setScene(scene);

        detailsStage.showAndWait();
    }

    private static GridPane createGuestModification(Guest guest, Stage modifyStage) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Modify Guest:"), 0, 0, 2, 1);
        gridPane.add(new Label("First Name:"), 0, 1);
        TextField firstNameField = new TextField(guest.getFirstName());
        gridPane.add(firstNameField, 1, 1);

        gridPane.add(new Label("Last Name:"), 0, 2);
        TextField lastNameField = new TextField(guest.getLastName());
        gridPane.add(lastNameField, 1, 2);

        gridPane.add(new Label("Email:"), 0, 3);
        TextField emailField = new TextField(guest.getEmail());
        gridPane.add(emailField, 1, 3);

        gridPane.add(new Label("Password:"), 0, 4);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Leave empty to keep the existing password");
        gridPane.add(passwordField, 1, 4);

        // Add more fields as needed

        Button submitButton = new Button("Submit");
        submitButton.setOnAction(e -> {
            String newFirstName = firstNameField.getText().trim();
            String newLastName = lastNameField.getText().trim();
            String newEmail = emailField.getText().trim();
            String newPassword = passwordField.getText().trim();

            if (newFirstName.isEmpty() || newLastName.isEmpty() || newEmail.isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, null, "Error", "All fields must be filled in.");
                return;
            }

            // Password validation
            if (!newPassword.isEmpty() && newPassword.length() < 8) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, null, "Error", "Password must be at least 8 characters long.");
                return;
            }

            if (!newEmail.equals(guest.getEmail()) && emailExists(newEmail)) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, null, "Error", "Email already exists. Please choose a different email.");
                return;
            }

            // Update the guest object with new values
            guest.setFirstName(newFirstName);
            guest.setLastName(newLastName);
            guest.setEmail(newEmail);

            if (!newPassword.isEmpty()) {
                guest.setPassword(newPassword);
            }

            // Update the guest in the database
            DbConnection.updateGuest(DbConnection.getConnection(), guest);

            // Close the modifyStage
            modifyStage.close();

            AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Guest modified successfully!");

            // Update guestTable or perform other necessary actions
            GuestTable.getGuestTable().getItems().setAll(DbConnection.getAllGuests(DbConnection.getConnection()));
        });

        gridPane.add(submitButton, 0, 5, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);

        return gridPane;
    }

    private static boolean emailExists(String newEmail) {
        // Iterate through existing guests and check if the email already exists
        for (Guest existingGuest : DbConnection.getAllGuests(DbConnection.getConnection())) {
            if (existingGuest.getEmail().equals(newEmail)) {
                return true;
            }
        }
        return false;
    }


    private static GridPane createGuestDetails(Guest guest) {
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        gridPane.add(new Label("Guest Details:"), 0, 0, 2, 1);
        gridPane.add(new Label("First Name:"), 0, 1);
        gridPane.add(new Label(guest.getFirstName()), 1, 1);

        gridPane.add(new Label("Last Name:"), 0, 2);
        gridPane.add(new Label(guest.getLastName()), 1, 2);

        gridPane.add(new Label("Email:"), 0, 3);
        gridPane.add(new Label(guest.getEmail()), 1, 3);

        // Add more labels for other guest details

        return gridPane;
    }


    public static void submitGuestDetails(String firstName, String lastName, String email, String password) {
        if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || password.isEmpty()) {
            AlertHelper.showAlert(Alert.AlertType.ERROR, null, "Error", "All fields must be filled in.");
            return;
        } else {
            Guest newGuest = new Guest(0, firstName, lastName, email, password);

            DbConnection.addGuest(DbConnection.getConnection(), newGuest);

            ObservableList<Guest> updatedGuestList = DbConnection.getAllGuests(DbConnection.getConnection());

            Guest.GuestTable.getGuestTable().setItems(updatedGuestList);

            AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Guest added successfully!");
        }
    }

    public static class GuestDetailsDialog extends Dialog<Pair<String, String>> {

        private TextField firstNameField;
        private TextField lastNameField;
        private TextField emailField; // Add email field
        private PasswordField passwordField; // Add password field

        private boolean isOKClicked = false;

        public GuestDetailsDialog() {
            setTitle("Guest Details");
            setHeaderText(null);

            ButtonType okButton = new ButtonType("OK", ButtonType.OK.getButtonData());
            ButtonType cancelButton = new ButtonType("Cancel", ButtonType.CANCEL.getButtonData());

            getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

            // Create the guest details form
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);

            firstNameField = new TextField();
            lastNameField = new TextField();
            emailField = new TextField(); // Initialize email field
            passwordField = new PasswordField(); // Initialize password field

            grid.add(new Label("First Name:"), 0, 0);
            grid.add(firstNameField, 1, 0);
            grid.add(new Label("Last Name:"), 0, 1);
            grid.add(lastNameField, 1, 1);
            grid.add(new Label("Email:"), 0, 2);
            grid.add(emailField, 1, 2);
            grid.add(new Label("Password:"), 0, 3);
            grid.add(passwordField, 1, 3);

            getDialogPane().setContent(grid);

            // Request focus on the first field by default.
            firstNameField.requestFocus();

            setResultConverter(dialogButton -> {
                if (dialogButton == okButton) {
                    return new Pair<>(firstNameField.getText(), lastNameField.getText());
                }
                return null;
            });

            // Set the OK button behavior
            Optional<Pair<String, String>> result = showAndWait();
            if (result.isPresent()) {
                isOKClicked = true;

                // Check for null fields and show alert
                if (firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
                        emailField.getText().isEmpty() || passwordField.getText().isEmpty()) {
                    AlertHelper.showAlert(Alert.AlertType.ERROR, null, "Error", "All fields must be filled in.");
                    isOKClicked = false;
                }
            }
        }

        public boolean isOKClicked() {
            return isOKClicked;
        }

        public Guest getGuest() {
            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            // You may add more validations here

            // Create a new guest object
            return new Guest(0, firstName, lastName, email, password);
        }
    }


}
