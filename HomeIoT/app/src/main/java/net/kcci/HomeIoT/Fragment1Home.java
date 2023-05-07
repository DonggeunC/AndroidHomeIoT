package net.kcci.HomeIoT;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment1Home extends Fragment {
    ImageButton imageButtonLamp;
    ImageButton imageButtonPlug;
    boolean imageButtonLampCheck;
    boolean imageButtonPlugCheck;
    MainActivity mainActivity;
    SimpleDateFormat dataFormat = new SimpleDateFormat("yy.MM.dd HH:mm:ss");
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment1home, container, false);
        mainActivity = (MainActivity) getActivity();
        imageButtonLamp = view.findViewById(R.id.imageButtonLamp);
        imageButtonPlug = view.findViewById(R.id.imageButtonPlug);
        imageButtonLamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null){
                    if(imageButtonLampCheck) {
                        mainActivity.clientThread.sendData("[CDG_ARD]LAMPOFF\n");
                    }
                    else
                        mainActivity.clientThread.sendData("[CDG_ARD]LAMPON\n");
                }
            }
        });
        imageButtonPlug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket != null){
                    if(imageButtonPlugCheck) {
                        mainActivity.clientThread.sendData("[CDG_ARD]PLUGOFF\n");
                    }
                    else
                        mainActivity.clientThread.sendData("[CDG_ARD]PLUGON\n");
                }
            }
        });
        return view;
    }
    void recvDataProcess(String strRecvData) {
        Date date = new Date();
        String strDate = dataFormat.format(date);
        String[] splitLists = strRecvData.split("\\[|]|@|\\r");
        for (int i = 0; i < splitLists.length; i++)
            Log.d("recvDataProcess", "i: " + i + ", value: " + splitLists[i]);
        if(splitLists[2].equals("LAMPON")) {
            imageButtonLamp.setImageResource(R.drawable.lamp_on);
            imageButtonLampCheck = true;
        }
        else if(splitLists[2].equals("LAMPOFF")) {
            imageButtonLamp.setImageResource(R.drawable.lamp_off);
            imageButtonLampCheck = false;
        }
        else if(splitLists[2].equals("PLUGON")){
            imageButtonPlug.setImageResource(R.drawable.plug_on);
            imageButtonPlugCheck = true;
        }
        else if(splitLists[2].equals("PLUGOFF")){
            imageButtonPlug.setImageResource(R.drawable.plug_off);
            imageButtonPlugCheck = false;
        }
        //String strData = "ID: " + splitLists[1] + "명령어: " +splitLists[2]+"\n";
        //textView.append(strDate);
    }
}
