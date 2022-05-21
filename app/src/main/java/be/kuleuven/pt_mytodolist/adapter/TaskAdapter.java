package be.kuleuven.pt_mytodolist.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;

import be.kuleuven.pt_mytodolist.R;
import be.kuleuven.pt_mytodolist.model.Task;
import be.kuleuven.pt_mytodolist.model.TaskTmp;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.VH> {
    ArrayList<Task> list = new ArrayList<>();
    OnClickItem onClickItem;

    public void update(Task task, int p) {
        this.list.set(p, task);
        notifyItemChanged(p);
    }

    public void insert(Task task) {
        int index = this.list.size();
        this.list.add(task);
        notifyItemInserted(index);
    }

    public void delete(int position) {
        this.list.remove(position);
        notifyItemRemoved(position);
    }

    public interface OnClickItem {
        void onClick(Task task, int p);
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new VH(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Task task = list.get(holder.getAdapterPosition());
        holder.tvCategory.setText(task.getTaskCategory());
        holder.tvTaskName.setText(task.getTaskName());
        holder.ratting.setRating(task.getTaskRating());
        holder.tvTime.setText(DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT).format(new Date(Long.parseLong(task.getTaskTime()))));
        String stringBase64 = task.getImageContent();
        if (stringBase64 != null) {
            byte[] imageBytes = Base64.decode(stringBase64, Base64.DEFAULT);
            Bitmap bitmap2 = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            Glide.with(holder.itemView.getContext())
                    .load(bitmap2)
                    .centerCrop()
                    .into(holder.imgPicture);
        }

        holder.itemView.setOnClickListener(view -> onClickItem.onClick(task, holder.getAdapterPosition()));
    }

    @Override
    public int getItemCount() {
        if (this.list != null) {
            return this.list.size();
        }
        return 0;
    }

    public void setListItem(ArrayList<Task> taskItem) {
        this.list = taskItem;
        notifyDataSetChanged();
    }

    static class VH extends RecyclerView.ViewHolder {
        private TextView tvTaskName;
        private TextView tvCategory;
        private TextView tvTime;
        private RatingBar ratting;
        private ImageView imgPicture;

        public VH(@NonNull View itemView) {
            super(itemView);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvTaskName = itemView.findViewById(R.id.tvTaskName);
            tvTime = itemView.findViewById(R.id.tvTime);
            ratting = itemView.findViewById(R.id.ratting);
            imgPicture = itemView.findViewById(R.id.imgPicture);
        }
    }
}
