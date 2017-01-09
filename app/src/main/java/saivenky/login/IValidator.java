package saivenky.login;

/**
 * Created by saivenky on 1/7/17.
 */

public interface IValidator {
    UserInfo hasUser(String username);
    boolean validLogin(LoginInfo loginInfo);
}
