package com.example.aginvest.controller.user;

import at.favre.lib.crypto.bcrypt.BCrypt;
/*
import com.invest7.dao.*;
import com.invest7.model.UserModel;
import com.invest7.util.JwtUTIL;
import com.invest7.view.menus.MenuInicial;

 */

import com.example.aginvest.dao.*;
import com.example.aginvest.model.UserModel;
import com.example.aginvest.util.JwtUTIL;
public class UserController {
    public boolean verificaCPF(String cpf){
        VerificaSeExisteDAO cpfCliente = new VerificaSeExisteDAO();
        return cpfCliente.verificaCPF(cpf);
    }

    public boolean verificaEmail(String email){
        VerificaSeExisteDAO cpfCliente = new VerificaSeExisteDAO();
        return cpfCliente.verificaEmail(email);
    }

    public boolean criarUser(String nome, String email, String senhaHash, String cpf, String endereco,String genero, String dt_nasc, int id_perfil){
        UserDAO userDAO = new UserDAO();
        UserModel user = new UserModel(nome, cpf, endereco, email, senhaHash, genero, dt_nasc, id_perfil);
        user = userDAO.createUser(user);
        if (user.getId_user() > 0){
            UserSession.setLoggedInUserId(user.getId_user());
            return true;
        } else return false;

    }

    public String login(String email, String senha) {
        UserDAO userDAO = new UserDAO();
        UserModel user = userDAO.findUserByEmail(new UserModel(email));
        String token = null;

        if (user == null) {
            return null;
        }

        if (BCrypt.verifyer().verify(senha.toCharArray(), user.getSenha()).verified) {
            token = JwtUTIL.generateToken(email);

            // Armazena o ID do usuário na sessão
            UserSession.setLoggedInUserId(user.getId_user());
            return token;
        } else {
            return null;
        }
    }

    public void logout() {
        // Limpa a sessão do usuário
        UserSession.clear();
/*
        MenuInicial menuInicial = new MenuInicial();
        menuInicial.exibirMenuInicial();

 */
    }


    public boolean uptadeUser(String nome, String genero, String endereco, String dt_nasc){
        UserDAO userDao =  new UserDAO();
        int id_user = UserSession.getLoggedInUserId();
        UserModel user = userDao.atualizarUser(id_user, nome, genero, endereco, dt_nasc);

        if (user != null){

            return true;}
        else {return false;}
    }

    public UserModel  lerUser(){
        int userId = UserSession.getLoggedInUserId();
        UserDAO userDAO= new UserDAO();
        UserModel user = userDAO.readUser(new UserModel(userId));
        return user;

    }

    public boolean deletarConta(String email, String senha){
        boolean existe = false;
        UserDAO user = new UserDAO();
        UserController userCreate = new UserController();
        existe = userCreate.verificaEmail(email);
        if (existe) {
            user.deletarBanco(new UserModel(email));
            return true;
        } else {
            return false;
        }
    }

    public UserModel atualizarPerfil(UserModel user){
        UserDAO dao = new UserDAO();
        UserModel userR = dao.formsUp(user);
        return user;
    }

}
