package com.example.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DbConnection {

    private static Connection connection;

    DbConnection() {
        // Private constructor to prevent instantiation
    }

    public static Connection getConnection() {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/vagohotel";
                String username = "root";
                String password = "";
                connection = DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static User getUserInfo(Connection connection, String username) {
        String query = "SELECT user_id, full_name, email, role, created_at FROM users WHERE username = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String fullName = resultSet.getString("full_name");
                    String email = resultSet.getString("email");
                    User.UserRole role = User.UserRole.valueOf(resultSet.getString("role"));
                    Timestamp createdAt = resultSet.getTimestamp("created_at");
                    User user = new User(userId, username, fullName, email, role, createdAt);
                    return user;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Employee> getAllEmployees(Connection connection) {
        ObservableList<Employee> employeeList = FXCollections.observableArrayList();
        String query = "SELECT * FROM employees";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int employeeID = resultSet.getInt("employee_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String department = resultSet.getString("department");
                    String position = resultSet.getString("position");
                    String hireDate = resultSet.getString("hire_date");

                    Employee employee = new Employee(employeeID, firstName, lastName, email, department, position, hireDate);
                    employeeList.add(employee);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeList;
    }
    public static void addEmployee(Connection connection, Employee employee) {
        String query = "INSERT INTO employees (first_name, last_name, email, department, position, hire_date) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setString(5, employee.getPosition());
            preparedStatement.setString(6, employee.getHireDate());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateEmployee(Connection connection, Employee employee) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE employees SET first_name=?, last_name=?, email=?, department=?, position=?, hire_date=? WHERE employee_id=?")) {
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setString(4, employee.getDepartment());
            preparedStatement.setString(5, employee.getPosition());
            preparedStatement.setString(6, employee.getHireDate());
            preparedStatement.setInt(7, employee.getEmployeeID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteEmployee(Connection connection, Employee employee) {
        String query = "DELETE FROM employees WHERE employee_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, employee.getEmployeeID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Guest> getAllGuests(Connection connection) {
        ObservableList<Guest> guestList = FXCollections.observableArrayList();
        String query = "SELECT * FROM guests";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int guestID = resultSet.getInt("guest_id");
                    String firstName = resultSet.getString("first_name");
                    String lastName = resultSet.getString("last_name");
                    String email = resultSet.getString("email");
                    String password = resultSet.getString("password");

                    Guest guest = new Guest(guestID, firstName, lastName, email, password);
                    guestList.add(guest);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return guestList;
    }


    public static void addGuest(Connection connection, Guest guest) {
        String query = "INSERT INTO guests (first_name, last_name, email, password) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, guest.getFirstName());
            preparedStatement.setString(2, guest.getLastName());
            preparedStatement.setString(3, guest.getEmail());
            preparedStatement.setString(4, guest.getPassword());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGuest(Connection connection, Guest guest) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(
                "UPDATE guests SET first_name=?, last_name=?, email=?, password=? WHERE guest_id=?")) {
            preparedStatement.setString(1, guest.getFirstName());
            preparedStatement.setString(2, guest.getLastName());
            preparedStatement.setString(3, guest.getEmail());
            preparedStatement.setString(4, guest.getPassword());
            preparedStatement.setInt(5, guest.getGuestID());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void deleteGuest(Connection connection, Guest guest) {
        String query = "DELETE FROM guests WHERE guest_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, guest.getGuestID());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ObservableList<Room> getAllRooms(Connection connection) {
        ObservableList<Room> roomList = FXCollections.observableArrayList();
        String query = "SELECT * FROM rooms";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    String roomType = resultSet.getString("room_type");
                    int capacity = resultSet.getInt("capacity");
                    String amenitiesString = resultSet.getString("amenities");
                    List<String> amenities = Arrays.asList(amenitiesString.split(","));
                    String availabilityStatus = resultSet.getString("availability_status");
                    double pricePerNight = resultSet.getDouble("price_per_night");
                    int guestId = resultSet.getInt("guest_id");

                    Room room = new Room(roomNumber, roomType, capacity, amenities, availabilityStatus, pricePerNight, guestId);
                    roomList.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomList;
    }

    public static ObservableList<Room> getAvailableRooms(Connection connection) {
        ObservableList<Room> roomList = FXCollections.observableArrayList();
        String query = "SELECT * FROM rooms WHERE availability_status = 'available'";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int roomNumber = resultSet.getInt("room_number");
                    String roomType = resultSet.getString("room_type");
                    int capacity = resultSet.getInt("capacity");
                    String amenitiesString = resultSet.getString("amenities");
                    List<String> amenities = Arrays.asList(amenitiesString.split(","));
                    String availabilityStatus = resultSet.getString("availability_status");
                    double pricePerNight = resultSet.getDouble("price_per_night");
                    int guestId = resultSet.getInt("guest_id");

                    Room room = new Room(roomNumber, roomType, capacity, amenities, availabilityStatus, pricePerNight, guestId);
                    roomList.add(room);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roomList;
    }

    public static List<Reservation> getReservationsByRoom(Connection connection, int roomNumber) {
        List<Reservation> reservationList = new ArrayList<>();
        String query = "SELECT * FROM reservations WHERE room_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int reservationId = resultSet.getInt("reservation_id");
                    String referenceNumber = resultSet.getString("reference_number");
                    int guestId = resultSet.getInt("guest_id");
                    int numberOfGuests = resultSet.getInt("number_of_guests");
                    Date checkinDate = resultSet.getDate("checkin_date");
                    Date checkoutDate = resultSet.getDate("checkout_date");
                    String reservationStatus = resultSet.getString("reservation_status");

                    Reservation reservation = new Reservation();
                    reservation.setReservationId(reservationId);
                    reservation.setReferenceNumber(referenceNumber);
                    reservation.setRoomNumber(roomNumber);
                    reservation.setGuestId(guestId);
                    reservation.setNumberOfGuests(numberOfGuests);
                    reservation.setCheckinDate(checkinDate);
                    reservation.setCheckoutDate(checkoutDate);
                    reservation.setReservationStatus(reservationStatus);

                    reservationList.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservationList;
    }



    public static void addRoom(Connection connection, Room room) {
        String query = "INSERT INTO rooms (room_number, room_type, capacity, amenities, availability_status, price_per_night, guest_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, room.getRoomNumber());
            preparedStatement.setString(2, room.getRoomType());
            preparedStatement.setInt(3, room.getCapacity());
            preparedStatement.setString(4, String.join(",", room.getAmenities()));
            preparedStatement.setString(5, room.getAvailabilityStatus());
            preparedStatement.setDouble(6, room.getPricePerNight());

            // Check if guestId is null before setting it in the statement
            if (room.getGuestId() != null) {
                preparedStatement.setInt(7, room.getGuestId());
            } else {
                preparedStatement.setNull(7, Types.INTEGER);
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




    public static void updateRoom(Connection connection, Room room) {
        String updateQuery;

        if (room.getGuestId() == 0) {
            // If guest ID is 0, update availability status without assigning a guest
            updateQuery = "UPDATE rooms SET availability_status = 'Available', guest_id = NULL WHERE room_number = ?";
        } else {
            // If guest ID is not 0, update availability status and assign the guest
            updateQuery = "UPDATE rooms SET availability_status = 'Booked', guest_id = ? WHERE room_number = ?";
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            if (room.getGuestId() != 0) {
                preparedStatement.setInt(1, room.getGuestId());
                preparedStatement.setInt(2, room.getRoomNumber());
            } else {
                preparedStatement.setInt(1, room.getRoomNumber());
            }

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception according to your application's error handling strategy
        }
    }


    public static ObservableList<Reservation> getAllReservations(Connection connection) {
        ObservableList<Reservation> reservationList = FXCollections.observableArrayList();
        String query = "SELECT * FROM reservations";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int reservationId = resultSet.getInt("reservation_id");
                    String referenceNumber = resultSet.getString("reference_number");
                    int roomNumber = resultSet.getInt("room_number");
                    Date checkinDate = resultSet.getDate("checkin_date");
                    Date checkoutDate = resultSet.getDate("checkout_date");
                    int guestId = resultSet.getInt("guest_id");
                    int numberOfGuests = resultSet.getInt("number_of_guests");
                    String reservationStatus = resultSet.getString("reservation_status");

                    Reservation reservation = new Reservation(reservationId, referenceNumber, roomNumber, checkinDate,
                            checkoutDate, guestId, numberOfGuests, reservationStatus);
                    reservationList.add(reservation);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return reservationList;
    }


    public static void updateReservation(Connection connection, Reservation reservation) {
        String updateQuery = "UPDATE reservations SET reference_number=?, room_number=?, checkin_date=?, " +
                "checkout_date=?, guest_id=?, number_of_guests=?, reservation_status=? WHERE reservation_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateQuery)) {
            preparedStatement.setString(1, reservation.getReferenceNumber());
            preparedStatement.setInt(2, reservation.getRoomNumber());
            preparedStatement.setDate(3, reservation.getCheckinDate());
            preparedStatement.setDate(4, reservation.getCheckoutDate());
            preparedStatement.setInt(5, reservation.getGuestId());
            preparedStatement.setInt(6, reservation.getNumberOfGuests());
            preparedStatement.setString(7, reservation.getReservationStatus());
            preparedStatement.setInt(8, reservation.getReservationId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addReservation(Connection connection, Reservation reservation) {
        String query = "INSERT INTO reservations (reference_number, room_number, checkin_date, checkout_date, guest_id, number_of_guests, reservation_status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, reservation.getReferenceNumber());
            preparedStatement.setInt(2, reservation.getRoomNumber());
            preparedStatement.setDate(3, reservation.getCheckinDate());
            preparedStatement.setDate(4, reservation.getCheckoutDate());
            preparedStatement.setInt(5, reservation.getGuestId());
            preparedStatement.setInt(6, reservation.getNumberOfGuests());
            preparedStatement.setString(7, reservation.getReservationStatus());

            preparedStatement.executeUpdate();

            // Retrieve the auto-generated key (reservation ID)
            try (ResultSet generatedKeys = preparedStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    reservation.setReservationId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Failed to retrieve auto-generated key for reservation");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static int getRoomCapacityByNumber(Connection connection, int roomNumber) {
        String query = "SELECT capacity FROM rooms WHERE room_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, roomNumber);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("capacity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Return a default value or handle the absence of data as needed
    }

    public static void deleteReservationByReferenceNumber(Connection connection, String referenceNumber) {
        String query = "DELETE FROM reservations WHERE reference_number = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, referenceNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();

            } finally {
                connection = null;
            }
        }
    }
}
