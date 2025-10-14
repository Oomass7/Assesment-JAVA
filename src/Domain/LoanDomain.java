package Domain;

import java.util.Date;

public class LoanDomain {
    private Long idLoan;
    private int isbn;
    private Long idUser;
    private Date startDate;
    private Date returnDate;
    private String statusLoan;

    public LoanDomain() {
    }

    public LoanDomain(Long idLoan, int isbn, Long idUser, Date startDate, Date returnDate, String statusLoan) {
        this.idLoan = idLoan;
        this.isbn = isbn;
        this.idUser = idUser;
        this.startDate = startDate;
        this.returnDate = returnDate;
        this.statusLoan = statusLoan;
    }

    public Long getIdLoan() {
        return idLoan;
    }

    public void setIdLoan(Long idLoan) {
        this.idLoan = idLoan;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public String getStatusLoan() {
        return statusLoan;
    }

    public void setStatusLoan(String statusLoan) {
        this.statusLoan = statusLoan;
    }
}
