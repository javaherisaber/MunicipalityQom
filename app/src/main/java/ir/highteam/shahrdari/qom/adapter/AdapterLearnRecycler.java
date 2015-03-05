package ir.highteam.shahrdari.qom.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.activity.ActivityLearn;
import ir.highteam.shahrdari.qom.bundle.BundleLearn;

/**
 * Created by mohammad on 5/19/2016.
 */
public class AdapterLearnRecycler extends RecyclerView.Adapter<AdapterLearnRecycler.ProductViewHolder> {

    protected List<BundleLearn> learnList;
    private Context contextMain;
    Typeface tfSans;


    public AdapterLearnRecycler(List<BundleLearn> learnList, Context context) {
        this.learnList = learnList;
        this.contextMain = context;
        tfSans = Typeface.createFromAsset(contextMain.getAssets(), "IRAN Sans.ttf");
    }


    @Override
    public void onBindViewHolder(ProductViewHolder appViewHolder, final int position) {


        BundleLearn learnInfo = learnList.get(position);
        appViewHolder.learnTitle.setText(learnInfo.title);
        appViewHolder.learnCount.setText(learnInfo.count);
        appViewHolder.lessonId = learnInfo.lessonId;
        appViewHolder.learnTitle.setTypeface(tfSans);
        appViewHolder.learnCount.setTypeface(tfSans);
        int id = contextMain.getResources().getIdentifier(learnInfo.image, "drawable", contextMain.getPackageName());
        appViewHolder.learnImage.setImageResource(id);

    }

    @Override
    public int getItemCount() {
        return learnList.size();
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_learn_item, viewGroup, false);
        ProductViewHolder learn = new ProductViewHolder(itemView);
        return learn;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        protected TextView learnTitle;
        protected TextView learnCount;
//        protected CardView cardView;
        protected ImageView learnImage;
        protected int lessonId;

        public ProductViewHolder(View v) {
            super(v);

            contextMain = v.getContext();
            learnTitle =  (TextView) v.findViewById(R.id.txtTitle);
            learnCount = (TextView)  v.findViewById(R.id.txtCounter);
//            cardView = (CardView) v.findViewById(R.id.learnCard);
            learnImage = (ImageView) v.findViewById(R.id.imgLearnImage);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(contextMain, ActivityLearn.class);
                    intent.putExtra("lessonId", lessonId);
                    intent.putExtra("lessonPosition", getLayoutPosition());
                    contextMain.startActivity(intent);
                }
            });

//            cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(contextMain, ActivityLearn.class);
//                    intent.putExtra("lessonId", lessonId);
//                    intent.putExtra("lessonPosition", getLayoutPosition());
//                    contextMain.startActivity(intent);
//                }
//            });


        }

    }
}
