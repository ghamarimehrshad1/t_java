package com.example.library.service;

import com.example.library.exception.AdminNotFoundException;
import com.example.library.exception.SecurityException;
import com.example.library.exception.UsernameAlreadyTakenException;
import com.example.library.model.Admin;
import com.example.library.repository.AdminRepository;

import java.sql.SQLException;

public class AuthService {
    private AuthService(){}
    private final static AuthService instance = new AuthService();
    public static AuthService getInstance() {
        return instance;
    }

    public Admin adminInfo = null;
    boolean isAuthenticated = false;

    public void login(String username, String password) throws SQLException, SecurityException {
        Admin admin = AdminRepository.getInstance().findByUsername(username)
                .orElseThrow(()-> new SecurityException("Error: username and password not matched!"));
        if (!admin.getPassword().equals(password))
            throw new SecurityException("Error: username and password not matched!");

        isAuthenticated = true;
        adminInfo = admin;
    }
    public void logout(){
        adminInfo = null;
        isAuthenticated = false;
    }
    public void register(Admin admin) throws SQLException, UsernameAlreadyTakenException {
        if( AdminRepository.getInstance().findByUsername(admin.getUsername()).isPresent() ){
            throw new UsernameAlreadyTakenException("Error: username already is taken;");
        }
        AdminRepository.getInstance().save(admin);
        adminInfo = admin;
        isAuthenticated = true;
    }
    public void update(Admin admin) throws SQLException, AdminNotFoundException {
        if( AdminRepository.getInstance().findByUsername(admin.getUsername()).isEmpty() ) {
            throw new AdminNotFoundException("Error: admin not found!");
        }
        AdminRepository.getInstance().save(admin);
    }
}
