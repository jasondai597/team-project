package view;

import interface_adapter.signUp.SignUpController;
import interface_adapter.signUp.SignUpState;
import interface_adapter.signUp.SignUpViewModel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class signUpView extends JPanel implements ActionListener, PropertyChangeListener {
    // Logic stuff
    private final String viewName = "signUp";
    private SignUpController signUpController = null;
    private final SignUpViewModel signUpViewModel;
    // UI STUFF
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);
    private final JPasswordField repeatPasswordInputField = new JPasswordField(15);

    private final JButton signUpButton;
    private final JButton homeButton;
    private final JButton loginButton;

    public signUpView(SignUpViewModel signUpViewModel) {

        //Create title
        this.signUpViewModel = signUpViewModel;
        signUpViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SignUpViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 80));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Font for text below
        int textFont = 40;

        // create usernameField
        final JPanel usernameField = new JPanel();
        usernameInputField.setFont(new Font("Arial", Font.PLAIN, textFont));
        usernameField.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel(SignUpViewModel.USERNAME_LABEL);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, textFont));
        usernameField.add(usernameLabel);
        usernameField.add(usernameInputField);

        // create passwordField
        final JPanel passwordField = new JPanel();
        passwordInputField.setFont(new Font("Arial", Font.PLAIN, textFont));
        passwordField.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordInputLabel = new JLabel(SignUpViewModel.PASSWORD_LABEL);
        passwordInputLabel.setFont(new Font("Arial", Font.PLAIN, textFont));
        passwordField.add(passwordInputLabel);
        passwordField.add(passwordInputField);

        // create repeatPasswordField
        final JPanel repeatPasswordField = new JPanel();
        repeatPasswordInputField.setFont(new Font("Arial", Font.PLAIN, textFont));
        repeatPasswordField.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel repeatPasswordLabel = new JLabel(SignUpViewModel.REPEAT_PASSWORD_LABEL);
        repeatPasswordLabel.setFont(new Font("Arial", Font.PLAIN, textFont));
        repeatPasswordField.add(repeatPasswordLabel);
        repeatPasswordField.add(repeatPasswordInputField);

        //create buttons
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));

        loginButton = new JButton(SignUpViewModel.LOGIN_BUTTON_LABEL);
        loginButton.setActionCommand("LOGIN");
        loginButton.addActionListener(this);

        signUpButton = new JButton(SignUpViewModel.SIGNUP_BUTTON_LABEL);
        signUpButton.setActionCommand("SIGNUP");
        signUpButton.addActionListener(this);

        homeButton = new JButton(SignUpViewModel.HOME_BUTTON_LABEL);
        homeButton.setActionCommand("HOME");
        homeButton.addActionListener(this);


        buttonPanel.add(loginButton);
        buttonPanel.add(signUpButton);
        buttonPanel.add(homeButton);

        // Add listeners for text fields
        addUsernameListener();
        addPasswordListener();
        addRepeatPasswordListener();

        // Add labels and panels to this
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(title);
        this.add(usernameField);
        this.add(passwordField);
        this.add(repeatPasswordField);
        this.add(buttonPanel);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = evt.getActionCommand();
        switch (cmd) {
            case "LOGIN":
                signUpController.switchToLoginView();
                break;

            case "SIGNUP":
                final SignUpState state = signUpViewModel.getState();

                signUpController.execute(
                        state.getUsername(),
                        state.getPassword(),
                        state.getRepeatPassword());
                break;

            case "HOME":
                signUpController.switchToHomeView();
                break;

        }
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignUpState currentState = signUpViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                signUpViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addPasswordListener() {
        passwordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignUpState currentState = signUpViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                signUpViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    private void addRepeatPasswordListener() {
        repeatPasswordInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final SignUpState currentState = signUpViewModel.getState();
                currentState.setRepeatPassword(new String(repeatPasswordInputField.getPassword()));
                signUpViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SignUpState state = (SignUpState) evt.getNewValue();
        if (state.getUsernameError() != null) {
            JOptionPane.showMessageDialog(this, state.getUsernameError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(SignUpController controller) {
        this.signUpController = controller;
    }
}
