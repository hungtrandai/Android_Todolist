package be.kuleuven.pt_mytodolist;

public interface TaskListener {
    void onSuccess();

    void onError(String errorMessage);
}
