package com.test.maybank.customerapp;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class CustomerAppSwing extends JFrame {

    private final JComboBox<String> customerComboBox;
    private final JTextArea addressTextArea;
    private final JTextField shortNameField, fullNameField, cityField, postalCodeField;
    private final JButton addButton;
    private final JButton modifyButton;
    private final JButton deleteButton;
    private final CustomerDAO customerDAO;

    public CustomerAppSwing(CustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
        setTitle("Customer Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create UI Components
        customerComboBox = new JComboBox<>();
        addressTextArea = new JTextArea(10, 40);
        addressTextArea.setEditable(false);

        shortNameField = new JTextField(20);
        fullNameField = new JTextField(20);
        cityField = new JTextField(20);
        postalCodeField = new JTextField(10);

        addButton = new JButton("Add");
        modifyButton = new JButton("Modify");
        deleteButton = new JButton("Delete");

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Customer:"));
        topPanel.add(customerComboBox);

        JPanel addressPanel = new JPanel();
        addressPanel.setLayout(new BorderLayout());
        addressPanel.add(new JLabel("Address:"), BorderLayout.NORTH);
        addressPanel.add(new JScrollPane(addressTextArea), BorderLayout.CENTER);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2));
        formPanel.add(new JLabel("Short Name:"));
        formPanel.add(shortNameField);
        formPanel.add(new JLabel("Full Name:"));
        formPanel.add(fullNameField);
        formPanel.add(new JLabel("City:"));
        formPanel.add(cityField);
        formPanel.add(new JLabel("Postal Code:"));
        formPanel.add(postalCodeField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);

        add(topPanel, BorderLayout.NORTH);
        add(addressPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.SOUTH);

        // Event Handling
        customerComboBox.addActionListener(e -> loadCustomerDetails());
        addButton.addActionListener(e -> addCustomer());
        modifyButton.addActionListener(e -> modifyCustomer());
        deleteButton.addActionListener(e -> deleteCustomer());

        loadCustomers();
    }

    private void loadCustomers() {
        try {
            List<Customer> customers = customerDAO.getAllCustomers();
            customerComboBox.removeAllItems();
            for (Customer customer : customers) {
                customerComboBox.addItem(customer.getShortName());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading customers", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadCustomerDetails() {
        String selectedShortName = (String) customerComboBox.getSelectedItem();
        if (selectedShortName != null) {
            try {
                Customer customer = customerDAO.getCustomerByShortName(selectedShortName);
                if (customer != null) {
                    shortNameField.setText(customer.getShortName());
                    fullNameField.setText(customer.getFullName());
                    cityField.setText(customer.getCity());
                    postalCodeField.setText(customer.getPostalCode());
                    addressTextArea.setText(customer.getAddress1() + "\n" +
                            customer.getAddress2() + "\n" +
                            customer.getAddress3());
                } else {
                    clearFields();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error loading customer details", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addCustomer() {
        try {
            Customer customer = new Customer();
            customer.setShortName(shortNameField.getText());
            customer.setFullName(fullNameField.getText());
            customer.setAddress1(addressTextArea.getText().split("\n")[0]);
            customer.setAddress2(addressTextArea.getText().split("\n").length > 1 ? addressTextArea.getText().split("\n")[1] : "");
            customer.setAddress3(addressTextArea.getText().split("\n").length > 2 ? addressTextArea.getText().split("\n")[2] : "");
            customer.setCity(cityField.getText());
            customer.setPostalCode(postalCodeField.getText());

            if (validatePostalCode(customer.getPostalCode())) {
                customerDAO.saveCustomer(customer);
                loadCustomers();
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Invalid postal code", "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding customer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void modifyCustomer() {
        try {
            String selectedShortName = (String) customerComboBox.getSelectedItem();
            if (selectedShortName != null) {
                Customer customer = customerDAO.getCustomerByShortName(selectedShortName);
                if (customer != null) {
                    customer.setShortName(shortNameField.getText());
                    customer.setFullName(fullNameField.getText());
                    customer.setAddress1(addressTextArea.getText().split("\n")[0]);
                    customer.setAddress2(addressTextArea.getText().split("\n").length > 1 ? addressTextArea.getText().split("\n")[1] : "");
                    customer.setAddress3(addressTextArea.getText().split("\n").length > 2 ? addressTextArea.getText().split("\n")[2] : "");
                    customer.setCity(cityField.getText());
                    customer.setPostalCode(postalCodeField.getText());

                    if (validatePostalCode(customer.getPostalCode())) {
                        customerDAO.saveCustomer(customer);
                        loadCustomers();
                        clearFields();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid postal code", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error modifying customer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        try {
            String selectedShortName = (String) customerComboBox.getSelectedItem();
            if (selectedShortName != null) {
                Customer customer = customerDAO.getCustomerByShortName(selectedShortName);
                if (customer != null) {
                    int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this customer?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        customerDAO.deleteCustomer(customer.getId());
                        loadCustomers();
                        clearFields();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error deleting customer", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean validatePostalCode(String postalCode) {
        return postalCode.matches("\\d{5}"); // Example postal code validation (5-digit ZIP code)
    }

    private void clearFields() {
        shortNameField.setText("");
        fullNameField.setText("");
        cityField.setText("");
        postalCodeField.setText("");
        addressTextArea.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerDAO customerDAO = new CustomerDAO(); // Instantiate DAO here
            new CustomerAppSwing(customerDAO).setVisible(true);
        });
    }
}
