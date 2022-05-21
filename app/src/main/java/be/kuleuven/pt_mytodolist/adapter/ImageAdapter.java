package be.kuleuven.pt_mytodolist.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import be.kuleuven.pt_mytodolist.R;
import be.kuleuven.pt_mytodolist.model.Image;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.VH> {
    private ArrayList<Image> list = new ArrayList<>();

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        String stringBase64 = list.get(holder.getAdapterPosition()).getImageContent();
        if (stringBase64 != null) {
            byte[] imageBytes = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Glide.with(holder.itemView.getContext())
                    .load(bitmap2)
                    .centerCrop()
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class VH extends RecyclerView.ViewHolder {
        private ImageView image;

        public VH(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
