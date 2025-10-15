package controller;

import domain.BookDomain;
import service.BookService;

import java.util.List;

public class BookController {
    private final BookService bookService = new BookService();

    public boolean addBook(BookDomain book) {
        return bookService.addBook(book);
    }

    public boolean updateBook(BookDomain book) {
        return bookService.updateBook(book);
    }

    public boolean deleteBook(int isbn) {
        return bookService.deleteBook(isbn);
    }

    public BookDomain getBook(int isbn) {
        return bookService.getBook(isbn);
    }

    public List<BookDomain> getAllBooks() {
        return bookService.getAllBooks();
    }
}
