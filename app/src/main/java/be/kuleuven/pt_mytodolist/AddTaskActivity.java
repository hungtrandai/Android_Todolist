package be.kuleuven.pt_mytodolist;

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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import be.kuleuven.pt_mytodolist.model.Image;
import be.kuleuven.pt_mytodolist.model.Task;

public class AddTaskActivity extends AppCompatActivity {
    private EditText taskName, taskCategory;
    private TextView taskTime, taskDate;
    private Button btnCreateAt, buttonSave;
    private RatingBar taskRating;
    private ImageButton back;
    private ProgressDialog progressDialog;
    private String selectTime;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        findMyViewById();
        onClick();
        getListImage();
        Calendar calendar = Calendar.getInstance();
        selectTime = String.valueOf(calendar.getTimeInMillis());
        taskTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(calendar.getTimeInMillis())));
        taskDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(new Date(calendar.getTimeInMillis())));
        taskTime.setOnClickListener(view -> {
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(AddTaskActivity.this, (timePicker, selectedHour, selectedMinute) -> {
                calendar.set(Calendar.HOUR, selectedHour);
                calendar.set(Calendar.MINUTE, selectedMinute);
                selectTime = String.valueOf(calendar.getTimeInMillis());
                taskTime.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(new Date(calendar.getTimeInMillis())));
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
            mTimePicker.show();
        });
        taskDate.setOnClickListener(view -> {
            DatePickerDialog dialog = new DatePickerDialog(AddTaskActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(year, monthOfYear, dayOfMonth);
                    selectTime = String.valueOf(calendar.getTimeInMillis());
                    taskDate.setText(DateFormat.getDateInstance(DateFormat.LONG).format(new Date(calendar.getTimeInMillis())));
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            dialog.show();
        });


    }


    private void showLoading() {
        progressDialog = new ProgressDialog(AddTaskActivity.this);
        progressDialog.setMessage("Loading, please wait...");
        progressDialog.show();
    }

    private AppCompatSpinner mSpinner;

    private void spinner() {
        ArrayAdapter<CharSequence> sequenceArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        sequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(sequenceArrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String base64 = listImage.get(i).getImageContent();
                byte[] imageBytes = Base64.decode(base64, Base64.DEFAULT);
                Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                Glide.with(AddTaskActivity.this)
                        .load(bitmap2)
                        .centerCrop()
                        .into(image);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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
                list = new String[lt.size()];
                for (int i = 0; i < lt.size(); i++) {
                    list[i] = "" + lt.get(i).getImageId();
                }
                progressDialog.dismiss();
                spinner();
            }

            @Override
            public void onError(String errorMessage) {
                progressDialog.dismiss();
            }
        });
    }

    private void onClick() {
        buttonSave.setOnClickListener(view -> {
            if (!taskName.getText().toString().isEmpty() && !taskTime.getText().toString().isEmpty() && !taskCategory.getText().toString().isEmpty()) {
                progressDialog = new ProgressDialog(AddTaskActivity.this);
                progressDialog.setMessage("Uploading, please wait...");
                progressDialog.show();
                Task task = new Task();
                task.setTaskTime(selectTime);
                task.setTaskName(taskName.getText().toString());
                task.setTaskCategory(taskCategory.getText().toString());
                task.setTaskRating(taskRating.getRating());
                task.setImageContent(listImage.get(mSpinner.getSelectedItemPosition()).getImageContent());
                task.setImageName(listImage.get(mSpinner.getSelectedItemPosition()).getImageName());
                task.setImageId(listImage.get(mSpinner.getSelectedItemPosition()).getImageId());
                task.setUserName(UserManager.getInstance().userInfo.getName());

                APIConnection.insertTask(AddTaskActivity.this, task, new TaskListener() {
                    @Override
                    public void onSuccess() {
                        progressDialog.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("insert_task", task);
                        setResult(RESULT_OK, intent);
                        finish();
                    }

                    @Override
                    public void onError(String errorMessage) {
                        progressDialog.dismiss();
                        Toast.makeText(AddTaskActivity.this, "There is an error out, please try again after", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });

            } else {
                Toast.makeText(AddTaskActivity.this, "There is an empty space", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void findMyViewById() {
        taskName = findViewById(R.id.taskName);
        taskCategory = findViewById(R.id.idCat);
        taskTime = findViewById(R.id.idTime);
        taskRating = findViewById(R.id.taskRating);
        buttonSave = findViewById(R.id.buttonSave);
        back = findViewById(R.id.back);
        mSpinner = findViewById(R.id.mSpinner);
        image = findViewById(R.id.image);
        taskDate = findViewById(R.id.idDate);

    }

}