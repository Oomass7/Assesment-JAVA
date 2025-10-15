package view;

import service.BookService;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import domain.BookDomain;

import java.awt.*;
import java.util.List;

public class BookView {
    private final BookService bookService = new BookService();

    public void showAllBooks() {
        try {
            List<BookDomain> books = bookService.getAllBooks();
            if (books.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No books registered.");
                return;
            }
            
            String[] columnNames = {"ISBN", "Title", "Author", "Category", "Total", "Available", "Price"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            
            for (BookDomain book : books) {
                Object[] row = {
                    book.getIsbn(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getTotalCopies(),
                    book.getCopiesAvailable(),
                    String.format("$%.2f", book.getPriceRef())
                };
                model.addRow(row);
            }
            
            JTable table = new JTable(model);
            table.setFillsViewportHeight(true);
            table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            
            int[] columnWidths = {100, 200, 150, 100, 60, 80, 80};
            for (int i = 0; i < columnWidths.length; i++) {
                table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
            }
            
            JScrollPane scrollPane = new JScrollPane(table);
            scrollPane.setPreferredSize(new Dimension(800, 400));
            
            JOptionPane.showMessageDialog(null, scrollPane, "List of Books", JOptionPane.PLAIN_MESSAGE);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error loading books: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void searchBookByIsbn(String isbnStr) {
        try {
            int isbn = Integer.parseInt(isbnStr);
            BookDomain book = bookService.getBook(isbn);
            
            if (book == null) {
                JOptionPane.showMessageDialog(null, 
                    "No book found with the ISBN: " + isbn, 
                    "Not Found", 
                    JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            
            String message = String.format(
                "<html><body style='width: 300px;'>" +
                "<h3>Book Details</h3>" +
                "<p><b>ISBN:</b> %d</p>" +
                "<p><b>Title:</b> %s</p>" +
                "<p><b>Author:</b> %s</p>" +
                "<p><b>Category:</b> %s</p>" +
                "<p><b>Total copies:</b> %d</p>" +
                "<p><b>Available copies:</b> %d</p>" +
                "<p><b>Reference price:</b> $%.2f</p>" +
                "</body></html>",
                book.getIsbn(),
                book.getTitle(),
                book.getAuthor(),
                book.getCategory(),
                book.getTotalCopies(),
                book.getCopiesAvailable(),
                book.getPriceRef()
            );
            
            JOptionPane.showMessageDialog(null, message, "Book Found", JOptionPane.INFORMATION_MESSAGE);
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                "The ISBN must be a valid number.", 
                "Format Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, 
                "Error searching book: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public void addBook() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
        
        JTextField isbnField = new JTextField();
        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField totalCopiesField = new JTextField();
        JTextField availableCopiesField = new JTextField();
        JTextField priceField = new JTextField();
            
        panel.add(new JLabel("* ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("* Title:"));
        panel.add(titleField);
        panel.add(new JLabel("* Author:"));
        panel.add(authorField);
        panel.add(new JLabel("* Category:"));
        panel.add(categoryField);
        panel.add(new JLabel("* Total copies:"));
        panel.add(totalCopiesField);
        panel.add(new JLabel("* Available copies:"));
        panel.add(availableCopiesField);
        panel.add(new JLabel("* Reference price:"));
        panel.add(priceField);
        
        panel.add(new JLabel("* Campos obligatorios"));
        
        int result = JOptionPane.showConfirmDialog(
            null, 
            panel, 
            "Add New Book",
            JOptionPane.OK_CANCEL_OPTION,
            JOptionPane.PLAIN_MESSAGE
        );
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                if (isbnField.getText().trim().isEmpty() || 
                    titleField.getText().trim().isEmpty() || 
                    authorField.getText().trim().isEmpty() || 
                    categoryField.getText().trim().isEmpty() ||
                    totalCopiesField.getText().trim().isEmpty() ||
                    availableCopiesField.getText().trim().isEmpty() ||
                    priceField.getText().trim().isEmpty()) {
                    throw new BadRequestException("Todos los campos son obligatorios.");
                }
                
                int isbn = Integer.parseInt(isbnField.getText().trim());
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String category = categoryField.getText().trim();
                int totalCopies = Integer.parseInt(totalCopiesField.getText().trim());
                int copiesAvailable = Integer.parseInt(availableCopiesField.getText().trim());
                float priceRef = Float.parseFloat(priceField.getText().trim());
                
                if (totalCopies < 0 || copiesAvailable < 0 || priceRef < 0) {
                    throw new BadRequestException("Los valores numéricos no pueden ser negativos.");
                }
                
                if (copiesAvailable > totalCopies) {
                    throw new BadRequestException("Las copias disponibles no pueden ser más que el total de copias.");
                }
                
                BookDomain book = new BookDomain(isbn, title, author, category, totalCopies, copiesAvailable, priceRef);
                bookService.addBook(book);
                
                JOptionPane.showMessageDialog(
                    null, 
                    "Book added successfully.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Please enter valid numeric values for numeric fields.", 
                    "Format Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (BadRequestException | ConflictException e) {
                JOptionPane.showMessageDialog(
                    null, 
                    e.getMessage(), 
                    "Validation Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Error inesperado: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
    
    public void updateBook() {
        String isbnStr = JOptionPane.showInputDialog("Ingrese el ISBN del libro a actualizar:");
        if (isbnStr == null || isbnStr.trim().isEmpty()) {
            return;
        }
        
        try {
            int isbn = Integer.parseInt(isbnStr);
            BookDomain existingBook = bookService.getBook(isbn);
            
            if (existingBook == null) {
                JOptionPane.showMessageDialog(
                    null, 
                    "No book found with the ISBN: " + isbn, 
                    "Not Found", 
                    JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            
            JPanel panel = new JPanel(new GridLayout(0, 2, 5, 5));
            
            JTextField titleField = new JTextField(existingBook.getTitle());
            JTextField authorField = new JTextField(existingBook.getAuthor());
            JTextField categoryField = new JTextField(existingBook.getCategory());
            JTextField totalCopiesField = new JTextField(String.valueOf(existingBook.getTotalCopies()));
            JTextField availableCopiesField = new JTextField(String.valueOf(existingBook.getCopiesAvailable()));
            JTextField priceField = new JTextField(String.format("%.2f", existingBook.getPriceRef()));
            
            panel.add(new JLabel("* Title:"));
            panel.add(titleField);
            panel.add(new JLabel("* Author:"));
            panel.add(authorField);
            panel.add(new JLabel("* Category:"));
            panel.add(categoryField);
            panel.add(new JLabel("* Total copies:"));
            panel.add(totalCopiesField);
            panel.add(new JLabel("* Available copies:"));
            panel.add(availableCopiesField);
            panel.add(new JLabel("* Reference price:"));
            panel.add(priceField);
            
            panel.add(new JLabel("* Required fields"));
            
            int result = JOptionPane.showConfirmDialog(
                null, 
                panel, 
                "Update Book (ISBN: " + isbn + ")",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
            );
            
            if (result == JOptionPane.OK_OPTION) {
                if (titleField.getText().trim().isEmpty() || 
                    authorField.getText().trim().isEmpty() || 
                    categoryField.getText().trim().isEmpty() ||
                    totalCopiesField.getText().trim().isEmpty() ||
                    availableCopiesField.getText().trim().isEmpty() ||
                    priceField.getText().trim().isEmpty()) {
                    throw new BadRequestException("All fields are required.");
                }
                
                String title = titleField.getText().trim();
                String author = authorField.getText().trim();
                String category = categoryField.getText().trim();
                int totalCopies = Integer.parseInt(totalCopiesField.getText().trim());
                int copiesAvailable = Integer.parseInt(availableCopiesField.getText().trim());
                float priceRef = Float.parseFloat(priceField.getText().trim());
                
                if (totalCopies < 0 || copiesAvailable < 0 || priceRef < 0) {
                    throw new BadRequestException("Numeric values cannot be negative.");
                }
                
                if (copiesAvailable > totalCopies) {
                    throw new BadRequestException("Available copies cannot be more than total copies.");
                }
                
                BookDomain updatedBook = new BookDomain(isbn, title, author, category, totalCopies, copiesAvailable, priceRef);
                bookService.updateBook(updatedBook);
                
                JOptionPane.showMessageDialog(
                    null, 
                    "Book updated successfully.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null, 
                "Please enter valid numeric values for numeric fields.", 
                "Format Error", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(
                null, 
                e.getMessage(), 
                "Validation Error", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null, 
                "Error updating book: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void deleteBook(String isbnStr) {
        try {
            int isbn = Integer.parseInt(isbnStr);
            
            int confirm = JOptionPane.showConfirmDialog(
                null, 
                "Are you sure you want to delete the book with ISBN: " + isbn + "?\nThis action cannot be undone.", 
                "Confirm Deletion", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                bookService.deleteBook(isbn);
                JOptionPane.showMessageDialog(
                    null, 
                    "Book deleted successfully.", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                null, 
                "The ISBN must be a valid number.", 
                "Format Error", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(
                null, 
                e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                null, 
                "Error deleting book: " + e.getMessage(), 
                "Error", 
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    public void show() {
        showAllBooks();
    }

    
}
