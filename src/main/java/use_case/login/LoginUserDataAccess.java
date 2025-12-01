package use_case.login;


/**
 * DAO Interface for Login usecase
 */
public interface LoginUserDataAccess {
    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean checkForUser(String username);

    /**
     * Validates the user credentials. to see if the user exists and the password is correct.
     * @param username the username of the user
     * @param password the password entered
     * @return whether or not the password matches with database
     */
    boolean validateUser(String username, String password);
}
