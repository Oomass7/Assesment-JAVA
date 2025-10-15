package dao;

import java.util.List;

import domain.LoanDomain;

public interface LoanDao {
    boolean createLoan(LoanDomain loan);
    boolean returnLoan(long idLoan);
    LoanDomain getLoan(long idLoan);
    List<LoanDomain> getAllLoans();
}
