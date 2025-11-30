package use_case.signUp;

/**
 * output data for SignUp usecase
 */
public class SignUpOutputData {
    private final String username;

    public SignUpOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
