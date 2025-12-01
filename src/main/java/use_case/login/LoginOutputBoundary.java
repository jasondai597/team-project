package use_case.login;

public interface LoginOutputBoundary {
    /**
     * Presents an error message to the user.
     * @param errorMessage the error message to display
     */
    void presentError(String errorMessage);

    /**
     * Presents the success view.
     * @param data the output data
     */
    void presentSuccess(LoginOutputData data);
}
