package dao;


import Domain.UserDomain;
import config.Config;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class UserDaoImpl implements UserDao {
    @Override
    public UserDomain registerUser(UserDomain user) {
        String query = "INSERT INTO users (name_user, email, password_user, rol) VALUES (?, ?, ?, ?)";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, user.getNameUser());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordUser());
            ps.setString(4, user.getRol());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public UserDomain loginUser(UserDomain user) {
        String query = "SELECT * FROM users WHERE email = ? AND password_user = ?";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPasswordUser());
            var rs = ps.executeQuery();
            if (rs.next()) {
                UserDomain loggedUser = new UserDomain();
                loggedUser.setIdUser(rs.getLong("id_user"));
                loggedUser.setNameUser(rs.getString("name_user"));
                loggedUser.setEmail(rs.getString("email"));
                loggedUser.setPasswordUser(rs.getString("password_user"));
                loggedUser.setRol(rs.getString("rol"));
                return loggedUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @Override
    public UserDomain findUser(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        try (Connection c = Config.getConnection(); PreparedStatement ps = c.prepareStatement(query)) {
            ps.setString(1, email);
            var rs = ps.executeQuery();
            if (rs.next()) {
                UserDomain foundUser = new UserDomain();
                foundUser.setIdUser(rs.getLong("id_user"));
                foundUser.setNameUser(rs.getString("name_user"));
                foundUser.setEmail(rs.getString("email"));
                foundUser.setPasswordUser(rs.getString("password_user"));
                foundUser.setRol(rs.getString("rol"));
                return foundUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
