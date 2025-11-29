package interface_adapter;

import use_case.resume.ResumeGameInputBoundary;

public class ResumeGameController {
    private final ResumeGameInputBoundary interactor;

    public ResumeGameController(ResumeGameInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void resume() {
        interactor.execute();
    }
}