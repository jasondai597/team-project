package use_case;

import org.junit.Test;
import use_case.login.*;

public class LoginInteractorTest {

    private static class TestPresenter implements LoginOutputBoundary {

        private String errorMessage;
        private LoginOutputData response;

        @Override
        public void presentError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        @Override
        public void presentSuccess(LoginOutputData response) {
            this.response = response;
        }

        public String getErrorMessage() {
            return errorMessage;
        }

        public LoginOutputData getResponse() {
            return response;
        }
    }

    private static class TestDataAccess implements LoginUserDataAccess {

        private final boolean userExists;
        private final boolean passwordValid;

        TestDataAccess(boolean userExists, boolean passwordValid) {
            this.userExists = userExists;
            this.passwordValid = passwordValid;
        }

        @Override
        public boolean checkForUser(String username) {
            return userExists;
        }

        @Override
        public boolean validateUser(String username, String password) {
            return passwordValid;
        }
    }
    //TEst empty uesrname
    @Test
    public void emptyUsername() {
        //setup empty username
        LoginInputData inputData = new LoginInputData("", "password");
        TestDataAccess dataAccess = new TestDataAccess(true, true);
        TestPresenter presenter = new TestPresenter();
        //interactor
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        //execute
        interactor.execute(inputData);
        assert (presenter.getErrorMessage().equals("Username cannot be empty"));
    }
    //test empty password
    @Test
    public void emptyPassword() {
        //setup empty username
        LoginInputData inputData = new LoginInputData("username", "");
        TestDataAccess dataAccess = new TestDataAccess(true, true);
        TestPresenter presenter = new TestPresenter();
        //interactor
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        //execute
        interactor.execute(inputData);
        assert (presenter.getErrorMessage().equals("Password cannot be empty"));
    }
    //username no exist
    @Test
    public void usernoExist() {
        //setup empty username
        LoginInputData inputData = new LoginInputData("username", "password");
        TestDataAccess dataAccess = new TestDataAccess(false, true);
        TestPresenter presenter = new TestPresenter();
        //interactor
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        //execute
        interactor.execute(inputData);
        assert (presenter.getErrorMessage().equals("User does not exist"));
    }

    //password incorrect
    @Test
    public void passwordIncorrect() {
        //setup empty username
        LoginInputData inputData = new LoginInputData("username", "password");
        TestDataAccess dataAccess = new TestDataAccess(true, false);
        TestPresenter presenter = new TestPresenter();
        //interactor
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        interactor.execute(inputData);
        assert (presenter.getErrorMessage().equals("Incorrect password"));
    }

    //success
    @Test
    public void success() {
        //setup empty username
        LoginInputData inputData = new LoginInputData("testusername", "testpassword");
        TestDataAccess dataAccess = new TestDataAccess(true, true);
        TestPresenter presenter = new TestPresenter();
        //interactor
        LoginInteractor interactor = new LoginInteractor(dataAccess, presenter);
        interactor.execute(inputData);
        assert (presenter.getResponse().getUsername().equals("testusername"));
    }

}
