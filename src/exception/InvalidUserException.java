/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package exception;

/**
 *
 * @author User
 */
public class InvalidUserException extends Exception {

    /**
     * Creates a new instance of <code>InvalidUserException</code> without
     * detail message.
     */
    public InvalidUserException() {
    }

    /**
     * Constructs an instance of <code>InvalidUserException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public InvalidUserException(String msg) {
        super(msg);
    }
    
     public String getMessage(){
        return "Invalid user. Incorrect ID or Password";
    }
}
