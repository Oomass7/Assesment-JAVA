package dao;

import java.util.List;

import domain.BookDomain;

public interface BookDao {
    boolean addBook(BookDomain book);
    boolean updateBook(BookDomain book);
    boolean deleteBook(int isbn);
    BookDomain getBook(int isbn);
    List<BookDomain> getAllBooks();
    boolean decrementCopiesAvailable(int isbn);
    boolean incrementCopiesAvailable(int isbn);
}
