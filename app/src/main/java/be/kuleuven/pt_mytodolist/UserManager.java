package be.kuleuven.pt_mytodolist;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;

import be.kuleuven.pt_mytodolist.model.Task;
import be.kuleuven.pt_mytodolist.model.User;

public class UserManager {
    private static UserManager instance;
    public User userInfo;

    public static UserManager getInstance() {
        if (instance == null) {
            instance = new UserManager();
        }
        return instance;
    }

    private UserManager() {

    }

    public interface OnLoadInfoListener {
        void onSuccess();

        void onFailure();

    }

    private static final String SIGN_URL = "https://studev.groept.be/api/a21pt309/user_login/";

    public void sigIn(Context context, String email, String password, OnLoadInfoListener listener) {
        RequestQueue signIn = Volley.newRequestQueue(context);
        String signIn_user_url = SIGN_URL + email + "/" + password;
        JsonArrayRequest signIn_user = new JsonArrayRequest(Request.Method.GET, signIn_user_url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() == 0) {
                    listener.onFailure();
                } else {
                    getUserInfo(context, email, listener);
                }
            }
        }, error -> {
            listener.onFailure();
        });
        signIn.add(signIn_user);
    }

    private void getUserInfo(Context context, String email, OnLoadInfoListener listener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a21pt309/user_get/" + email;
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> {
                    ArrayList<User> users = new Gson().fromJson(response, new TypeToken<ArrayList<User>>() {
                    }.getType());
                    userInfo = users.get(0);
                    listener.onSuccess();
                }, error -> listener.onFailure());
        requestQueue.add(submitRequest);
    }

}
