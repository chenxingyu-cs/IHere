package cmu.sv.flubber.ihere.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import cmu.sv.flubber.ihere.R;

public class CreateActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        Button createTagButton = (Button)findViewById(R.id.btn_create_tag);
        createTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createButton();
            }
        });
    }

    public void createButton() {
        Context context = getApplicationContext();
        CharSequence text = "The tag has been created!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();

        Intent intent = new Intent(this, ItagDetailActivity.class);
        startActivity(intent);
    }
}
