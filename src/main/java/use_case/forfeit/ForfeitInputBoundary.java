package use_case.forfeit;

/**
 * Input boundary for the Forfeit use case.
 */
public interface ForfeitInputBoundary {
    /**
     * Executes the forfeit use case.
     * @param forfeitInputData
     */
    void execute(ForfeitInputData forfeitInputData);
}
