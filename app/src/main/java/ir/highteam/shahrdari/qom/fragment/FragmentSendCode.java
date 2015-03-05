package ir.highteam.shahrdari.qom.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import ir.highteam.shahrdari.qom.R;
import ir.highteam.shahrdari.qom.activity.ActivityHome;
import ir.highteam.shahrdari.qom.bundle.BundleLottery;
import ir.highteam.shahrdari.qom.database.ShahrdariDB;
import ir.highteam.shahrdari.qom.utile.NetworkFunctions;

/**
 * Created by mohammad on 5/16/2016.
 */
public class FragmentSendCode  extends Fragment {
    View rootView;
    EditText edtLotteryCode;
    TextInputLayout textInputLayout;
    ImageView imgSendCode;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_send_code, container, false);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "IRAN Sans.ttf");
        edtLotteryCode = (EditText) rootView.findViewById(R.id.edtLotteryCode);
        imgSendCode = (ImageView) rootView.findViewById(R.id.fab_add);
        textInputLayout = (TextInputLayout) rootView.findViewById(R.id.txtInput);
        textInputLayout.setTypeface(typeface);
        edtLotteryCode.setTypeface(typeface);
        edtLotteryCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    hideKeyboard(v);
                }
            }
        });

        imgSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtLotteryCode.getText().toString().equals(""))
                {
                    Toast.makeText(getContext(),"ابتدا کد قرعه کشی خود را وارد نمایید",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    NetworkFunctions networkFunctions = new NetworkFunctions(getContext());
                    if(networkFunctions.isOnline())
                    {
                        try {

                            final ProgressDialog progress = ProgressDialog.show(getContext(),"قرعه کشی","در حال بررسی اطلاعات", true);
                            progress.show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {

                                    String code = edtLotteryCode.getText().toString();
                                    try {

                                        MessageDigest md = MessageDigest.getInstance("MD5");
                                        md.update(code.getBytes());

                                        byte byteData[] = md.digest();

                                        //convert the byte to hex format method 1
                                        StringBuilder sb = new StringBuilder();
                                        for (int i = 0; i < byteData.length; i++) {
                                            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
                                        }
                                        String pursueCode = sb.toString();
                                        BundleLottery bundleLottery = new BundleLottery();
                                        bundleLottery.lotteryCode = code;
                                        bundleLottery.pursueCode = pursueCode;
                                        bundleLottery.dateTime = getCurrentDateTime();
                                        ShahrdariDB db = new ShahrdariDB(getContext());
                                        db.open();
                                        db.insertLottery(bundleLottery);
                                        db.close();

                                        Toast.makeText(getContext(),"کد پیگیری شما " + pursueCode + " در صندوق پیام ها ذخیره شد",Toast.LENGTH_LONG).show();
                                        edtLotteryCode.setText("");
                                        ((ActivityHome)getActivity()).txtMailCount.setVisibility(View.VISIBLE);
                                        int count = Integer.parseInt(((ActivityHome)getActivity()).txtMailCount.getText().toString());
                                        ((ActivityHome)getActivity()).txtMailCount.setText(String.valueOf(++count));

                                    }catch (NoSuchAlgorithmException e){
                                        Log.e("TAG",e.getMessage());
                                    }
                                    progress.dismiss();
                                }
                            }, 3000);

                        } catch (Exception e)
                        {
                            Toast.makeText(getContext(),"خطایی رخ داد ، دوباره امتحان کنید",Toast.LENGTH_LONG).show();
                        }
                    }
                    else
                        Toast.makeText(getContext(),"از اتصال به اینترنت اطمینان حاصل نمایید",Toast.LENGTH_LONG).show();
                }

            }
        });
        return rootView;
    }

    public void hideKeyboard(View view) {
        InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getApplicationContext().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private String getCurrentDateTime() {
        DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, HH:mm:ss");
        return df.format(Calendar.getInstance().getTime());
    }
}
