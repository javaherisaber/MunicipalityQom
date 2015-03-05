package ir.highteam.shahrdari.qom.adapter;
/**
 * Created by mohammad on 5/3/2016.
 */
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.List;

import com.squareup.picasso.Picasso;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.activity.ActivityNews;
import ir.highteam.shahrdari.qom.bundle.BundleNews;

public class AdapterNewsRecycler extends RecyclerView.Adapter<AdapterNewsRecycler.ProductViewHolder> {

    private List<BundleNews> newsList;
    private Context contextMain;
    Typeface tfSans;

    public AdapterNewsRecycler(List<BundleNews> newsList, Context context) {
        this.newsList = newsList;
        this.contextMain = context;
        tfSans = Typeface.createFromAsset(contextMain.getAssets(), "IRAN Sans.ttf");
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }



    @Override
    public void onBindViewHolder(ProductViewHolder appViewHolder, final int position) {


        BundleNews newsInfo = newsList.get(position);
        appViewHolder.newsTitle.setText(newsInfo.title);
        appViewHolder.newsDesc.setText(newsInfo.shortDes);
        appViewHolder.newsId = newsInfo.newsId;
        appViewHolder.newsTitle.setTypeface(tfSans);
        appViewHolder.newsDesc.setTypeface(tfSans);
        appViewHolder.txtDate.setTypeface(tfSans);
        appViewHolder.txtTime.setTypeface(tfSans);

        Picasso
                .with(contextMain)
                .load(newsInfo.picture)
                .placeholder(R.mipmap.ic_launcher)
                .into(appViewHolder.newsImage);

    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_news_item, viewGroup, false);
        ProductViewHolder news = new ProductViewHolder(itemView);
        return news;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        protected TextView newsTitle;
        protected TextView newsDesc;
        protected ImageView newsImage;
        protected TextView txtTime;
        protected TextView txtDate;
        protected int newsId;

        public ProductViewHolder(View v) {
            super(v);

            contextMain = v.getContext();
            newsTitle =  (TextView) v.findViewById(R.id.txtTitle);
            newsDesc = (TextView)  v.findViewById(R.id.txtDesc);
            newsImage = (ImageView) v.findViewById(R.id.imgNews);
            txtTime  = (TextView) v.findViewById(R.id.txtTime);
            txtDate  = (TextView) v.findViewById(R.id.txtDate);

            v.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(contextMain, ActivityNews.class);
                    intent.putExtra("newsId", newsId);
                    contextMain.startActivity(intent);
                }
            });


        }

    }

}