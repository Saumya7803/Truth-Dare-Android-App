package com.farizma.truthdare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class DareActivity extends AppCompatActivity {

    private final ArrayList<String> questionPool = new ArrayList<>();
    private Toolbar toolbar;
    private TextView questionText;
    private Button nextButton;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private final Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_values);

        sharedPreferences = getSharedPreferences("mySharedPreference", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        populateDefaultData();
        if (sharedPreferences.contains("UserDares")) {
            populateUserData(sharedPreferences.getString("UserDares", null));
        }

        questionText = findViewById(R.id.questionText);
        nextButton = findViewById(R.id.nextButton);
        nextButton.setText("NEXT");
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        showRandomQuestion();
    }

    public void populateDefaultData() {
        Values values = new Values();
        questionPool.addAll(Arrays.asList(values.dares));
    }

    public void populateUserData(String jsonDares) {
        String[] dares = gson.fromJson(jsonDares, String[].class);
        if (dares == null) return;

        questionPool.addAll(Arrays.asList(dares));
    }

    public void showRandomQuestion() {
        questionText.setText(
                QuestionSessionManager.nextQuestion(QuestionSessionManager.MODE_DARE, questionPool)
        );
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.custom_dialog_box);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // setup buttons
        final EditText input = dialog.findViewById(R.id.editText);
        Button dismiss = dialog.findViewById(R.id.dismiss);
        Button add = dialog.findViewById(R.id.add);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mText = input.getText().toString();
                if(mText.isEmpty())
                    Toast.makeText(getApplicationContext(), "Empty Text", Toast.LENGTH_LONG).show();
                else{
                    updateUserData(mText);
                    Toast.makeText(getApplicationContext(), "Successfully Added", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    public void updateUserData(String string) {
        ArrayList<String> textList = new ArrayList<>();

        if(sharedPreferences.contains("UserDares")) {
            String jsonDares = sharedPreferences.getString("UserDares", null);
            String[] dares = gson.fromJson(jsonDares, String[].class);
            for(int i=0; i<dares.length; i++)
                textList.add(dares[i]);
        }

        textList.add(string);
        editor.putString("UserDares", gson.toJson(textList));
        editor.apply();
        questionPool.add(string);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return  super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {


        switch (item.getItemId()) {

            case R.id.action_add:
                showDialog();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
