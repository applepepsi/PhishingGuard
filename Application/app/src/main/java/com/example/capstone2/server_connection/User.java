package com.example.capstone2.server_connection;

import android.content.Intent;
import android.util.Log;

import com.example.capstone2.Link_Insert;
import com.example.capstone2.Login;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class User
{
    private String username;
    private String password;

    private String realname;

    public User(String username,String password,String realname)
    {
        this.username=username;
        this.password=password;
        this.realname=realname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() { return realname; }

    public void setRealname(String realname) { this.realname = realname; }

}

