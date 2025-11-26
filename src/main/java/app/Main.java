package app;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Main entry point for the Sudoku application.
 * Uses AppBuilder to construct the application following Clean Architecture principles.
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
                    .addUnrankedBoardView()
                    .addRankedBoardView()
                    .addForfeitView()
                    .addWinView()
                    .build();

            // Display the application
            application.setSize(900, 900);
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}