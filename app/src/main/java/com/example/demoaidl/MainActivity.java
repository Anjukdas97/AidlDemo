package com.example.demoaidl;



import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.IBinder;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView textViewResult;
    private EditText editTextFirst,editTextSecond;
    private Button add,mul,div,sub,clear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult=findViewById(R.id.display_result);
        editTextFirst=findViewById(R.id.edt_1stvalue);
        editTextSecond=findViewById(R.id.edt_2ndvalue);
        add=findViewById(R.id.addition);
        mul=findViewById(R.id.multiplication);
        div=findViewById(R.id.divition);
        sub=findViewById(R.id.substraction);
        clear=findViewById(R.id.clear);

        add.setOnClickListener(this);
        sub.setOnClickListener(this);
        div.setOnClickListener(this);
        mul.setOnClickListener(this);
        clear.setOnClickListener(this);
        bindToAIDLService();


    }

    private void bindToAIDLService() {
        Intent aidlServiceIntent=new Intent("connect_to_aidl_service");
        bindService(aidlServiceIntent,serviceConnectionObject,BIND_AUTO_CREATE);
    }
    ServiceConnection serviceConnectionObject =new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.addition:
                verifyAndCalculate(1);
                break;

            case R.id.substraction:
                verifyAndCalculate(2);
                break;

            case R.id.multiplication:
                verifyAndCalculate(3);
                break;

            case R.id.divition:
                verifyAndCalculate(4);
                break;

            case R.id.clear:
                editTextFirst.setText(null);
                editTextSecond.setText(null);
                textViewResult.setText(null);
                break;

            default:
                Log.i("Error","Default case");

        }
    }

    private void verifyAndCalculate(int operationtype) {

        if(isAnyValueMissing()){
            Toast.makeText(this,"please enter values",Toast.LENGTH_SHORT).show();
        }else{
            int result,firstvalue,secondvalue;
            firstvalue=Integer.parseInt(editTextFirst.getText().toString());
            secondvalue=Integer.parseInt(editTextSecond.getText().toString());
            result=performCalculation(firstvalue,secondvalue,operationtype);
            textViewResult.setText(" "+result);
        }
    }

    private int performCalculation(int firstvalue, int secondvalue, int operationtype) {
        switch (operationtype){
            case 1:
                return firstvalue+secondvalue;
            case 2:
                return firstvalue-secondvalue;
            case 3:
                return firstvalue*secondvalue;
            case 4:
                return firstvalue/secondvalue;

            default:
                Log.e("Error","invalid operator");
                return 0;
        }
    }


    private boolean isAnyValueMissing() {
        return editTextFirst.getText().toString().isEmpty() && editTextSecond.getText().toString().isEmpty();

    }
}