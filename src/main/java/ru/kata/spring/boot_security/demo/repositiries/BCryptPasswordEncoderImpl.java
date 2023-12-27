package ru.kata.spring.boot_security.demo.repositiries;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public class BCryptPasswordEncoderImpl implements PasswwordEncoder{

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public BCryptPasswordEncoderImpl() {
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }
    @Override
    public String encode(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
