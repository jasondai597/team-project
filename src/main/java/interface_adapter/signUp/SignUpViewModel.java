package interface_adapter.signUp;

import interface_adapter.ViewModel;

public class SignUpViewModel extends ViewModel<SignUpState> {

    // Define labels for buttons and stuff in view
    public static final String TITLE_LABEL = "Sign Up";
    public static final String USERNAME_LABEL = "Choose Username:";
    public static final String PASSWORD_LABEL = "Chose Password:";
    public static final String REPEAT_PASSWORD_LABEL = "Confirm Password:";
    public static final String LOGIN_BUTTON_LABEL = "Go to Login";
    public static final String SIGNUP_BUTTON_LABEL = "Sign Up";
    public static final String HOME_BUTTON_LABEL = "Home";

    // Initial state of the viewmodel
    public SignUpViewModel() {
        super("signup");
        setState(new SignUpState());
    }
}
