package com.example.sharedpreferencesexemple2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private SeekBar seekBarSound ;
    private SeekBar seekBarBrightness;
    private TextView son;
    private TextView brightness;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.System.canWrite(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + getPackageName()));
                startActivityForResult(intent, 1000);
        }
        // request audio permission
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.seekBarBrightness= (SeekBar)this.findViewById(R.id.seekBar_brightness);
        this.seekBarSound= (SeekBar)this.findViewById(R.id.seekBar_sound);
        this.seekBarBrightness.setMax(255);
        this.son = this.findViewById(R.id.textView2);
        this.brightness = this.findViewById(R.id.textView3);
        // to get maximum sound value for the device running the app
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        // set the sound bar to the maxVolume
        this.seekBarSound.setMax(maxVolume);


        // ---------------------------------- start of the edited code --------------------------------

        // listening to the Brightiness bar value
        seekBarBrightness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            // changing device brightness
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // changing text value with current progress
                String sondtxt = brightness.getText().toString().split(":")[0];
                String newTxt = sondtxt+": "+seekBarBrightness.getProgress();
                brightness.setText(newTxt);
                // calling device api to change the brightness
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.screenBrightness = seekBar.getProgress() / 255f;
                getWindow().setAttributes(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // empty one
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // empty one
            }
        });
        // listening to the sound bar value
        seekBarSound.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                // changing text value with current progress
                String sondtxt = son.getText().toString().split(":")[0];
                String newTxt = sondtxt+": "+seekBarSound.getProgress();
                son.setText(newTxt);
                // calling device api to change sound
                AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBarSound.getProgress(), 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // ---------------------------------- end edited code -----------------------------------------

    //  Load saved game setting.
        this.loadGameSetting();
}
    private void loadGameSetting()  {
        SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);

        if(sharedPreferences!= null) {

            int brightness = sharedPreferences.getInt("brightness", 90);
            int sound = sharedPreferences.getInt("sound",95);

            this.seekBarSound.setProgress(sound);
            this.seekBarBrightness.setProgress(brightness);

        } else {
            Toast.makeText(this,"Use the default game setting",Toast.LENGTH_LONG).show();
        }

    }
    // Called when user click to Save button.
    public void doSave(View view)  {
        // The created file can only be accessed by the calling application
        // (or all applications sharing the same user ID).
        SharedPreferences sharedPreferences= this.getSharedPreferences("gameSetting", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("brightness", this.seekBarBrightness.getProgress());
        editor.putInt("sound", this.seekBarSound.getProgress());
        // Save.
        editor.apply();

        Toast.makeText(this,"Game Setting saved!",Toast.LENGTH_LONG).show();
    }
}



