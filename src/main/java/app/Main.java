package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the Sudoku application.
 */
public class Main {

    /**
     * Main method to launch the application.
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Build the application using the builder pattern
            // Note: Order matters - Sudoku use case must be added before Forfeit use case and Main view
            final JFrame application = new AppBuilder()
                    .addSudokuUseCase()
                    .addForfeitUseCase()
                    .addMainView()
                    .addDifficultyView()
                    .addUnrankedBoardView()
                    .addRankedBoardView()
                    .addForfeitView()
                    .addWinView()
                    .addSignUpView() //added sign up view
                    .addLoggedInView()
                    .addLoginView()
                    .build();

        // Display the application
        final int applicationSize = 900;
        application.setSize(applicationSize, applicationSize);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}