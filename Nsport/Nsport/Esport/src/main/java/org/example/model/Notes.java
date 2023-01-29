package org.example.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "notes")
public class Notes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private int NoteId;

    @Column(name = "note_title")
    private String title;


    @Column(name = "note_content")
    private String NoteContent;

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name = "notes_tags",
            joinColumns = {@JoinColumn(name = "note_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    private List<Tags> notes_tags = new ArrayList<>();

    public Notes() {
    }

    public void addTag(Tags tags) {
        this.notes_tags.add(tags);
    }

    public Notes(String title, String noteContent) {
        this.title = title;
        NoteContent = noteContent;
    }

    public int getNoteId() {
        return NoteId;
    }


    public String getTitle() {
        return title;
    }

    public List<Tags> getNotes_tags() {
        return notes_tags;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNoteContent() {
        return NoteContent;
    }

    public void setNoteContent(String noteContent) {
        NoteContent = noteContent;
    }

    @Override
    public String toString() {
        return this.title;
    }

    public void clearTags() {
        notes_tags.clear();
    }
}