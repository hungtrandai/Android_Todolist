package be.kuleuven.pt_mytodolist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TaskTmp{
    private int taskId;
    private String taskName;
    private String userName;
    private String taskTime;
    private float taskRating;
    private String taskIcon;
    private String taskCategory;
    private int imageId;

    public TaskTmp(int taskId, String taskName, String userName, String taskTime, float taskRating, String taskIcon, String taskCategory, int imageId) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.userName = userName;
        this.taskTime = taskTime;
        this.taskRating = taskRating;
        this.taskIcon = taskIcon;
        this.taskCategory = taskCategory;
        this.imageId = imageId;
    }

    public TaskTmp() {
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }


    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public float getTaskRating() {
        return taskRating;
    }

    public void setTaskRating(float taskRating) {
        this.taskRating = taskRating;
    }

    public String getTaskIcon() {
        return taskIcon;
    }

    public void setTaskIcon(String taskIcon) {
        this.taskIcon = taskIcon;
    }

    public String getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(String taskCategory) {
        this.taskCategory = taskCategory;
    }



}
