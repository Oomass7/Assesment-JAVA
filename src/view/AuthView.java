package view;

import Domain.UserDomain;
import service.UserService;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;
import javax.swing.*;

public class AuthView {
    private final UserService userService = new UserService();

    public UserDomain show() {
        while (true) {
            String[] options = {"Iniciar Sesión", "Registrarse", "Salir"};
            int choice = JOptionPane.showOptionDialog(null, "Bienvenido a LibroNOVA", "Autenticación",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                String email = JOptionPane.showInputDialog("Email:");
                String password = JOptionPane.showInputDialog("Contraseña:");
                try {
                    UserDomain user = userService.loginUser(email, password);
                    JOptionPane.showMessageDialog(null, "Bienvenido " + user.getNameUser() + " (" + user.getRol() + ")");
                    return user;
                } catch (BadRequestException | NotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
                }
            } else if (choice == 1) {
                String name = JOptionPane.showInputDialog("Nombre:");
                String email = JOptionPane.showInputDialog("Email:");
                String password = JOptionPane.showInputDialog("Contraseña:");
                String rol = JOptionPane.showInputDialog("Rol (admin/client):");
                UserDomain user = new UserDomain(null, name, email, password, rol);
                try {
                    userService.registerUser(user);
                    JOptionPane.showMessageDialog(null, "Usuario registrado correctamente");
                } catch (BadRequestException | ConflictException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Error inesperado: " + e.getMessage());
                }
            } else {
                return null; // Salir
            }
        }
    }
}
