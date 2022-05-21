package be.kuleuven.pt_mytodolist.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import be.kuleuven.pt_mytodolist.MainActivity;
import be.kuleuven.pt_mytodolist.R;
import be.kuleuven.pt_mytodolist.UserManager;

public class SignInActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private TextView register;
    private Button signIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        email = findViewById(R.id.email);
        register = findViewById(R.id.register);
        password = findViewById(R.id.password);
        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(view -> {

            if (!email.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {
                showLoading();
                UserManager.getInstance().sigIn(SignInActivity.this, email.getText().toString(), password.getText().toString(), new UserManager.OnLoadInfoListener() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        startActivity(new Intent(SignInActivity.this, MainActivity.class));
                        finish();
                        Log.d("__hi", "onSuccess: " + UserManager.getInstance().userInfo.getName());
                    }

                    @Override
                    public void onFailure() {
                        progressDialog.dismiss();
                        Log.d("__hi", "Error");
                    }
                });
            }
        });
        register.setOnClickListener(view -> {
            Intent intent = new Intent(SignInActivity.this, RegisterActivity.class);
            startActivity(intent);
        });


    }

    private ProgressDialog progressDialog;

    private void showLoading() {
        progressDialog = new ProgressDialog(SignInActivity.this);
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.show();
    }
}