package service;

import dao.UserDao;
import dao.UserDaoImpl;
import domain.UserDomain;
import exception.BadRequestException;
import exception.ConflictException;
import exception.NotFoundException;

public class UserService {
    private final UserDao userDao = new UserDaoImpl();

    public UserDomain registerUser(UserDomain user) {
        if (user.getEmail() == null || user.getPasswordUser() == null) {
            throw new BadRequestException("Email y contraseña son obligatorios.");
        }
        if (userDao.findUser(user.getEmail()) != null) {
            throw new ConflictException("El usuario ya existe.");
        }
        UserDomain created = userDao.registerUser(user);
        if (created == null) {
            throw new RuntimeException("Error al registrar el usuario.");
        }
        return created;
    }

    public UserDomain loginUser(String email, String password) {
        if (email == null || password == null) {
            throw new BadRequestException("Email y contraseña son obligatorios.");
        }
        UserDomain user = new UserDomain();
        user.setEmail(email);
        user.setPasswordUser(password);
        UserDomain found = userDao.loginUser(user);
        if (found == null) {
            throw new NotFoundException("Usuario o contraseña incorrectos.");
        }
        return found;
    }

    public UserDomain findUser(String email) {
        UserDomain found = userDao.findUser(email);
        if (found == null) {
            throw new NotFoundException("Usuario no encontrado.");
        }
        return found;
    }
}
