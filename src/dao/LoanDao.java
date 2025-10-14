package dao;

import Domain.LoanDomain;
import java.util.List;

public interface LoanDao {
    boolean createLoan(LoanDomain loan);
    boolean returnLoan(long idLoan);
    LoanDomain getLoan(long idLoan);
    List<LoanDomain> getAllLoans();
}
