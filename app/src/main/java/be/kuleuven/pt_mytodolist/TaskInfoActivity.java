package be.kuleuven.pt_mytodolist;

import static be.kuleuven.pt_mytodolist.R.id;
import static be.kuleuven.pt_mytodolist.R.layout;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import be.kuleuven.pt_mytodolist.model.Image;
import be.kuleuven.pt_mytodolist.model.Task;

public class TaskInfoActivity extends AppCompatActivity {
    private EditText taskName;
    private ImageView taskIcon;
    private EditText taskCategory;
    private TextView taskTime;
    private TextView taskDate;
    private RatingBar taskRating;
    private Button btnEdit, btnDelete;
    private ImageButton back;
    private Task task;
    private ProgressDialog progressDialog;
    private AppCompatSpinner mSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_task_info);
        findMyViewById();
        loadUI();
        getListImage();
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(task.getTaskTime()));
        taskTime.setOnClickListener(view -> {
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(TaskInfoActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                calendar.set(Calendar.HOUR, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                Date date = new Date(calendar.getTimeInMillis());
                task.setTaskTime(String.valueOf(calendar.getTimeInMillis()));
                taskTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(date));
            }, Calendar.HOUR_OF_DAY, Calendar.MINUTE, false);
            mTimePicker.show();
        });
        taskDate.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(TaskInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    task.setTaskTime(String.valueOf(calendar.getTimeInMillis()));
                    taskDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(new Date(calendar.getTimeInMillis())));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });
    }

    private void showLoading() {
        progressDialog = new ProgressDialog(TaskInfoActivity.this);
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.show();
    }


    private void spinner() {
        ArrayAdapter<CharSequence> sequenceArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        sequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(sequenceArrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String stringBase64 = listImage.get(position).getImageContent();
                if (stringBase64 != null) {
                    byte[] imageBytes = Base64.decode(stringBase64, Base64.DEFAULT);
                    Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    Glide.with(TaskInfoActivity.this)
                            .load(bitmap2)
                            .centerCrop()
                            .into(taskIcon);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    String[] list;
    ArrayList<Image> listImage;


    private void getListImage() {
        showLoading();
        APIConnection.getListImage(this, new GetListImageListener() {
            @Override
            public void onSuccess(ArrayList<Image> lt) {
                listImage = lt;
                int selectPosition = 0;
                list = new String[lt.size()];
                for (int i = 0; i < lt.size(); i++) {
                    list[i] = "" + lt.get(i).getImageId();
                    if (task.getImageId() == lt.get(i).getImageId()) {
                        selectPosition = i;
                    }
                }

                progressDialog.dismiss();
                spinner();
                mSpinner.setSelection(selectPosition);
            }

            @Override
            public void onError(String errorMessage) {
                progressDialog.dismiss();
            }
        });
    }


    private void loadUI() {
        if (getIntent().getParcelableExtra("task") != null) {
            task = getIntent().getParcelableExtra("task");
            taskName.setText(task.getTaskName());
            taskCategory.setText(task.getTaskCategory());
            taskTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(Long.parseLong(task.getTaskTime()))));
            taskDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(new Date(Long.parseLong(task.getTaskTime()))));
            taskRating.setRating(task.getTaskRating());
            if (task.getImageContent() != null) {
                byte[] imageBytes = Base64.decode(task.getImageContent(), Base64.DEFAULT);
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                Glide.with(TaskInfoActivity.this)
                        .load(bitmap2)
                        .into(taskIcon);
            }
        } else {
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void findMyViewById() {
        taskName = findViewById(id.taskName);
        taskCategory = findViewById(id.taskCategory);
        taskTime = findViewById(id.taskTime);
        taskRating = findViewById(id.taskRating);
        taskIcon = findViewById(id.taskIcon);
        btnEdit = findViewById(id.TaskInfo_Edit);
        btnDelete = findViewById(id.TaskInfo_Delete);
        back = findViewById(id.back);
        mSpinner = findViewById(id.mSpinner);
        taskDate = findViewById(id.taskDate);


        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteTodoList();
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editTodoList(task);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }


    private void onFinish(Task ts, String key) {
        Intent intent = new Intent();
        intent.putExtra("key", key);
        intent.putExtra("info", ts);
        intent.putExtra("p", getIntent().getIntExtra("p", 0));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void editTodoList(Task task) {

        if (!taskName.getText().toString().trim().isEmpty() && !taskCategory.getText().toString().trim().isEmpty() && !taskTime.getText().toString().trim().isEmpty()) {
            showLoading();
            task.setTaskName(taskName.getText().toString());
            task.setTaskCategory(taskCategory.getText().toString());
            task.setTaskTime(task.getTaskTime());
            task.setTaskRating(taskRating.getRating());
            task.setImageContent(listImage.get(mSpinner.getSelectedItemPosition()).getImageContent());
            task.setImageName(listImage.get(mSpinner.getSelectedItemPosition()).getImageName());
            task.setImageId(listImage.get(mSpinner.getSelectedItemPosition()).getImageId());
            APIConnection.updateTask(this, task, new TaskListener() {
                @Override
                public void onSuccess() {
                    progressDialog.dismiss();
                    Toast.makeText(TaskInfoActivity.this, "Update successful", Toast.LENGTH_SHORT).show();
                    onFinish(task, "update");
                }

                @Override
                public void onError(String errorMessage) {
                    progressDialog.dismiss();
                    Toast.makeText(TaskInfoActivity.this, "An error has occurred", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Can't leave any content", Toast.LENGTH_SHORT).show();
        }


    }

    private void deleteTodoList() {
        showLoading();
        APIConnection.deleteTask(this, task, new TaskListener() {
            @Override
            public void onSuccess() {
                progressDialog.dismiss();
                Toast.makeText(TaskInfoActivity.this, "onSuccess", Toast.LENGTH_SHORT).show();
                onFinish(task, "delete");
            }

            @Override
            public void onError(String errorMessage) {
                progressDialog.dismiss();
                Toast.makeText(TaskInfoActivity.this, "errorMessage", Toast.LENGTH_SHORT).show();
            }
        });
    }


}