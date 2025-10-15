package util;

import service.BookService;
import exception.BadRequestException;
import exception.ConflictException;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import domain.BookDomain;

public class BulkLoader {
    private final BookService bookService = new BookService();

    /**
     * Carga libros desde un archivo CSV.
     * @param csvFile El archivo CSV a cargar.
     * @return El número de libros agregados exitosamente.
     */
    public int loadBooksFromCSV(File csvFile) {
        int count = 0;
        int lineNumber = 0;
        
        if (csvFile == null || !csvFile.exists() || !csvFile.canRead()) {
            throw new IllegalArgumentException("El archivo no existe o no se puede leer");
        }

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Leer y descartar la primera línea (encabezado)
            String header = br.readLine();
            if (header == null) {
                System.err.println("El archivo está vacío");
                return 0;
            }
            
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                try {
                    // Manejar comas dentro de comillas y espacios en blanco
                    String[] data = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
                    
                    if (data.length != 7) {
                        System.err.println("Error en línea " + lineNumber + ": Formato incorrecto. Se esperaban 7 campos");
                        continue;
                    }

                    // Limpiar y validar campos
                    long isbn = Long.parseLong(data[0].trim());
                    String title = data[1].trim();
                    String author = data[2].trim();
                    String category = data[3].trim();
                    int totalCopies = Integer.parseInt(data[4].trim());
                    int copiesAvailable = Integer.parseInt(data[5].trim());
                    float priceRef = Float.parseFloat(data[6].trim());

                    // Validaciones adicionales
                    if (title.isEmpty() || author.isEmpty() || category.isEmpty()) {
                        System.err.println("Error en línea " + lineNumber + ": Campos requeridos vacíos");
                        continue;
                    }
                    
                    if (totalCopies < 0 || copiesAvailable < 0 || priceRef < 0) {
                        System.err.println("Error en línea " + lineNumber + ": Los valores numéricos no pueden ser negativos");
                        continue;
                    }
                    
                    if (copiesAvailable > totalCopies) {
                        System.err.println("Error en línea " + lineNumber + ": Las copias disponibles no pueden ser mayores que el total de copias");
                        continue;
                    }

                    BookDomain book = new BookDomain((int)isbn, title, author, category,
                                                   totalCopies, copiesAvailable, priceRef);
                    
                    bookService.addBook(book);
                    count++;
                    System.out.println("Libro agregado exitosamente: " + isbn);
                    
                } catch (NumberFormatException e) {
                    System.err.println("Error en línea " + lineNumber + ": Formato numérico inválido - " + e.getMessage());
                } catch (BadRequestException e) {
                    System.err.println("Error en línea " + lineNumber + ": " + e.getMessage());
                } catch (ConflictException e) {
                    System.err.println("Error en línea " + lineNumber + ": " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Error inesperado en línea " + lineNumber + ": " + e.getMessage());
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error al procesar el archivo: " + e.getMessage());
            e.printStackTrace();
        }
        
        return count;
    }
}
