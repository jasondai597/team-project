package interface_adapter.signUp;

/**
 * The state for the Signup View Model.
 */
public class SignUpState {
    private String username = "";
    private String usernameError;
    private String password = "";
    private String repeatPassword = "";

    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }


    @Override
    public String toString() {
        return "SignupState{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", repeatPassword='" + repeatPassword + '\''
                + '}';
    }
}
