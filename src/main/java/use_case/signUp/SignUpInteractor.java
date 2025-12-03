package use_case.signUp;

import entity.User;
import entity.UserFactory;

/**
 * Signup interactor
 */

public class SignUpInteractor implements SignUpInputBoundary {
    private final SignUpUserDataAccessInterface userDataAccessObject;
    private final SignUpOutputBoundary userPresenter;
    private final UserFactory userFactory;

    public SignUpInteractor(SignUpUserDataAccessInterface userDataAccessObject,
                            SignUpOutputBoundary userPresenter,
                            UserFactory userFactory) {
        this.userDataAccessObject = userDataAccessObject;
        this.userPresenter = userPresenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignUpInputData signUpInputData) {
        if ("".equals(signUpInputData.getUsername())) {
            userPresenter.presentFailureView("Username cannot be empty");
        }
        else if ("".equals(signUpInputData.getPassword())) {
            userPresenter.presentFailureView("Password cannot be empty");
        }
        else if (!signUpInputData.getPassword().equals(signUpInputData.getRepeatPassword())) {
            userPresenter.presentFailureView("Passwords do not match");
        }
        else if (userDataAccessObject.checkForUser(signUpInputData.getUsername())) {
            userPresenter.presentFailureView("Username already exists");
        }
        else {
            final User user = userFactory.createUser(signUpInputData.getUsername(), signUpInputData.getPassword());
            userDataAccessObject.addUser(user);

            final SignUpOutputData signUpOutputData = new SignUpOutputData(user.getUsername());
            userPresenter.presentSuccessView(signUpOutputData);
        }
    }

}
