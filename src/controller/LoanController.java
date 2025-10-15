package controller;

import domain.LoanDomain;
import service.LoanService;

import java.util.List;

public class LoanController {
    private final LoanService loanService = new LoanService();

    public boolean createLoan(LoanDomain loan) {
        return loanService.createLoan(loan);
    }

    public boolean returnLoan(long idLoan) {
        return loanService.returnLoan(idLoan);
    }

    public LoanDomain getLoan(long idLoan) {
        return loanService.getLoan(idLoan);
    }

    public List<LoanDomain> getAllLoans() {
        return loanService.getAllLoans();
    }
}
