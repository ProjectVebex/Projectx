package com.example.the_project.model;

import android.net.Uri;
import android.view.View;

import java.util.Objects;

public class NotesDetails {
    String subjectName;
    String uri;

    public NotesDetails(String subjectName, String uri) {
        this.subjectName = subjectName;
        this.uri = uri;
    }

    public NotesDetails(){}

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotesDetails that = (NotesDetails) o;
        return Objects.equals(subjectName, that.subjectName) &&
                Objects.equals(uri, that.uri);
    }

    @Override
    public String toString() {
        return "\nNotesDetails{" +
                "subjectName='" + subjectName + '\'' +
                ", uri='" + uri + '\'' +
                '}';
    }
}
