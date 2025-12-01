package interface_adapter.signUp;

import interface_adapter.ViewManagerModel;
import use_case.signUp.SignUpOutputBoundary;
import use_case.signUp.SignUpOutputData;

public class SignUpPresenter implements SignUpOutputBoundary {

    private final SignUpViewModel signUpViewModel;
    private final ViewManagerModel viewManagerModel;
    //TODO implement this
    // private final LoggedInViewModel:


    public SignUpPresenter(SignUpViewModel signUpViewModel, ViewManagerModel viewManagerModel) {
        this.signUpViewModel = signUpViewModel;
        this.viewManagerModel = viewManagerModel;
        // this.loggedInViewModel = loggedInViewModel;
    }


    @Override
    public void presentSuccessView(SignUpOutputData outputData) {
        // On success switch to logged in view
        //TODO implement this when logged in view is implemented
        System.out.println("Sign Up Success");

    }

    @Override
    public void presentFailureView(String errorMessage) {
        final SignUpState signUpState = signUpViewModel.getState();
        signUpState.setUsernameError(errorMessage);
        signUpViewModel.firePropertyChange();
    }
}
