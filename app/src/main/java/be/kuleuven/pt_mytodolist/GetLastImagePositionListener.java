package be.kuleuven.pt_mytodolist;

import android.graphics.Bitmap;

public interface GetLastImagePositionListener {
    void onSuccess(int p);

    void onError(String errorMessage);
}
