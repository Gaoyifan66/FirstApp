package com.swufe.firstapp;

        import androidx.appcompat.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;

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
        String temp = inp.getText().toString();
        if(temp!=null){
            out.setText(String.valueOf(gettemp(temp)));
        }
    }

    private float gettemp(String temp) {
        float tem = Float.parseFloat(temp);
        return(tem*1.8f)+32.0f;

    }

}
