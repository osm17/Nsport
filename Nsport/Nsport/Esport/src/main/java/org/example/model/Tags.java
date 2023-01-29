package org.example.model;

import javax.persistence.*;

@Entity
@Table(name = "tags")
public class Tags {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tag_id")
    private int TagId;

    @Column(name = "tag_content")
    private String TagContent;

    public String getTagContent() {
        return TagContent;
    }

    public void setTagContent(String tagContent) {
        TagContent = tagContent;
    }

    public Tags() {
    }

    @Override
    public String toString() {
        return this.getTagContent();
    }
}