package com.example.tom.gruppe5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Tom on 06.01.2017.
 */

public class TextEditor extends AppCompatActivity {

    final static int NOTE_DELETED = 10;
    final static int NOTE_NEW = 11;


    private Button  btnTextCreate,
                    btnZurück;

    private ListView listView;

    final static int    TEXTEDITOR = 1;

    private Notizen note_selected = null;

    ArrayList<File> fileList;                     //Liste, welche die Dateipfade der Dateien im Notizordner beinhaltet
    ArrayList<Notizen> notizList;                     //Liste, die die Textdateien beinhaltet

    NotizAdapter arrayAdapter;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text_editor);
        InitializeActivity();
    }

    private void InitializeActivity() {

        /*Ansprache der Layout-Elemente über den Quellcode möglich*/

        btnTextCreate = (Button)findViewById(R.id.btnTextCreate);
        btnZurück = (Button)findViewById(R.id.btnZurück);
        listView = (ListView)findViewById(R.id.listView);

        ArrayListSetUp(); /*Eigene Methode in der das Auslesen der Textdateien aus dem Ordner beschrieben wird*/

        arrayAdapter = new NotizAdapter(notizList, TextEditor.this);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent editIntent = new Intent(TextEditor.this, Edit_Notes_Activity.class);

                editIntent.putExtra("EXTRA_NOTE_TEXT", notizList.get(position));
                editIntent.putExtra("OLD_TITEL", notizList.get(position).getName());
                editIntent.putExtra("EXTRA_NOTE_FILE", fileList.get(position));
                startActivityForResult(editIntent, TEXTEDITOR);
            }
        });


        /*Wechsel von Texteditor zu CreateTextFile*/

        btnTextCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent A = new Intent(TextEditor.this, CreateTextFile.class);
                startActivityForResult(A, NOTE_NEW);
            }
        });


        btnZurück.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(TextEditor.this, "Läuft!", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    /*Anzeige in Liste*/

    private void ArrayListSetUp() {

        fileList = new ArrayList<>();
        notizList = new ArrayList<>();

        /*Anlegen einer File(die später als Ordner benannt wird), auf die alle Files gespeichert werden*/
        new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Notizdateien").mkdirs();

        /*Befüllen der Dateienliste, die alle Dateien des Ordners beinhalten soll*/
        /*File wird benannt und "greifbar" gemacht, um diese leichter mit anderen Files zu befüllen*/
        File ordner = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Notizdateien");
        fileList.addAll(Arrays.asList(ordner.listFiles()));             //Listet alle Files in der Liste


        Collections.sort(fileList);         //Sortieren nach alphabetischer Reihenfolge und Zahlen aufsteigend

        Collections.reverse(fileList);      //Umkehrung der Sortierung


        /*Dateien der Position nach in die Liste einfügen*/

        int fileCounter = 0;

        while(fileCounter < fileList.size()) {

            String filename = fileList.get(fileCounter).getPath().substring(fileList.get(fileCounter).getPath().lastIndexOf("/") + 1);
            String[] parts = filename.split("\\.");
            String fileName = parts[0];

            Notizen note = new Notizen(fileName, getTextFromFile(fileList.get(fileCounter)));
            notizList.add(note);

            fileCounter++;

        }
    }




    /*Funktion mit der, der zuvor in der File (im Ordner liegend) gespeicherte Text "ausgelesen" wird*/

    private String getTextFromFile(File file) {

        StringBuilder stringBuilder = new StringBuilder(); //Setzt die Strings der einzeln ausgelesenen Zeilen zusammen

        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            /*Mit BufferedReader kann lediglich eine Zeile im Text ausgelesen werden*/
                /*Also: String wird erstellt, auf den die aktuelle Zeile gespeichert wird, welcher vom BufferedReader zuvor ausgelesen wurde*/
                /*While.Loop wird je nach Länge der Textzeilen einer Textnotiz ausgeführt*/
            String actualLine;

            while((actualLine = bufferedReader.readLine()) != null){        //Zeile wird ausgelesen und actualLine übergeben
                stringBuilder.append(actualLine);                           //Inhalt der actualLine wird dem StringBuilder hinzugefügt
                stringBuilder.append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return stringBuilder.toString().trim();                         //trim dient dazu alle überflüssigen zeilen zu entfernen
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == NOTE_DELETED) {

            File deletedNote = new File((String) data.getSerializableExtra("path"));
            Notizen note = (Notizen) data.getSerializableExtra("note");

            fileList.remove(deletedNote);
            notizList.remove(note);

            arrayAdapter.notifyDataSetChanged();
        }

        if (resultCode == RESULT_OK) {

            if (requestCode == TEXTEDITOR) {

                String oldName = (String) data.getSerializableExtra("OLD_NAME");
                Notizen alt = new Notizen(oldName, "");
                Notizen note = (Notizen) data.getSerializableExtra("NOTIZ");

                notizList.remove(alt);
                notizList.add(0, note);

                arrayAdapter.notifyDataSetChanged();

            }

            if (requestCode == NOTE_NEW) {

                Notizen note = (Notizen) data.getSerializableExtra("note");

                notizList.add(0, note);
                arrayAdapter.notifyDataSetChanged();

            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
