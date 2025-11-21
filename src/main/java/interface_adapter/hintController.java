package interface_adapter;

import use_case.hints.HintInteractor;

public class hintController {
    private final HintInteractor interactor;

    public hintController(HintInteractor interactor) {
        this.interactor = interactor;
    }

    public void hint() {
        interactor.execute();
    }

}
