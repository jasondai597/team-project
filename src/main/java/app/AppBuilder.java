package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import API.SudokuApiClient;
import data_access.FirebaseGameDataAccess;
import data_access.SudokuRepositoryImpl;
import data_access.UserAccessDAO;
import entity.UserFactory;
import interface_adapter.CheckController;
import interface_adapter.CheckPresenter;
import interface_adapter.ForfeitController;
import interface_adapter.ForfeitViewModel;
import interface_adapter.HintPresenter;
import interface_adapter.ResumeGameController;   // NEW
import interface_adapter.SaveGameController; // NEW
import interface_adapter.SudokuBoardViewModel;
import interface_adapter.SudokuController;
import interface_adapter.SudokuPresenter;
import interface_adapter.ViewManagerModel;
import interface_adapter.hintController;
import interface_adapter.processController;
import interface_adapter.processPresenter;
import interface_adapter.signUp.SignUpController;
import interface_adapter.signUp.SignUpPresenter;
import interface_adapter.signUp.SignUpViewModel;
import use_case.Check.CheckInteractor;
import use_case.LoadingSudoku.LoadSudokuInteractor;
import use_case.game.GameDataAccess;
import use_case.hints.HintInteractor;
import use_case.processUserMoves.ProcessInteractor;
import use_case.resume.ResumeGameInteractor;
import use_case.save.SaveGameInteractor;
import use_case.signUp.SignUpInputBoundary;
import use_case.signUp.SignUpInteractor;
import view.ForfeitView;
import view.ViewManager;
import view.difficultyView;
import view.mainView;
import view.rankedSudokuBoardView;
import view.signUpView;
import view.unRankedSudokuBoardView;
import view.winView;

public class AppBuilder {

    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final UserFactory userFactory = new UserFactory();

    // Data Access
    private SudokuApiClient apiClient;
    private SudokuRepositoryImpl repository;
    private GameDataAccess gameDataAccess;
    private UserAccessDAO userAccessDAO;

    // ViewModels
    private SudokuBoardViewModel sudokuBoardViewModel;
    private ForfeitViewModel forfeitViewModel;
    private SignUpViewModel signUpViewModel;

    // Controllers
    private SudokuController sudokuController;
    private SaveGameController saveController;     // NEW
    private ResumeGameController resumeController; // NEW

    private hintController hintController;
    private processController processController;
    private CheckController checkController;
    private ForfeitController forfeitController;

    private SignUpController signUpController;

    // Interactors
    private LoadSudokuInteractor loadSudokuInteractor;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    public AppBuilder addSudokuUseCase() {

        apiClient = new SudokuApiClient();
        repository = new SudokuRepositoryImpl(apiClient);
        gameDataAccess = new FirebaseGameDataAccess();
        userAccessDAO = new UserAccessDAO();

        sudokuBoardViewModel = new SudokuBoardViewModel();

        signUpViewModel = new SignUpViewModel();

        // Presenters
        SudokuPresenter sudokuPresenter = new SudokuPresenter(sudokuBoardViewModel);
        HintPresenter hintPresenter = new HintPresenter(sudokuBoardViewModel);
        processPresenter processPresenterAdapter = new processPresenter(sudokuBoardViewModel);
        CheckPresenter checkPresenter = new CheckPresenter(sudokuBoardViewModel, viewManagerModel);
        SignUpPresenter signUpPresenter = new SignUpPresenter(signUpViewModel, viewManagerModel);

        // --- INTERACTOR WIRING ---

        // 1. ProcessInteractor (Must be created early to be shared)
        ProcessInteractor processInteractor = new ProcessInteractor(null, processPresenterAdapter);

        // 2. LoadSudokuInteractor (Now receives ProcessInteractor)
        loadSudokuInteractor = new LoadSudokuInteractor(
                repository,
                sudokuPresenter,
                gameDataAccess,
                processInteractor // <== Injection
        );

        // 3. Save & Resume Interactors (New Architecture)
        SaveGameInteractor saveInteractor = new SaveGameInteractor(gameDataAccess, sudokuBoardViewModel);
        ResumeGameInteractor resumeInteractor = new ResumeGameInteractor(gameDataAccess, sudokuPresenter, processInteractor);

        // 4. Other Interactors
        HintInteractor hintInteractor = new HintInteractor(hintPresenter);
        CheckInteractor checkInteractor = new CheckInteractor(checkPresenter);

        // 5. SignUp Interactor
        SignUpInputBoundary signUpInteractor = new SignUpInteractor(userAccessDAO, signUpPresenter, userFactory);

        // --- CONTROLLERS ---

        sudokuController = new SudokuController(loadSudokuInteractor);
        saveController = new SaveGameController(saveInteractor);       // NEW
        resumeController = new ResumeGameController(resumeInteractor); // NEW

        hintController = new hintController(hintInteractor);
        processController = new processController(processInteractor);
        checkController = new CheckController(checkInteractor);

        signUpController = new SignUpController(signUpInteractor, viewManagerModel);

        return this;
    }

    public AppBuilder addForfeitUseCase() {
        forfeitViewModel = new ForfeitViewModel();

        // Create the presenter (output boundary)
        interface_adapter.ForfeitPresenter forfeitPresenter =
            new interface_adapter.ForfeitPresenter(forfeitViewModel, viewManagerModel);

        // Create the interactor (input boundary)
        use_case.forfeit.ForfeitInteractor forfeitInteractor =
            new use_case.forfeit.ForfeitInteractor(gameDataAccess, forfeitPresenter);

        // Create the controller
        forfeitController = new ForfeitController(forfeitInteractor, forfeitPresenter, sudokuBoardViewModel);

        return this;
    }

    public AppBuilder addMainView() {
        mainView mainViewPanel = new mainView(
                viewManagerModel,
                sudokuController,
                resumeController, // <== Pass Resume Controller
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
                        saveController,   // <== Pass Save Controller
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

    public AppBuilder addSignUpView() {
        signUpView signUp = new signUpView(signUpViewModel);
        signUp.setController(signUpController);
        cardPanel.add(signUp, signUp.getViewName());
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