package use_case.signUp;

/**
 * Input boundary for SignUp usecase, used in interactor
 */
public interface SignUpInputBoundary {
    /**
     * Execute the signup usecase
     * @param signupInputData the input data
     */
    void execute(SignUpInputData signupInputData);
    //Deleted switch to logged in view cuz i can handle that in view
}
