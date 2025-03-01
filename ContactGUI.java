import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;

public class ContactGUI extends JFrame {
    private JTextField nameField, phoneField;
    private JTextArea contactList;
    private JButton addButton, updateButton, deleteButton, viewButton;

    public ContactGUI() {
        setTitle("Gestor de Contactos");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        add(new JLabel("Nombre:"));
        nameField = new JTextField(20);
        add(nameField);

        add(new JLabel("Teléfono:"));
        phoneField = new JTextField(20);
        add(phoneField);

        addButton = new JButton("Agregar");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        viewButton = new JButton("Ver Contactos");

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);

        contactList = new JTextArea(10, 30);
        contactList.setEditable(false);
        add(new JScrollPane(contactList));

        // Eventos de los botones
        addButton.addActionListener(e -> addContact());
        viewButton.addActionListener(e -> viewContacts());
        updateButton.addActionListener(e -> updateContact());
        deleteButton.addActionListener(e -> deleteContact());
    }

    private void addContact() {
        try {
            String name = nameField.getText();
            String phone = phoneField.getText();
            if (!name.isEmpty() && !phone.isEmpty()) {
                ContactManager.addContact(new Contact(name, phone));
                JOptionPane.showMessageDialog(this, "Contacto agregado");
                nameField.setText("");
                phoneField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Ingrese datos válidos");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar contacto");
        }
    }

    private void viewContacts() {
        try {
            List<Contact> contacts = ContactManager.getContacts();
            contactList.setText("");
            for (Contact c : contacts) {
                contactList.append("Nombre: " + c.getName() + ", Teléfono: " + c.getPhoneNumber() + "\n");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al leer contactos");
        }
    }

    private void updateContact() {
        try {
            String oldName = JOptionPane.showInputDialog(this, "Ingrese nombre del contacto a actualizar:");
            String newName = nameField.getText();
            String newPhone = phoneField.getText();
            if (oldName != null && !newName.isEmpty() && !newPhone.isEmpty()) {
                boolean updated = ContactManager.updateContact(oldName, new Contact(newName, newPhone));
                JOptionPane.showMessageDialog(this, updated ? "Contacto actualizado" : "Contacto no encontrado");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar contacto");
        }
    }

    private void deleteContact() {
        try {
            String name = JOptionPane.showInputDialog(this, "Ingrese el nombre a eliminar:");
            boolean deleted = ContactManager.deleteContact(name);
            JOptionPane.showMessageDialog(this, deleted ? "Contacto eliminado" : "Contacto no encontrado");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar contacto");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ContactGUI().setVisible(true));
    }
}
