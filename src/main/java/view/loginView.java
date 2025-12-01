package view;

import interface_adapter.login.LoginController;
import interface_adapter.login.LoginState;
import interface_adapter.login.LoginViewModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class loginView extends JPanel implements ActionListener, PropertyChangeListener {
    // Logic stuff
    private final String viewName = "login";
    private LoginController loginController;
    private final LoginViewModel loginViewModel;
    // UI STUFF
    private final JTextField usernameInputField = new JTextField(15);
    private final JPasswordField passwordInputField = new JPasswordField(15);

    private final JButton loginButton;
    private final JButton homeButton;
    private final JButton signUpButton;

    public loginView(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
        loginViewModel.addPropertyChangeListener(this);

        // title
        final JLabel title = new JLabel(LoginViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font("Arial", Font.BOLD, 80));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 40, 0));

        // Username field
        final JPanel usernameField = new JPanel();
        final JLabel usernameLabel = new JLabel(LoginViewModel.USERNAME_LABEL);
        usernameField.setLayout(new FlowLayout(FlowLayout.LEFT));
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, LoginViewModel.FONT_SIZE));
        usernameInputField.setFont(new Font("Arial", Font.PLAIN, LoginViewModel.FONT_SIZE));
        usernameField.add(usernameLabel);
        usernameField.add(usernameInputField);

        // Password field
        final JPanel passwordField = new JPanel();
        passwordField.setLayout(new FlowLayout(FlowLayout.LEFT));
        final JLabel passwordLabel = new JLabel(LoginViewModel.PASSWORD_LABEL);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, LoginViewModel.FONT_SIZE));
        passwordInputField.setFont(new Font("Arial", Font.PLAIN, LoginViewModel.FONT_SIZE));
        passwordField.add(passwordLabel);
        passwordField.add(passwordInputField);

        // Buttons
        final JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 3, 10, 10));

        loginButton = new JButton(LoginViewModel.LOGIN_BUTTON_LABEL);
        loginButton.setActionCommand("LOGIN");
        loginButton.addActionListener(this);

        homeButton = new JButton(LoginViewModel.HOME_BUTTON_LABEL);
        homeButton.setActionCommand("HOME");
        homeButton.addActionListener(this);

        signUpButton = new JButton(LoginViewModel.SIGNUP_BUTTON_LABEL);
        signUpButton.setActionCommand("SIGNUP");
        signUpButton.addActionListener(this);

        buttonPanel.add(signUpButton);
        buttonPanel.add(loginButton);
        buttonPanel.add(homeButton);

        addUsernameListener();
        addPasswordListener();

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(usernameField);
        this.add(passwordField);
        this.add(buttonPanel);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        final String cmd = evt.getActionCommand();
        switch (cmd) {
            case "LOGIN":
                final LoginState currentState = loginViewModel.getState();

                loginController.login(
                        currentState.getUsername(),
                        currentState.getPassword()
                );
                break;

            case "SIGNUP":
                loginController.switchToSignUpView();
                break;

            case "HOME":
                loginController.switchToHomeView();
                break;

            default:
                break;
        }
    }

    private void addUsernameListener() {
        usernameInputField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final LoginState currentState = loginViewModel.getState();
                currentState.setUsername(usernameInputField.getText());
                loginViewModel.setState(currentState);
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
                final LoginState currentState = loginViewModel.getState();
                currentState.setPassword(new String(passwordInputField.getPassword()));
                loginViewModel.setState(currentState);
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

    public void propertyChange(PropertyChangeEvent evt) {
        final LoginState state = (LoginState) evt.getNewValue();
        if (state.getLoginError() != null) {
            JOptionPane.showMessageDialog(this, state.getLoginError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setController(LoginController controller) {
        this.loginController = controller;
    }
}
