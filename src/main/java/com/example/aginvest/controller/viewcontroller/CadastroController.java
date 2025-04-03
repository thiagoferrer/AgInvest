package com.example.aginvest.controller.viewcontroller;

import com.example.aginvest.controller.BuscarCep;
import com.example.aginvest.controller.DataValidate;
import com.example.aginvest.controller.HashSenha;
import com.example.aginvest.controller.user.UserController;
import com.example.aginvest.model.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import com.example.aginvest.controller.user.CpfValidate;

import java.io.IOException;

public class CadastroController {

    @FXML
    private TextField nomeFieldCadastro;

    @FXML
    private TextField cpfFieldCadastro;

    @FXML
    private ComboBox<String> generoComboCadastro;

    @FXML
    private TextField dataNascimentoFieldCadastro;

    @FXML
    private TextField emailFieldCadastro;

    @FXML
    private TextField enderecoField;

    @FXML
    private TextField cepFieldCadastro;

    @FXML
    private PasswordField senhaFieldCadastro;

    @FXML
    private Button voltarButton;

    @FXML
    private Button cadastrarButton;

    private UserModel user;

    @FXML
    private void handleVoltar() {
        try {
            // Carrega a tela inicial (Logo.fxml)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/Logo.fxml"));
            Parent root = loader.load();

            // Obtém o palco atual a partir do botão
            Stage stage = (Stage) voltarButton.getScene().getWindow();

            // Cria uma nova cena e define no palco
            Scene scene = new Scene(root, 360, 640);
            stage.setScene(scene);
            stage.setTitle("Invest7");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erro ao carregar a tela inicial: " + e.getMessage());
        }
    }

    @FXML
    private void handleCadastrar() {
        // Coleta os dados dos campos
        String nome = nomeFieldCadastro.getText();
        String cpf = cpfFieldCadastro.getText();
        String genero = generoComboCadastro.getValue();
        String dataNascimento = dataNascimentoFieldCadastro.getText();
        String email = emailFieldCadastro.getText();
        String endereco = enderecoField.getText();
        String cep = cepFieldCadastro.getText();
        String senha = senhaFieldCadastro.getText();
        UserController userCadastro =  new UserController();
        HashSenha senhacrip = new HashSenha();
        String senhaHash = senhacrip.hashSenha(senha);
        BuscarCep viacep = new BuscarCep();
        DataValidate data = new DataValidate();
        String data_nasc = null;

        try {
            if (nome.isEmpty() || cpf.isEmpty() || genero.isEmpty() || dataNascimento.isEmpty()
                    || email.isEmpty()|| cep.isEmpty() || senha.isEmpty() ){
                // Mostrar alerta para o usuário
                System.err.println("Algum campo nulo");
                return;
            }



            boolean emailExiste = userCadastro.verificaEmail(email);

            if (emailExiste){
                System.err.println("Email já existe");
            }


            String cpfValidado = CpfValidate.validaCpf(cpf);
            if (cpfValidado != null) {
                boolean cpfExiste = userCadastro.verificaCPF(cpfValidado);

                if (cpfExiste) {
                    System.out.println("CPF já cadastrado. Tente novamente.");
                }
            } else {
                System.out.println("CPF inválido! Tente novamente.");
            }

            String enderecoCEP = viacep.buscarApi(cep);


            data_nasc = data.validarData(dataNascimento);
            if (data_nasc == null) {
                System.out.println("Data inválida. Por favor, tente novamente.");
            }

            UserModel userModel = new UserModel(nome, email, senhaHash, cpf,enderecoCEP,  genero, data_nasc, 0);
            carregarTelaQuestionario(userModel);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro durante o login: " + e.getMessage());
        }
    }

    private void carregarTelaQuestionario(UserModel user) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/aginvest/QuestionarioPerfil.fxml"));
            Scene mainScene = new Scene(loader.load(), 360, 640);

            QuestionarioPerfilController controller = loader.getController();
            controller.setUser(user);

            String css = getClass().getResource("/com/example/aginvest/styles.css").toExternalForm();
            if (css != null) {
                mainScene.getStylesheets().add(css);
            }

            Stage stage = (Stage) cadastrarButton.getScene().getWindow();
            stage.setScene(mainScene);
            stage.setTitle("Tela Principal");
            stage.setWidth(360);
            stage.setHeight(640);
            stage.centerOnScreen();
            stage.show();

            // Opcional: feche a janela atual se necessário
            // ((Stage) cadastrarButton.getScene().getWindow()).close();

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar tela principal: " + e.getMessage());
        }
    }
}