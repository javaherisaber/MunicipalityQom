package ir.highteam.shahrdari.qom.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.bundle.BundleScheduale;

/**
 * Created by mohammad on 5/3/2016.
 */
public class AdapterSchedualeRecycler extends RecyclerView.Adapter<AdapterSchedualeRecycler.ProductViewHolder>{


    private List<BundleScheduale> schedualeList;
    private Context contextMain;
    Typeface tfSans;

    public AdapterSchedualeRecycler(List<BundleScheduale> schedualeList, Context context) {
        this.schedualeList = schedualeList;
        this.contextMain = context;
        tfSans = Typeface.createFromAsset(contextMain.getAssets(), "IRAN Sans.ttf");
    }

    @Override
    public int getItemCount() {
        return schedualeList.size();
    }



    @Override
    public void onBindViewHolder(ProductViewHolder appViewHolder, final int position) {


        BundleScheduale schedualeInfo = schedualeList.get(position);
        appViewHolder.schedualeDay.setText(schedualeInfo.day);
        appViewHolder.schedualeAddress.setText(schedualeInfo.address);
        appViewHolder.schedualeDay.setTypeface(tfSans);
        appViewHolder.schedualeAddress.setTypeface(tfSans);
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_scheduale_item, viewGroup, false);
        ProductViewHolder scheduale = new ProductViewHolder(itemView);
        return scheduale;
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {

        protected TextView schedualeDay;
        protected TextView schedualeAddress;

        public ProductViewHolder(View v) {
            super(v);

            contextMain = v.getContext();
            schedualeDay =  (TextView) v.findViewById(R.id.txtDay);
            schedualeAddress = (TextView)  v.findViewById(R.id.txtAddress);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(contextMain, ActivityNews.class);
//                    contextMain.startActivity(intent);
                }
            });


        }

    }

}
