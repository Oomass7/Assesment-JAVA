package view;

import Domain.BookDomain;
import service.BookService;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

import javax.swing.*;
import java.util.List;

public class BookView {
    private final BookService bookService = new BookService();

    public void show() {
        String[] options = {
            "Agregar Libro",
            "Actualizar Libro",
            "Eliminar Libro",
            "Buscar Libro",
            "Listar Todos",
            "Volver"
        };
        while (true) {
            String choice = (String) JOptionPane.showInputDialog(
                    null,
                    "Gestión de Libros",
                    "LibroNOVA",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == null || choice.equals("Volver")) {
                break;
            }
            switch (choice) {
                case "Agregar Libro" -> agregarLibro();
                case "Actualizar Libro" -> actualizarLibro();
                case "Eliminar Libro" -> eliminarLibro();
                case "Buscar Libro" -> buscarLibro();
                case "Listar Todos" -> listarLibros();
            }
        }
    }

    private void agregarLibro() {
        try {
            int isbn = Integer.parseInt(JOptionPane.showInputDialog("ISBN:"));
            String title = JOptionPane.showInputDialog("Título:");
            String author = JOptionPane.showInputDialog("Autor:");
            String category = JOptionPane.showInputDialog("Categoría:");
            int totalCopies = Integer.parseInt(JOptionPane.showInputDialog("Total de copias:"));
            int copiesAvailable = Integer.parseInt(JOptionPane.showInputDialog("Copias disponibles:"));
            float priceRef = Float.parseFloat(JOptionPane.showInputDialog("Precio de referencia:"));

            BookDomain book = new BookDomain(isbn, title, author, category, totalCopies, copiesAvailable, priceRef);
            bookService.addBook(book);
            JOptionPane.showMessageDialog(null, "Libro agregado correctamente.");
        } catch (BadRequestException | ConflictException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void actualizarLibro() {
        try {
            int isbn = Integer.parseInt(JOptionPane.showInputDialog("ISBN del libro a actualizar:"));
            String title = JOptionPane.showInputDialog("Nuevo título:");
            String author = JOptionPane.showInputDialog("Nuevo autor:");
            String category = JOptionPane.showInputDialog("Nueva categoría:");
            int totalCopies = Integer.parseInt(JOptionPane.showInputDialog("Nuevo total de copias:"));
            int copiesAvailable = Integer.parseInt(JOptionPane.showInputDialog("Nuevas copias disponibles:"));
            float priceRef = Float.parseFloat(JOptionPane.showInputDialog("Nuevo precio de referencia:"));

            BookDomain book = new BookDomain(isbn, title, author, category, totalCopies, copiesAvailable, priceRef);
            bookService.updateBook(book);
            JOptionPane.showMessageDialog(null, "Libro actualizado correctamente.");
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void eliminarLibro() {
        try {
            int isbn = Integer.parseInt(JOptionPane.showInputDialog("ISBN del libro a eliminar:"));
            bookService.deleteBook(isbn);
            JOptionPane.showMessageDialog(null, "Libro eliminado correctamente.");
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void buscarLibro() {
        try {
            int isbn = Integer.parseInt(JOptionPane.showInputDialog("ISBN del libro a buscar:"));
            BookDomain book = bookService.getBook(isbn);
            JOptionPane.showMessageDialog(null, "ISBN: " + book.getIsbn() +
                    "\nTítulo: " + book.getTitle() +
                    "\nAutor: " + book.getAuthor() +
                    "\nCategoría: " + book.getCategory() +
                    "\nTotal de copias: " + book.getTotalCopies() +
                    "\nCopias disponibles: " + book.getCopiesAvailable() +
                    "\nPrecio de referencia: " + book.getPriceRef());
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void listarLibros() {
        try {
            List<BookDomain> books = bookService.getAllBooks();
            StringBuilder sb = new StringBuilder();
            for (BookDomain book : books) {
                sb.append("ISBN: ").append(book.getIsbn())
                  .append(", Título: ").append(book.getTitle())
                  .append(", Autor: ").append(book.getAuthor())
                  .append(", Categoría: ").append(book.getCategory())
                  .append(", Total: ").append(book.getTotalCopies())
                  .append(", Disponibles: ").append(book.getCopiesAvailable())
                  .append(", Precio: ").append(book.getPriceRef())
                  .append("\n");
            }
            JOptionPane.showMessageDialog(null, sb.toString());
        } catch (NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }
}
