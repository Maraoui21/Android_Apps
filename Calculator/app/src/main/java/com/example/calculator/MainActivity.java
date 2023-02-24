package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    MutableLiveData<String> observableVariable = new MutableLiveData<>();
    String selectedOperation;
    TextView Board;
    androidx.gridlayout.widget.GridLayout gl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        Board = findViewById(R.id.Board);
        gl = findViewById(R.id.GridContainer);
        int size = gl.getChildCount();

        for (int i=0;i<size;i++){
            Button btn = (Button) gl.getChildAt(i);
            if(android.text.TextUtils.isDigitsOnly(btn.getText().toString())){
                btn.setTextColor(Color.parseColor("#303030"));
            }
            btn.setOnClickListener(e->{
                // numbers buttons
                try {
                    if(android.text.TextUtils.isDigitsOnly(btn.getText().toString())){
                        if(observableVariable.getValue() == "0"){
                            observableVariable.setValue(btn.getText().toString());
                        }else {
                            observableVariable.setValue(observableVariable.getValue()+btn.getText().toString());
                        }
                    }
                    // operators buttons
                    else{
                        if(!(btn.getText().toString().contains("=") ||
                                btn.getText().toString().contains("AC"))){
                            // click des buttons des operations
                            selectedOperation=btn.getText().toString();
                            observableVariable.setValue(observableVariable.getValue()+selectedOperation);
                        }
                        if (btn.getText().toString().contains("=")){
                            String arr[] = observableVariable.getValue().split(Pattern.quote(selectedOperation));
                            Integer operator1 = Integer.parseInt(arr[0]);
                            Integer operator2 = Integer.parseInt(arr[1]);
                            Integer res = calculer(operator1,operator2);
                            observableVariable.setValue(res.toString());
                        }
                        if(btn.getText().toString().contains("AC")){
                            selectedOperation="";
                            observableVariable.setValue("0");
                        }
                    }
                }catch (Exception ex){
                    ex.getStackTrace();
                }
            });
        }


        observableVariable.setValue("0");
        observableVariable.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String String) {
                // update textView
                Board.setText(observableVariable.getValue().toString());
            }
        });
    }

    private Integer calculer(Integer a,Integer b) {
        if(selectedOperation.contains("+")){
            return a+b;
        }
        if(selectedOperation.contains("-")){
            return a-b;
        }
        if(selectedOperation.contains("/")){
            return a/b;
        }
        if(selectedOperation.contains("*")){
            return a*b;
        }
        return 0;
    }
}