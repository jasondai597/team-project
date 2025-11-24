package app;

import API.SudokuApiClient;
import data_access.InMemoryGameDataAccess;
import data_access.SudokuRepositoryImpl;
import entity.SudokuPuzzle;
import interface_adapter.*;
import use_case.Check.CheckInteractor;
import use_case.LoadingSudoku.LoadSudokuInteractor;
import use_case.game.GameDataAccess;
import use_case.hints.HintInteractor;
import use_case.processUserMoves.ProcessInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;

/**
 * AppBuilder constructs the application by wiring all layers following Clean Architecture.
 * Similar to the ca-lab AppBuilder pattern.
 */
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

    /**
     * Adds the Main View to the application.
     * @return this builder
     */
    public AppBuilder addMainView() {
        if (sudokuController == null) {
            throw new IllegalStateException("Must initialize Sudoku use case before adding main view");
        }
        mainView mainViewPanel = new mainView(viewManagerModel, sudokuController);
        cardPanel.add(mainViewPanel, mainViewPanel.getViewName());
        return this;
    }

    /**
     * Adds the Unranked Sudoku Board View to the application.
     * @return this builder
     */
    public AppBuilder addUnrankedBoardView() {
        if (sudokuBoardViewModel == null || sudokuController == null ||
            hintController == null || processController == null ||
            checkController == null || forfeitController == null) {
            throw new IllegalStateException("Must initialize Sudoku use case before adding unranked board view");
        }

        unRankedSudokuBoardView unrankedView = new unRankedSudokuBoardView(
                sudokuBoardViewModel, sudokuController, hintController,
                processController, checkController, forfeitController);
        cardPanel.add(unrankedView, "unranked");
        return this;
    }

    /**
     * Adds the Ranked Sudoku Board View to the application.
     * @return this builder
     */
    public AppBuilder addRankedBoardView() {
        if (sudokuBoardViewModel == null || sudokuController == null ||
            hintController == null || processController == null ||
            checkController == null || forfeitController == null) {
            throw new IllegalStateException("Must initialize Sudoku use case before adding ranked board view");
        }

        rankedSudokuBoardView rankedView = new rankedSudokuBoardView(
                sudokuBoardViewModel, sudokuController, hintController,
                processController, checkController, forfeitController);
        cardPanel.add(rankedView, "ranked");
        return this;
    }

    /**
     * Adds the Forfeit View to the application.
     * @return this builder
     */
    public AppBuilder addForfeitView() {
        if (forfeitViewModel == null || forfeitController == null) {
            throw new IllegalStateException("Must initialize Forfeit use case before adding forfeit view");
        }

        ForfeitView forfeitView = new ForfeitView(forfeitViewModel, forfeitController);
        cardPanel.add(forfeitView, forfeitView.getViewName());
        return this;
    }

    /**
     * Initializes the Sudoku use case with all necessary components.
     * @return this builder
     */
    public AppBuilder addSudokuUseCase() {
        // Initialize data access
        apiClient = new SudokuApiClient();
        repository = new SudokuRepositoryImpl(apiClient);
        gameDataAccess = new InMemoryGameDataAccess();

        // Create ViewModels
        sudokuBoardViewModel = new SudokuBoardViewModel();

        // Create Presenters
        SudokuPresenter sudokuPresenter = new SudokuPresenter(sudokuBoardViewModel);
        HintPresenter hintPresenter = new HintPresenter(sudokuBoardViewModel);
        processPresenter processPresenterAdapter = new processPresenter(sudokuBoardViewModel);
        CheckPresenter checkPresenter = new CheckPresenter(sudokuBoardViewModel);

        // Create Interactors (Use Cases)
        loadSudokuInteractor = new LoadSudokuInteractor(repository, sudokuPresenter, gameDataAccess);
        HintInteractor hintInteractor = new HintInteractor(hintPresenter);

        // Create Controllers
        sudokuController = new SudokuController(loadSudokuInteractor);
        hintController = new hintController(hintInteractor);

        // Load initial puzzle
        sudokuController.loadPuzzle("easy");

        // Create process and check controllers after puzzle is loaded
        SudokuPuzzle puzzle = loadSudokuInteractor.getCurrentPuzzle();
        ProcessInteractor processInteractor = new ProcessInteractor(puzzle, processPresenterAdapter);
        processController = new processController(processInteractor);

        CheckInteractor checkInteractor = new CheckInteractor(checkPresenter);
        checkController = new CheckController(checkInteractor);

        return this;
    }

    /**
     * Initializes the Forfeit use case.
     * @return this builder
     */
    public AppBuilder addForfeitUseCase() {
        if (sudokuBoardViewModel == null) {
            throw new IllegalStateException("Must initialize Sudoku use case before Forfeit use case");
        }
        forfeitViewModel = new ForfeitViewModel();
        forfeitController = new ForfeitController(viewManagerModel, sudokuBoardViewModel);
        return this;
    }

    /**
     * Builds and returns the application as a JFrame.
     * @return the application frame
     */
    public JFrame build() {
        final JFrame application = new JFrame("Sudoku App");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        application.add(cardPanel);

        // Set initial view
        viewManagerModel.setState("main");
        viewManagerModel.firePropertyChange();

        return application;
    }
}
