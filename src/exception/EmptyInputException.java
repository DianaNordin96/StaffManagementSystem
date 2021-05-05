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
public class EmptyInputException extends Exception {

    /**
     * Creates a new instance of <code>EmptyInputException</code> without detail
     * message.
     */
    public EmptyInputException() {
    }

    /**
     * Constructs an instance of <code>EmptyInputException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public EmptyInputException(String msg) {
        super(msg);
    }
    
    public String getMessage(){
        return "Please do not leave any boxes empty!";
    }
}
