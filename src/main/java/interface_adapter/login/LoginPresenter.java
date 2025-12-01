package interface_adapter.login;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedIn.LoggedInState;
import interface_adapter.loggedIn.LoggedInViewModel;
import use_case.login.LoginOutputBoundary;
import use_case.login.LoginOutputData;

public class LoginPresenter implements LoginOutputBoundary {
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public LoginPresenter(LoginViewModel loginViewModel,
                          ViewManagerModel viewManagerModel,
                          LoggedInViewModel loggedInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.loginViewModel = loginViewModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void presentError(String errorMessage) {
        final LoginState loginState = loginViewModel.getState();
        loginState.setLoginError(errorMessage);
        loginViewModel.firePropertyChange();
    }

    @Override
    public void presentSuccess(LoginOutputData loginOutputData) {
        final LoggedInState loggedInState = loggedInViewModel.getState();
        loggedInState.setUsername(loginOutputData.getUsername());
        loggedInViewModel.firePropertyChange();
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }
}
