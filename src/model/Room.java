package model;

import com.example.hotel.AlertHelper;
import com.example.hotel.DbConnection;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Room {
    private SimpleIntegerProperty roomNumber;
    private SimpleStringProperty roomType;
    private SimpleIntegerProperty capacity;
    private SimpleStringProperty amenities;
    private SimpleStringProperty availabilityStatus;
    private SimpleDoubleProperty pricePerNight;
    private int guestId; // New attribute to associate a guest with a room

    public Room(int roomNumber, String roomType, int capacity, List<String> amenities,
                String availabilityStatus, double pricePerNight, int guestId) {
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.roomType = new SimpleStringProperty(roomType);
        this.capacity = new SimpleIntegerProperty(capacity);
        this.amenities = new SimpleStringProperty(String.join(", ", amenities));
        this.availabilityStatus = new SimpleStringProperty(availabilityStatus);
        this.pricePerNight = new SimpleDoubleProperty(pricePerNight);
        this.guestId = guestId;
    }

    public int getRoomNumber() {
        return roomNumber.get();
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public String getRoomType() {
        return roomType.get();
    }

    public void setRoomType(String roomType) {
        this.roomType.set(roomType);
    }

    public int getCapacity() {
        return capacity.get();
    }

    public void setCapacity(int capacity) {
        this.capacity.set(capacity);
    }

    public String getAmenities() {
        return amenities.get();
    }

    public void setAmenities(String amenities) {
        this.amenities.set(amenities);
    }

    public String getAvailabilityStatus() {
        return availabilityStatus.get();
    }

    public void setAvailabilityStatus(String availabilityStatus) {
        this.availabilityStatus.set(availabilityStatus);
    }

    public double getPricePerNight() {
        return pricePerNight.get();
    }

    public void setPricePerNight(double pricePerNight) {
        this.pricePerNight.set(pricePerNight);
    }

    public int getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public SimpleIntegerProperty roomNumberProperty() {
        return roomNumber;
    }
    public SimpleIntegerProperty guestIdProperty() {
        return new SimpleIntegerProperty(guestId);
    }


    public SimpleStringProperty roomTypeProperty() {
        return roomType;
    }

    public SimpleIntegerProperty capacityProperty() {
        return capacity;
    }

    public SimpleStringProperty amenitiesProperty() {
        return amenities;
    }

    public SimpleStringProperty availabilityStatusProperty() {
        return availabilityStatus;
    }

    public SimpleDoubleProperty pricePerNightProperty() {
        return pricePerNight;
    }

    public void checkRoomAvailabilityAndBookRooms() {
        // Placeholder for check room availability and book rooms functionality
        // You may implement logic to check if the room is available and book it for a guest
    }

    public void manageRoomInventoryAndMaintenance() {
        // Placeholder for manage room inventory and maintenance functionality
        // You may implement logic to manage room inventory and maintenance tasks
    }

    public void adjustRoomRatesAndPromotions() {
        // Placeholder for adjust room rates and promotions functionality
        // You may implement logic to adjust room rates and promotions based on certain criteria
    }


    public class RoomTable {
        private static TableView<Room> roomTable;
        private static TableColumn<Room, Integer> roomNumberColumn;
        private static TableColumn<Room, String> roomTypeColumn;
        private static TableColumn<Room, Integer> capacityColumn;
        private static TableColumn<Room, String> amenitiesColumn;
        private static TableColumn<Room, String> availabilityStatusColumn;
        private static TableColumn<Room, Double> pricePerNightColumn;
        private static TableColumn<Room, Integer> guestIdColumn; // New column for displaying guest id
        private static TableColumn<Room, Void> actionsColumn;

        private static ObservableList<Room> originalRoomList;

        public static void initialize() {
            roomTable = new TableView<>();

            roomNumberColumn = new TableColumn<>("Room Number");
            roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());

            roomTypeColumn = new TableColumn<>("Room Type");
            roomTypeColumn.setCellValueFactory(cellData -> cellData.getValue().roomTypeProperty());

            capacityColumn = new TableColumn<>("Capacity");
            capacityColumn.setCellValueFactory(cellData -> cellData.getValue().capacityProperty().asObject());

            amenitiesColumn = new TableColumn<>("Amenities");
            amenitiesColumn.setCellValueFactory(cellData -> cellData.getValue().amenitiesProperty());

            availabilityStatusColumn = new TableColumn<>("Availability");
            availabilityStatusColumn.setCellValueFactory(cellData -> cellData.getValue().availabilityStatusProperty());

            pricePerNightColumn = new TableColumn<>("Price Per Night");
            pricePerNightColumn.setCellValueFactory(cellData -> cellData.getValue().pricePerNightProperty().asObject());

            guestIdColumn = new TableColumn<>("Guest ID"); // New column for displaying guest id
            guestIdColumn.setCellValueFactory(cellData -> cellData.getValue().guestIdProperty().asObject());

            actionsColumn = new TableColumn<>("Actions");
            actionsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button viewButton = new Button("View");
                private final Button bookButton = new Button("Book");
                private final Button clearButton = new Button("Clear");

                {
                    viewButton.setOnAction(event -> {
                        Room room = getTableView().getItems().get(getIndex());
                        showRoomDetails(room);
                    });

                    bookButton.setOnAction(event -> {
                        Room room = getTableView().getItems().get(getIndex());
                        bookRoom(room);
                    });

                    clearButton.setOnAction(event -> {
                        Room room = getTableView().getItems().get(getIndex());
                        clearRoom(room);
                    });

                    viewButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    viewButton.setOnMouseEntered(e -> viewButton.setCursor(javafx.scene.Cursor.HAND));
                    viewButton.setOnMouseExited(e -> viewButton.setCursor(javafx.scene.Cursor.DEFAULT));

                    bookButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    bookButton.setOnMouseEntered(e -> bookButton.setCursor(javafx.scene.Cursor.HAND));
                    bookButton.setOnMouseExited(e -> bookButton.setCursor(javafx.scene.Cursor.DEFAULT));

                    clearButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    clearButton.setOnMouseEntered(e -> clearButton.setCursor(javafx.scene.Cursor.HAND));
                    clearButton.setOnMouseExited(e -> clearButton.setCursor(javafx.scene.Cursor.DEFAULT));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Room room = getTableView().getItems().get(getIndex());

                        // Check if the room is available
                        boolean isRoomAvailable = room.getAvailabilityStatus().equalsIgnoreCase("Available");

                        // Set the "Book" button disabled if the room is not available
                        bookButton.setDisable(!isRoomAvailable);

                        // Set the "Clear" button disabled if the room is not available
                        clearButton.setDisable(!isRoomAvailable);

                        // Add only the "View," "Book," and "Clear" buttons to the HBox
                        HBox buttonsBox = new HBox(viewButton, bookButton, clearButton);
                        buttonsBox.setSpacing(5);
                        setGraphic(buttonsBox);
                    }
                }
                private void clearRoom(Room room) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Clear Room");
                    alert.setHeaderText("Clear Room " + room.getRoomNumber());
                    alert.setContentText("Are you sure you want to clear this room and set it as available?");

                    ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
                    ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

                    alert.getButtonTypes().setAll(yesButton, noButton);

                    Optional<ButtonType> result = alert.showAndWait();
                    if (result.isPresent() && result.get() == yesButton) {
                        // User clicked "Yes," clear the room
                        room.setAvailabilityStatus("Available");
                        room.setGuestId(null); // Set guest ID to NULL
                        DbConnection.updateRoom(DbConnection.getConnection(), room);

                        // Update the roomTable
                        originalRoomList = getAllRooms();
                        roomTable.setItems(originalRoomList);

                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Room cleared successfully!");
                    }
                }

            });

            roomTable.getColumns().addAll(roomNumberColumn, roomTypeColumn, capacityColumn,
                    amenitiesColumn, availabilityStatusColumn, pricePerNightColumn, guestIdColumn, actionsColumn);

            originalRoomList = getAllRooms();

            roomTable.setItems(originalRoomList);
        }

        public static TableView<Room> getRoomTable() {
            return roomTable;
        }

        public static TableColumn<Room, Integer> getRoomNumberColumn() {
            return roomNumberColumn;
        }

        public static TableColumn<Room, String> getRoomTypeColumn() {
            return roomTypeColumn;
        }

        public static TableColumn<Room, Integer> getCapacityColumn() {
            return capacityColumn;
        }

        public static TableColumn<Room, String> getAmenitiesColumn() {
            return amenitiesColumn;
        }

        public static TableColumn<Room, String> getAvailabilityStatusColumn() {
            return availabilityStatusColumn;
        }

        public static TableColumn<Room, Double> getPricePerNightColumn() {
            return pricePerNightColumn;
        }
        public static TableColumn<Room, Integer> getGuestIdColumn() {
            return guestIdColumn;
        }

        public static TableColumn<Room, Void> getActionsColumn() {
            return actionsColumn;
        }

        public static void filterRooms(String searchText) {
            if (originalRoomList == null) {
                originalRoomList = getAllRooms();
            }

            if (searchText == null || searchText.trim().isEmpty()) {
                getRoomTable().getItems().setAll(originalRoomList);
            } else {
                ObservableList<Room> filteredList = originalRoomList.stream()
                        .filter(room -> String.valueOf(room.getRoomNumber()).contains(searchText)
                                || room.getRoomType().toLowerCase().contains(searchText.toLowerCase())
                                || String.valueOf(room.getCapacity()).contains(searchText)
                                || room.getAmenities().toLowerCase().contains(searchText.toLowerCase())
                                || room.getAvailabilityStatus().toLowerCase().contains(searchText.toLowerCase())
                                || String.valueOf(room.getPricePerNight()).contains(searchText))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                getRoomTable().getItems().setAll(filteredList);
            }
        }
        public static ObservableList<Room> getAllRooms() {
            return DbConnection.getAllRooms(DbConnection.getConnection());
        }

        private static void showRoomDetails(Room room) {
            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Room Details");

            GridPane gridPane = createRoomDetails(room);

            Scene scene = new Scene(gridPane, 400, 200);
            detailsStage.setScene(scene);

            detailsStage.showAndWait();
        }

        private static Guest selectedGuest; // Declare selectedGuest at a higher scope

        private static void bookRoom(Room room) {
            // Create a DatePicker for check-in and check-out dates
            DatePicker checkInDatePicker = new DatePicker();
            DatePicker checkOutDatePicker = new DatePicker();

            // Set the current date as the minimum allowed date
            checkInDatePicker.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    setDisable(empty || date.isBefore(LocalDate.now()));
                }
            });

            // Allow only dates after the check-in date for the check-out date
            checkOutDatePicker.setDayCellFactory(picker -> new DateCell() {
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);
                    LocalDate checkInDate = checkInDatePicker.getValue();
                    setDisable(empty || date.isBefore(checkInDate));
                }
            });

            Label selectedClientLabel = new Label("Selected Client: None");
            // Create a VBox to hold the booking form
            VBox bookingFormVBox = new VBox(
                    new Label("Room Details: " + room.getRoomNumber()),
                    new Label("Room Type: " + room.getRoomType()),
                    new Label("Capacity: " + room.getCapacity()),
                    new Label("Amenities: " + room.getAmenities()),
                    new Label("Price Per Night: $" + room.getPricePerNight()),
                    new Separator(),
                    new Label("Select Check-In and Check-Out Dates:"),
                    new HBox(new Label("Check-In Date:"), checkInDatePicker),
                    new HBox(new Label("Check-Out Date:"), checkOutDatePicker),
                    selectedClientLabel
            );
            bookingFormVBox.setSpacing(10);

            // Create the Search/Add Client button
            Button searchAddClientButton = createSearchAddClientButton(room, selectedClientLabel);

            // Add the button to the VBox
            bookingFormVBox.getChildren().add(searchAddClientButton);

            // Display a dialog with the booking form
            Dialog<ButtonType> bookingDialog = new Dialog<>();
            bookingDialog.setTitle("Book Room");
            bookingDialog.getDialogPane().setContent(bookingFormVBox);
            bookingDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

            // Show the dialog and wait for the user's response
            Optional<ButtonType> result = bookingDialog.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                LocalDate checkInDate = checkInDatePicker.getValue();
                LocalDate checkOutDate = checkOutDatePicker.getValue();

                // Check if valid dates are selected
                if (checkInDate != null && checkOutDate != null && checkInDate.isBefore(checkOutDate)) {
                    // Proceed with booking

                    // Check if there are existing clients
                    ObservableList<Guest> existingClients = DbConnection.getAllGuests(DbConnection.getConnection());

                    if (!existingClients.isEmpty() && selectedGuest != null) {

                        room.setGuestId(selectedGuest.getGuestID());
                        room.setAvailabilityStatus("Booked");

                        // Create a new reservation
                        Reservation reservation = new Reservation();
                        reservation.setReferenceNumber(Reservation.ReservationGenerator.generateUniqueReservationReference());
                        reservation.setRoomNumber(room.getRoomNumber());
                        reservation.setCheckinDate(Date.valueOf(checkInDate));
                        reservation.setCheckoutDate(Date.valueOf(checkOutDate));

                        // Set other reservation details as needed

                        // Add the reservation to the database
                        DbConnection.addReservation(DbConnection.getConnection(), reservation);

                        // Update the roomTable
                        RoomTable.getAllRooms().forEach(r -> {
                            if (r.getRoomNumber() == room.getRoomNumber()) {
                                r.setGuestId(selectedGuest.getGuestID());
                                r.setAvailabilityStatus("Booked");
                            }
                        });

                        // Refresh the roomTable and update the selectedClientLabel
                        RoomTable.getRoomTable().refresh();
                        selectedClientLabel.setText("Selected Client: " + selectedGuest.getFirstName() + " " + selectedGuest.getLastName());

                        AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Room booked successfully!");
                    } else {
                        // No existing clients, prompt the user to add a new client
                        boolean addNewClient = AlertHelper.showConfirmationAlert("No Existing Clients",
                                "There are no existing clients. Do you want to add a new client?");

                        if (addNewClient) {
                            // Call the method to add a new client
                            Guest.GuestDetailsDialog guestDetailsDialog = new Guest.GuestDetailsDialog();
                            guestDetailsDialog.showAndWait();

                            if (guestDetailsDialog.isOKClicked()) {
                                // New client is added, get the new client
                                Guest newGuest = guestDetailsDialog.getGuest();

                                // Update the room information
                                room.setGuestId(newGuest.getGuestID());
                                room.setAvailabilityStatus("Booked");

                                // Create a new reservation
                                // Proceed with booking
                                Reservation reservation = new Reservation();
                                reservation.setReferenceNumber(Reservation.ReservationGenerator.generateUniqueReservationReference());
                                reservation.setRoomNumber(room.getRoomNumber());
                                reservation.setCheckinDate(Date.valueOf(checkInDate));
                                reservation.setCheckoutDate(Date.valueOf(checkOutDate));

                                // Set other reservation details as needed

                                // Add the reservation to the database
                                DbConnection.addReservation(DbConnection.getConnection(), reservation);

                                // Update the roomTable
                                RoomTable.getAllRooms().forEach(r -> {
                                    if (r.getRoomNumber() == room.getRoomNumber()) {
                                        r.setGuestId(selectedGuest.getGuestID());
                                        r.setAvailabilityStatus("Booked");
                                    }
                                });

                                // Refresh the roomTable and update the selectedClientLabel
                                RoomTable.getRoomTable().refresh();
                                selectedClientLabel.setText("Selected Client: " + selectedGuest.getFirstName() + " " + selectedGuest.getLastName());

                                AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Room booked successfully!");

                            }
                        }
                    }
                } else {
                    // Invalid dates selected, show a message
                    AlertHelper.showAlert(Alert.AlertType.WARNING, null, "Warning", "Invalid check-in or check-out date.");
                }
            }
        }

        // Create the method to generate the Search/Add Client button
        private static Button createSearchAddClientButton(Room room, Label selectedClientLabel) {
            Button searchAddClientButton = new Button("Search/Add Client");
            searchAddClientButton.setOnAction(event -> {
                // Show a dialog to search for or add a new client
                selectedGuest = showGuestSelectionDialog(
                        DbConnection.getAllGuests(DbConnection.getConnection()), selectedClientLabel
                );

                if (selectedGuest != null) {
                    // Set the selected client's ID in the room details
                    room.setGuestId(selectedGuest.getGuestID());
                }
            });

            return searchAddClientButton;
        }





        public static Guest showGuestSelectionDialog(List<Guest> existingClients, Label selectedClientLabel) {
            Dialog<Guest> guestSelectionDialog = new Dialog<>();
            guestSelectionDialog.setTitle("Select or Add Guest");
            guestSelectionDialog.setHeaderText("Choose an existing guest or add a new guest:");

            // Create the content of the dialog (you may customize this based on your needs)
            VBox content = new VBox();
            content.setSpacing(10);

            // Create a combo box for existing guests
            ComboBox<Guest> guestComboBox = new ComboBox<>();
            guestComboBox.setPromptText("Select existing guest");
            guestComboBox.setItems(FXCollections.observableArrayList(existingClients));
            content.getChildren().add(guestComboBox);

            // Create a button to add a new guest
            Button addGuestButton = new Button("Add New Guest");
            addGuestButton.setOnAction(event -> {
                // Call the add guest method here
                Guest.GuestDetailsDialog guestDetailsDialog = new Guest.GuestDetailsDialog();
                guestDetailsDialog.showAndWait();

                // Check if the user clicked OK in the dialog
                if (guestDetailsDialog.isOKClicked()) {
                    Guest newGuest = guestDetailsDialog.getGuest();
                    guestComboBox.getItems().add(newGuest);
                    guestComboBox.setValue(newGuest);
                    updateSelectedClientLabel(selectedClientLabel, newGuest);
                }
            });
            content.getChildren().add(addGuestButton);

            guestSelectionDialog.getDialogPane().setContent(content);

            // Add OK and Cancel buttons to the dialog
            ButtonType okButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
            guestSelectionDialog.getDialogPane().getButtonTypes().addAll(okButtonType, ButtonType.CANCEL);

            // Set the result converter for the dialog
            guestSelectionDialog.setResultConverter(dialogButton -> {
                if (dialogButton == okButtonType) {
                    // Return the selected guest or null if none is selected
                    Guest selectedGuest = guestComboBox.getValue();
                    updateSelectedClientLabel(selectedClientLabel, selectedGuest);
                    return selectedGuest;
                }
                return null;
            });

            // Show the dialog and handle the result
            Optional<Guest> result = AlertHelper.showCustomDialog("Select or Add Guest", content, guestSelectionDialog);

            // Check if a guest was selected
            result.ifPresent(selectedGuest -> {
                // Do something with the selected guest, e.g., update the room
                System.out.println("Selected Guest: " + selectedGuest.getFirstName() + " " + selectedGuest.getLastName());
            });
            return result.orElse(null);
        }

        private static void updateSelectedClientLabel(Label selectedClientLabel, Guest guest) {
            if (guest != null) {
                selectedClientLabel.setText("Selected Client: " + guest.getFirstName() + " " + guest.getLastName());
            } else {
                selectedClientLabel.setText("Selected Client: None");
            }
        }





        private static GridPane createRoomDetails(Room room) {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.add(new Label("Room Details:"), 0, 0, 2, 1);
            gridPane.add(new Label("Room Number:"), 0, 1);
            gridPane.add(new Label(String.valueOf(room.getRoomNumber())), 1, 1);

            gridPane.add(new Label("Room Type:"), 0, 2);
            gridPane.add(new Label(room.getRoomType()), 1, 2);

            gridPane.add(new Label("Capacity:"), 0, 3);
            gridPane.add(new Label(String.valueOf(room.getCapacity())), 1, 3);

            gridPane.add(new Label("Amenities:"), 0, 4);
            gridPane.add(new Label(room.getAmenities()), 1, 4);

            gridPane.add(new Label("Availability:"), 0, 5);
            gridPane.add(new Label(room.getAvailabilityStatus()), 1, 5);

            gridPane.add(new Label("Price Per Night:"), 0, 6);
            gridPane.add(new Label(String.valueOf(room.getPricePerNight())), 1, 6);

            // Fetch guest details based on the guest ID
            int guestId = room.getGuestId();
            Guest guest = Guest.getGuestById(guestId);

            if (guest != null) {
                gridPane.add(new Label("Guest Name:"), 0, 7);
                gridPane.add(new Label(guest.getFirstName() + " " + guest.getLastName()), 1, 7);
            }

            // Add more labels for other room details

            return gridPane;
        }

    }
}
