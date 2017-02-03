package com.example.tom.gruppe5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by Tom on 06.01.2017.
 */

public class CreateTextFile extends AppCompatActivity{

    private EditText    editText,
                        editTitle;

            File        ordner;

    private Button      btnSave,
                        btnSwitch;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_text_file);

        InitializeActivity();
        }

       private void InitializeActivity(){

            /* Anlegen eines Ordners im Dateimanager des Gerätes, in dem die Notizen gespeichert werden */

           ordner = new File(Environment.getExternalStorageDirectory(), "Notizdateien");
           if(!ordner.exists()) {
               ordner.mkdirs();     //mkirds = make directoriy
           }

           /*Ansprache der Layout-Elemente nun über den Quellcode möglich*/

            editText = (EditText)findViewById(R.id.editText);
            editTitle = (EditText) findViewById(R.id.editTitle);

            btnSave = (Button)findViewById(R.id.btnSave);
            btnSwitch = (Button)findViewById(R.id.btnSwitch);

           /*Speicherung der Notizdatei per Save-Button in den Ordner*/

           btnSave.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if (editText.getText().length() > 0){

                       Notizen note = new Notizen(editTitle.getText().toString(), editText.getText().toString());

                       /*File, auf die die aktuelle Notizdatei übertragen wird*/
                       File notizdatei = new File(ordner, note.getName() + ".txt");
                       try {
                           OutputStream outputstream = new FileOutputStream(notizdatei);            //FileOutputstream um Dateien zu exportieren
                           outputstream.write(editText.getText().toString().getBytes());            //wird in Datei eingefügt
                           outputstream.close();
                           editText.setText(null);                                                  //Eingabefeld wird geleert


                           Intent result = new Intent();
                           result.putExtra("note", note);
                           setResult(RESULT_OK, result);

                           finish();

                       } catch (FileNotFoundException e) {
                           e.printStackTrace();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
                   else {
                       Toast.makeText(getApplicationContext(), "Kein Text vorhanden !", Toast.LENGTH_SHORT).show();
                   }

               }
           });

           /*Anlegen eines SwitchButtons*/

            btnSwitch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(CreateTextFile.this, TextEditor.class);
                    startActivity(intent);
                }
            });

       }
}
