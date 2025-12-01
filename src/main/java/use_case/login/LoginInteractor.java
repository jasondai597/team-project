package use_case.login;

import entity.UserFactory;

public class LoginInteractor implements LoginInputBoundary {

    private final LoginUserDataAccess userDataAccess;
    private final LoginOutputBoundary presenter;

    public LoginInteractor(LoginUserDataAccess userDataAccess,
                           LoginOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(LoginInputData loginInputData) {
        if ("".equals(loginInputData.getUsername())) {
            presenter.presentError("Username cannot be empty");
        }
        else if ("".equals(loginInputData.getPassword())) {
            presenter.presentError("Password cannot be empty");
        }
        else if (!userDataAccess.checkForUser(loginInputData.getUsername())) {
            presenter.presentError("User does not exist");
        }
        else if (!userDataAccess.validateUser(loginInputData.getUsername(), loginInputData.getPassword())) {
            presenter.presentError("Incorrect password");
        }
        else {
            final LoginOutputData loginOutputData = new LoginOutputData(loginInputData.getUsername());
            presenter.presentSuccess(loginOutputData);
        }
    }
}
