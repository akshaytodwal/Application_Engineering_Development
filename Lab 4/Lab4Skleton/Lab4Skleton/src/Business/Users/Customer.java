/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Business.Users;

import Business.Abstract.User;
import java.util.Date;

/**
 *
 * @author AEDSpring2019
 */
public class Customer extends User {
    
    
    public Customer(String password, String userName) {
        super(password, userName, "CUSTOMER");
        this.date = new Date();
    }
    
    private Date date;

    public Date getDate(){
          return date;
    }
    
   
    
    @Override
    public String toString() {
        return getUserName(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    @Override
    public boolean verify(String password) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
         if(password.equals(getPassword()))
            return true;
        return false;
    }

    
}
