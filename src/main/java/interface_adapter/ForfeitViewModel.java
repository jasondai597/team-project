package interface_adapter;

/**
 * ViewModel for the Forfeit View.
 */
public class ForfeitViewModel extends ViewModel<ForfeitState> {

    public ForfeitViewModel() {
        super("forfeit");
        setState(new ForfeitState());
    }
}
