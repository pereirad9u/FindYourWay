package control;

import org.mindrot.jbcrypt.BCrypt;
/**
 * Created by debian on 09/02/17.
 */
public class PasswordManagement {
    public static String digestPassword(String plainTextPassword) {
        try {
            String hashed = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
            //System.out.println(">>>> hashed : " + hashed);
            return hashed;
        } catch (Exception e) {
            throw new RuntimeException("Pb hashing mot de passe", e);
        }
    }
}
