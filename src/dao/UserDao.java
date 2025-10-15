package dao;

import domain.UserDomain;

public interface UserDao {
    UserDomain registerUser(UserDomain user);
    UserDomain loginUser(UserDomain user);
    UserDomain findUser(String email);
}
