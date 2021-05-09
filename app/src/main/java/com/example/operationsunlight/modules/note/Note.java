package com.example.operationsunlight.modules.note;

import java.util.Date;
import java.util.Objects;

public class Note {
    String key;
    private String note;
    private Date date_created;

    public Note() {    }

    public Note(String note, Date date_created) {
        this.note = note;
        this.date_created = date_created;
    }

    @Override
    public String toString() {
        return "Note{" +
                "key='" + key + '\'' +
                ", note='" + note + '\'' +
                ", date_created=" + date_created +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return key.equals(note.key);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }
}
