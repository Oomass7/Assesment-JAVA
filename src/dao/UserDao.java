package dao;

import Domain.UserDomain;

public interface UserDao {
    UserDomain registerUser(UserDomain user);
    UserDomain loginUser(UserDomain user);
    UserDomain findUser(String username);
    
}
