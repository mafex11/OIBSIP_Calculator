package com.example.calculator_app1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.mozilla.javascript.Context;  //these are the two imports from the rhino library
import org.mozilla.javascript.Scriptable;




import com.google.android.material.button.MaterialButton;




public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView resulttv, solutiontv;
    MaterialButton buttonc, buttonbrackop, buttonbrackcl, buttondiv;
    MaterialButton button7, button8, button9, buttonmul;
    MaterialButton button4, button5, button6, buttonadd;
    MaterialButton button1, button2, button3, buttonsub;
    MaterialButton buttonac, button0, buttondot, buttoneq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resulttv = findViewById(R.id.result_tv);
        solutiontv = findViewById(R.id.solution_tv);

        assignId(buttonc, R.id.button_c);
        assignId(buttonbrackop, R.id.button_openbracket);
        assignId(buttonbrackcl, R.id.button_closebracket);
        assignId(buttondiv, R.id.button_divide);
        assignId(button7, R.id.button_7);
        assignId(button8, R.id.button_8);
        assignId(button9, R.id.button_9);
        assignId(buttonmul, R.id.button_multiply);
        assignId(button4, R.id.button_4);
        assignId(button5, R.id.button_5);
        assignId(button6, R.id.button_6);
        assignId(buttonadd, R.id.button_add);
        assignId(button1, R.id.button_1);
        assignId(button2, R.id.button_2);
        assignId(button3, R.id.button_3);
        assignId(buttonsub, R.id.button_sub);
        assignId(buttonac, R.id.button_ac);
        assignId(button0, R.id.button_0);
        assignId(buttondot, R.id.button_dot);
        assignId(buttoneq, R.id.button_equals);
        assignId(button0, R.id.button_0);


    }

    void assignId(MaterialButton btn, int id) {
        btn = findViewById(id);
        btn.setOnClickListener(this); //it will set on click listner to all these buttons
    }

    public void onClick(View view) {
        //Print text..
        MaterialButton button = (MaterialButton) view;
        String buttontext = button.getText().toString();  //here we get the buttons that we click is shown in the screen
        String datatocalculate = solutiontv.getText().toString();  //here we get the data which from the solutiontv //here we
        // get only one data that we pressed into calcaulator
        if (buttontext.equals("AC")) {
            solutiontv.setText("");
            resulttv.setText("0");
            return;
        }
        if (buttontext.equals("=")) {
            solutiontv.setText(resulttv.getText());
            return;
        }
        if (buttontext.equals("C")) {
            if (solutiontv.length()==1) {

                solutiontv.setText("");
                resulttv.setText("0");
                return;
            }
            else if(solutiontv.length()==0)
            {
                Toast.makeText(MainActivity.this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
                resulttv.setText("0");
                solutiontv.setText("");
                return;
            }
            else {

                datatocalculate = datatocalculate.substring(0, datatocalculate.length() - 1);//it will trim the last character only
            }


            //that means 1 value becomes less

        } else {
            datatocalculate = datatocalculate + buttontext;//Concatinate
        }

        solutiontv.setText(datatocalculate);

        String finalresult = getResult(datatocalculate); //we call the method and in parameter we pass the datatocalculate
        if(!finalresult.equals("Err")){
            resulttv.setText(finalresult);
        }
    }

    String getResult(String data) {  //here for calculation we have added one library i.e is rhino library in the build.gradle
        try {
            Context context = Context.enter();
            context.setOptimizationLevel(-1);
            Scriptable scriptable = context.initSafeStandardObjects();
            String finalresult = context.evaluateString(scriptable,data, "Javascript", 1, null).toString();
            if(finalresult.endsWith(".0")){
                finalresult=finalresult.replace(".0","");
            }
            return finalresult;
        } catch (Exception e) {
            return "Err";
        }
    }

}