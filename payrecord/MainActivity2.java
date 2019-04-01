package com.android.garima.payrecord;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity2 extends AppCompatActivity {

    dbhelper Dbhelper;
    public int total;
    MainActivity main;
    public EditText food1,transport1,beauty1,medical1,cloth1,electricity1,water1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Dbhelper = new dbhelper(this);
        main=new MainActivity();
        final Bundle bundle=getIntent().getExtras();
        final String record=bundle.getString("stuff");
        food1=(EditText)(findViewById(R.id.food1));
        transport1=(EditText)(findViewById(R.id.transport1));
        beauty1=(EditText)(findViewById(R.id.beauty1));
        medical1=(EditText)(findViewById(R.id.medical1));
        cloth1=(EditText)(findViewById(R.id.cloth1));
        electricity1=(EditText)(findViewById(R.id.electricity1));
        water1=(EditText)findViewById(R.id.water1);
        ImageView save=findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               totalpay();
               final AlertDialog dialog = new AlertDialog.Builder(MainActivity2.this).setTitle("Total Expenditure Spend Over:").setMessage("total:- "+total )
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Dbhelper.insertnewrecord(record,total);
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton("Cancle", null).create();
                dialog.show();

            }
        });

    }


     public void totalpay()
    {
        int f1=Integer.parseInt(String.valueOf(food1.getText()));
        int t1=Integer.parseInt(String.valueOf(transport1.getText()));
        int b1=Integer.parseInt(String.valueOf(medical1.getText()));
        int m1=Integer.parseInt(String.valueOf(electricity1.getText()));
        int c1=Integer.parseInt(String.valueOf(cloth1.getText()));
        int e1=Integer.parseInt(String.valueOf(beauty1.getText()));
        int w1=Integer.parseInt(String.valueOf(water1.getText()));
        total=f1+t1+m1+c1+e1+b1+w1;

    }


}
