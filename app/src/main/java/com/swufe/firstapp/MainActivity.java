package com.swufe.firstapp;

        import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText inp;
    TextView out;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       EditText inp = (EditText)findViewById(R.id.et_input);
       TextView out = (TextView)findViewById(R.id.tv_result);

    }
    public void clik(View x){
        String temp = inp.getText().toString();//获取输入内容
        if(temp!=null){
            out.setText(String.valueOf(gettemp(temp)));//在文本框中输出华氏度
        }
    }

    private float gettemp(String temp) {
        float tem = Float.parseFloat(temp);//摄氏度向华氏度转变运算
        return(tem*1.8f)+32.0f;

    }

}
