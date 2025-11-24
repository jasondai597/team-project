package view;

import API.SudokuApiClient;
import data_access.SudokuRepositoryImpl;
import interface_adapter.SudokuBoardViewModel;
import interface_adapter.SudokuController;
import interface_adapter.SudokuPresenter;
import use_case.LoadingSudoku.LoadSudokuInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;


public class mainView extends JPanel implements ActionListener, PropertyChangeListener {

    private JButton playButton;
    private JButton rankedButton;
    private JButton loginButton;

    // optional card navigation context
    private CardLayout cardLayout = null;
    private JPanel cardContainer = null;

    public mainView() {
        this(null, null);
    }

    public mainView(CardLayout cardLayout, JPanel cardContainer) {
        this.cardLayout = cardLayout;
        this.cardContainer = cardContainer;

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        JLabel title = new JLabel("SudokuThing", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 32));
        title.setBorder(BorderFactory.createEmptyBorder(30, 0, 30, 0));
        add(title, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new GridLayout(2, 1, 0, 20));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
        centerPanel.setBackground(Color.WHITE);

        playButton = new JButton("Play");
        playButton.setActionCommand("PLAY");
        playButton.addActionListener(this);

        rankedButton = new JButton("Ranked");
        rankedButton.setActionCommand("RANKED");
        rankedButton.addActionListener(this);

        rankedButton.setOpaque(true);
        rankedButton.setBackground(new Color(196,196,196));
        rankedButton.setForeground(new Color(150,150,150));
        rankedButton.setFocusPainted(false);

        centerPanel.add(playButton);
        centerPanel.add(rankedButton);

        add(centerPanel, BorderLayout.CENTER);

        loginButton = new JButton("Login");
        loginButton.setActionCommand("LOGIN");
        loginButton.addActionListener(this);

        JPanel bottomPanel = new JPanel();
        bottomPanel.add(loginButton);
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        add(bottomPanel, BorderLayout.SOUTH);
    }


    public void setCardContext(CardLayout layout, JPanel container) {
        this.cardLayout = layout;
        this.cardContainer = container;
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();

        switch (cmd) {
            case "PLAY":
                if (cardLayout != null && cardContainer != null) {
                    cardLayout.show(cardContainer, "unranked");
                } else {
                    System.out.println("Play clicked");
                }
                break;

            case "RANKED":
                if (cardLayout != null && cardContainer != null) {
                    cardLayout.show(cardContainer, "ranked");
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "You must login first.",
                            "Ranked Mode Locked",
                            JOptionPane.WARNING_MESSAGE
                    );
                }
                break;

            case "LOGIN":
                System.out.println("Login clicked");
                break;
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CardLayout cards = new CardLayout();
            JPanel container = new JPanel(cards);

            mainView main = new mainView(cards, container);

            SudokuApiClient apiClient = new SudokuApiClient();
            SudokuRepositoryImpl repo = new SudokuRepositoryImpl(apiClient);

            SudokuBoardViewModel viewModel = new SudokuBoardViewModel();
            SudokuPresenter presenter = new SudokuPresenter(viewModel);

            use_case.game.GameDataAccess gameDataAccess = new data_access.InMemoryGameDataAccess();
            LoadSudokuInteractor interactor = new LoadSudokuInteractor(repo, presenter, gameDataAccess);
            SudokuController controller = new SudokuController(interactor);

            interface_adapter.HintPresenter hintPresenter = new interface_adapter.HintPresenter(viewModel);
            use_case.hints.HintInteractor hintinteractor = new use_case.hints.HintInteractor(hintPresenter);
            interface_adapter.hintController hint = new interface_adapter.hintController(hintinteractor);

            container.add(main, "main");

            JFrame frame = new JFrame("Sudoku App");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.getContentPane().add(container);
            frame.setSize(900, 900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            controller.loadPuzzle("easy");

            entity.SudokuPuzzle puzzle = interactor.getCurrentPuzzle();

            interface_adapter.processPresenter processPresenter = new interface_adapter.processPresenter(viewModel);
            use_case.processUserMoves.ProcessInteractor processInteractor = new use_case.processUserMoves.ProcessInteractor(puzzle, processPresenter);
            interface_adapter.processController processController = new interface_adapter.processController(processInteractor);

            interface_adapter.CheckPresenter checkPresenter = new interface_adapter.CheckPresenter(viewModel);
            use_case.Check.CheckInteractor checkInteractor = new use_case.Check.CheckInteractor(checkPresenter);
            interface_adapter.CheckController check = new interface_adapter.CheckController(checkInteractor);

            unRankedSudokuBoardView unranked = new unRankedSudokuBoardView(viewModel, controller, hint, processController, check);
            container.add(unranked, "unranked");

            rankedSudokuBoardView ranked = new rankedSudokuBoardView(viewModel, controller, hint, processController, check);
            container.add(ranked, "ranked");
        });
    }

    // Greyed out for testing purposes
//    public static void main(String[] args) {
//
//        SwingUtilities.invokeLater(() -> {
//            JFrame frame = new JFrame("Sudoku App");
//            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//            frame.setContentPane(new mainView());
//            frame.pack();
//            frame.setSize(400, 400);
//            frame.setLocationRelativeTo(null);
//            frame.setVisible(true);
//        });
//
//    }
}
