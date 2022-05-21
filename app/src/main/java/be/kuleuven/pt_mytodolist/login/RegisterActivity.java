package be.kuleuven.pt_mytodolist.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import be.kuleuven.pt_mytodolist.MainActivity;
import be.kuleuven.pt_mytodolist.R;
import be.kuleuven.pt_mytodolist.UserManager;
import be.kuleuven.pt_mytodolist.model.User;

public class RegisterActivity extends AppCompatActivity {
    private RequestQueue addUser;
    private static final String ADD_URL = "https://studev.groept.be/api/a21pt309/add_user/";
    private static final String CHECK_USER = "https://studev.groept.be/api/a21pt309/check_user/";
    private TextView firstName;
    private TextView lastName;
    private TextView email;
    private TextView password;
    private TextView signIn;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        register = findViewById(R.id.register);
        signIn = findViewById(R.id.signIn);
        addUser = Volley.newRequestQueue(this);

        register.setOnClickListener(view -> {
            showLoading();
            String check_user_url = CHECK_USER + email.getText();
            String new_user_url = ADD_URL + firstName.getText() + "/" + lastName.getText() + "/" + email.getText() + "/" + password.getText();
            StringRequest submitRequest = new StringRequest(Request.Method.GET, new_user_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    UserManager.getInstance().sigIn(RegisterActivity.this, email.getText().toString(), password.getText().toString(), new UserManager.OnLoadInfoListener() {
                        @Override
                        public void onSuccess() {
                            progressDialog.dismiss();
                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                            finish();
                        }

                        @Override
                        public void onFailure() {
                            progressDialog.dismiss();
                        }
                    });
                }
            }, error -> Toast.makeText(RegisterActivity.this, "Unable to communicate with the server", Toast.LENGTH_SHORT).show());

            JsonArrayRequest check_user = new JsonArrayRequest(Request.Method.GET, check_user_url, null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    String info = "";
                    if (response.length() == 0) {
                        addUser.add(submitRequest);
                    } else {
                        Toast.makeText(RegisterActivity.this, "Email Already Used", Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "Unable to communicate with the server", Toast.LENGTH_LONG).show();
                }
            });
            addUser.add(check_user);
        });


        signIn.setOnClickListener(view -> {
            finish();
        });
    }

    private ProgressDialog progressDialog;

    private void showLoading() {
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.show();
    }

}