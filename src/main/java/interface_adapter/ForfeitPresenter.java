package interface_adapter;

import use_case.forfeit.ForfeitOutputBoundary;
import use_case.forfeit.ForfeitOutputData;

/**
 * Presenter for the Forfeit use case.
 */
public class ForfeitPresenter implements ForfeitOutputBoundary {
    private final ForfeitViewModel forfeitViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Constructs a ForfeitPresenter.
     * @param forfeitViewModel
     * @param viewManagerModel
     */
    public ForfeitPresenter(ForfeitViewModel forfeitViewModel, ViewManagerModel viewManagerModel) {
        this.forfeitViewModel = forfeitViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares and displays the forfeit view with game statistics.
     * @param outputData
     */
    @Override
    public void presentForfeitView(ForfeitOutputData outputData) {
        // Update the forfeit state with game information
        final ForfeitState state = forfeitViewModel.getState();
        state.setGameId(outputData.getGameId());
        state.setMode(outputData.getMode());
        state.setDifficulty(outputData.getDifficulty());
        state.setElapsedMs(outputData.getElapsedMs());
        state.setFinished(false); // Game was forfeited, not finished

        // Fire property change to update the view
        forfeitViewModel.firePropertyChange();

        // Navigate to forfeit view
        viewManagerModel.setState("forfeit");
        viewManagerModel.firePropertyChange();
    }

    /**
     * Navigates back to the main menu.
     */
    @Override
    public void presentMainMenu() {
        viewManagerModel.setState("main");
        viewManagerModel.firePropertyChange();
    }
}
