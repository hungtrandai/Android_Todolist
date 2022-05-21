package be.kuleuven.pt_mytodolist;

import java.util.ArrayList;

import be.kuleuven.pt_mytodolist.model.Task;
import be.kuleuven.pt_mytodolist.model.TaskTmp;

public interface TaskListListener {
    void onSuccess(ArrayList<Task> tasks);

    void onError(String errorMessage);
}
