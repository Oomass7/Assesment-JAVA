package view;

import Domain.UserDomain;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;
import javax.swing.*;

public class MainView {
    public void show(UserDomain user) {
        BookView bookView = new BookView();
        LoansView loansView = new LoansView();
        BulkLoading bulkLoading = new BulkLoading();

        String[] options = {"Gestión de Libros", "Préstamos", "Carga Masiva", "Cerrar Sesión"};
        while (true) {
            String choice = (String) JOptionPane.showInputDialog(
                    null,
                    "Menú Principal",
                    "LibroNOVA",
                    JOptionPane.PLAIN_MESSAGE,
                    null,
                    options,
                    options[0]
            );
            if (choice == null || choice.equals("Cerrar Sesión")) {
                break;
            }
            try {
                switch (choice) {
                    case "Gestión de Libros" -> bookView.show();
                    case "Préstamos" -> loansView.show();
                    case "Carga Masiva" -> bulkLoading.show();
                }
            } catch (BadRequestException | ConflictException | NotFoundException e) {
                JOptionPane.showMessageDialog(null, e.getMessage());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
            }
        }
    }
}
