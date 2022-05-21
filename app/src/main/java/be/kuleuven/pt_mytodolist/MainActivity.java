package be.kuleuven.pt_mytodolist;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import be.kuleuven.pt_mytodolist.adapter.TaskAdapter;
import be.kuleuven.pt_mytodolist.login.SignInActivity;
import be.kuleuven.pt_mytodolist.model.Task;
import be.kuleuven.pt_mytodolist.model.TaskTmp;

public class MainActivity extends AppCompatActivity {

    private Context context; //using thay cho MainActivity.this
    private ImageButton add;
    private ImageButton signOut;
    private TaskAdapter adapter;
    private RecyclerView recyclerview;

    private ImageButton sort;

    private ArrayList<Task> listTaskTmp;
//test git

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = MainActivity.this;
        setContentView(R.layout.activity_main);

        showLoading();
        findMyViewById();

        listTaskTmp = new ArrayList<>();
        add.setOnClickListener(view -> {
            Intent myIntent = new Intent(context, AddTaskActivity.class);
            mAddTaskStartForResult.launch(myIntent);
        });
        signOut.setOnClickListener(view -> {
            startActivity(new Intent(MainActivity.this, SignInActivity.class));
            finish();
        });
        sort.setOnClickListener(view -> {
            if (spinner.getSelectedItemPosition() == 0) {
                nameAz();
                spinner.setSelection(1);
            } else if (spinner.getSelectedItemPosition() == 1) {
                nameZa();
                spinner.setSelection(2);
            } else if (spinner.getSelectedItemPosition() == 2) {
                nameZa();
                spinner.setSelection(3);
            } else if (spinner.getSelectedItemPosition() == 3) {
                nameZa();
                spinner.setSelection(4);
            } else if (spinner.getSelectedItemPosition() == 4) {
                sortId();
                spinner.setSelection(0);
            }

        });

        spinner();
        initList();
        getTodoLists();


    }

    private ProgressDialog progressDialog;

    private void showLoading() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.show();
    }

    private void initList() {
        adapter = new TaskAdapter();
        recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerview.setAdapter(adapter);
        adapter.setOnClickItem(new TaskAdapter.OnClickItem() {
            @Override
            public void onClick(Task task, int p) {
                Intent myIntent = new Intent(context, TaskInfoActivity.class);
                myIntent.putExtra("task", task);
                myIntent.putExtra("p", p);
                mShowTaskStartForResult.launch(myIntent);
            }
        });
    }

    private ActivityResultLauncher<Intent> mAddTaskStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            Task t = intent.getParcelableExtra("insert_task");
                            adapter.insert(t);
                        } else {
                            Toast.makeText(context, "An error has occurred", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

    private ActivityResultLauncher<Intent> mShowTaskStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            Task task = intent.getParcelableExtra("info");
                            int position = intent.getIntExtra("p", 0);
                            if (intent.getStringExtra("key").equals("delete")) {
                                adapter.delete(position);
                            } else if (intent.getStringExtra("key").equals("update")) {
                                adapter.update(task, position);
                            }

                        } else {
                            Toast.makeText(context, "An error has occurred", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
            });

    private void getTodoLists() {
        APIConnection.getTaskList(this, UserManager.getInstance().userInfo.getName(), new TaskListListener() {
            @Override
            public void onSuccess(ArrayList<Task> tasks) {
                progressDialog.dismiss();
                listTaskTmp = tasks;
                adapter.setListItem(listTaskTmp);
            }

            @Override
            public void onError(String errorMessage) {
                progressDialog.dismiss();
                Toast.makeText(context, "Errol", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void findMyViewById() {
        recyclerview = findViewById(R.id.recyclerview);
        add = findViewById(R.id.add);
        sort = findViewById(R.id.sort);
        spinner = findViewById(R.id.spinner);
        signOut = findViewById(R.id.signOut);
    }


    private AppCompatSpinner spinner;

    private void spinner() {
        ArrayAdapter<String> sequenceArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSort());
        sequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(sequenceArrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    sortId();
                } else if (position == 1) {
                    nameAz();
                } else if (position == 2) {
                    nameZa();
                } else if (position == 3) {
                    ratingAz();
                } else if (position == 4) {
                    ratingZa();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void sortId() {
        Collections.sort(listTaskTmp, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return task1.getTaskId() - (task2.getTaskId());
            }
        });
        adapter.setListItem(listTaskTmp);
    }

    private void ratingAz() {
        Collections.sort(listTaskTmp, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return (int) (task2.getTaskRating() - task1.getTaskRating());
            }
        });
        adapter.setListItem(listTaskTmp);
    }

    private void ratingZa() {
        Collections.sort(listTaskTmp, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return (int) (task1.getTaskRating() - task2.getTaskRating());
            }
        });
        adapter.setListItem(listTaskTmp);
    }

    private void nameZa() {
        Collections.sort(listTaskTmp, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return task2.getTaskName().compareTo(task1.getTaskName());
            }
        });
        adapter.setListItem(listTaskTmp);
    }

    private void nameAz() {
        Collections.sort(listTaskTmp, new Comparator<Task>() {
            @Override
            public int compare(Task task1, Task task2) {
                return task1.getTaskName().compareTo(task2.getTaskName());
            }
        });
        adapter.setListItem(listTaskTmp);
    }

    private String[] listSort() {
        String[] list = new String[5];
        list[0] = "ALL";
        list[1] = "Name Az";
        list[2] = "Name Za";
        list[3] = "Rating Az";
        list[4] = "Rating Za";
        return list;
    }
}