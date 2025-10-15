package dao;

import config.DbConfig;
import domain.BookDomain;

import java.sql.*;
import java.util.*;

public class BookDaoImpl implements BookDao {
    @Override
    public boolean addBook(BookDomain book) {
        String sql = "INSERT INTO books (isbn, title, author, category, total_copies, copies_available, price_ref) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, book.getIsbn());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getAuthor());
            ps.setString(4, book.getCategory());
            ps.setInt(5, book.getTotalCopies());
            ps.setInt(6, book.getCopiesAvailable());
            ps.setFloat(7, book.getPriceRef());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean updateBook(BookDomain book) {
        String sql = "UPDATE books SET title=?, author=?, category=?, total_copies=?, copies_available=?, price_ref=? WHERE isbn=?";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setString(3, book.getCategory());
            ps.setInt(4, book.getTotalCopies());
            ps.setInt(5, book.getCopiesAvailable());
            ps.setFloat(6, book.getPriceRef());
            ps.setInt(7, book.getIsbn());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean deleteBook(int isbn) {
        String sql = "DELETE FROM books WHERE isbn=?";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, isbn);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BookDomain getBook(int isbn) {
        String sql = "SELECT * FROM books WHERE isbn=?";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
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
                        rs.getFloat("price_ref"));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<BookDomain> getAllBooks() {
        List<BookDomain> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                books.add(new BookDomain(
                        rs.getInt("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("category"),
                        rs.getInt("total_copies"),
                        rs.getInt("copies_available"),
                        rs.getFloat("price_ref")));
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    @Override
    public boolean decrementCopiesAvailable(int isbn) {
        String sql = "UPDATE books SET copies_available = copies_available - 1 WHERE isbn = ? AND copies_available > 0";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, isbn);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean incrementCopiesAvailable(int isbn) {
        String sql = "UPDATE books SET copies_available = copies_available + 1 WHERE isbn = ? AND copies_available < total_copies";
        try (Connection c = DbConfig.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, isbn);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}

