package controllers;

import models.User;
import connexion.Connexion;

import java.sql.*;
import java.util.*;

public class UsersController {

    public static void handleAddUser(String fullname, String cin, String passport, String phone, String email, String password, String address) {
        String sql = "INSERT INTO user(fullName, cin, passport, phone, email, password, address, role, account_status) VALUES(?,?,?,?,?,?,?,?,?)";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, fullname);
            pstmt.setString(2, cin);
            pstmt.setString(3, passport);
            pstmt.setString(4, phone);
            pstmt.setString(5, email);
            pstmt.setString(6, password);
            pstmt.setString(7, address);
            pstmt.setString(8, "User");
            pstmt.setString(9, "Active");
            pstmt.executeUpdate();

            System.out.println("User added successfully!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE role='User'";
        try (Connection conn = Connexion.obtenirConnexion();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fN = rs.getString("fullName");
                String c = rs.getString("cin");
                String passp = rs.getString("passport");
                String ph = rs.getString("phone");
                String em = rs.getString("email");
                String pass = rs.getString("password");
                String ad = rs.getString("address");
                String r = rs.getString("role");
                String as = rs.getString("account_status");

                User u = new User(userId, fN, c, passp, ph, em, pass, ad, r, as);
                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public static User getUserById(int userId) {
        String sql = "SELECT * FROM user WHERE user_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String fN = rs.getString("fullName");
                String c = rs.getString("cin");
                String passp = rs.getString("passport");
                String ph = rs.getString("phone");
                String em = rs.getString("email");
                String pass = rs.getString("password");
                String ad = rs.getString("address");
                String r = rs.getString("role");
                String as = rs.getString("account_status");

                return new User(userId, fN, c, passp, ph, em, pass, ad, r, as);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static User updateUser(int userId, String fullname, String cin, String passport, String phone, String email, String password, String address, String accountStatus) {
        User oldUser = getUserById(userId);
        if (oldUser == null) {
            return null;
        }

        String newName = (fullname != null) ? fullname : oldUser.getFullName();
        String newCin = (cin != null) ? cin : oldUser.getCin();
        String newPassport = (passport != null) ? passport : oldUser.getPassport();
        String newPhone = (phone != null) ? phone : oldUser.getPhone();
        String newEmail = (email != null) ? email : oldUser.getEmail();
        String newPassword = (password != null) ? password : oldUser.getPassword();
        String newAddress = (address != null) ? address : oldUser.getAddress();
        String newAStatus = (accountStatus != null) ? accountStatus : oldUser.getAccountStatus();

        String sql = "UPDATE user SET fullName = ?, cin = ?, passport = ?, phone = ?, email = ?, password = ?, address = ?, account_status = ? WHERE user_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, newName);
            pstmt.setString(2, newCin);
            pstmt.setString(3, newPassport);
            pstmt.setString(4, newPhone);
            pstmt.setString(5, newEmail);
            pstmt.setString(6, newPassword);
            pstmt.setString(7, newAddress);
            pstmt.setString(8, newAStatus);
            pstmt.setInt(9, userId);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                return new User(userId, newName, newCin, newPassport, newPhone, newEmail, newPassword, newAddress, "User", newAStatus);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE user_id = ?";
        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("User deleted successfully!");
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<User> searchUsers(String query) {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user WHERE user_id LIKE ? OR fullName LIKE ? OR cin LIKE ? OR passport LIKE ? OR phone LIKE ? OR email LIKE ? OR password LIKE ? OR address LIKE ? OR role LIKE ? OR account_status LIKE ?";

        try (Connection conn = Connexion.obtenirConnexion();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchQuery = "%" + query + "%";
            pstmt.setString(1, searchQuery);
            pstmt.setString(2, searchQuery);
            pstmt.setString(3, searchQuery);
            pstmt.setString(4, searchQuery);
            pstmt.setString(5, searchQuery);
            pstmt.setString(6, searchQuery);
            pstmt.setString(7, searchQuery);
            pstmt.setString(8, searchQuery);
            pstmt.setString(9, searchQuery);
            pstmt.setString(10, searchQuery);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("user_id");
                String fN = rs.getString("fullName");
                String c = rs.getString("cin");
                String passp = rs.getString("passport");
                String ph = rs.getString("phone");
                String em = rs.getString("email");
                String pass = rs.getString("password");
                String ad = rs.getString("address");
                String r = rs.getString("role");
                String as = rs.getString("account_status");

                User u = new User(userId, fN, c, passp, ph, em, pass, ad, r, as);
                users.add(u);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }
}
