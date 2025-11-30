package use_case.signUp;

import entity.User;

/**
 * DAO Interface for SignUp usecase
 */
public interface SignUpUserDataAccessInterface {

    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    public boolean checkForUser(String username);

    /**
     * Adds a new user to the database.
     * @param user the user to add
     */
    public void addUser(User user);
}
