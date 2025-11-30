package use_case.signUp;

public interface SignUpOutputBoundary {

    /**
     * prepare success view
     * @param outputData the output data
     */
    void presentSuccessView(SignUpOutputData outputData);


    /**
     * Presents a failure view with the given error message.
     * @param errorMessage the error message to display
     */
    void presentFailureView(String errorMessage);

    /**
     * present a logged in view if sucess
     */
    void presentLoggedInView();


}
