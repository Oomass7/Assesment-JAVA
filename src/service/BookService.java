package service;

import Domain.BookDomain;
import dao.BookDao;
import dao.BookDaoImpl;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

import java.util.List;

public class BookService {
    private final BookDao bookDao = new BookDaoImpl();

    public boolean addBook(BookDomain book) {
        if (book == null || book.getIsbn() == 0 || book.getTitle() == null) {
            throw new BadRequestException("Datos del libro incompletos.");
        }
        if (bookDao.getBook(book.getIsbn()) != null) {
            throw new ConflictException("El libro ya existe.");
        }
        boolean result = bookDao.addBook(book);
        if (!result) {
            throw new RuntimeException("Error al agregar el libro.");
        }
        return true;
    }

    public boolean updateBook(BookDomain book) {
        if (book == null || book.getIsbn() == 0) {
            throw new BadRequestException("ISBN requerido para actualizar.");
        }
        if (bookDao.getBook(book.getIsbn()) == null) {
            throw new NotFoundException("Libro no encontrado.");
        }
        boolean result = bookDao.updateBook(book);
        if (!result) {
            throw new RuntimeException("Error al actualizar el libro.");
        }
        return true;
    }

    public boolean deleteBook(int isbn) {
        if (isbn == 0) {
            throw new BadRequestException("ISBN requerido para eliminar.");
        }
        if (bookDao.getBook(isbn) == null) {
            throw new NotFoundException("Libro no encontrado.");
        }
        boolean result = bookDao.deleteBook(isbn);
        if (!result) {
            throw new RuntimeException("Error al eliminar el libro.");
        }
        return true;
    }

    public BookDomain getBook(int isbn) {
        BookDomain book = bookDao.getBook(isbn);
        if (book == null) {
            throw new NotFoundException("Libro no encontrado.");
        }
        return book;
    }

    public List<BookDomain> getAllBooks() {
        List<BookDomain> books = bookDao.getAllBooks();
        if (books == null || books.isEmpty()) {
            throw new NotFoundException("No hay libros registrados.");
        }
        return books;
    }
}
