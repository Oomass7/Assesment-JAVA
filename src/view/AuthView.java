package view;

import controller.UserController;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;
import javax.swing.*;

import domain.UserDomain;

public class AuthView {
    private final UserController userController = new UserController();

    public UserDomain show() {
        while (true) {
            String[] options = {"Login", "Register", "Exit"};
            int choice = JOptionPane.showOptionDialog(null, "Welcome to LibroNOVA", "Authentication",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
            if (choice == 0) {
                String email = JOptionPane.showInputDialog("Email:");
                String password = JOptionPane.showInputDialog("Password:");
                if (email != null) email = email.trim();
                if (password != null) password = password.trim();
                if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email y contraseña son obligatorios.");
                    continue;
                }
                try {
                    UserDomain user = userController.login(email, password);
                    JOptionPane.showMessageDialog(null, "Welcome " + user.getNameUser() + " (" + user.getRol() + ")");
                    return user;
                } catch (BadRequestException | NotFoundException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage());
                }
            } else if (choice == 1) {
                String name = JOptionPane.showInputDialog("Name:");
                String email = JOptionPane.showInputDialog("Email:");
                String password = JOptionPane.showInputDialog("Password:");
                String rol = JOptionPane.showInputDialog("Role (admin/client):");
                if (name != null) name = name.trim();
                if (email != null) email = email.trim();
                if (password != null) password = password.trim();
                if (rol != null) rol = rol.trim();
                if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Email y contraseña son obligatorios.");
                    continue;
                }
                UserDomain user = new UserDomain(null, name, email, password, rol);
                try {
                    userController.register(user);
                    JOptionPane.showMessageDialog(null, "User registered successfully");
                } catch (BadRequestException | ConflictException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage());
                }
            } else {
                return null;
            }
        }
    }
}

