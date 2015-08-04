package app.meantneat.com.meetneat.Controller.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.meantneat.com.meetneat.Controller.MainAndSettings.MainTabActivity;
import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;


public class LoginActivity extends ActionBarActivity {
    private Button loginButton;
    private EditText emailTextField,passwordTextField;
    private ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }
    private void initView()
    {
        emailTextField = (EditText)findViewById(R.id.login_activity_email_text_field);
        passwordTextField = (EditText)findViewById(R.id.login_activity_password_text_field);

        loginButton = (Button)findViewById(R.id.login_activity_login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar=new ProgressDialog(LoginActivity.this);
                progressBar.setMessage("Logging in...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.setIndeterminate(false);
                progressBar.show();
                login();
            }
        });

    }
    public interface LoginCallback
    {
        public void loggedIn();
        public void failed(String s);
    }
    private void login() {
        String email = emailTextField.getText().toString();
        String password = passwordTextField.getText().toString();
        MyModel.getInstance().getModel().LoginToMeetNeat(email,password,new LoginCallback() {
            @Override
            public void loggedIn() {
                progressBar.hide();
                Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
                startActivity(intent);
            }

            @Override
            public void failed(String s) {
                progressBar.hide();
                Toast.makeText(LoginActivity.this,s,Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
