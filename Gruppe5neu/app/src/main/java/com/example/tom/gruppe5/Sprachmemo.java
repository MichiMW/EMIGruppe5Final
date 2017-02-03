package com.example.tom.gruppe5;

import java.io.File;

/**
 * Created by Tom on 02.02.2017.
 */

public class Sprachmemo {

    String name;
    File audio;

    public Sprachmemo(String name, File audio) {

        this.name = name;
        this.audio = audio;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getAudio() {
        return audio;
    }

    public void setAudio(File audio) {
        this.audio = audio;
    }
}
