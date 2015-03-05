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
import ir.highteam.shahrdari.qom.bundle.BundleMessage;

/**
 * Created by mohammad on 5/21/2016.
 */
public class AdapterMessageRecycler extends RecyclerView.Adapter<AdapterMessageRecycler.MessageViewHolder> {

    private List<BundleMessage> messageList;
    private Context contextMain;
    Typeface tfSans;

    public AdapterMessageRecycler(List<BundleMessage> messageList, Context context) {
        this.messageList = messageList;
        this.contextMain = context;
        tfSans = Typeface.createFromAsset(contextMain.getAssets(), "IRAN Sans.ttf");
    }

    @Override
    public void onBindViewHolder(MessageViewHolder appViewHolder, final int position) {

        BundleMessage messageInfo = messageList.get(position);
        appViewHolder.txtLotteryCode.setText("کد قرعه کشی : "+messageInfo.lotteryCode);
        appViewHolder.txtPursueCode.setText("کد پیگیری : "+messageInfo.pursueCode);
        appViewHolder.txtDate.setText(messageInfo.date);
        appViewHolder.txtLotteryCode.setTypeface(tfSans);
        appViewHolder.txtPursueCode.setTypeface(tfSans);
        appViewHolder.txtDate.setTypeface(tfSans);

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_message_item, viewGroup, false);
        MessageViewHolder message = new MessageViewHolder(itemView);
        return message;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {

        protected TextView txtLotteryCode;
        protected TextView txtPursueCode;
        protected TextView txtDate;

        public MessageViewHolder(View v) {
            super(v);

            contextMain = v.getContext();
            txtLotteryCode =  (TextView) v.findViewById(R.id.txtLotteryCode);
            txtPursueCode = (TextView)  v.findViewById(R.id.txtPursueCode);
            txtDate = (TextView) v.findViewById(R.id.txtDate);

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

        }

    }

}
