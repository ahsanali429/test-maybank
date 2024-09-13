package com.test.maybank.customerapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    public List<Customer> getAllCustomers() throws SQLException {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = DBUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setShortName(rs.getString("short_name"));
                customer.setFullName(rs.getString("full_name"));
                customer.setAddress1(rs.getString("address1"));
                customer.setAddress2(rs.getString("address2"));
                customer.setAddress3(rs.getString("address3"));
                customer.setCity(rs.getString("city"));
                customer.setPostalCode(rs.getString("postal_code"));
                customers.add(customer);
            }
        }
        return customers;
    }

    public Customer getCustomerByShortName(String shortName) throws SQLException {
        String query = "SELECT * FROM customers WHERE short_name = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, shortName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getLong("id"));
                    customer.setShortName(rs.getString("short_name"));
                    customer.setFullName(rs.getString("full_name"));
                    customer.setAddress1(rs.getString("address1"));
                    customer.setAddress2(rs.getString("address2"));
                    customer.setAddress3(rs.getString("address3"));
                    customer.setCity(rs.getString("city"));
                    customer.setPostalCode(rs.getString("postal_code"));
                    return customer;
                }
            }
        }
        return null;
    }

    public void saveCustomer(Customer customer) throws SQLException {
        String query = customer.getId() == null ?
                "INSERT INTO customers (short_name, full_name, address1, address2, address3, city, postal_code) VALUES (?, ?, ?, ?, ?, ?, ?)" :
                "UPDATE customers SET short_name = ?, full_name = ?, address1 = ?, address2 = ?, address3 = ?, city = ?, postal_code = ? WHERE id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, customer.getShortName());
            pstmt.setString(2, customer.getFullName());
            pstmt.setString(3, customer.getAddress1());
            pstmt.setString(4, customer.getAddress2());
            pstmt.setString(5, customer.getAddress3());
            pstmt.setString(6, customer.getCity());
            pstmt.setString(7, customer.getPostalCode());

            if (customer.getId() != null) {
                pstmt.setLong(8, customer.getId());
            }
            pstmt.executeUpdate();
        }
    }

    public void deleteCustomer(Long id) throws SQLException {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}

