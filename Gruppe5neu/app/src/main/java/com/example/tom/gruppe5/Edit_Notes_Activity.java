package com.example.tom.gruppe5;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Edit_Notes_Activity extends AppCompatActivity {

    String      oldName;

    Notizen     note;

    File        noteFile;
    boolean     isDeleted;

    EditText    editText,
                title;

    Button      btnDelete,
                btnSave,
                btnNotice,
                btnShare;

    final static int NOTE_DELETED = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__notes_);

        EditFunction();
        DeleteNote();
        BackFunction();
        SaveFunktion();
    }

    private void EditFunction(){

        editText =(EditText)findViewById(R.id.editText);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnSave=(Button)findViewById(R.id.btnSaveEditNotes);
        btnNotice = (Button)findViewById(R.id.btnNotizen);
        title = (EditText) findViewById(R.id.title);
        btnShare = (Button)findViewById(R.id.btnShare);


         /*Überprüfung ob bei Übergang in Activity beide Intents mitgeliefert wurden*/
        if(getIntent().hasExtra("EXTRA_NOTE_TEXT") && getIntent().hasExtra("EXTRA_NOTE_FILE")){

            /*Mit "StringExtra" kann ein Extra in Form eines Strings aus dem Intent geholt werden*/
            note = (Notizen) getIntent().getSerializableExtra("EXTRA_NOTE_TEXT");
            oldName = getIntent().getStringExtra("OLD_TITEL");

            noteFile = (File) getIntent().getExtras().get("EXTRA_NOTE_FILE");

            /*Wir setzen in den editText den Text, welchen wir mit dem Intent mitbekommen haben*/
            editText.setText(note.gettext());
            title.setText(note.getName());
        }

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent send = new Intent();
                send.setAction(Intent.ACTION_SEND);
                send.setType("text/plain");
                send.putExtra(Intent.EXTRA_TEXT, note.getName() + "\n\n" + note.gettext());
                startActivity(send);
            }
        });

    }

    @Override
    protected void onPause() {

        if(!isDeleted) {
            try {
                OutputStream outputStream = new FileOutputStream(noteFile);
                outputStream.write(editText.getText().toString().getBytes());
                outputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.onPause();
    }

    /*Delete-Funktion*/

    private void BackFunction(){

        btnNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

    private void SaveFunktion() {

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().length() > 0) {
                    Intent result = new Intent();
                    result.putExtra("OLD_NAME", oldName);
                    result.putExtra("NOTIZ", new Notizen(title.getText().toString(), editText.getText().toString()));
                    setResult(RESULT_OK, result);
                    Toast.makeText(getApplicationContext(), "Änderungen wurden gespeichert !", Toast.LENGTH_SHORT).show();




                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Kein Text vorhanden !", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void DeleteNote(){

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            /*Vor dem offiziellen Delete wird eine erneute Abfrage durchgeführt*/
                AlertDialog.Builder dialog = new AlertDialog.Builder(Edit_Notes_Activity.this);
                dialog.setTitle("Möchten Sie diese Notiz wirklich löschen ?");

        /*Option: Löschen*/
                dialog.setPositiveButton("Löschen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "Die Notiz wurde gelöscht !", Toast.LENGTH_SHORT).show();
                        isDeleted=true;

                        Intent result = new Intent();
                        result.putExtra("path", noteFile.getPath());
                        result.putExtra("note", note);

                        setResult(NOTE_DELETED, result);

                        noteFile.delete();

                        Edit_Notes_Activity.this.finish();

                    }
                });

        /*Option: Abbrechen*/
                dialog.setNegativeButton("Abbrechen", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                dialog.show();
            }
        });

    }
}

