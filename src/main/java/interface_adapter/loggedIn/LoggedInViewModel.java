package interface_adapter.loggedIn;

import interface_adapter.ViewModel;

public class LoggedInViewModel extends ViewModel<LoggedInState> {
    // View model for logged in screen
    public LoggedInViewModel() {
        super("loggedIn");
        setState(new LoggedInState());
    }
}
