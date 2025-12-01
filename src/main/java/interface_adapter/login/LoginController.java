package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInputData;
import use_case.login.LoginUserDataAccess;

public class LoginController {
    private final LoginInputBoundary logInUseCaseInteractor;
    private final ViewManagerModel viewManagerModel;

    public LoginController(LoginInputBoundary logInUseCaseInteractor, ViewManagerModel viewManagerModel) {
        this.logInUseCaseInteractor = logInUseCaseInteractor;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Executes the login use case.
     * @param username the inputted username
     * @param password the inputted password
     */
    public void login(String username, String password) {
        final LoginInputData loginInputData = new LoginInputData(username, password);
        logInUseCaseInteractor.execute(loginInputData);
    }

    /**
     * switch to home if clicked on the home button.
     */
    public void switchToHomeView() {
        viewManagerModel.setState("main");
        viewManagerModel.firePropertyChange();
    }

    /**
     * switch to the signup view.
     */
    public void switchToSignUpView() {
        viewManagerModel.setState("signup");
        viewManagerModel.firePropertyChange();
    }
}
