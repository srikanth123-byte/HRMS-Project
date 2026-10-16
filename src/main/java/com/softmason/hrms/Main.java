package com.softmason.hrms;
import java.sql.*;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);
    static Connection con;

    static int orgId;
    static String orgName;

    public static void main(String[] args) {

        try {
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/hrms_db",
                    "root",
                    "srikanth@123"
            );

            System.out.println("===== HRMS SYSTEM =====");
            mainMenu();

            con.close();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // MAIN MENU
    static void mainMenu() throws Exception {

        while (true) {
            System.out.println("\n1. Create Organization");
            System.out.println("2. Login to Organization");
            System.out.println("3. Exit");

            int choice = readInt("Enter option: ");

            switch (choice) {
                case 1:
                    createOrganization();
                    break;

                case 2:
                    loginMenu();
                    break;

                case 3:
                    System.out.println("Thank you!");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    // CREATE ORGANIZATION
    static void createOrganization() throws Exception {

        System.out.print("Organization Name: ");
        String name = sc.nextLine();

        System.out.print("Address: ");
        String address = sc.nextLine();

        System.out.print("PAN: ");
        String pan = sc.nextLine();

        System.out.print("GST: ");
        String gst = sc.nextLine();

        System.out.print("Department: ");
        String department = sc.nextLine();

        System.out.print("Login Email: ");
        String email = sc.nextLine();

        System.out.print("Password: ");
        String password = sc.nextLine();

        int count = readInt("Employee Count: ");

        if (count < 0) {
            System.out.println("Invalid employee count");
            return;
        }

        String sql = "INSERT INTO organizations " +
                "(name,address,pan,gst,department,email,password,employee_count) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, pan);
            ps.setString(4, gst);
            ps.setString(5, department);
            ps.setString(6, email);
            ps.setString(7, password);
            ps.setInt(8, count);

            ps.executeUpdate();

            System.out.println("Organization Created Successfully!");

        } catch (SQLException e) {
            System.out.println("Organization creation failed: "
                    + e.getMessage());
        }
    }

    // LOGIN MENU
    static void loginMenu() throws Exception {

        while (true) {
            System.out.println("\n1. Login");
            System.out.println("2. Forgot Password");
            System.out.println("3. Back to Main Menu");
            System.out.println("4. Exit");

            int choice = readInt("Enter option: ");

            switch (choice) {
                case 1:
                    login();
                    break;

                case 2:
                    forgotPassword();
                    break;

                case 3:
                    return;

                case 4:
                    System.out.println("Thank you!");
                    System.exit(0);
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    // LOGIN
    static void login() throws Exception {

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        String sql = "SELECT id,name FROM organizations " +
                "WHERE email=? AND password=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    orgId = rs.getInt("id");
                    orgName = rs.getString("name");

                    System.out.println("Welcome to " + orgName);

                    organizationMenu();

                } else {
                    System.out.println("Invalid Email or Password");
                }
            }
        }
    }

    // FORGOT PASSWORD
    static void forgotPassword() throws Exception {

        System.out.print("Enter Email: ");
        String email = sc.nextLine();

        System.out.print("Enter New Password: ");
        String password = sc.nextLine();

        String sql = "UPDATE organizations SET password=? WHERE email=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, password);
            ps.setString(2, email);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Password Updated Successfully");
            } else {
                System.out.println("Email Not Found");
            }
        }
    }

    // ORGANIZATION MENU
    static void organizationMenu() throws Exception {

        while (true) {
            System.out.println("\n===== " + orgName + " =====");
            System.out.println("1. Employee");
            System.out.println("2. Attendance");
            System.out.println("3. Salary");
            System.out.println("4. Notice / Holiday");
            System.out.println("5. Logout");

            int choice = readInt("Enter option: ");

            switch (choice) {
                case 1:
                    employeeMenu();
                    break;

                case 2:
                    attendanceMenu();
                    break;

                case 3:
                    salaryMenu();
                    break;

                case 4:
                    noticeMenu();
                    break;

                case 5:
                    System.out.println("Logged Out Successfully");
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    // EMPLOYEE MENU
    static void employeeMenu() throws Exception {

        while (true) {
            System.out.println("\n--- EMPLOYEE MENU ---");
            System.out.println("1. Add Employee");
            System.out.println("2. View Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Back");

            int choice = readInt("Enter option: ");

            switch (choice) {
                case 1:
                    addEmployee();
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    updateEmployee();
                    break;

                case 4:
                    deleteEmployee();
                    break;

                case 5:
                    return;

                default:
                    System.out.println("Invalid option");
            }
        }
    }

    static void addEmployee() throws Exception {

        System.out.print("Employee Name: ");
        String name = sc.nextLine();

        System.out.print("Email: ");
        String email = sc.nextLine();

        System.out.print("Phone: ");
        String phone = sc.nextLine();

        System.out.print("Department: ");
        String department = sc.nextLine();

        String sql = "INSERT INTO employees " +
                "(organization_id,name,email,phone,department) " +
                "VALUES (?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);
            ps.setString(2, name);
            ps.setString(3, email);
            ps.setString(4, phone);
            ps.setString(5, department);

            ps.executeUpdate();

            System.out.println("Employee Added Successfully");
        }
    }


    static void viewEmployees() throws Exception {

        String sql = "SELECT * FROM employees WHERE organization_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);

            try (ResultSet rs = ps.executeQuery()) {

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    System.out.println(
                            "ID: " + rs.getInt("employee_id")
                                    + " | Name: " + rs.getString("name")
                                    + " | Email: " + rs.getString("email")
                                    + " | Phone: " + rs.getString("phone")
                                    + " | Department: " + rs.getString("department")
                    );
                }

                if (!found) {
                    System.out.println("No Employees Found");
                }
            }
        }
    }


    static void updateEmployee() throws Exception {

        int id = readInt("Enter Employee ID: ");

        System.out.print("New Name: ");
        String name = sc.nextLine();

        System.out.print("New Email: ");
        String email = sc.nextLine();

        System.out.print("New Phone: ");
        String phone = sc.nextLine();

        System.out.print("New Department: ");
        String department = sc.nextLine();

        String sql = "UPDATE employees SET name=?, email=?, phone=?, department=? " +
                "WHERE employee_id=? AND organization_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, phone);
            ps.setString(4, department);
            ps.setInt(5, id);
            ps.setInt(6, orgId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Employee Updated Successfully");
            } else {
                System.out.println("Employee Not Found");
            }
        }
    }

    static void deleteEmployee() throws Exception {

        int id = readInt("Enter Employee ID: ");

        String sql = "DELETE FROM employees WHERE employee_id=? AND organization_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, orgId);

            if (ps.executeUpdate() > 0) {
                System.out.println("Employee Deleted Successfully");
            } else {
                System.out.println("Employee Not Found");
            }
        }
    }

    // ATTENDANCE MENU
    static void attendanceMenu() throws Exception {

        while (true) {
            System.out.println("\n--- ATTENDANCE MENU ---");
            System.out.println("1. Create Attendance");
            System.out.println("2. View Attendance");
            System.out.println("3. Update Attendance");
            System.out.println("4. Delete Attendance");
            System.out.println("5. Back");

            int choice = readInt("Enter option: ");


            try {
                switch (choice) {

                    case 1:
                        createAttendance();
                        break;

                    case 2:
                        viewRecords("attendance",
                                "attendance_id,employee_id,attendance_date,status");
                        break;

                    case 3:
                        updateAttendance();
                        break;

                    case 4:
                        deleteRecord("attendance");
                        break;

                    case 5:
                        return;

                    default:
                        System.out.println("Invalid option");
                }

            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    static void createAttendance() throws Exception {

        int employeeId = readInt("Employee ID: ");

        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        System.out.print("Status (PRESENT/ABSENT/LEAVE): ");
        String status = sc.nextLine().toUpperCase();

        if (!status.equals("PRESENT") &&
                !status.equals("ABSENT") &&
                !status.equals("LEAVE")) {
            System.out.println("Invalid status");
            return;
        }

        String sql = "INSERT INTO attendance " +
                "(organization_id,employee_id,attendance_date,status) " +
                "VALUES (?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);
            ps.setInt(2, employeeId);
            ps.setDate(3, Date.valueOf(date));
            ps.setString(4, status);

            ps.executeUpdate();

            System.out.println("Attendance Created Successfully");
        }
    }


    static void viewRecords(String table, String columns) throws Exception {

        String sql = "SELECT " + columns + " FROM " + table
                + " WHERE organization_id = ?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);

            try (ResultSet rs = ps.executeQuery()) {

                ResultSetMetaData md = rs.getMetaData();
                int count = md.getColumnCount();
                boolean found = false;

                while (rs.next()) {
                    found = true;

                    for (int i = 1; i <= count; i++) {
                        System.out.print(
                                md.getColumnName(i) + ": "
                                        + rs.getString(i) + " | "
                        );
                    }

                    System.out.println();
                }

                if (!found) {
                    System.out.println("No Records Found");
                }
            }
        }
    }


    static void updateAttendance() throws Exception {

        int id = readInt("Attendance Record ID: ");

        System.out.print("New Status (PRESENT/ABSENT/LEAVE): ");
        String status = sc.nextLine().toUpperCase();

        if (!status.equals("PRESENT") &&
                !status.equals("ABSENT") &&
                !status.equals("LEAVE")) {
            System.out.println("Invalid status");
            return;
        }

        String sql = "UPDATE attendance SET status=? " +
                "WHERE attendance_id=? AND organization_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setInt(2, id);
            ps.setInt(3, orgId);

            System.out.println(ps.executeUpdate() > 0
                    ? "Attendance Updated" : "Record Not Found");
        }
    }


    // SALARY MENU
    static void salaryMenu() throws Exception {

        while (true) {
            System.out.println("\n--- SALARY MENU ---");
            System.out.println("1. Create Salary");
            System.out.println("2. View Salary");
            System.out.println("3. Update Salary");
            System.out.println("4. Delete Salary");
            System.out.println("5. Back");

            int choice = readInt("Enter option: ");

            switch (choice) {
                case 1:
                    createSalary();
                    break;
                case 2:
                    viewRecords("salaries",
                            "salary_id,employee_id,salary_month,basic,allowances,deductions,net_salary");
                    break;
                case 3:
                    updateSalary();
                    break;
                case 4:
                    deleteRecord("salaries");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    static void createSalary() throws Exception {

        int employeeId = readInt("Employee ID: ");

        System.out.print("Salary Month (YYYY-MM): ");
        String month = sc.nextLine();

        double basic = readDouble("Basic Salary: ");
        double allowances = readDouble("Allowances: ");
        double deductions = readDouble("Deductions: ");

        double net = basic + allowances - deductions;

        if (basic < 0 || allowances < 0 ||
                deductions < 0 || net < 0) {
            System.out.println("Invalid salary amounts");
            return;
        }

        String sql = "INSERT INTO salaries " +
                "(organization_id,employee_id,salary_month,basic,allowances,deductions,net_salary) " +
                "VALUES (?,?,?,?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);
            ps.setInt(2, employeeId);
            ps.setString(3, month);
            ps.setDouble(4, basic);
            ps.setDouble(5, allowances);
            ps.setDouble(6, deductions);
            ps.setDouble(7, net);

            ps.executeUpdate();

            System.out.println("Salary Created Successfully");
            System.out.println("Net Salary: " + net);
        }
    }

    static void updateSalary() throws Exception {

        int id = readInt("Salary Record ID: ");

        double basic = readDouble("New Basic Salary: ");
        double allowances = readDouble("New Allowances: ");
        double deductions = readDouble("New Deductions: ");

        double net = basic + allowances - deductions;

        if (basic < 0 || allowances < 0 ||
                deductions < 0 || net < 0) {
            System.out.println("Invalid salary amounts");
            return;
        }

        String sql = "UPDATE salaries SET basic=?,allowances=?,deductions=?,net_salary=? " +
                "WHERE salary_id=? AND organization_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, basic);
            ps.setDouble(2, allowances);
            ps.setDouble(3, deductions);
            ps.setDouble(4, net);
            ps.setInt(5, id);
            ps.setInt(6, orgId);

            System.out.println(ps.executeUpdate() > 0
                    ? "Salary Updated" : "Salary Record Not Found");
        }
    }

    // NOTICE / HOLIDAY MENU
    static void noticeMenu() throws Exception {

        while (true) {
            System.out.println("\n--- NOTICE / HOLIDAY MENU ---");
            System.out.println("1. Create Notice");
            System.out.println("2. View Notices");
            System.out.println("3. Update Notice");
            System.out.println("4. Delete Notice");
            System.out.println("5. Back");

            int choice = readInt("Enter option: ");

            switch (choice) {
                case 1:
                    createNotice();
                    break;
                case 2:
                    viewRecords("notices",
                            "notice_id,title,message,notice_date");
                    break;
                case 3:
                    updateNotice();
                    break;
                case 4:
                    deleteRecord("notices");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option");
            }
        }
    }

    static void createNotice() throws Exception {

        System.out.print("Notice Title: ");
        String title = sc.nextLine();

        System.out.print("Notice Message: ");
        String message = sc.nextLine();

        System.out.print("Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        String sql = "INSERT INTO notices " +
                "(organization_id,title,message,notice_date) " +
                "VALUES (?,?,?,?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, orgId);
            ps.setString(2, title);
            ps.setString(3, message);
            ps.setDate(4, Date.valueOf(date));

            ps.executeUpdate();

            System.out.println("Notice Created Successfully");
        }
    }

    static void updateNotice() throws Exception {

        int id = readInt("Notice ID: ");

        System.out.print("New Title: ");
        String title = sc.nextLine();

        System.out.print("New Message: ");
        String message = sc.nextLine();

        System.out.print("New Date (YYYY-MM-DD): ");
        String date = sc.nextLine();

        String sql = "UPDATE notices SET title=?,message=?,notice_date=? " +
                "WHERE notice_id=? AND organization_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, title);
            ps.setString(2, message);
            ps.setDate(3, Date.valueOf(date));
            ps.setInt(4, id);
            ps.setInt(5, orgId);

            System.out.println(ps.executeUpdate() > 0
                    ? "Notice Updated" : "Notice Not Found");
        }
    }


    // COMMON DELETE METHOD FOR ATTENDANCE, SALARY, NOTICE
    static void deleteRecord(String table) throws Exception {

        int id = readInt("Enter Record ID: ");

        String idColumn;

        if (table.equals("attendance")) {
            idColumn = "attendance_id";
        } else if (table.equals("salaries")) {
            idColumn = "salary_id";
        } else if (table.equals("notices")) {
            idColumn = "notice_id";
        } else {
            System.out.println("Invalid table name");
            return;
        }

        String sql = "DELETE FROM " + table
                + " WHERE " + idColumn
                + "=? AND organization_id=?";

        try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.setInt(2, orgId);

            System.out.println(ps.executeUpdate() > 0
                    ? "Record Deleted Successfully"
                    : "Record Not Found");
        }
    }


    // INPUT METHODS
    static int readInt(String message) {

        while (true) {
            try {
                System.out.print(message);
                int value = Integer.parseInt(sc.nextLine());
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number");
            }
        }
    }

    static double readDouble(String message) {

        while (true) {
            try {
                System.out.print(message);
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid amount");
            }
        }
    }
}
