import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection connection = DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "Shapok2006"
        );

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nChoose an action:");
            System.out.println("1. Add student");
            System.out.println("2. Change data by ID");
            System.out.println("3. Delete student by ID");
            System.out.println("4. Show all students");
            System.out.println("5. Exit");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent(connection, scanner);
                    break;
                case 2:
                    showStudentTable(connection);
                    changeDataById(connection, scanner);
                    break;
                case 3:
                    showStudentTable(connection);
                    deleteStudentById(connection, scanner);
                    break;
                case 4:
                    showStudentTable(connection);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    showStudentTable(connection);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addStudent(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter surname: ");
        String surname = scanner.next();
        System.out.print("Enter GPA: ");
        double gpa = scanner.nextDouble();
        System.out.println(gpa);

        double salary = calculatePayment(gpa);

        PreparedStatement statement = connection.prepareStatement("INSERT INTO student (name, surname, gpa, salary) VALUES (?, ?, ?, ?)");
        statement.setString(1, name);
        statement.setString(2, surname);
        statement.setDouble(3, gpa);
        statement.setDouble(4, salary);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 1) {
            System.out.println("Student added successfully!");
        } else {
            System.out.println("Failed to add student.");
        }
    }

    private static void changeDataById(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID: ");
        int id = scanner.nextInt();

        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM student WHERE id = " + id);

        if (resultSet.next()) {
            System.out.println("Current data:");
            System.out.println("GPA: " + resultSet.getDouble("gpa"));

            System.out.println("\nEnter new gpa:");

            System.out.print("GPA: ");
            double newGpa = scanner.nextDouble();

            double salary = calculatePayment(newGpa);

            PreparedStatement updateStatement = connection.prepareStatement("UPDATE student SET gpa = ?, salary = ? WHERE id = ?");
            updateStatement.setDouble(1, newGpa);
            updateStatement.setDouble(2, salary);
            updateStatement.setInt(3, id);

            int rowsAffected = updateStatement.executeUpdate();
            if (rowsAffected == 1) {
                System.out.println("Student data updated successfully!");
            } else {
                System.out.println("Failed to update student data.");
            }
        } else {
            System.out.println("No student found with ID " + id);
        }
    }

    private static void deleteStudentById(Connection connection, Scanner scanner) throws SQLException {
        System.out.print("Enter student ID to delete: ");
        int id = scanner.nextInt();

        PreparedStatement statement = connection.prepareStatement("DELETE FROM student WHERE id = ?");
        statement.setInt(1, id);

        int rowsAffected = statement.executeUpdate();
        if (rowsAffected == 1) {
            System.out.println("Student with ID " + id + " deleted successfully!");
        } else if (rowsAffected == 0) {
            System.out.println("No student found with ID " + id);
        } else {
            System.out.println("An unexpected error occurred while deleting the student.");
        }
    }

    private static void showStudentTable(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM student order by id");

        System.out.println("\nStudent Table:");
        System.out.println("| ID | Name | Surname | GPA | Salary |");
        System.out.println("");

        while (resultSet.next()) {
            System.out.printf("| %d | %s | %s | %.2f | %.2f |\n",
                    resultSet.getInt("id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getDouble("gpa"),
                    resultSet.getDouble("salary"));
        }
    }


    public static double calculatePayment(double gpa) {
        if (gpa > 2.67){
            return 36600;
        }
        else {
            return 0;
        }
    }
}

