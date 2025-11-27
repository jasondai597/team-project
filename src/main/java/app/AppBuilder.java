package app;

import API.SudokuApiClient;
import data_access.FirebaseGameDataAccess;
import data_access.SudokuRepositoryImpl;
import interface_adapter.*;
import use_case.Check.CheckInteractor;
import use_case.LoadingSudoku.LoadSudokuInteractor;
import use_case.game.GameDataAccess;
import use_case.hints.HintInteractor;
import use_case.processUserMoves.ProcessInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    // Data Access
    private SudokuApiClient apiClient;
    private SudokuRepositoryImpl repository;
    private GameDataAccess gameDataAccess;

    // ViewModels
    private SudokuBoardViewModel sudokuBoardViewModel;
    private ForfeitViewModel forfeitViewModel;

    // Controllers
    private SudokuController sudokuController;
    private hintController hintController;
    private processController processController;
    private CheckController checkController;
    private ForfeitController forfeitController;

    // Interactors
    private LoadSudokuInteractor loadSudokuInteractor;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSudokuUseCase() {

        apiClient = new SudokuApiClient();
        repository = new SudokuRepositoryImpl(apiClient);
        gameDataAccess = new FirebaseGameDataAccess();

        sudokuBoardViewModel = new SudokuBoardViewModel();

        // Presenters
        SudokuPresenter sudokuPresenter = new SudokuPresenter(sudokuBoardViewModel);
        HintPresenter hintPresenter = new HintPresenter(sudokuBoardViewModel);
        processPresenter processPresenterAdapter = new processPresenter(sudokuBoardViewModel);
        CheckPresenter checkPresenter = new CheckPresenter(sudokuBoardViewModel, viewManagerModel);

        // Interactors
        loadSudokuInteractor =
                new LoadSudokuInteractor(repository, sudokuPresenter, gameDataAccess);

        HintInteractor hintInteractor = new HintInteractor(hintPresenter);
        ProcessInteractor processInteractor =
                new ProcessInteractor(null, processPresenterAdapter);
        CheckInteractor checkInteractor = new CheckInteractor(checkPresenter);

        // Controllers
        sudokuController = new SudokuController(loadSudokuInteractor);
        hintController = new hintController(hintInteractor);
        processController = new processController(processInteractor);
        checkController = new CheckController(checkInteractor);

        return this;
    }

    public AppBuilder addForfeitUseCase() {
        forfeitViewModel = new ForfeitViewModel();
        forfeitController = new ForfeitController(viewManagerModel, sudokuBoardViewModel);
        return this;
    }

    public AppBuilder addMainView() {
        mainView mainViewPanel = new mainView(
                viewManagerModel,
                sudokuController,
                sudokuBoardViewModel
        );
        cardPanel.add(mainViewPanel, mainViewPanel.getViewName());
        return this;
    }

    public AppBuilder addUnrankedBoardView() {
        unRankedSudokuBoardView unrankedView =
                new unRankedSudokuBoardView(
                        sudokuBoardViewModel,
                        viewManagerModel,
                        sudokuController,
                        hintController,
                        processController,
                        checkController,
                        forfeitController
                );
        cardPanel.add(unrankedView, unrankedView.getViewName());
        return this;
    }

    public AppBuilder addRankedBoardView() {
        rankedSudokuBoardView rankedView =
                new rankedSudokuBoardView(
                        sudokuBoardViewModel,
                        sudokuController,
                        hintController,
                        processController,
                        checkController,
                        forfeitController
                );
        cardPanel.add(rankedView, "ranked");
        return this;
    }

    public AppBuilder addForfeitView() {
        ForfeitView forfeitView = new ForfeitView(forfeitViewModel, forfeitController);
        cardPanel.add(forfeitView, forfeitView.getViewName());
        return this;
    }

    public AppBuilder addWinView(){
        winView winView = new winView(viewManagerModel, sudokuBoardViewModel);
        cardPanel.add(winView, winView.getViewName());

        return this;
    }

    public AppBuilder addDifficultyView(){
        difficultyView difficultyView = new difficultyView(sudokuController, viewManagerModel);
        cardPanel.add(difficultyView, "difficulty");
        return this;
    }

    public JFrame build() {
        JFrame frame = new JFrame("Sudoku App");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.add(cardPanel);

        viewManagerModel.setState("main");
        viewManagerModel.firePropertyChange();

        return frame;
    }
}
