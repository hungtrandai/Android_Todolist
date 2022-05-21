package be.kuleuven.pt_mytodolist;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import be.kuleuven.pt_mytodolist.model.Image;
import be.kuleuven.pt_mytodolist.model.Task;
import be.kuleuven.pt_mytodolist.model.TaskTmp;

public class APIConnection {


    public static void insertTask(Context context, Task task, TaskListener listener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a21pt309/task_insert/"
                + task.getTaskName() + "/"
                + task.getTaskCategory() + "/"
                + task.getTaskTime() + "/"
                + task.getTaskRating() + "/"
                + task.getImageId() + "/"
                + task.getUserName();
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> {
                    listener.onSuccess();
                }, error -> listener.onError(error.toString())
        );
        requestQueue.add(submitRequest);
    }

    public static void getTaskList(Context context, String name, TaskListListener listener) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a21pt309/task_load/" + name;
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> {
                    ArrayList<Task> tasks = new Gson().fromJson(response, new TypeToken<ArrayList<Task>>() {
                    }.getType());
                    listener.onSuccess(tasks);
                }, error -> listener.onError(error.toString())
        );
        requestQueue.add(submitRequest);
    }

    public static void updateTask(Context context, Task task, TaskListener listener) {
        RequestQueue sRequestQueue = Volley.newRequestQueue(context);
        StringRequest sStringRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a21pt309/task_update/"
                        + task.getTaskName() + "/"
                        + task.getTaskCategory() + "/"
                        + task.getTaskTime() + "/"
                        + task.getTaskRating() + "/"
                        + task.getImageId() + "/"
                        + task.getTaskId(),
                response -> {
                    listener.onSuccess();
                },
                error -> {
                    listener.onError(error.toString());
                });
        sRequestQueue.add(sStringRequest);

    }

    public static void deleteTask(Context context, Task task, TaskListener listener) {
        RequestQueue sRequestQueue = Volley.newRequestQueue(context);
        StringRequest sStringRequest = new StringRequest(Request.Method.GET,
                "https://studev.groept.be/api/a21pt309/task_delete/" + task.getTaskId(),
                response -> listener.onSuccess(),
                error -> listener.onError(error.getMessage()));
        sRequestQueue.add(sStringRequest);
    }


    public static void getListImage(Context context, GetListImageListener listener) {
        RequestQueue sRequestQueue = Volley.newRequestQueue(context);
        String requestURL = "https://studev.groept.be/api/a21pt309/image_getall";
        StringRequest submitRequest = new StringRequest(Request.Method.GET, requestURL,
                response -> {
                    ArrayList<Image> images = new Gson().fromJson(response, new TypeToken<ArrayList<Image>>() {
                    }.getType());
                    listener.onSuccess(images);
                }, error -> listener.onError(error.toString())
        );

        sRequestQueue.add(submitRequest);
    }


}
