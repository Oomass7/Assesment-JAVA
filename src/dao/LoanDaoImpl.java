package dao;

import config.DbConfig;
import domain.LoanDomain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoanDaoImpl implements LoanDao {
    @Override
    public boolean createLoan(LoanDomain loan) {
        String sql = "INSERT INTO loans (isbn, id_user, start_date, return_date, status_loan) VALUES (?, ?, ?, ?, ?)";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, loan.getIsbn());
            ps.setLong(2, loan.getIdUser());
            ps.setDate(3, new java.sql.Date(loan.getStartDate().getTime()));
            if (loan.getReturnDate() != null) {
                ps.setDate(4, new java.sql.Date(loan.getReturnDate().getTime()));
            } else {
                ps.setNull(4, Types.DATE);
            }
            ps.setString(5, loan.getStatusLoan());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean returnLoan(long idLoan) {
        String sql = "UPDATE loans SET status_loan = ?, return_date = ? WHERE id_loan = ?";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, "returned");
            ps.setDate(2, new java.sql.Date(System.currentTimeMillis()));
            ps.setLong(3, idLoan);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoanDomain getLoan(long idLoan) {
        String sql = "SELECT * FROM loans WHERE id_loan = ?";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setLong(1, idLoan);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LoanDomain loan = new LoanDomain();
                loan.setIdLoan(rs.getLong("id_loan"));
                loan.setIsbn(rs.getInt("isbn"));
                loan.setIdUser(rs.getLong("id_user"));
                loan.setStartDate(rs.getDate("start_date"));
                loan.setReturnDate(rs.getDate("return_date"));
                loan.setStatusLoan(rs.getString("status_loan"));
                return loan;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<LoanDomain> getAllLoans() {
        List<LoanDomain> loans = new ArrayList<>();
        String sql = "SELECT * FROM loans";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                LoanDomain loan = new LoanDomain();
                loan.setIdLoan(rs.getLong("id_loan"));
                loan.setIsbn(rs.getInt("isbn"));
                loan.setIdUser(rs.getLong("id_user"));
                loan.setStartDate(rs.getDate("start_date"));
                loan.setReturnDate(rs.getDate("return_date"));
                loan.setStatusLoan(rs.getString("status_loan"));
                loans.add(loan);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return loans;
    }
}
