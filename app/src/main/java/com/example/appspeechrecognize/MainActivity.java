package com.example.appspeechrecognize;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends Activity implements TextToSpeech.OnInitListener {
    TextView tv;
    EditText et;
    Button bt1,bt2;
    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
        et = findViewById(R.id.et);
        bt1 = findViewById(R.id.bt1);
        bt2 = findViewById(R.id.bt2);

        tts = new TextToSpeech(getApplicationContext(), this);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String msg = et.getText().toString();

                if(msg.equals(""))
                {
                    //na lakhe to nai ave
                    tts.speak("WRITE A MESSAGE FIRST : ",TextToSpeech.QUEUE_ADD,null,null);
                }
                else {
                    //user kai lakhse to ahiya avse
                    tts.speak(msg,TextToSpeech.QUEUE_ADD,null,null);
                }
            }
        });

        bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //koi b app k koi b api ne cal karvi hoy to intent use karvu pade ...ex:google nu speech api
                Intent ii = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                ii.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                startActivityForResult(ii,121);

            }
        });


    }

    //strat activity result mate niche ek method banayi
    @Override
    protected void onActivityResult(int reqCode,int resCode, Intent data)
    {
        if (reqCode == 121)
        {
            if(resCode == RESULT_OK) //result code ok atle kai to lai ne avyu che speech amm
            {
                ArrayList<String> al = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                String msg="";

                for (int i=0;i<al.size();i++){
                    msg = msg+al.get(i);
                }
                tv.setText(msg);

                if(msg.indexOf("open YouTube")!=-1){
                    Intent i1 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://youtube.com"));
                    startActivity(i1);
                }
                else if(msg.indexOf("open Google")!=-1){
                    Intent i2 = new Intent(Intent.ACTION_VIEW, Uri.parse("http://google.com"));
                    startActivity(i2);
                }
                else if (msg.indexOf("call AJ")!=-1){
                    Intent i3 = new Intent(Intent.ACTION_CALL, Uri.parse("tel:"+"9510372889"));
                    startActivity(i3);
                }
                
                else if(msg.indexOf("open maps")!=-1) {
                    Intent i5 = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@22.3297655,73.1467615,15z"));
                    startActivity(i5);
                }

            }
        }

    }

    @Override
    public void onInit(int i) {

    }
}