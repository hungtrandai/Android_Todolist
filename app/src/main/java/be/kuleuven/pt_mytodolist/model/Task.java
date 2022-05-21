package be.kuleuven.pt_mytodolist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Task implements Parcelable {
    private int taskId;
    private String taskName;
    private String userName;
    private String taskTime;
    private float taskRating;
    private String taskIcon;
    private String taskCategory;
    private int imageId;
    private String imageName;
    private String imageContent;

    public Task(int taskId, String taskName, String userName, String taskTime, float taskRating, String taskIcon, String taskCategory, int imageId, String imageName, String imageContent) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.userName = userName;
        this.taskTime = taskTime;
        this.taskRating = taskRating;
        this.taskIcon = taskIcon;
        this.taskCategory = taskCategory;
        this.imageId = imageId;
        this.imageName = imageName;
        this.imageContent = imageContent;
    }

    public Task() {
    }


    protected Task(Parcel in) {
        taskId = in.readInt();
        taskName = in.readString();
        userName = in.readString();
        taskTime = in.readString();
        taskRating = in.readFloat();
        taskIcon = in.readString();
        taskCategory = in.readString();
        imageId = in.readInt();
        imageName = in.readString();
        imageContent = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(taskId);
        dest.writeString(taskName);
        dest.writeString(userName);
        dest.writeString(taskTime);
        dest.writeFloat(taskRating);
        dest.writeString(taskIcon);
        dest.writeString(taskCategory);
        dest.writeInt(imageId);
        dest.writeString(imageName);
        dest.writeString(imageContent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Task> CREATOR = new Creator<Task>() {
        @Override
        public Task createFromParcel(Parcel in) {
            return new Task(in);
        }

        @Override
        public Task[] newArray(int size) {
            return new Task[size];
        }
    };

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

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageContent() {
        return imageContent;
    }

    public void setImageContent(String imageContent) {
        this.imageContent = imageContent;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
