package model;

import com.example.hotel.AlertHelper;
import com.example.hotel.DbConnection;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Reservation {
    private SimpleIntegerProperty reservationId;
    private SimpleStringProperty referenceNumber;
    private SimpleIntegerProperty roomNumber;
    private SimpleObjectProperty<Date> checkinDate;
    private SimpleObjectProperty<Date> checkoutDate;
    private SimpleIntegerProperty guestId;
    private SimpleIntegerProperty numberOfGuests;
    private SimpleStringProperty reservationStatus;

    public Reservation(int reservationId, String referenceNumber, int roomNumber, Date checkinDate, Date checkoutDate,
                       int guestId, int numberOfGuests, String reservationStatus) {
        this.reservationId = new SimpleIntegerProperty(reservationId);
        this.referenceNumber = new SimpleStringProperty(ReservationGenerator.generateUniqueReservationReference());
        this.roomNumber = new SimpleIntegerProperty(roomNumber);
        this.checkinDate = new SimpleObjectProperty<>(checkinDate);
        this.checkoutDate = new SimpleObjectProperty<>(checkoutDate);
        this.guestId = new SimpleIntegerProperty(guestId);
        this.numberOfGuests = new SimpleIntegerProperty(numberOfGuests);
        this.reservationStatus = new SimpleStringProperty(reservationStatus);
    }

    // Default constructor without parameters
    public Reservation() {
        // Provide default values or leave fields uninitialized
    }

    public int getReservationId() {
        return reservationId.get();
    }

    public void setReservationId(int reservationId) {
        this.reservationId.set(reservationId);
    }

    public String getReferenceNumber() {
        return referenceNumber.get();
    }

    public void setReferenceNumber(String referenceNumber) {
        this.referenceNumber.set(referenceNumber);
    }

    public int getRoomNumber() {
        return roomNumber.get();
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber.set(roomNumber);
    }

    public Date getCheckinDate() {
        return checkinDate.get();
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate.set(checkinDate);
    }

    public Date getCheckoutDate() {
        return checkoutDate.get();
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate.set(checkoutDate);
    }

    public int getGuestId() {
        return guestId.get();
    }

    public void setGuestId(int guestId) {
        this.guestId.set(guestId);
    }

    public int getNumberOfGuests() {
        return numberOfGuests.get();
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests.set(numberOfGuests);
    }

    public String getReservationStatus() {
        return reservationStatus.get();
    }

    public void setReservationStatus(String reservationStatus) {
        this.reservationStatus.set(reservationStatus);
    }

    public SimpleIntegerProperty reservationIdProperty() {
        return reservationId;
    }

    public SimpleStringProperty referenceNumberProperty() {
        return referenceNumber;
    }

    public SimpleIntegerProperty roomNumberProperty() {
        return roomNumber;
    }

    public SimpleObjectProperty<Date> checkinDateProperty() {
        return checkinDate;
    }

    public SimpleObjectProperty<Date> checkoutDateProperty() {
        return checkoutDate;
    }

    public SimpleIntegerProperty guestIdProperty() {
        return guestId;
    }

    public SimpleIntegerProperty numberOfGuestsProperty() {
        return numberOfGuests;
    }

    public SimpleStringProperty reservationStatusProperty() {
        return reservationStatus;
    }

    public void manageReservation() {
        // Placeholder for manage reservation functionality
        // You may implement logic to manage reservation details
    }

    public void cancelReservation() {
        // Placeholder for cancel reservation functionality
        // You may implement logic to cancel a reservation
    }

    public void viewReservationDetails() {
        // Placeholder for view reservation details functionality
        // You may implement logic to display reservation details
    }


    public static class ReservationGenerator {
        private static int counter = 0;
        private static Map<String, Boolean> referenceNumberMap = new HashMap<>();

        public static String generateUniqueReservationReference() {
            // Use timestamp and a counter to generate a unique reference number
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
            String timestamp = dateFormat.format(new Date(System.currentTimeMillis()));
            String referenceNumber = "RES" + timestamp + counter;

            // Ensure uniqueness using the HashMap
            while (referenceNumberMap.containsKey(referenceNumber)) {
                counter++;
                referenceNumber = "RES" + timestamp + counter;
            }

            // Mark the generated reference number as used
            referenceNumberMap.put(referenceNumber, true);
            counter++;

            return referenceNumber;
        }
    }



    public class ReservationTable {
        private static TableView<Reservation> reservationTable;
        private static TableColumn<Reservation, Integer> reservationIdColumn;
        private static TableColumn<Reservation, String> referenceNumberColumn;
        private static TableColumn<Reservation, Integer> roomNumberColumn;
        private static TableColumn<Reservation, Date> checkinDateColumn;
        private static TableColumn<Reservation, Date> checkoutDateColumn;
        private static TableColumn<Reservation, Integer> guestIdColumn;
        private static TableColumn<Reservation, Integer> numberOfGuestsColumn;
        private static TableColumn<Reservation, String> reservationStatusColumn;
        private static TableColumn<Reservation, Void> actionsColumn;

        private static ObservableList<Reservation> originalReservationList;

        public static void initialize() {
            reservationTable = new TableView<>();

            reservationIdColumn = new TableColumn<>("Reservation ID");
            reservationIdColumn.setCellValueFactory(cellData -> cellData.getValue().reservationIdProperty().asObject());

            referenceNumberColumn = new TableColumn<>("Reference Number");
            referenceNumberColumn.setCellValueFactory(cellData -> cellData.getValue().referenceNumberProperty());

            roomNumberColumn = new TableColumn<>("Room Number");
            roomNumberColumn.setCellValueFactory(cellData -> cellData.getValue().roomNumberProperty().asObject());

            checkinDateColumn = new TableColumn<>("Check-In Date");
            checkinDateColumn.setCellValueFactory(cellData -> cellData.getValue().checkinDateProperty());

            checkoutDateColumn = new TableColumn<>("Check-Out Date");
            checkoutDateColumn.setCellValueFactory(cellData -> cellData.getValue().checkoutDateProperty());

            guestIdColumn = new TableColumn<>("Guest ID");
            guestIdColumn.setCellValueFactory(cellData -> cellData.getValue().guestIdProperty().asObject());

            numberOfGuestsColumn = new TableColumn<>("Number of Guests");
            numberOfGuestsColumn.setCellValueFactory(cellData -> cellData.getValue().numberOfGuestsProperty().asObject());

            reservationStatusColumn = new TableColumn<>("Reservation Status");
            reservationStatusColumn.setCellValueFactory(cellData -> cellData.getValue().reservationStatusProperty());

            actionsColumn = new TableColumn<>("Actions");
            actionsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button viewButton = new Button("View");
                private final Button manageButton = new Button("Manage");
                private final Button cancelButton = new Button("Cancel");

                {
                    viewButton.setOnAction(event -> {
                        Reservation reservation = getTableView().getItems().get(getIndex());
                        viewReservationDetails(reservation);
                    });

                    manageButton.setOnAction(event -> {
                        Reservation reservation = getTableView().getItems().get(getIndex());
                        manageReservation(reservation);
                    });

                    cancelButton.setOnAction(event -> {
                        Reservation reservation = getTableView().getItems().get(getIndex());
                        cancelReservation(reservation);
                    });

                    viewButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    viewButton.setOnMouseEntered(e -> viewButton.setCursor(javafx.scene.Cursor.HAND));
                    viewButton.setOnMouseExited(e -> viewButton.setCursor(javafx.scene.Cursor.DEFAULT));

                    manageButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    manageButton.setOnMouseEntered(e -> manageButton.setCursor(javafx.scene.Cursor.HAND));
                    manageButton.setOnMouseExited(e -> manageButton.setCursor(javafx.scene.Cursor.DEFAULT));

                    cancelButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0;");
                    cancelButton.setOnMouseEntered(e -> cancelButton.setCursor(javafx.scene.Cursor.HAND));
                    cancelButton.setOnMouseExited(e -> cancelButton.setCursor(javafx.scene.Cursor.DEFAULT));
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Reservation reservation = getTableView().getItems().get(getIndex());
                        boolean isCancelable = reservation.getReservationStatus().equalsIgnoreCase("Confirmed");

                        cancelButton.setDisable(!isCancelable);

                        HBox buttonsBox = new HBox(viewButton, manageButton, cancelButton);
                        buttonsBox.setSpacing(5);
                        setGraphic(buttonsBox);
                    }
                }
            });

            reservationTable.getColumns().addAll(reservationIdColumn, referenceNumberColumn, roomNumberColumn,
                    checkinDateColumn, checkoutDateColumn, guestIdColumn, numberOfGuestsColumn,
                    reservationStatusColumn, actionsColumn);

            originalReservationList = getAllReservations();

            reservationTable.setItems(originalReservationList);
        }

        public static TableView<Reservation> getReservationTable() {
            return reservationTable;
        }

        public static TableColumn<Reservation, Integer> getReservationIdColumn() {
            return reservationIdColumn;
        }

        public static TableColumn<Reservation, String> getReferenceNumberColumn() {
            return referenceNumberColumn;
        }

        public static TableColumn<Reservation, Integer> getRoomNumberColumn() {
            return roomNumberColumn;
        }

        public static TableColumn<Reservation, Date> getCheckinDateColumn() {
            return checkinDateColumn;
        }

        public static TableColumn<Reservation, Date> getCheckoutDateColumn() {
            return checkoutDateColumn;
        }

        public static TableColumn<Reservation, Integer> getGuestIdColumn() {
            return guestIdColumn;
        }

        public static TableColumn<Reservation, Integer> getNumberOfGuestsColumn() {
            return numberOfGuestsColumn;
        }

        public static TableColumn<Reservation, String> getReservationStatusColumn() {
            return reservationStatusColumn;
        }

        public static TableColumn<Reservation, Void> getActionsColumn() {
            return actionsColumn;
        }

        private static GridPane createReservationDetails(Reservation reservation) {
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.add(new Label("Reservation Details:"), 0, 0, 2, 1);
            gridPane.add(new Label("Reservation ID:"), 0, 1);
            gridPane.add(new Label(String.valueOf(reservation.getReservationId())), 1, 1);

            gridPane.add(new Label("Reference Number:"), 0, 2);
            gridPane.add(new Label(reservation.getReferenceNumber()), 1, 2);

            gridPane.add(new Label("Room Number:"), 0, 3);
            gridPane.add(new Label(String.valueOf(reservation.getRoomNumber())), 1, 3);

            gridPane.add(new Label("Check-In Date:"), 0, 4);
            gridPane.add(new Label(reservation.getCheckinDate().toString()), 1, 4);

            gridPane.add(new Label("Check-Out Date:"), 0, 5);
            gridPane.add(new Label(reservation.getCheckoutDate().toString()), 1, 5);

            gridPane.add(new Label("Guest ID:"), 0, 6);
            gridPane.add(new Label(String.valueOf(reservation.getGuestId())), 1, 6);

            gridPane.add(new Label("Number of Guests:"), 0, 7);
            gridPane.add(new Label(String.valueOf(reservation.getNumberOfGuests())), 1, 7);

            gridPane.add(new Label("Reservation Status:"), 0, 8);
            gridPane.add(new Label(reservation.getReservationStatus()), 1, 8);

            // Add more labels for other reservation details

            return gridPane;
        }


        public static void filterReservations(String searchText) {
            if (originalReservationList == null) {
                originalReservationList = getAllReservations();
            }

            if (searchText == null || searchText.trim().isEmpty()) {
                getReservationTable().getItems().setAll(originalReservationList);
            } else {
                ObservableList<Reservation> filteredList = originalReservationList.stream()
                        .filter(reservation -> String.valueOf(reservation.getReservationId()).contains(searchText)
                                || reservation.getReferenceNumber().toLowerCase().contains(searchText.toLowerCase())
                                || String.valueOf(reservation.getRoomNumber()).contains(searchText)
                                || reservation.getCheckinDate().toString().contains(searchText)
                                || reservation.getCheckoutDate().toString().contains(searchText)
                                || String.valueOf(reservation.getGuestId()).contains(searchText)
                                || String.valueOf(reservation.getNumberOfGuests()).contains(searchText)
                                || reservation.getReservationStatus().toLowerCase().contains(searchText.toLowerCase()))
                        .collect(Collectors.toCollection(FXCollections::observableArrayList));

                getReservationTable().getItems().setAll(filteredList);
            }
        }

        public static ObservableList<Reservation> getAllReservations() {
            return DbConnection.getAllReservations(DbConnection.getConnection());
        }

        private static void viewReservationDetails(Reservation reservation) {
            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Reservation Details");

            GridPane gridPane = createReservationDetails(reservation);

            Scene scene = new Scene(gridPane, 400, 200);
            detailsStage.setScene(scene);

            detailsStage.showAndWait();
        }

        private static void manageReservation(Reservation reservation) {
            // Placeholder for manage reservation functionality
            // You may implement logic to manage reservation details
        }

        private static void cancelReservation(Reservation reservation) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cancel Reservation");
            alert.setHeaderText("Cancel Reservation " + reservation.getReservationId());
            alert.setContentText("Are you sure you want to cancel this reservation?");

            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(yesButton, noButton);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == yesButton) {
                // User clicked "Yes," cancel the reservation
                reservation.setReservationStatus("Cancelled");
                DbConnection.updateReservation(DbConnection.getConnection(), reservation);

                // Update the reservationTable
                originalReservationList = getAllReservations();
                reservationTable.setItems(originalReservationList);

                AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Reservation cancelled successfully!");
            }

        }
    }
}
