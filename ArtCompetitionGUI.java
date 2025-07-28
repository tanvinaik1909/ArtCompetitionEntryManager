package com.artcomp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ArtCompetitionGUI extends JFrame {

    public ArtCompetitionGUI() {
        setTitle("Art Competition Entry Manager");
        setSize(736, 1104);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(new JLabel(new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\ArtCompetitionProject\\com\\artcomp\\BG1.jpg")));
        setLayout(null);
        setResizable(false);

        JLabel heading = new JLabel("Art Competition Entry");
        heading.setFont(new Font("Arial", Font.BOLD, 24));
        heading.setBounds(220, 40, 400, 30);
        heading.setForeground(Color.BLACK);
        add(heading);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(200, 120, 80, 25);
        JTextField nameField = new JTextField();
        nameField.setBounds(300, 120, 200, 25);
        add(nameLabel);
        add(nameField);

        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setBounds(200, 170, 80, 25);
        JTextField ageField = new JTextField();
        ageField.setBounds(300, 170, 200, 25);
        add(ageLabel);
        add(ageField);

        JLabel artLabel = new JLabel("Art Type:");
        artLabel.setBounds(200, 220, 80, 25);
        JTextField artField = new JTextField();
        artField.setBounds(300, 220, 200, 25);
        add(artLabel);
        add(artField);

        JLabel contactLabel = new JLabel("Contact:");
        contactLabel.setBounds(200, 270, 80, 25);
        JTextField contactField = new JTextField();
        contactField.setBounds(300, 270, 200, 25);
        add(contactLabel);
        add(contactField);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(200, 320, 80, 25);
        JTextField addressField = new JTextField();
        addressField.setBounds(300, 320, 200, 25);
        add(addressLabel);
        add(addressField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(300, 370, 100, 30);
        add(submitButton);

        JButton viewButton = new JButton("View All Participants");
        viewButton.setBounds(260, 420, 180, 30);
        add(viewButton);

        JLabel statusLabel = new JLabel("");
        statusLabel.setBounds(200, 470, 400, 25);
        add(statusLabel);

        // ENTER key movement
        nameField.addActionListener(e -> ageField.requestFocus());
        ageField.addActionListener(e -> artField.requestFocus());
        artField.addActionListener(e -> contactField.requestFocus());
        contactField.addActionListener(e -> addressField.requestFocus());
        addressField.addActionListener(e -> submitButton.doClick());

        // Submit Logic
        submitButton.addActionListener(e -> {
            String name = nameField.getText();
            String ageText = ageField.getText();
            String art = artField.getText();
            String contact = contactField.getText();
            String address = addressField.getText();

            if (name.isEmpty() || ageText.isEmpty() || art.isEmpty() || contact.isEmpty() || address.isEmpty()) {
                statusLabel.setText("Please fill all fields!");
                return;
            }

            try {
                int age = Integer.parseInt(ageText);
                String sql = "INSERT INTO participants(name, age, art_type, contact, address) VALUES (?, ?, ?, ?, ?)";
                try (Connection conn = DBConnection.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {
                    stmt.setString(1, name);
                    stmt.setInt(2, age);
                    stmt.setString(3, art);
                    stmt.setString(4, contact);
                    stmt.setString(5, address);
                    stmt.executeUpdate();

                    statusLabel.setText("✅ Participant added successfully!");
                    nameField.setText("");
                    ageField.setText("");
                    artField.setText("");
                    contactField.setText("");
                    addressField.setText("");
                    nameField.requestFocus();
                }
            } catch (NumberFormatException nfe) {
                statusLabel.setText("Age must be a number!");
            } catch (Exception ex) {
                statusLabel.setText("❌ Error: " + ex.getMessage());
            }
        });

        // View button logic
        viewButton.addActionListener(e -> showParticipantsTable());

        setVisible(true);
    }

    // METHOD: View All Participants with Background
    private void showParticipantsTable() {
        JFrame tableFrame = new JFrame("All Participants");
        tableFrame.setSize(736,1104);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Set Background Image
        JLabel background = new JLabel(new ImageIcon("C:\\Users\\hp\\OneDrive\\Desktop\\ArtCompetitionProject\\com\\artcomp\\bgg2.jpg"));
        tableFrame.setContentPane(background);
        tableFrame.setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel();
        JTable table = new JTable(model);
        model.addColumn("ID");
        model.addColumn("Name");
        model.addColumn("Age");
        model.addColumn("Art Type");
        model.addColumn("Contact");
        model.addColumn("Address");

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM participants");
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("age"),
                        rs.getString("art_type"),
                        rs.getString("contact"),
                        rs.getString("address")
                });
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "❌ Error fetching participants: " + ex.getMessage());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        table.setOpaque(false);

        tableFrame.add(scrollPane, BorderLayout.CENTER);
        tableFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new ArtCompetitionGUI();
    }
}
