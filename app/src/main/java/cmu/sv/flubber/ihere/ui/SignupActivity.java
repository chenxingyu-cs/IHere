package cmu.sv.flubber.ihere.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import cmu.sv.flubber.ihere.R;
import cmu.sv.flubber.ihere.dbLayout.DBLocalConnector;
import cmu.sv.flubber.ihere.entities.User;
import cmu.sv.flubber.ihere.ws.remote.RemoteUser;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";


    private EditText _nameText;
    private EditText _emailText;
    private EditText _passwordText;
    private Button _signupButton;
    private TextView _loginLink;
    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        _nameText = (EditText)findViewById(R.id.input_name);
        _emailText = (EditText)findViewById(R.id.input_email);
        _passwordText = (EditText)findViewById(R.id.input_password);
        _signupButton = (Button)findViewById(R.id.btn_signup);
        _loginLink = (TextView)findViewById(R.id.link_login);

        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }



    private class SignupTask extends AsyncTask<String, Integer, User> {
        protected User doInBackground(String... strings) {
            User user = RemoteUser.signupUser(strings[0], strings[1], strings[2]);
            updateUserInLocalDB(user);
            return user;
        }

        protected void onPostExecute(User user) {
            if(user == null || user.getUserName() == null)
                onSignupFailed();
            else
                onSignupSuccess(user);
            progressDialog.dismiss();
        }
    }

    public void updateUserInLocalDB(User user){
        DBLocalConnector dbLocalConnector = new DBLocalConnector(this);
        dbLocalConnector.setUser(user.getUserId(),user.getUserName(),user.getEmail());
    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        progressDialog = new ProgressDialog(SignupActivity.this);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        new SignupTask().execute(name, email,password);

    }


    public void onSignupSuccess(User user) {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("UserName", user.getUserName());
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("userid", user.getUserId());
        editor.putString("username", user.getUserName());
        editor.commit();
        startActivity(intent);
//        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Signup failed please try again", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }
}
