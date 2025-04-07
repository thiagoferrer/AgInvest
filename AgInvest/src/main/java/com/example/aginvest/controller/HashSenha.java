package com.example.aginvest.controller;
import at.favre.lib.crypto.bcrypt.BCrypt;


public class HashSenha {

    public String hashSenha(String senha){
     return BCrypt.withDefaults().hashToString(12, senha.toCharArray());
    }
}
