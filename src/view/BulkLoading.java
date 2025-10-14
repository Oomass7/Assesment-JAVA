package view;

import service.BookService;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;

public class BulkLoading {
    private final BookService bookService = new BookService();

    public void show() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona un archivo CSV");
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            File csvFile = fileChooser.getSelectedFile();
            if (csvFile != null && csvFile.getName().endsWith(".csv")) {
                int count = 0;
                try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        // Suponiendo formato: isbn,title,author,category,totalCopies,copiesAvailable,priceRef
                        String[] data = line.split(",");
                        if (data.length == 7) {
                            try {
                                int isbn = Integer.parseInt(data[0]);
                                String title = data[1];
                                String author = data[2];
                                String category = data[3];
                                int totalCopies = Integer.parseInt(data[4]);
                                int copiesAvailable = Integer.parseInt(data[5]);
                                float priceRef = Float.parseFloat(data[6]);
                                bookService.addBook(new Domain.BookDomain(isbn, title, author, category, totalCopies, copiesAvailable, priceRef));
                                count++;
                            } catch (Exception e) {
                                // Puedes mostrar un mensaje o ignorar líneas con error
                            }
                        }
                    }
                    JOptionPane.showMessageDialog(null, "Carga masiva completada. Libros agregados: " + count);
                } catch (BadRequestException | ConflictException | NotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona un archivo .csv válido.");
            }
        }
    }
}
