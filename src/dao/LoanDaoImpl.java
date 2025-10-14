package dao;

import Domain.LoanDomain;
import java.util.List;
import java.util.ArrayList;

public class LoanDaoImpl implements LoanDao {
    @Override
    public boolean createLoan(LoanDomain loan) {
        // Implementación aquí
        return false;
    }

    @Override
    public boolean returnLoan(long idLoan) {
        // Implementación aquí
        return false;
    }

    @Override
    public LoanDomain getLoan(long idLoan) {
        // Implementación aquí
        return null;
    }

    @Override
    public List<LoanDomain> getAllLoans() {
        // Implementación aquí
        return new ArrayList<>();
    }
}
