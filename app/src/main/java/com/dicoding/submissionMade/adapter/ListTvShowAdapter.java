package com.dicoding.submissionMade.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dicoding.submissionMade.R;
import com.dicoding.submissionMade.activity.DetailTvShowActivity;
import com.dicoding.submissionMade.item.TvShow;

import java.util.ArrayList;

public class ListTvShowAdapter extends RecyclerView.Adapter<ListTvShowAdapter.ListViewHolder> {
    private ArrayList<TvShow> listTvShow = new ArrayList<>();

    public void setData(ArrayList<TvShow> items) {
        listTvShow.clear();
        listTvShow.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_row_tv_show, viewGroup, false);
        return new ListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ListViewHolder holder, int position) {
        holder.bind(listTvShow.get(position));
    }

    @Override
    public int getItemCount() {
        return listTvShow.size();
    }


    class ListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgPhoto;
        TextView tvName, tvFrom;
        Button btnToDetailTvShow;

        ListViewHolder(final View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_item_photo2);
            tvName = itemView.findViewById(R.id.tv_item_name2);
            tvFrom = itemView.findViewById(R.id.tv_item_from2);
            btnToDetailTvShow = itemView.findViewById(R.id.btn_tvshow);

            btnToDetailTvShow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    TvShow tvshow = listTvShow.get(position);

                    tvshow.setTitle(tvshow.getTitle());
                    tvshow.setDescription(tvshow.getDescription());
                    tvshow.setPoster(tvshow.getPoster());

                    Intent showTvShowActivityIntent = new Intent(itemView.getContext(), DetailTvShowActivity.class);
                    showTvShowActivityIntent.putExtra(DetailTvShowActivity.EXTRA_TvShow, tvshow);
                    v.getContext().startActivity(showTvShowActivityIntent);
                }
            });
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            TvShow tvshow = listTvShow.get(position);

            tvshow.setTitle(tvshow.getTitle());
            tvshow.setDescription(tvshow.getDescription());
            tvshow.setPoster(tvshow.getPoster());

            Intent showTvShowActivityIntent = new Intent(itemView.getContext(), DetailTvShowActivity.class);
            showTvShowActivityIntent.putExtra(DetailTvShowActivity.EXTRA_TvShow, tvshow);
            view.getContext().startActivity(showTvShowActivityIntent);
        }

        void bind(TvShow item) {
            String url_image = "https://image.tmdb.org/t/p/w185" + item.getPoster();

            tvName.setText(item.getTitle());
            tvFrom.setText(item.getDescription());

            Glide.with(itemView.getContext())
                    .load(url_image)
                    .placeholder(R.color.colorAccent)
                    .dontAnimate()
                    .into(imgPhoto);
        }
    }

}
