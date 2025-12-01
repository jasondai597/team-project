package app;

import API.SudokuApiClient;
import data_access.FirebaseGameDataAccess;
import data_access.SudokuRepositoryImpl;
import data_access.UserAccessDAO;
import entity.UserFactory;
import interface_adapter.*;
import interface_adapter.loggedIn.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signUp.SignUpController;
import interface_adapter.signUp.SignUpPresenter;
import interface_adapter.signUp.SignUpViewModel;
import use_case.Check.CheckInteractor;
import use_case.LoadingSudoku.LoadSudokuInteractor;
import use_case.game.GameDataAccess;
import use_case.hints.HintInteractor;
import use_case.login.LoginInputBoundary;
import use_case.login.LoginInteractor;
import use_case.processUserMoves.ProcessInteractor;
import use_case.save.SaveGameInteractor;   // NEW
import use_case.resume.ResumeGameInteractor; // NEW
import use_case.signUp.SignUpInputBoundary;
import use_case.signUp.SignUpInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;

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
    private LoggedInViewModel loggedInViewModel;
    private LoginViewModel loginViewModel;

    // Controllers
    private SudokuController sudokuController;
    private SaveGameController saveController;     // NEW
    private ResumeGameController resumeController; // NEW

    private hintController hintController;
    private processController processController;
    private CheckController checkController;
    private ForfeitController forfeitController;

    private SignUpController signUpController;
    private LoginController loginController;

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

        loggedInViewModel = new LoggedInViewModel();
        loginViewModel = new LoginViewModel();

        // Presenters
        SudokuPresenter sudokuPresenter = new SudokuPresenter(sudokuBoardViewModel);
        HintPresenter hintPresenter = new HintPresenter(sudokuBoardViewModel);
        processPresenter processPresenterAdapter = new processPresenter(sudokuBoardViewModel);
        CheckPresenter checkPresenter = new CheckPresenter(sudokuBoardViewModel, viewManagerModel);
        final SignUpPresenter signUpPresenter = new SignUpPresenter(signUpViewModel, viewManagerModel,
                loggedInViewModel);
        final LoginPresenter loginPresenter = new LoginPresenter(loginViewModel, viewManagerModel, loggedInViewModel);

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

        // 5. SignUp/login Interactor
        SignUpInputBoundary signUpInteractor = new SignUpInteractor(userAccessDAO, signUpPresenter, userFactory);
        LoginInputBoundary loginInteractor = new LoginInteractor(userAccessDAO, loginPresenter);
        // --- CONTROLLERS ---

        sudokuController = new SudokuController(loadSudokuInteractor);
        saveController = new SaveGameController(saveInteractor);       // NEW
        resumeController = new ResumeGameController(resumeInteractor); // NEW

        hintController = new hintController(hintInteractor);
        processController = new processController(processInteractor);
        checkController = new CheckController(checkInteractor);

        signUpController = new SignUpController(signUpInteractor, viewManagerModel);
        loginController = new LoginController(loginInteractor, viewManagerModel);

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

    public AppBuilder addWinView() {
        winView winView = new winView(viewManagerModel, sudokuBoardViewModel);
        cardPanel.add(winView, winView.getViewName());

        return this;
    }

    public AppBuilder addDifficultyView() {
        difficultyView difficultyView = new difficultyView(sudokuController, viewManagerModel);
        cardPanel.add(difficultyView, "difficulty");
        return this;
    }

    /**
     * SignUpView builder.
     * @return the app
     */
    public AppBuilder addSignUpView() {
        final signUpView signUp = new signUpView(signUpViewModel);
        signUp.setController(signUpController);
        cardPanel.add(signUp, signUp.getViewName());
        return this;
    }

    /**
     * LoggedInView builder.
     * @return this builder
     */
    public AppBuilder addLoggedInView() {
        final loggedInView loggedIn = new loggedInView(
                viewManagerModel,
                sudokuController,
                resumeController,
                sudokuBoardViewModel,
                loggedInViewModel
                );
        cardPanel.add(loggedIn, loggedIn.getViewName());
        return this;
    }

    /**
     * LoginView builder.
     * @return the app
     */
    public AppBuilder addLoginView() {
        final loginView login = new loginView(loginViewModel);
        login.setController(loginController);
        cardPanel.add(login, login.getViewName());
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