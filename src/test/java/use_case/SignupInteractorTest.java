package use_case;

import entity.User;
import entity.UserFactory;
import org.junit.jupiter.api.Test;
import use_case.signUp.*;

public class SignupInteractorTest {
    // test presenter
    private static class TestPresenter implements SignUpOutputBoundary {
        private String errorMessage;
        private SignUpOutputData outputdata;

        public String getErrorMessage() {
            return errorMessage;
        }
        public SignUpOutputData getOutputdata() {
            return outputdata;
        }
        @Override
        public void presentSuccessView(SignUpOutputData outputdata) {
            this.outputdata = outputdata;
        }

        @Override
        public void presentFailureView(String errorMessage) {
            this.errorMessage = errorMessage;
        }
    }
    // test DATA
    private static class TestDataAccess implements SignUpUserDataAccessInterface {
        private User user;
        private Boolean userexists;

        public TestDataAccess(Boolean userexists) {
            this.userexists = userexists;
        }

        @Override
        public void addUser(User user) {
            this.user = user;
        }
        @Override
        public boolean checkForUser(String username) {
            return userexists;
        }

        public User getUser() {
            return user;
        }
    }
    //test empty username
    @Test
    public void emptyUsernameTest() {
        TestPresenter presenter = new TestPresenter();
        TestDataAccess dao = new TestDataAccess(true);
        UserFactory factory = new UserFactory();
        //interactor
        SignUpInteractor interactor = new SignUpInteractor(dao, presenter, factory);

        //Test execute
        SignUpInputData input = new SignUpInputData("", "password", "password");
        interactor.execute(input);
        assert (presenter.getErrorMessage().equals("Username cannot be empty"));
    }

    //test empty password
    @Test
    public void emptyPasswordTest() {
        TestPresenter presenter = new TestPresenter();
        TestDataAccess dao = new TestDataAccess(true);
        UserFactory factory = new UserFactory();
        //interactor
        SignUpInteractor interactor = new SignUpInteractor(dao, presenter, factory);

        //Test execute
        SignUpInputData input = new SignUpInputData("username", "", "password");
        interactor.execute(input);
        assert (presenter.getErrorMessage().equals("Password cannot be empty"));
    }

    //test password match
    @Test
    public void passwordMatchTest() {
        TestPresenter presenter = new TestPresenter();
        TestDataAccess dao = new TestDataAccess(true);
        UserFactory factory = new UserFactory();
        //interactor
        SignUpInteractor interactor = new SignUpInteractor(dao, presenter, factory);

        //Test execute
        SignUpInputData input = new SignUpInputData("Username", "password",
                "notSamepassword");
        interactor.execute(input);
        assert (presenter.getErrorMessage().equals("Passwords do not match"));
    }

    //test username not exist
    @Test
    public void usernameNotExistTest() {
        TestPresenter presenter = new TestPresenter();
        TestDataAccess dao = new TestDataAccess(true);
        UserFactory factory = new UserFactory();
        //interactor
        SignUpInteractor interactor = new SignUpInteractor(dao, presenter, factory);
        //test execute
        interactor.execute(new SignUpInputData("username", "password", "password"));
        assert (presenter.getErrorMessage().equals("Username already exists"));

    }

    //success
    @Test
    public void successTest() {
        TestPresenter presenter = new TestPresenter();
        TestDataAccess dao = new TestDataAccess(false);
        UserFactory factory = new UserFactory();
        //interactor
        SignUpInteractor interactor = new SignUpInteractor(dao, presenter, factory);
        //test execute
        interactor.execute(new SignUpInputData("username", "password", "password"));
        User output = dao.getUser();
        assert (output.getUsername().equals("username"));
        assert (output.getPassword().equals("password"));
        assert (presenter.getOutputdata().getUsername().equals("username"));
    }
}
