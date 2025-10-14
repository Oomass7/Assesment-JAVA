package view;

import Domain.LoanDomain;
import service.LoanService;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

import javax.swing.*;
import java.util.List;

public class LoansView {
    private final LoanService loanService = new LoanService();

    public void show() {
        while (true) {
            String menu = """
                    GESTIÓN DE PRÉSTAMOS
                    1. Crear Préstamo
                    2. Devolver Préstamo
                    3. Buscar Préstamo
                    4. Listar Todos
                    5. Volver
                    Elige una opción:
                    """;
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break; // Cancelar = salir
            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Opción inválida.");
                continue;
            }
            switch (option) {
                case 1 -> crearPrestamo();
                case 2 -> devolverPrestamo();
                case 3 -> buscarPrestamo();
                case 4 -> listarPrestamos();
                case 5 -> { return; }
                default -> JOptionPane.showMessageDialog(null, "Opción inválida.");
            }
        }
    }

    private void crearPrestamo() {
        try {
            long idUser = Long.parseLong(JOptionPane.showInputDialog("ID del usuario:"));
            int isbn = Integer.parseInt(JOptionPane.showInputDialog("ISBN del libro:"));
            String fecha = JOptionPane.showInputDialog("Fecha de inicio (YYYY-MM-DD):");

            LoanDomain loan = new LoanDomain();
            loan.setIdUser(idUser);
            loan.setIsbn(isbn);
            loan.setStartDate(java.sql.Date.valueOf(fecha));
            loanService.createLoan(loan);
            JOptionPane.showMessageDialog(null, "Préstamo creado correctamente.");
        } catch (BadRequestException | ConflictException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void devolverPrestamo() {
        try {
            long idLoan = Long.parseLong(JOptionPane.showInputDialog("ID del préstamo a devolver:"));
            loanService.returnLoan(idLoan);
            JOptionPane.showMessageDialog(null, "Préstamo devuelto correctamente.");
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void buscarPrestamo() {
        try {
            long idLoan = Long.parseLong(JOptionPane.showInputDialog("ID del préstamo a buscar:"));
            LoanDomain loan = loanService.getLoan(idLoan);
            JOptionPane.showMessageDialog(null, "ID: " + loan.getIdLoan() +
                    "\nUsuario: " + loan.getIdUser() +
                    "\nISBN: " + loan.getIsbn() +
                    "\nFecha inicio: " + loan.getStartDate() +
                    "\nFecha devolución: " + loan.getReturnDate() +
                    "\nEstado: " + loan.getStatusLoan());
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void listarPrestamos() {
        try {
            List<LoanDomain> loans = loanService.getAllLoans();
            StringBuilder sb = new StringBuilder();
            for (LoanDomain loan : loans) {
                sb.append("ID: ").append(loan.getIdLoan())
                  .append(", Usuario: ").append(loan.getIdUser())
                  .append(", ISBN: ").append(loan.getIsbn())
                  .append(", Inicio: ").append(loan.getStartDate())
                  .append(", Devolución: ").append(loan.getReturnDate())
                  .append(", Estado: ").append(loan.getStatusLoan())
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
