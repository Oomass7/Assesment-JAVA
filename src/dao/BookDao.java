package dao;

import Domain.BookDomain;
import java.util.List;

public interface BookDao {
    boolean addBook(BookDomain book);
    boolean updateBook(BookDomain book);
    boolean deleteBook(int isbn);
    BookDomain getBook(int isbn);
    List<BookDomain> getAllBooks();
}
