package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the Sudoku application.
 */
public class Main {

    /**
     * Main method to launch the application.
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> buildAndDisplayApplication());
    }

    private static void buildAndDisplayApplication() {
        // Build the application using the builder pattern
        final JFrame application = new AppBuilder()
                .addSudokuUseCase()
                .addForfeitUseCase()
                .addMainView()
                .addDifficultyView()
                .addUnrankedBoardView()
                .addRankedBoardView()
                .addForfeitView()
                .addWinView()
                .addSignUpView()
                .build();

        // Display the application
        final int applicationSize = 900;
        application.setSize(applicationSize, applicationSize);
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}