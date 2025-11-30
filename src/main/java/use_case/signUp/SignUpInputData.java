package use_case.signUp;

public class SignUpInputData {

    private final String username;
    private final String password;
    private final String repeatPassword;

    public SignUpInputData(String username, String password, String repeatPassword) {
        this.username = username;
        this.password = password;
        this.repeatPassword = repeatPassword;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }
}
