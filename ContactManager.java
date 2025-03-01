import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ContactManager {
    private static final String FILE_NAME = "contacts.txt";

    public static void addContact(Contact contact) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write(contact.toString());
            writer.newLine();
        }
    }

    public static List<Contact> getContacts() throws IOException {
        List<Contact> contacts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("!");
                if (parts.length == 2) {
                    contacts.add(new Contact(parts[0], parts[1]));
                }
            }
        }
        return contacts;
    }

    public static boolean updateContact(String oldName, Contact newContact) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("!");
                if (parts[0].equalsIgnoreCase(oldName)) {
                    writer.write(newContact.toString());
                    found = true;
                } else {
                    writer.write(line);
                }
                writer.newLine();
            }

            if (!found) {
                return false;
            }
        }

        if (!inputFile.delete()) {
            return false;
        }
        return tempFile.renameTo(inputFile);
    }

    public static boolean deleteContact(String name) throws IOException {
        File inputFile = new File(FILE_NAME);
        File tempFile = new File("temp.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("!");
                if (parts[0].equalsIgnoreCase(name)) {
                    found = true;
                    continue;
                }
                writer.write(line);
                writer.newLine();
            }

            if (!found) {
                return false;
            }
        }

        if (!inputFile.delete()) {
            return false;
        }
        return tempFile.renameTo(inputFile);
    }
}
