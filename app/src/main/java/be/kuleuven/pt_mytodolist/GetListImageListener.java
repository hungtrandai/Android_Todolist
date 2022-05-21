package be.kuleuven.pt_mytodolist;

import android.graphics.Bitmap;

import java.util.ArrayList;

import be.kuleuven.pt_mytodolist.model.Image;

public interface GetListImageListener {
    void onSuccess(ArrayList<Image> list);

    void onError(String errorMessage);
}
