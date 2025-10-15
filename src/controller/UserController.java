package controller;

import domain.UserDomain;
import service.UserService;

public class UserController {
    private final UserService userService = new UserService();

    public UserDomain register(UserDomain user) {
        return userService.registerUser(user);
    }

    public UserDomain login(String email, String password) {
        return userService.loginUser(email, password);
    }
}
