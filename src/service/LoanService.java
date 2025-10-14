package service;

import Domain.LoanDomain;
import dao.LoanDao;
import dao.LoanDaoImpl;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

import java.util.List;

public class LoanService {
    private final LoanDao loanDao = new LoanDaoImpl();

    public boolean createLoan(LoanDomain loan) {
        if (loan == null || loan.getIdUser() == null || loan.getIsbn() == 0) {
            throw new BadRequestException("Datos del préstamo incompletos.");
        }
        // Puedes agregar más validaciones aquí
        boolean result = loanDao.createLoan(loan);
        if (!result) {
            throw new RuntimeException("Error al crear el préstamo.");
        }
        return true;
    }

    public boolean returnLoan(long idLoan) {
        if (idLoan == 0) {
            throw new BadRequestException("ID de préstamo requerido.");
        }
        LoanDomain loan = loanDao.getLoan(idLoan);
        if (loan == null) {
            throw new NotFoundException("Préstamo no encontrado.");
        }
        boolean result = loanDao.returnLoan(idLoan);
        if (!result) {
            throw new RuntimeException("Error al devolver el préstamo.");
        }
        return true;
    }

    public LoanDomain getLoan(long idLoan) {
        LoanDomain loan = loanDao.getLoan(idLoan);
        if (loan == null) {
            throw new NotFoundException("Préstamo no encontrado.");
        }
        return loan;
    }

    public List<LoanDomain> getAllLoans() {
        List<LoanDomain> loans = loanDao.getAllLoans();
        if (loans == null || loans.isEmpty()) {
            throw new NotFoundException("No hay préstamos registrados.");
        }
        return loans;
    }
}
