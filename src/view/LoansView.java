package view;

import service.LoanService;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

import javax.swing.*;

import domain.LoanDomain;

import java.util.List;

public class LoansView {
    private final LoanService loanService = new LoanService();

    public void show() {
        while (true) {
            String menu = """
                    LOAN MANAGEMENT
                    1. Create Loan
                    2. Return Loan
                    3. Search Loan
                    4. List All
                    5. Return
                    Choose an option:
                    """;
            String input = JOptionPane.showInputDialog(menu);
            if (input == null) break; // Cancelar = salir
            int option;
            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Invalid option.");
                continue;
            }
            switch (option) {
                case 1 -> crearPrestamo();
                case 2 -> devolverPrestamo();
                case 3 -> buscarPrestamo();
                case 4 -> listarPrestamos();
                case 5 -> { return; }
                default -> JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    private void crearPrestamo() {
        try {
            long idUser = Long.parseLong(JOptionPane.showInputDialog("ID of the user:"));
            int isbn = Integer.parseInt(JOptionPane.showInputDialog("ISBN of the book:"));
            String fecha = JOptionPane.showInputDialog("Start date (YYYY-MM-DD):");

            LoanDomain loan = new LoanDomain();
            loan.setIdUser(idUser);
            loan.setIsbn(isbn);
            loan.setStartDate(java.sql.Date.valueOf(fecha));
            loanService.createLoan(loan);
            JOptionPane.showMessageDialog(null, "Loan created successfully.");
        } catch (BadRequestException | ConflictException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void devolverPrestamo() {
        try {
            long idLoan = Long.parseLong(JOptionPane.showInputDialog("ID of the loan to return:"));
            loanService.returnLoan(idLoan);
            JOptionPane.showMessageDialog(null, "Loan returned successfully.");
        } catch (BadRequestException | NotFoundException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
        }
    }

    private void buscarPrestamo() {
        try {
            long idLoan = Long.parseLong(JOptionPane.showInputDialog("ID of the loan to search:"));
            LoanDomain loan = loanService.getLoan(idLoan);
            JOptionPane.showMessageDialog(null, "ID: " + loan.getIdLoan() +
                    "\nUser ID: " + loan.getIdUser() +
                    "\nISBN: " + loan.getIsbn() +
                    "\nStart Date: " + loan.getStartDate() +
                    "\nReturn Date: " + loan.getReturnDate() +
                    "\nStatus: " + loan.getStatusLoan());
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
                  .append(", User ID: ").append(loan.getIdUser())
                  .append(", ISBN: ").append(loan.getIsbn())
                  .append(", Start Date: ").append(loan.getStartDate())
                  .append(", Return Date: ").append(loan.getReturnDate())
                  .append(", Status: ").append(loan.getStatusLoan())
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
