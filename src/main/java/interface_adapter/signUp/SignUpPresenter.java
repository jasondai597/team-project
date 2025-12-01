package interface_adapter.signUp;

import interface_adapter.ViewManagerModel;
import interface_adapter.loggedIn.LoggedInState;
import interface_adapter.loggedIn.LoggedInViewModel;
import use_case.signUp.SignUpOutputBoundary;
import use_case.signUp.SignUpOutputData;

public class SignUpPresenter implements SignUpOutputBoundary {

    private final SignUpViewModel signUpViewModel;
    private final ViewManagerModel viewManagerModel;
    private final LoggedInViewModel loggedInViewModel;

    public SignUpPresenter(SignUpViewModel signUpViewModel,
                           ViewManagerModel viewManagerModel,
                           LoggedInViewModel loggedInViewModel) {
        this.signUpViewModel = signUpViewModel;
        this.viewManagerModel = viewManagerModel;
        this.loggedInViewModel = loggedInViewModel;
    }

    @Override
    public void presentSuccessView(SignUpOutputData outputData) {
        // On success switch to logged in view
        final LoggedInState loggedInState = this.loggedInViewModel.getState();
        loggedInState.setUsername(outputData.getUsername());
        loggedInViewModel.firePropertyChange();
        viewManagerModel.setState(loggedInViewModel.getViewName());
        viewManagerModel.firePropertyChange();
    }

    @Override
    public void presentFailureView(String errorMessage) {
        final SignUpState signUpState = signUpViewModel.getState();
        signUpState.setUsernameError(errorMessage);
        signUpViewModel.firePropertyChange();
    }
}
