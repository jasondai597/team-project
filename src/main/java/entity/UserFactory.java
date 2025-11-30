package entity;

/*
User factory that creates users
 */
public class UserFactory {

    public User createUser(String username, String password) {
        return new User(username, password);
    }
}
