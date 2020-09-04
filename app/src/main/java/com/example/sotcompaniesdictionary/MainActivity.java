package com.example.sotcompaniesdictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    EditText editTextSearch;
    Button btnSearch;
    TextView txtViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextSearch = (EditText) findViewById(R.id.editTxtSearchCompany);
        btnSearch = (Button) findViewById(R.id.searchBtn);
        txtViewResult = (TextView) findViewById(R.id.txtViewResult);
        txtViewResult.setMovementMethod(new ScrollingMovementMethod());
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(editTextSearch.getText().toString())){
                    Toast.makeText( MainActivity.this, "Please fill in the search field", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Company");
                    mRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String searchValue = editTextSearch.getText().toString();
                            if (snapshot.child(searchValue).exists()){
                                txtViewResult.setText(snapshot.child(searchValue).getValue().toString());
                            } else {
                                Toast.makeText(MainActivity.this, "Sorry, This company does not participate in Summer of Tech", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
}