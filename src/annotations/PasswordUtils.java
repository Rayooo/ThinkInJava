package annotations;

import java.util.List;

/**
 * Created by Ray on 2017/4/4.
 */
public class PasswordUtils {

    @UseCase(id = 47,description = "Password must contain at least one numeric")
    public boolean validatePassword(String password){
        return password.matches("\\w*\\d\\w*");
    }

    @UseCase(id = 48)
    public String encryptPassword(String password){
        return new StringBuilder(password).reverse().toString();
    }

    @UseCase(id = 49,description = "New passwords can't equals previously used ones")
    public boolean checkForNewPassword(List<String> previousPasswords, String password) {
        return !previousPasswords.contains(password);
    }

}
