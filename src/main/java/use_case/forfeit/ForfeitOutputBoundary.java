package use_case.forfeit;

/**
 * Output boundary for the Forfeit use case.
 */
public interface ForfeitOutputBoundary {
    /**
     * Prepares the success view after successfully forfeiting a game.
     * @param outputData
     */
    void presentForfeitView(ForfeitOutputData outputData);

    /**
     * Prepares the view to return to the main menu.
     */
    void presentMainMenu();
}
