package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.BuscarCep;
import com.example.aginvest.controller.DataValidate;
import com.example.aginvest.controller.HashSenha;
import com.example.aginvest.controller.user.UserController;
import com.example.aginvest.model.UserModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.aginvest.controller.user.CpfValidate;

import java.io.IOException;

public class CadastroController {

    @FXML private TextField nomeFieldCadastro;
    @FXML private TextField cpfFieldCadastro;
    @FXML private ComboBox<String> generoComboCadastro;
    @FXML private TextField dataNascimentoFieldCadastro;
    @FXML private TextField emailFieldCadastro;
    @FXML private TextField enderecoField;
    @FXML private TextField cepFieldCadastro;
    @FXML private PasswordField senhaFieldCadastro;
    @FXML private Button voltarButton;
    @FXML private Button cadastrarButton;
    @FXML private Label errorMessage;

    private UserModel user;

    @FXML
    public void initialize() {
        // Limpa os estilos de erro quando o usuário começa a digitar
        nomeFieldCadastro.setOnKeyPressed(e -> clearErrorStyles());
        cpfFieldCadastro.setOnKeyPressed(e -> clearErrorStyles());
        generoComboCadastro.setOnAction(e -> clearErrorStyles());
        dataNascimentoFieldCadastro.setOnKeyPressed(e -> clearErrorStyles());
        emailFieldCadastro.setOnKeyPressed(e -> clearErrorStyles());
        enderecoField.setOnKeyPressed(e -> clearErrorStyles());
        cepFieldCadastro.setOnKeyPressed(e -> clearErrorStyles());
        senhaFieldCadastro.setOnKeyPressed(e -> clearErrorStyles());
    }

    @FXML
    private void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Logo.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) voltarButton.getScene().getWindow();
            Scene scene = new Scene(root, 360, 640);
            stage.setScene(scene);
            stage.setTitle("Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar a tela inicial: " + e.getMessage());
        }
    }

    @FXML
    private void handleCadastrar() {
        try {
            clearErrorStyles();

            String nome = nomeFieldCadastro.getText();
            String cpf = cpfFieldCadastro.getText();
            String genero = generoComboCadastro.getValue();
            String dataNascimento = dataNascimentoFieldCadastro.getText();
            String email = emailFieldCadastro.getText();
            String endereco = enderecoField.getText();
            String cep = cepFieldCadastro.getText();
            String senha = senhaFieldCadastro.getText();

            // Validação de campos obrigatórios
            if (nome.isEmpty() || cpf.isEmpty() || genero == null || dataNascimento.isEmpty()
                    || email.isEmpty() || cep.isEmpty() || senha.isEmpty()) {
                showError("Todos os campos são obrigatórios!");
                highlightEmptyFields(nome, cpf, genero, dataNascimento, email, cep, senha);
                return;
            }

            UserController userCadastro = new UserController();

            // Validação de email
            boolean emailExiste = userCadastro.verificaEmail(email);
            if (emailExiste) {
                showError("Email já cadastrado!");
                emailFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
                return;
            }

            // Validação de CPF
            String cpfValidado = CpfValidate.validaCpf(cpf);
            if (cpfValidado == null) {
                showError("CPF inválido!");
                cpfFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
                return;
            }

            boolean cpfExiste = userCadastro.verificaCPF(cpfValidado);
            if (cpfExiste) {
                showError("CPF já cadastrado!");
                cpfFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
                return;
            }

            // Validação de data
            DataValidate data = new DataValidate();
            String data_nasc = data.validarData(dataNascimento);
            if (data_nasc == null) {
                showError("Data de nascimento inválida! Use o formato aaaa/mm/dd");
                dataNascimentoFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
                return;
            }

            // Validação de CEP
            BuscarCep viacep = new BuscarCep();
            String enderecoCEP = viacep.buscarApi(cep);
            if (enderecoCEP == null) {
                showError("CEP inválido ou não encontrado!");
                cepFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
                return;
            }


            // Se todas as validações passarem, cria o usuário
            HashSenha senhacrip = new HashSenha();
            String senhaHash = senhacrip.hashSenha(senha);

            UserModel userModel = new UserModel(nome, email, senhaHash, cpfValidado, enderecoCEP, genero, data_nasc, 0);
            carregarTelaQuestionario(userModel);

        } catch (Exception e) {
            e.printStackTrace();
            showError("Erro durante o cadastro: " + e.getMessage());
        }
    }

    private void highlightEmptyFields(String nome, String cpf, String genero,
                                      String dataNascimento, String email, String cep, String senha) {
        if (nome.isEmpty()) nomeFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        if (cpf.isEmpty()) cpfFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        if (genero == null) generoComboCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        if (dataNascimento.isEmpty()) dataNascimentoFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        if (email.isEmpty()) emailFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        if (cep.isEmpty()) cepFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
        if (senha.isEmpty()) senhaFieldCadastro.setStyle("-fx-border-color: #FF0000; -fx-border-radius: 8;");
    }

    private void showError(String message) {
        errorMessage.setText(message);
        errorMessage.setVisible(true);
    }

    private void clearErrorStyles() {
        errorMessage.setVisible(false);
        nomeFieldCadastro.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        cpfFieldCadastro.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        generoComboCadastro.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        dataNascimentoFieldCadastro.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        emailFieldCadastro.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        enderecoField.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        cepFieldCadastro.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
        senhaFieldCadastro.setStyle("-fx-border-color: #D3D3D3; -fx-border-radius: 8;");
    }

    private void carregarTelaQuestionario(UserModel user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/QuestionarioPerfil.fxml"));
            Parent root = loader.load();

            QuestionarioPerfilController controller = loader.getController();
            controller.setUser(user);

            Stage stage = (Stage) cadastrarButton.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showError("Erro ao carregar próxima tela: " + e.getMessage());
        }
    }
}