package com.example.capstone2.server_connection;

public interface TokenCallback
{
    void onSuccess(String result);
    void onFailure(String errorMessage);
}
