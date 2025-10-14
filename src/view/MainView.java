package view;

import Domain.UserDomain;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;
import javax.swing.*;

public class MainView {
    public void show(UserDomain user) {
        while (true) {
            String[] options = {"Gestión de Libros", "Préstamos", "Carga Masiva", "Cerrar Sesión"};
            int choice = JOptionPane.showOptionDialog(null, "Menú Principal", "LibroNOVA",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                try {
                    // Aquí va la gestión de libros
                    JOptionPane.showMessageDialog(null, "Aquí va la gestión de libros.");
                } catch (BadRequestException | ConflictException | NotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
                }
            } else if (choice == 1) {
                try {
                    // Aquí va la gestión de préstamos
                    JOptionPane.showMessageDialog(null, "Aquí va la gestión de préstamos.");
                } catch (BadRequestException | ConflictException | NotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
                }
            } else if (choice == 2) {
                try {
                    // Aquí va la carga masiva
                    JOptionPane.showMessageDialog(null, "Aquí va la carga masiva.");
                } catch (BadRequestException | ConflictException | NotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
                }
            } else {
                break; // Cerrar sesión
            }
        }
    }
}
