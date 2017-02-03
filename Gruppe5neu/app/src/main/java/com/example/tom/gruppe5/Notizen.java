package com.example.tom.gruppe5;

import org.w3c.dom.Text;

import java.io.Serializable;

/**
 * Created by Tom on 02.02.2017.
 */

public class Notizen implements Serializable{


    private String name;
    private String text;

    public Notizen (String name, String text){
        this.name = name;
        this.text = text;
    }

    public String getName()  {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String gettext() {
        return text;
    }
    public void settext(String text) {
        this.text = text;
    }

    public boolean equals(Object o) {

        Notizen note;

        if (o instanceof Notizen) {

            note = (Notizen) o;
        } else {
            return false;
        }

        if (this.name.equals(note.getName())) {

            return true;

        } else {
            return false;
        }

    }
}
