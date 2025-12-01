package interface_adapter.login;

import interface_adapter.ViewModel;

public class LoginViewModel extends ViewModel<LoginState> {

    // Define labels for buttons and stuff in view
    public static final String TITLE_LABEL = "Sign Up";
    public static final String USERNAME_LABEL = "Enter Username:";
    public static final String PASSWORD_LABEL = "Enter Password:";
    public static final String LOGIN_BUTTON_LABEL = "Login";
    public static final String SIGNUP_BUTTON_LABEL = "Go to Sign Up";
    public static final String HOME_BUTTON_LABEL = "Home";
    public static final int FONT_SIZE = 20;

    public LoginViewModel() {
        super("login");
        setState(new LoginState());
    }
}
