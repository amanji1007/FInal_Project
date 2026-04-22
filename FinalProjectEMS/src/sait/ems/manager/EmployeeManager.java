package sait.ems.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import sait.ems.exception.InvalidDataException;
import sait.ems.persistence.DatabaseManager;
import sait.ems.problemdomain.Employee;
import sait.ems.problemdomain.ManagerEmployee;
import sait.ems.problemdomain.RegularEmployee;

public class EmployeeManager
{
    private Scanner keyboard;

    public EmployeeManager()
    {
        keyboard = new Scanner(System.in);
        displayMenu();
    }

    private void displayMenu()
    {
        int option = 0;

        while (option != 6)
        {
            System.out.println("\nEmployee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.print("Enter option: ");

            try
            {
                option = Integer.parseInt(keyboard.nextLine());

                switch (option)
                {
                    case 1:
                        addEmployee();
                        break;
                    case 2:
                        viewAllEmployees();
                        break;
                    case 3:
                        searchEmployeeById();
                        break;
                    case 4:
                        updateEmployee();
                        break;
                    case 5:
                        deleteEmployee();
                        break;
                    case 6:
                        System.out.println("Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option.");
                }
            }
            catch (NumberFormatException e)
            {
                System.out.println("Please enter a valid number.");
            }
        }

        keyboard.close();
    }

    private void addEmployee()
    {
        try
        {
            System.out.print("Enter name: ");
            String name = keyboard.nextLine().trim();

            if (name.isEmpty())
            {
                throw new InvalidDataException("Name cannot be empty.");
            }

            System.out.print("Enter age: ");
            int age = Integer.parseInt(keyboard.nextLine());

            if (age <= 0)
            {
                throw new InvalidDataException("Age must be greater than 0.");
            }

            System.out.print("Enter department: ");
            String department = keyboard.nextLine().trim();

            if (department.isEmpty())
            {
                throw new InvalidDataException("Department cannot be empty.");
            }

            System.out.print("Enter salary: ");
            double salary = Double.parseDouble(keyboard.nextLine());

            if (salary <= 0)
            {
                throw new InvalidDataException("Salary must be greater than 0.");
            }

            System.out.print("Enter employee type (manager/regular): ");
            String employeeType = keyboard.nextLine().trim().toLowerCase();

            if (!employeeType.equals("manager") && !employeeType.equals("regular"))
            {
                throw new InvalidDataException("Employee type must be manager or regular.");
            }

            String sql = "INSERT INTO employee (name, age, department, salary, employee_type) VALUES (?, ?, ?, ?, ?)";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, department);
                stmt.setDouble(4, salary);
                stmt.setString(5, employeeType);

                int rows = stmt.executeUpdate();
                System.out.println(rows + " employee added successfully.");
            }
        }
        catch (InvalidDataException e)
        {
            System.out.println("Validation Error: " + e.getMessage());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter valid numeric input.");
        }
        catch (SQLException e)
        {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private void viewAllEmployees()
    {
        String sql = "SELECT * FROM employee";

        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery())
        {
            System.out.println("\nID | Name | Age | Department | Salary | Type");

            while (rs.next())
            {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                int age = rs.getInt("age");
                String department = rs.getString("department");
                double salary = rs.getDouble("salary");
                String type = rs.getString("employee_type");

                System.out.println(id + " | " + name + " | " + age + " | " + department + " | " + salary + " | " + type);
            }
        }
        catch (SQLException e)
        {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private void searchEmployeeById()
    {
        try
        {
            System.out.print("Enter employee ID: ");
            int id = Integer.parseInt(keyboard.nextLine());

            String sql = "SELECT * FROM employee WHERE id = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, id);
                ResultSet rs = stmt.executeQuery();

                if (rs.next())
                {
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String department = rs.getString("department");
                    double salary = rs.getDouble("salary");
                    String type = rs.getString("employee_type");

                    Employee employee;

                    if (type.equalsIgnoreCase("manager"))
                    {
                        employee = new ManagerEmployee(id, name, age, department, salary);
                    }
                    else
                    {
                        employee = new RegularEmployee(id, name, age, department, salary);
                    }

                    employee.display();
                    System.out.println("Bonus: " + employee.calculateBonus());
                }
                else
                {
                    System.out.println("Employee not found.");
                }

                rs.close();
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter a valid employee ID.");
        }
        catch (SQLException e)
        {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private void updateEmployee()
    {
        try
        {
            System.out.print("Enter employee ID to update: ");
            int id = Integer.parseInt(keyboard.nextLine());

            System.out.print("Enter new name: ");
            String name = keyboard.nextLine().trim();

            if (name.isEmpty())
            {
                throw new InvalidDataException("Name cannot be empty.");
            }

            System.out.print("Enter new age: ");
            int age = Integer.parseInt(keyboard.nextLine());

            if (age <= 0)
            {
                throw new InvalidDataException("Age must be greater than 0.");
            }

            System.out.print("Enter new department: ");
            String department = keyboard.nextLine().trim();

            if (department.isEmpty())
            {
                throw new InvalidDataException("Department cannot be empty.");
            }

            System.out.print("Enter new salary: ");
            double salary = Double.parseDouble(keyboard.nextLine());

            if (salary <= 0)
            {
                throw new InvalidDataException("Salary must be greater than 0.");
            }

            System.out.print("Enter new employee type (manager/regular): ");
            String employeeType = keyboard.nextLine().trim().toLowerCase();

            if (!employeeType.equals("manager") && !employeeType.equals("regular"))
            {
                throw new InvalidDataException("Employee type must be manager or regular.");
            }

            String sql = "UPDATE employee SET name = ?, age = ?, department = ?, salary = ?, employee_type = ? WHERE id = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setString(1, name);
                stmt.setInt(2, age);
                stmt.setString(3, department);
                stmt.setDouble(4, salary);
                stmt.setString(5, employeeType);
                stmt.setInt(6, id);

                int rows = stmt.executeUpdate();
                System.out.println(rows + " employee updated successfully.");
            }
        }
        catch (InvalidDataException e)
        {
            System.out.println("Validation Error: " + e.getMessage());
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter valid numeric input.");
        }
        catch (SQLException e)
        {
            System.out.println("Database Error: " + e.getMessage());
        }
    }

    private void deleteEmployee()
    {
        try
        {
            System.out.print("Enter employee ID to delete: ");
            int id = Integer.parseInt(keyboard.nextLine());

            String sql = "DELETE FROM employee WHERE id = ?";

            try (Connection conn = DatabaseManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql))
            {
                stmt.setInt(1, id);

                int rows = stmt.executeUpdate();
                System.out.println(rows + " employee deleted successfully.");
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("Please enter a valid employee ID.");
        }
        catch (SQLException e)
        {
            System.out.println("Database Error: " + e.getMessage());
        }
    }
}