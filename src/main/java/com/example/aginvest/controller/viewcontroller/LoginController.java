import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private Button signInButton;

    @FXML
    private Hyperlink forgotPasswordLink;

    @FXML
    private void handleSignIn() {
        String email = emailField.getText();
        String password = passwordField.getText();
        System.out.println("Botão Sign In clicado! E-mail: " + email + ", Senha: " + password);
        // Adicione aqui a lógica para o login
    }

    @FXML
    private void handleForgotPassword() {
        System.out.println("Link Esqueci a senha clicado!");
        // Adicione aqui a lógica para recuperação de senha
    }
}

