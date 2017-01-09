package saivenky.login;

import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

import saivenky.login.db.SqlLiteLoginCreator;
import saivenky.login.db.SqlLiteValidator;
import saivenky.login.db.UserLoginDbHelper;
import saivenky.login.utils.PasswordUtils;

public class MainActivity extends AppCompatActivity {
    private IValidator validator;
    private ILoginCreator loginCreator;
    private ISaltCreator saltCreator;

    private TextInputEditText mUsername;
    private TextInputEditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            initialize();
        } catch (Exception e) {
            System.err.printf("Error on initialization!");
            e.printStackTrace();
        }
    }

    private void initialize() throws NoSuchAlgorithmException {
        UserLoginDbHelper.initialize(this);
        PasswordUtils.initialize(Sha256HashCreator.getInstance());
        validator = SqlLiteValidator.getInstance();
        loginCreator = SqlLiteLoginCreator.getInstance();
        saltCreator = new SaltCreator();

        setUiComponents();
    }

    private void setUiComponents() {
        mUsername = (TextInputEditText) findViewById(R.id.username);
        mPassword = (TextInputEditText) findViewById(R.id.password);
        Button mCreateButton = (Button) findViewById(R.id.create_button);
        Button mLoginButton = (Button) findViewById(R.id.login_button);

        mCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isSuccess = createUser();
                if(!isSuccess) {
                    mUsername.setError("Username already exists");
                    mUsername.requestFocus();
                    showToast("User exists");
                }
                else {
                    showToast("Created new user");
                }
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isLoginSuccessful = isLoginSuccessful();
                if(!isLoginSuccessful) {
                    mPassword.setError("Username or password incorrect");
                    mPassword.requestFocus();
                    showToast("Failure");
                }
                else {
                    showToast("Success");
                }
            }
        });
    }

    boolean isLoginFormValid() {
        return isUserFieldValid() && isPasswordFieldValid();
    }

    private boolean isUserFieldValid() {
        return true;
    }

    private boolean isPasswordFieldValid() {
        return true;
    }

    private String getUsernameFromField() {
        return mUsername.getText().toString();
    }

    private String getPasswordFromField() {
        return mPassword.getText().toString();
    }

    private boolean createUser() {
        String username = getUsernameFromField();
        String password = getPasswordFromField();

        byte[] clientSalt = saltCreator.createRandomSalt();
        UserInfo userInfo = new UserInfo(username, clientSalt);
        byte[] clientHashedPassword = PasswordUtils.createClientHashedPassword(password, clientSalt);

        return loginCreator.create(userInfo, clientHashedPassword);
    }

    private boolean isLoginSuccessful() {
        String username = getUsernameFromField();
        String password = getPasswordFromField();

        UserInfo userInfo = validator.hasUser(username);
        if(!userInfo.doesExist) return false;

        LoginInfo loginInfo = new LoginInfo();
        loginInfo.username = username;
        loginInfo.clientHashedPassword = PasswordUtils.createClientHashedPassword(password, userInfo.clientSalt);
        return validator.validLogin(loginInfo);
    }

    private void showToast(String message) {
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        toast.show();
    }
}
