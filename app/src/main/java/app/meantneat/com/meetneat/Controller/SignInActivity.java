package app.meantneat.com.meetneat.Controller;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import app.meantneat.com.meetneat.Model.MyModel;
import app.meantneat.com.meetneat.R;

public class SignInActivity extends ActionBarActivity {
    EditText userNameEditText,emailEditText,passwordEditText;
    Button signInButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in_activity_layout);
        initViews();
    }

    private void initViews() {
        signInButton = (Button)findViewById(R.id.sign_in_activity_sign_in_button_id);
        userNameEditText = (EditText)findViewById(R.id.sign_in_activity_user_name_text_field_id);
        emailEditText = (EditText)findViewById(R.id.sign_in_activity_email_text_field_id);
        passwordEditText = (EditText)findViewById(R.id.sign_in_activity_password_field_id);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            signIn();
            }
        });
    }
    public interface SignUpCallback
    {
        public void onResult(String s);
    }
    private void signIn() {
        String name,email,password;
        name = userNameEditText.getText().toString();
        email = emailEditText.getText().toString();
        password = passwordEditText.getText().toString();
        MyModel.getInstance().getModel().signUpToMeetNeat(name, email, password, new SignUpCallback() {
            @Override
            public void onResult(String s) {
                if(s.equals("Yes"))
                {
                    Intent intent = new Intent(SignInActivity.this,MainTabActivity.class);
                    startActivity(intent);
                }
                else
                Toast.makeText(SignInActivity.this,s,Toast.LENGTH_SHORT);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
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
