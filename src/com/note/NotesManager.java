package com.note;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class NotesManager {
	private static final String FILE_NAME = "notes.txt";
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== Text-Based Notes Manager =====");
            System.out.println("1. Add a Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Delete a Note");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    addNote();
                    break;
                case "2":
                    viewAllNotes();
                    break;
                case "3":
                    deleteNote();
                    break;
                case "4":
                    System.out.println("Exiting Notes Manager.");
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    // Add a note
    private static void addNote() {
        System.out.print("Enter your note: ");
        String note = scanner.nextLine();

        try (FileWriter writer = new FileWriter(FILE_NAME, true)) {
            writer.write(note + System.lineSeparator());
            System.out.println("Note saved successfully!");
        } catch (IOException e) {
            System.out.println("Error writing note: " + e.getMessage());
        }
    }

    // View all notes with numbering
    private static List<String> viewAllNotes() {
        List<String> notes = new ArrayList<>();
        System.out.println("\n     All Notes    ");
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            int index = 1;
            while ((line = reader.readLine()) != null) {
                notes.add(line);
                System.out.println(index + ". " + line);
                index++;
            }
            if (notes.isEmpty()) {
                System.out.println("(No notes found)");
            }
        } catch (FileNotFoundException e) {
            System.out.println("(No notes file found yet)");
        } catch (IOException e) {
            System.out.println("Error reading notes: " + e.getMessage());
        }
        return notes;
    }
    // Delete a note
    private static void deleteNote() {
        List<String> notes = viewAllNotes();
        if (notes.isEmpty()) {
            return;
        }
        System.out.print("Enter the note number to delete: ");
        try {
            int noteNumber = Integer.parseInt(scanner.nextLine());
            if (noteNumber < 1 || noteNumber > notes.size()) {
                System.out.println("Invalid note number.");
                return;
            }
            notes.remove(noteNumber - 1); // Remove chosen note

            try (FileWriter writer = new FileWriter(FILE_NAME, false)) {
                for (String note : notes) {
                    writer.write(note + System.lineSeparator());
                }
            }
            System.out.println("Note deleted successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (IOException e) {
            System.out.println("Error updating notes: " + e.getMessage());
        }
    }

}
