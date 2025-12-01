package use_case.login;

/**
 * Input boundary for Login usecase, used in the interactor
 */
public interface LoginInputBoundary {
    /**
     * Executes the login use case.
     * @param data
     */
    void execute(LoginInputData data);
}
