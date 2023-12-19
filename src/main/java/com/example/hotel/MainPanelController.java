package com.example.hotel;

import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static model.Employee.EmployeeTable.filterEmployees;
import static model.Employee.EmployeeTable.submitEmployeeDetails;

public class MainPanelController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private ContextMenu userContextMenu;

    private User currentuser;

    private List<Button> menus;

    @FXML
    private TitledPane employeePane;

    @FXML
    private TitledPane clientPane;

    @FXML
    private TitledPane roomPane;

    @FXML
    private TitledPane reservationPane;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        borderPane.sceneProperty().addListener((observable, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.getStylesheets().add(getClass().getResource("mainpanel.css").toExternalForm());
            }
        });
    }

    public void initUser(String username) {
        User user = DbConnection.getUserInfo(DbConnection.getConnection(), username);
        this.currentuser = user;
    }

    @FXML
    private void showUserInfo(ActionEvent event) {
        Node source = (Node) event.getSource();
        Bounds boundsInScreen = source.localToScreen(source.getBoundsInLocal());
        updateAndShowContextMenu(source, boundsInScreen.getMinX(), boundsInScreen.getMaxY());
    }

    private void updateAndShowContextMenu(Node source, double minX, double maxY) {
        userContextMenu.getItems().clear();

        MenuItem usernameMenuItem = new MenuItem("Username: " + currentuser.getUsername());
        MenuItem fullnameMenuItem = new MenuItem("Full Name: " + currentuser.getFullName());
        MenuItem emailMenuItem = new MenuItem("Email: " + currentuser.getEmail());
        MenuItem roleMenuItem = new MenuItem("Role: " + currentuser.getRole());

        userContextMenu.getItems().addAll(
                usernameMenuItem,
                fullnameMenuItem,
                emailMenuItem,
                new SeparatorMenuItem(),
                roleMenuItem,
                new SeparatorMenuItem()
        );

        userContextMenu.show(source, minX, maxY);
    }

    @FXML
    private void changeButtonBackground(ActionEvent e) {
        Iterator<Button> iteratorMenus = menus.iterator();

        while (iteratorMenus.hasNext()) {
            Button clickedButton = (Button) e.getSource();
            Button otherButton = iteratorMenus.next();
            if (clickedButton == otherButton) {
                clickedButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#2b2a26;");
            } else {
                if (otherButton != null) {
                    otherButton.setStyle("-fx-text-fill:#f0f0f0;-fx-background-color:#404040;");
                }
            }
        }
    }

    @FXML
    private void clear() {
        borderPane.setCenter(null);
    }

    @FXML
    private void loadFXML(String fileName) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/" + fileName + ".fxml"));
            borderPane.setCenter(parent);
        } catch (IOException ex) {
            Logger.getLogger(MainPanelController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void close() throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/hotel/LoginView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.getIcons().add(new Image(HotelApplication.class.getResource("/assets/vagologo.png").toExternalForm()));
        stage.show();
    }
    @FXML
    private void manageEmployees(ActionEvent e) {
        clear();
    }

    @FXML
    private void manageClients(ActionEvent e) {
        clear();
    }

    @FXML
    private void manageRoomAvailability(ActionEvent e) {
        clear();
    }

    @FXML
    private void manageReservations(ActionEvent e) {
        clear();
    }

    @FXML
    private void exportEmployees(ActionEvent e) {
    }

    @FXML
    private void importEmployees(ActionEvent e) {
    }

    public void addEmployee(ActionEvent actionEvent) {
        clear();

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setMaxWidth(200);

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setMaxWidth(200);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setMaxWidth(200);

        Label departmentLabel = new Label("Department:");
        TextField departmentField = new TextField();
        departmentField.setMaxWidth(200);

        Label positionLabel = new Label("Position:");
        TextField positionField = new TextField();
        positionField.setMaxWidth(200);

        Label hireDateLabel = new Label("Hire Date:");
        DatePicker hireDatePicker = new DatePicker();

        LocalDate currentDate = LocalDate.now();
        hireDatePicker.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isAfter(currentDate));
            }
        });

        Button addButton = new Button("Add Employee");
        addButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0; -fx-font-size: 12px; -fx-padding: 5 10; -fx-cursor: hand;");
        addButton.setAlignment(Pos.CENTER);
        addButton.setOnAction(e -> {
            if (validateInputs(firstNameField, lastNameField, emailField, departmentField, positionField, hireDatePicker.getEditor())) {
                Employee.EmployeeTable.submitEmployeeDetails(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        departmentField.getText(),
                        positionField.getText(),
                        hireDatePicker.getValue() != null ? hireDatePicker.getValue().toString() : ""
                );
                clearFields(firstNameField, lastNameField, emailField, departmentField, positionField, hireDatePicker);
            }
        });

        HBox datePickerBox = new HBox();
        datePickerBox.setAlignment(Pos.CENTER);
        datePickerBox.getChildren().add(hireDatePicker);

        VBox formVBox = new VBox();
        formVBox.setAlignment(Pos.CENTER);
        formVBox.setSpacing(10);
        formVBox.getChildren().addAll(
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                emailLabel, emailField,
                departmentLabel, departmentField,
                positionLabel, positionField,
                hireDateLabel, datePickerBox,
                new Region(),
                addButton
        );

        borderPane.setCenter(formVBox);
    }

    private void clearFields(Object... fields) {
        Arrays.asList(fields).forEach(field -> {
            if (field instanceof TextField) {
                ((TextField) field).clear();
            } else if (field instanceof DatePicker) {
                ((DatePicker) field).setValue(null);
            }
        });
    }

    public static boolean validateInputs(TextField... fields) {
        boolean allFieldsFilled = true;

        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                highlightField(field);
                allFieldsFilled = false;
            } else {
                removeHighlight(field);
            }
        }

        if (!allFieldsFilled) {
            showInputAlert();
        }

        return allFieldsFilled;
    }

    private static void highlightField(TextField field) {
        field.setStyle("-fx-border-color: red;");
    }

    private static void removeHighlight(TextField field) {
        field.setStyle("");
    }

    private static void showInputAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Please fill in all the required fields.");
        alert.showAndWait();
    }

    public void listEmployees(ActionEvent actionEvent) {
        ObservableList<Employee> employeeList = DbConnection.getAllEmployees(DbConnection.getConnection());
        clear();

        Employee.EmployeeTable.initialize();
        Employee.EmployeeTable.getEmployeeTable().setItems(employeeList);
        Employee.EmployeeTable.setOriginalEmployeeList(employeeList);

        VBox vbox = new VBox();

        HBox searchAndButtonsBox = new HBox(); // Create a new HBox for search and buttons
        HBox.setHgrow(searchAndButtonsBox, Priority.ALWAYS); // Allow HBox to grow horizontally

        // Create search field
        TextField searchField = new TextField();
        searchField.setPromptText("Search employees");

        searchField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.ENTER) {
                handleEmployeeSearchAction(searchField.getText());
            }
        });

        searchField.setOnKeyPressed(e -> handleEmployeeSearchAction(searchField.getText()));

        // Create export button
        Button exportButton = new Button("Export to CSV");
        exportButton.setOnAction(e -> {
            // Call exportToCSV method from EmployeeTable
            Employee.EmployeeTable.exportToCSV();
        });

        // Create import button
        Button importButton = new Button("Import from CSV");
        importButton.setOnAction(e -> {
            // Configure the FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv"));

            // Show the file chooser dialog
            File selectedFile = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
            if (selectedFile != null) {
                // Call importFromCSV method from EmployeeTable and pass the selected File object
                Employee.EmployeeTable.importFromCSV(selectedFile);
            }
        });

        // Add search field and buttons to the HBox
        searchAndButtonsBox.getChildren().addAll(searchField, exportButton, importButton);

        vbox.getChildren().add(searchAndButtonsBox); // Add HBox to VBox
        vbox.getChildren().add(Employee.EmployeeTable.getEmployeeTable());

        VBox.setVgrow(Employee.EmployeeTable.getEmployeeTable(), Priority.ALWAYS);
        borderPane.setCenter(vbox);
    }

    private void handleEmployeeSearchAction(String searchText) {
        Employee.EmployeeTable.filterEmployees(searchText);
    }


    public void addClient(ActionEvent actionEvent) {
        clear();

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField();
        firstNameField.setMaxWidth(200);

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField();
        lastNameField.setMaxWidth(200);

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();
        emailField.setMaxWidth(200);

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setMaxWidth(200);

        Button addButton = new Button("Add Client");
        addButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0; -fx-font-size: 12px; -fx-padding: 5 10; -fx-cursor: hand;");
        addButton.setAlignment(Pos.CENTER);
        addButton.setOnAction(e -> {
            if (validateInputs(firstNameField, lastNameField, emailField, passwordField)) {
                Guest.submitGuestDetails(
                        firstNameField.getText(),
                        lastNameField.getText(),
                        emailField.getText(),
                        passwordField.getText()
                );
                clearFields(firstNameField, lastNameField, emailField, passwordField);
            }
        });

        VBox formVBox = new VBox();
        formVBox.setAlignment(Pos.CENTER);
        formVBox.setSpacing(10);
        formVBox.getChildren().addAll(
                firstNameLabel, firstNameField,
                lastNameLabel, lastNameField,
                emailLabel, emailField,
                passwordLabel, passwordField,
                new Region(),
                addButton
        );

        borderPane.setCenter(formVBox);
    }


    public void listClients(ActionEvent actionEvent) {
        ObservableList<Guest> guestList = Guest.getAllGuests();
        clear();

        Guest.GuestTable.initialize();
        Guest.GuestTable.getGuestTable().setItems(guestList);

        VBox vbox = new VBox();

        TextField searchField = new TextField();
        searchField.setPromptText("Search clients...");

        searchField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.ENTER) {
                handleGuestSearchAction(searchField.getText());
            }
        });

        searchField.setOnKeyPressed(e -> handleGuestSearchAction(searchField.getText()));

        vbox.getChildren().add(searchField);
        vbox.getChildren().add(Guest.GuestTable.getGuestTable());

        VBox.setVgrow(Guest.GuestTable.getGuestTable(), Priority.ALWAYS);
        borderPane.setCenter(vbox);
    }

    public void listRooms(ActionEvent actionEvent) {
        ObservableList<Room> roomList = Room.RoomTable.getAllRooms();
        clear(); // Assuming you have a method to clear the current view

        Room.RoomTable.initialize(); // Initialize the room table
        Room.RoomTable.getRoomTable().setItems(roomList);

        VBox vbox = new VBox();

        TextField searchField = new TextField();
        searchField.setPromptText("Search rooms...");

        searchField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.ENTER) {
                handleRoomSearchAction(searchField.getText());
            }
        });

        searchField.setOnKeyPressed(e -> handleRoomSearchAction(searchField.getText()));

        vbox.getChildren().add(searchField);
        vbox.getChildren().add(Room.RoomTable.getRoomTable());

        VBox.setVgrow(Room.RoomTable.getRoomTable(), Priority.ALWAYS);
        borderPane.setCenter(vbox); // Assuming you have a BorderPane named 'borderPane'
    }

    private void handleRoomSearchAction(String searchText) {
        Room.RoomTable.filterRooms(searchText);
    }


    private void handleGuestSearchAction(String searchText) {
        Guest.GuestTable.filterGuests(searchText);
    }


    public void checkAvailability(ActionEvent actionEvent) {
    }

    public void listReservations(ActionEvent actionEvent) {
        ObservableList<Reservation> reservationList = DbConnection.getAllReservations(DbConnection.getConnection());
        clear();

        Reservation.ReservationTable.initialize();
        Reservation.ReservationTable.getReservationTable().setItems(reservationList);

        VBox vbox = new VBox();

        TextField searchField = new TextField();
        searchField.setPromptText("Search reservations...");

        searchField.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.BACK_SPACE || e.getCode() == KeyCode.ENTER) {
                handleReservationSearchAction(searchField.getText());
            }
        });

        searchField.setOnKeyPressed(e -> handleReservationSearchAction(searchField.getText()));

        vbox.getChildren().add(searchField);
        vbox.getChildren().add(Reservation.ReservationTable.getReservationTable());

        VBox.setVgrow(Reservation.ReservationTable.getReservationTable(), Priority.ALWAYS);
        borderPane.setCenter(vbox);
    }

    private void handleReservationSearchAction(String searchText) {
        Reservation.ReservationTable.filterReservations(searchText);
    }

    public void addRoom(ActionEvent actionEvent) {
    }

    public void makeReservation(ActionEvent actionEvent) {
        clear();

        // Create UI components for making a reservation
        Label roomLabel = new Label("Select Room:");
        ComboBox<Room> roomComboBox = new ComboBox<>(Room.RoomTable.getAllRooms());
        roomComboBox.setPromptText("Choose a room");
        roomComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item.getRoomNumber())); // Display room number
                }
            }
        });
        roomComboBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Room item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(item.getRoomNumber())); // Display room number
                }
            }
        });

        Label clientLabel = new Label("Select Client:");
        ComboBox<Guest> clientComboBox = new ComboBox<>(Guest.getAllGuests());
        clientComboBox.setPromptText("Choose a client");

        Label guestsLabel = new Label("Number of Guests:");
        TextField guestsField = new TextField();
        guestsField.setMaxWidth(200); // Adjusted width

        Label checkInLabel = new Label("Check-In Date:");
        DatePicker checkInDatePicker = new DatePicker();

        Label checkOutLabel = new Label("Check-Out Date:");
        DatePicker checkOutDatePicker = new DatePicker();

        Button reserveButton = new Button("Make Reservation");
        reserveButton.setStyle("-fx-background-color: #2b2a26; -fx-text-fill: #f0f0f0; -fx-font-size: 12px; -fx-padding: 5 10; -fx-cursor: hand;");
        reserveButton.setAlignment(Pos.CENTER);
        reserveButton.setOnAction(e -> {
            // Validate inputs and make reservation
            if (validateReservationInputs(roomComboBox, clientComboBox, guestsField, checkInDatePicker, checkOutDatePicker)) {
                Room selectedRoom = roomComboBox.getValue();
                Guest selectedClient = clientComboBox.getValue();
                int numberOfGuests = Integer.parseInt(guestsField.getText());
                LocalDate checkInDate = checkInDatePicker.getValue();
                LocalDate checkOutDate = checkOutDatePicker.getValue();

                // Additional logic for making a reservation based on selected inputs
                // Example: Reservation reservation = new Reservation(selectedRoom.getRoomNumber(), selectedClient.getGuestId(), numberOfGuests, checkInDate, checkOutDate);

                // Validate logical dates
                if (checkInDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(LocalDate.now()) || checkOutDate.isBefore(checkInDate)) {
                    showDateAlert();
                } else {
                    // Display success message or handle the reservation process accordingly
                    System.out.println("Reservation made successfully!");
                    clearFields(roomComboBox, clientComboBox, guestsField, checkInDatePicker, checkOutDatePicker);
                }
            }
        });
    }

        private static void showDateAlert() {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Invalid Dates");
            alert.setHeaderText(null);
            alert.setContentText("Please select logical dates. Check-out date must be later than check-in date, and both should be in the future.");
            alert.showAndWait();
        }

        private static boolean validateReservationInputs(Control... controls) {
        boolean allFieldsFilled = true;

        for (Control control : controls) {
            if (control instanceof ComboBox && ((ComboBox<?>) control).getValue() == null) {
                highlightField(control);
                allFieldsFilled = false;
            } else if (control instanceof TextField && ((TextField) control).getText().isEmpty()) {
                highlightField(control);
                allFieldsFilled = false;
            } else if (control instanceof DatePicker && ((DatePicker) control).getValue() == null) {
                highlightField(control);
                allFieldsFilled = false;
            } else {
                removeHighlight(control);
            }
        }

        if (!allFieldsFilled) {
            showInputAlert();
        }

        return allFieldsFilled;
    }

    private static void highlightField(Control control) {
        control.setStyle("-fx-border-color: red;");
    }

    private static void removeHighlight(Control control) {
        control.setStyle("");
    }



    @FXML
    private void logout() throws IOException {
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.close();

        Parent root = FXMLLoader.load(getClass().getResource("/com/example/hotel/LoginView.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("User Login");
        stage.getIcons().add(new Image("/assets/vagologo.png"));
        stage.show();
    }
}
