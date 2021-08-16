package com.javatechie.trx.exceptions;

public class InsufficientAmountException extends RuntimeException{

	private static final long serialVersionUID = 1L;

public InsufficientAmountException(String msg) {
	  super(msg);
  }
}
