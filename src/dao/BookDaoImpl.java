package dao;

import Domain.BookDomain;
import config.Config;
import java.sql.*;
import java.util.*;

public class BookDaoImpl implements BookDao {
    @Override
    public boolean addBook(BookDomain book) {
        String sql = "INSERT INTO books (isbn, title, author, category, total_copies, copies_available, price_ref) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getCategory());
            ps.setInt(5, book.getTotalCopies());
            ps.setInt(6, book.getCopiesAvailable());
            ps.setFloat(7, book.getPriceRef());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateBook(BookDomain book) {
        String sql = "UPDATE books SET title=?, author=?, category=?, total_copies=?, copies_available=?, price_ref=? WHERE isbn=?";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getCopiesAvailable());
            ps.setFloat(6, book.getPriceRef());
            ps.setInt(7, book.getIsbn());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteBook(int isbn) {
        String sql = "DELETE FROM books WHERE isbn=?";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, isbn);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public BookDomain getBook(int isbn) {
        String sql = "SELECT * FROM books WHERE isbn=?";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, isbn);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new BookDomain(
                    rs.getInt("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getInt("total_copies"),
                    rs.getInt("copies_available"),
                    rs.getFloat("price_ref")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<BookDomain> getAllBooks() {
        List<BookDomain> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(new BookDomain(
                    rs.getInt("isbn"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getString("category"),
                    rs.getInt("total_copies"),
                    rs.getInt("copies_available"),
                    rs.getFloat("price_ref")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return books;
    }
}
