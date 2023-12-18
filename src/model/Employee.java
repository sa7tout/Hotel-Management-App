package model;

import com.example.hotel.AlertHelper;
import com.example.hotel.DbConnection;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.example.hotel.MainPanelController;
import javafx.util.Callback;

import java.time.LocalDate;
import java.util.Optional;

public class Employee {
    private int employeeID;
    private String firstName;
    private String lastName;
    private String email;
    private String department;
    private String position;
    private String hireDate;

    public Employee(int employeeID, String firstName, String lastName, String email, String department, String position, String hireDate) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.position = position;
        this.hireDate = hireDate;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(int employeeID) {
        this.employeeID = employeeID;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getHireDate() {
        return hireDate;
    }

    public void setHireDate(String hireDate) {
        this.hireDate = hireDate;
    }

    public void manageEmployeeReservation(int reservationID, String action) {
    }

    public void accessEmployeeSpecificFeatures() {
    }

    public static class EmployeeTable {

        private static TableView<Employee> employeeTable;
        private static TableColumn<Employee, Integer> employeeIdColumn;
        private static TableColumn<Employee, String> firstNameColumn;
        private static TableColumn<Employee, String> lastNameColumn;
        private static TableColumn<Employee, String> emailColumn;
        private static TableColumn<Employee, String> departmentColumn;
        private static TableColumn<Employee, String> positionColumn;
        private static TableColumn<Employee, String> hireDateColumn;
        private static ObservableList<Employee> originalEmployeeList;
        private static TableColumn<Employee, Void> actionsColumn;

        public static void initialize() {
            employeeTable = new TableView<>();

            employeeIdColumn = new TableColumn<>("Employee ID");
            employeeIdColumn.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEmployeeID()).asObject());

            firstNameColumn = new TableColumn<>("First Name");
            firstNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFirstName()));

            lastNameColumn = new TableColumn<>("Last Name");
            lastNameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLastName()));

            emailColumn = new TableColumn<>("Email");
            emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

            departmentColumn = new TableColumn<>("Department");
            departmentColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartment()));

            positionColumn = new TableColumn<>("Position");
            positionColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPosition()));

            hireDateColumn = new TableColumn<>("Hire Date");
            hireDateColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getHireDate()));

            employeeTable.getColumns().addAll(employeeIdColumn, firstNameColumn, lastNameColumn, emailColumn, departmentColumn, positionColumn, hireDateColumn);

            actionsColumn = new TableColumn<>("Actions");
            actionsColumn.setCellFactory(param -> new TableCell<>() {
                private final Button modifyButton = new Button("Modify");
                private final Button detailsButton = new Button("Show Details");
                private final Button deleteButton = new Button("Delete");

                {
                    modifyButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        handleModification(employee);
                    });

                    detailsButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        showDetails(employee);
                    });

                    deleteButton.setOnAction(event -> {
                        Employee employee = getTableView().getItems().get(getIndex());
                        handleDeletion(employee);
                    });

                    // Set styles for buttons
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

            employeeTable.getColumns().add(actionsColumn);
        }

        private static void handleModification(Employee employee) {
            Stage modifyStage = new Stage();
            modifyStage.initModality(Modality.APPLICATION_MODAL);
            modifyStage.setTitle("Modify Employee");

            GridPane gridPane = createEmployeeModification(employee, modifyStage);

            Scene scene = new Scene(gridPane, 400, 300);
            modifyStage.setScene(scene);

            modifyStage.showAndWait();
        }

        private static void handleDeletion(Employee employee) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Confirm Deletion");
            alert.setContentText("Are you sure you want to delete the employee: " + employee.getFirstName() + " " + employee.getLastName() + "?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                DbConnection.deleteEmployee(DbConnection.getConnection(), employee);
                employeeTable.getItems().remove(employee);
                AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Employee deleted successfully!");
            }
        }


        private static void showDetails(Employee employee) {
            Stage detailsStage = new Stage();
            detailsStage.initModality(Modality.APPLICATION_MODAL);
            detailsStage.setTitle("Employee Details");

            GridPane gridPane = createEmployeeDetails(employee);

            Scene scene = new Scene(gridPane, 400, 200);
            detailsStage.setScene(scene);

            detailsStage.showAndWait();
        }
        private static GridPane createEmployeeDetails(Employee employee) {
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(20));
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.add(new Label("Employee Details:"), 0, 0, 2, 1);
            gridPane.add(new Label("First Name:"), 0, 1);
            gridPane.add(new Label(employee.getFirstName()), 1, 1);
            gridPane.add(new Label("Last Name:"), 0, 2);
            gridPane.add(new Label(employee.getLastName()), 1, 2);
            gridPane.add(new Label("Email:"), 0, 3);
            gridPane.add(new Label(employee.getEmail()), 1, 3);
            gridPane.add(new Label("Department:"), 0, 4);
            gridPane.add(new Label(employee.getDepartment()), 1, 4);
            gridPane.add(new Label("Position:"), 0, 5);
            gridPane.add(new Label(employee.getPosition()), 1, 5);
            gridPane.add(new Label("Hire Date:"), 0, 6);
            gridPane.add(new Label(employee.getHireDate()), 1, 6);

            return gridPane;
        }
        private static GridPane createEmployeeModification(Employee employee, Stage modifyStage) {
            GridPane gridPane = new GridPane();
            gridPane.setPadding(new Insets(20));
            gridPane.setHgap(10);
            gridPane.setVgap(10);

            gridPane.add(new Label("Modify Employee:"), 0, 0, 2, 1);
            gridPane.add(new Label("First Name:"), 0, 1);
            TextField firstNameField = new TextField(employee.getFirstName());
            gridPane.add(firstNameField, 1, 1);
            gridPane.add(new Label("Last Name:"), 0, 2);
            TextField lastNameField = new TextField(employee.getLastName());
            gridPane.add(lastNameField, 1, 2);
            gridPane.add(new Label("Email:"), 0, 3);
            TextField emailField = new TextField(employee.getEmail());
            gridPane.add(emailField, 1, 3);
            gridPane.add(new Label("Department:"), 0, 4);
            TextField departmentField = new TextField(employee.getDepartment());
            gridPane.add(departmentField, 1, 4);
            gridPane.add(new Label("Position:"), 0, 5);
            TextField positionField = new TextField(employee.getPosition());
            gridPane.add(positionField, 1, 5);
            gridPane.add(new Label("Hire Date:"), 0, 6);
            DatePicker hireDatePicker = new DatePicker(LocalDate.parse(employee.getHireDate()));
            gridPane.add(hireDatePicker, 1, 6);

            Button submitButton = new Button("Submit");
            submitButton.setOnAction(e -> {
                if (MainPanelController.validateInputs(firstNameField, lastNameField, emailField, departmentField, positionField, hireDatePicker.getEditor())) {
                    employee.setFirstName(firstNameField.getText());
                    employee.setLastName(lastNameField.getText());
                    employee.setEmail(emailField.getText());
                    employee.setDepartment(departmentField.getText());
                    employee.setPosition(positionField.getText());
                    employee.setHireDate(hireDatePicker.getValue() != null ? hireDatePicker.getValue().toString() : "");


                    DbConnection.updateEmployee(DbConnection.getConnection(), employee);


                    modifyStage.close();


                    AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Employee modified successfully!");


                    Employee.EmployeeTable.getEmployeeTable().setItems(DbConnection.getAllEmployees(DbConnection.getConnection()));
                }
            });

            gridPane.add(submitButton, 0, 7, 2, 1);
            GridPane.setHalignment(submitButton, HPos.CENTER);


            Callback<DatePicker, DateCell> dayCellFactory = dp -> new DateCell() {
                @Override
                public void updateItem(LocalDate item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item.isAfter(LocalDate.now())) {
                        setDisable(true);
                        setStyle("-fx-background-color: #ffc0cb;");
                    }
                }
            };

            hireDatePicker.setDayCellFactory(dayCellFactory);

            return gridPane;
        }

        private static void disableFormForDetails(GridPane gridPane) {
            for (Node node : gridPane.getChildren()) {
                if (node instanceof TextField) {
                    ((TextField) node).setEditable(false);
                }
            }
        }
        public static void setOriginalEmployeeList(ObservableList<Employee> employeeList) {
            originalEmployeeList = FXCollections.observableArrayList(employeeList);
        }

        public static TableView<Employee> getEmployeeTable() {
            return employeeTable;
        }

        public static TableColumn<Employee, Integer> getEmployeeIdColumn() {
            return employeeIdColumn;
        }

        public static TableColumn<Employee, String> getFirstNameColumn() {
            return firstNameColumn;
        }

        public static TableColumn<Employee, String> getLastNameColumn() {
            return lastNameColumn;
        }

        public static TableColumn<Employee, String> getEmailColumn() {
            return emailColumn;
        }

        public static TableColumn<Employee, String> getDepartmentColumn() {
            return departmentColumn;
        }

        public static TableColumn<Employee, String> getPositionColumn() {
            return positionColumn;
        }

        public static TableColumn<Employee, String> getHireDateColumn() {
            return hireDateColumn;
        }
        public static void filterEmployees(String searchText) {

            ObservableList<Employee> employeeList = employeeTable.getItems();
            ObservableList<Employee> filteredList = FXCollections.observableArrayList();


            if (searchText.isEmpty()) {
                filteredList.addAll(originalEmployeeList);
            } else {

                for (Employee employee : originalEmployeeList) {
                    if (matchesSearch(employee, searchText)) {
                        filteredList.add(employee);
                    }
                }
            }

            employeeTable.setItems(filteredList);
        }

        private static boolean matchesSearch(Employee employee, String searchText) {
            return employee.getFirstName().toLowerCase().contains(searchText.toLowerCase())
                    || employee.getLastName().toLowerCase().contains(searchText.toLowerCase())
                    || employee.getEmail().toLowerCase().contains(searchText.toLowerCase())
                    || employee.getDepartment().toLowerCase().contains(searchText.toLowerCase())
                    || employee.getPosition().toLowerCase().contains(searchText.toLowerCase())
                    || employee.getHireDate().toLowerCase().contains(searchText.toLowerCase());
        }



        public static void submitEmployeeDetails(String firstName, String lastName, String email, String department, String position, String hireDate) {
            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || department.isEmpty() || position.isEmpty() || hireDate.isEmpty()) {
                AlertHelper.showAlert(Alert.AlertType.ERROR, null, "Error", "All fields must be filled in.");
                return;
            }
            else{

            Employee newEmployee = new Employee(0, firstName, lastName, email, department, position, hireDate);

            DbConnection.addEmployee(DbConnection.getConnection(), newEmployee);

            ObservableList<Employee> updatedEmployeeList = DbConnection.getAllEmployees(DbConnection.getConnection());

            Employee.EmployeeTable.getEmployeeTable().setItems(updatedEmployeeList);

            AlertHelper.showAlert(Alert.AlertType.INFORMATION, null, "Success", "Employee added successfully!");}
        }





    }
}
