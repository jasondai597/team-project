package use_case.hints;

public interface HintOutputBoundary {
    void presentHint(int r, int c, int value);
    void presentNoHint();
}
