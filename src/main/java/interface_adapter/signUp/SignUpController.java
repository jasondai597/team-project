package interface_adapter.signUp;

import interface_adapter.ViewManagerModel;
import use_case.signUp.SignUpInputBoundary;
import use_case.signUp.SignUpInputData;
import use_case.signUp.SignUpOutputData;

public class SignUpController {
    private final SignUpInputBoundary userSignUpUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public SignUpController(SignUpInputBoundary userSignUpUseCaseInteractor,
                            ViewManagerModel viewManagerModel) {
        this.userSignUpUseCaseInteractor = userSignUpUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the sign up use case.
     * @param username
     * @param password
     * @param repeatPassword
     */
    public void execute(String username, String password, String repeatPassword) {
        final SignUpInputData signUpInputData = new SignUpInputData(username, password, repeatPassword);
        userSignUpUseCaseInteractor.execute(signUpInputData);
    }

    /**
     * Switch to home if clicked on the home button.
     */
    public void switchToHomeView() {
        viewManagerModel.setState("main");
        viewManagerModel.firePropertyChange();
    }

    public void switchToLoginView() {
        viewManagerModel.setState("login");
        viewManagerModel.firePropertyChange();
    }

}
