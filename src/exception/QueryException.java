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
public class QueryException extends Exception {

    /**
     * Creates a new instance of <code>QueryException</code> without detail
     * message.
     */
    public QueryException() {
    }

    /**
     * Constructs an instance of <code>QueryException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public QueryException(String msg) {
        super(msg);
    }
    
    public String getMessage(){
        return "Something went wrong";
    }
}
