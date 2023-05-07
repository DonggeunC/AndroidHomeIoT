package net.kcci.HomeIoT;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class Fragment2 extends Fragment {
    MainActivity mainActivity;
    TextView textViewLux;
    TextView textViewTemp;
    TextView textViewHumi;
    ImageView imageViewLED;
    ImageView imageViewAir;
    ImageView imageViewBlind;
    Switch switchLED;
    Switch switchBlind;
    Switch switchAir;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment2, container, false);
        mainActivity = (MainActivity) getActivity();
        switchLED = view.findViewById(R.id.switchLED);
        switchBlind = view.findViewById(R.id.switchBlind);
        switchAir = view.findViewById(R.id.switchAir);
        textViewLux = view.findViewById(R.id.textViewLux);
        textViewTemp = view.findViewById(R.id.textViewTemp);
        textViewHumi = view.findViewById(R.id.textViewHumi);
        imageViewAir = view.findViewById(R.id.imageViewAir);
        imageViewBlind = view.findViewById(R.id.imageViewBlind);
        imageViewLED = view.findViewById(R.id.imageViewLED);
        Button buttonCondition = view.findViewById(R.id.buttonCondition);
        buttonCondition.setOnClickListener(view1 ->
                mainActivity.clientThread.sendData("[CDG_ARD]GETSENSOR")
        );
        switchLED.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ClientThread.socket != null) {
                    if (switchLED.isChecked()) {
                        mainActivity.clientThread.sendData("[CDG_ARD]LEDON");
                        switchLED.setChecked(false);
                    } else if (!switchLED.isChecked()) {
                        mainActivity.clientThread.sendData("[CDG_ARD]LEDOFF");
                        switchLED.setChecked(true);
                    }
                }
            }
        });
        switchBlind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ClientThread.socket !=null){
                    if (switchBlind.isChecked()) {
                        mainActivity.clientThread.sendData("[CDG_ARD]BLINDON");
                        switchBlind.setChecked(false);
                    } else if (!switchBlind.isChecked()) {
                        mainActivity.clientThread.sendData("[CDG_ARD]BLINDOFF");
                        switchBlind.setChecked(true);
                    }
                }
            }
        });
        switchAir.setOnClickListener(view14 -> {
            if (ClientThread.socket != null) {
                if (switchAir.isChecked()) {
                    mainActivity.clientThread.sendData("[CDG_ARD]AIRON");
                    switchAir.setChecked(false);
                } else if (!switchAir.isChecked()) {
                    mainActivity.clientThread.sendData("[CDG_ARD]AIROFF");
                    switchAir.setChecked(true);
                }
            }
        });
        return view;
    }
    void recvSwitchData(String data){
        String[] splitData = data.split("\\[|]|@|\\r");
        if(splitData.length<5) {
            switch(splitData[2]) {
                case "LEDON":
                    imageViewLED.setImageResource(R.drawable.led_on);
                    switchLED.setChecked(true);
                    return;
                case "LEDOFF":
                    imageViewLED.setImageResource(R.drawable.led_off);
                    switchLED.setChecked(false);
                    return;
                case "AIRON":
                    imageViewAir.setImageResource(R.drawable.air_on);
                    switchAir.setChecked(true);
                    return;
                case "AIROFF":
                    imageViewAir.setImageResource(R.drawable.air_off);
                    switchAir.setChecked(false);
                    return;
                case "BLINDON":
                    imageViewBlind.setImageResource(R.drawable.blinds_on);
                    switchBlind.setChecked(true);
                    return;
                case "BLINDOFF":
                    imageViewBlind.setImageResource(R.drawable.blinds_off);
                    switchBlind.setChecked(false);
                    return;
            }
        }
        else{
            textViewLux.setText(null);
            textViewTemp.setText(null);
            textViewHumi.setText(null);
            textViewLux.append(splitData[3]+"%");
            textViewTemp.append(splitData[4]+"ºC");
            textViewHumi.append(splitData[5]+"%");
        }
    }
}
