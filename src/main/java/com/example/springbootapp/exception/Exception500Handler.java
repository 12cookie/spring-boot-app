package com.example.springbootapp.exception;

public class Exception500Handler extends RuntimeException {

  public Exception500Handler(String message) {
    super(message);
  }

  public Exception500Handler(String message, Throwable cause) {
    super(message, cause);
  }
}