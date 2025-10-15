package view;

import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;
import util.CsvExporter;

import javax.swing.*;

import domain.UserDomain;

public class MainView {
    private final BookView bookView = new BookView();
    private final LoansView loansView = new LoansView();
    private final BulkLoading bulkLoading = new BulkLoading();

    public void show(UserDomain user) {
        while (true) {
            String[] options = {
                    "Books Management",
                    "Loans",
                    "Bulk Loading",
                    "Export Data to CSV",
                    "Close Session"
            };

            String title = "Main menu - LibroNOVA";
            String message = "Welcome: " + user.getNameUser() + " (" + user.getRol() + ")";

            int choice = JOptionPane.showOptionDialog(
                    null,
                    message,
                    title,
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]);

            if (choice == -1 || choice == options.length - 1) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Are you sure you want to close the session?",
                        "Confirm Close Session",
                        JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    break;
                }
            } else {
                try {
                    switch (choice) {
                        case 0 -> showBookOptions();
                        case 1 -> loansView.show();
                        case 2 -> bulkLoading.show();
                        case 3 -> exportDataToCsv();
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void showBookOptions() {
        String[] options = {
                "1. View all books",
                "2. Search book by ISBN",
                "3. Add book",
                "4. Update book",
                "5. Delete book",
                "6. Return to main menu"
        };

        while (true) {
            StringBuilder message = new StringBuilder("Choose an option:\n");
            for (String option : options) {
                message.append(option).append("\n");
            }

            String input = JOptionPane.showInputDialog(
                    null,
                    message.toString(),
                    "Books Management",
                    JOptionPane.PLAIN_MESSAGE);

            if (input == null) {
                break;
            }

            input = input.trim();
            if (input.isEmpty()) {
                continue;
            }

            try {
                int choice = Integer.parseInt(input) - 1;

                if (choice < 0 || choice >= options.length) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Invalid option. Please enter a number between 1 and " + options.length,
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    continue;
                }

                if (choice == 5) {
                    break;
                }

                switch (choice) {
                    case 0 -> bookView.showAllBooks();
                    case 1 -> {
                        String isbn = JOptionPane.showInputDialog("Enter the ISBN of the book:");
                        if (isbn != null && !isbn.trim().isEmpty()) {
                            bookView.searchBookByIsbn(isbn);
                        }
                    }
                    case 2 -> bookView.addBook();
                    case 3 -> bookView.updateBook();
                    case 4 -> {
                        String isbn = JOptionPane.showInputDialog("Enter the ISBN of the book to delete:");
                        if (isbn != null && !isbn.trim().isEmpty()) {
                            bookView.deleteBook(isbn);
                        }
                    }
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Please enter a valid number.",
                        "Input Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (BadRequestException | ConflictException | NotFoundException e) {
                JOptionPane.showMessageDialog(
                        null,
                        e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        null,
                        "Unexpected error: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportDataToCsv() {
        String[] tables = { "books", "users", "loans" };
        String selectedTable = (String) JOptionPane.showInputDialog(
                null,
                "Select the table to export:",
                "Export to CSV",
                JOptionPane.QUESTION_MESSAGE,
                null,
                tables,
                tables[0]);

        if (selectedTable != null) {
            String defaultPath = System.getProperty("user.home") + "/" + selectedTable + "_export.csv";
            String filePath = JOptionPane.showInputDialog(
                    "Enter the path to save the file:",
                    defaultPath);

            if (filePath != null && !filePath.trim().isEmpty()) {
                if (!filePath.endsWith(".csv")) {
                    filePath += ".csv";
                }

                boolean success = CsvExporter.exportToCsv(selectedTable, filePath);
                if (success) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Data successfully exported to:\n" + filePath,
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error exporting data. Please try again.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}