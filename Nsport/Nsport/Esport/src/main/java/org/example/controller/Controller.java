package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.example.model.Notes;
import org.example.model.Tags;
import org.example.view.View;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.ArrayList;

public class Controller {


    public TextField searchBar;
    public ListView<Notes> noteList;

    public TextArea noteContent;
    public Button newNoteButton;
    public Button deleteButton;
    public Button updateButton;
    public ListView<Tags> tagList;
    public TextField newTagField;
    public Button removeTagButton;
    public Button addTagButton;
    public TextField noteTitle;

    private ObservableList<Notes> allNotes;

    public void initialize() {
        initializeNotes();
        searchBar.textProperty().addListener((observableValue, s, t1) -> {
            noteList.getItems().clear();
            noteList.getItems().addAll(getNoteByTag(t1));
        });
    }

    private ArrayList<Notes> getNoteByTag(String t1) {
        ArrayList<Notes> sortedNotes = new ArrayList<>();
        for (Notes allNote : allNotes) {
            for (Tags notesTag : allNote.getNotes_tags()) {
                if (notesTag.getTagContent().contains(t1)) {
                    if (!sortedNotes.contains(allNote)) {
                        sortedNotes.add(allNote);
                    }
                }
            }
        }
        return sortedNotes;
    }


    private void initializeNotes() {
        noteList.getItems().clear();
        allNotes = getAllNotes();
        for (Notes allNote : getAllNotes()) {
            noteList.getItems().add(allNote);
        }
    }

    public void addNewNote(MouseEvent mouseEvent) {
        String title = noteTitle.getText();
        String content = noteContent.getText();
        Notes notes;
        ObservableList<Tags> tags = tagList.getItems();
        if (!title.isEmpty() && !content.isEmpty()) {
            notes = new Notes(title, content);
            for (Tags tag : tags) {
                notes.addTag(tag);
                addTagToDB(tag);
            }
            addNoteToDB(notes);
            allNotes.add(notes);
            initializeNotes();
            noteTitle.clear();
            tagList.getItems().clear();
            noteContent.clear();
        }
    }

    private void addNoteToDB(Notes notes) {
        EntityManager entityManager = View.getENTITY_MANAGER_FACTORY().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(notes);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    private void addTagToDB(Tags tag) {
        EntityManager entityManager = View.getENTITY_MANAGER_FACTORY().createEntityManager();
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(tag);
            entityManager.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            entityManager.close();
        }
    }

    private ObservableList<Notes> getAllNotes() {
        ObservableList<Notes> listToReturn = FXCollections.observableArrayList();
        EntityManager entityManager = View.getENTITY_MANAGER_FACTORY().createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            TypedQuery<Notes> listOfNotes = entityManager.createQuery("FROM Notes", Notes.class);
            listToReturn.addAll(listOfNotes.getResultList());
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
            }
        } finally {
            entityManager.close();
        }
        return listToReturn;
    }

    public void addTag(MouseEvent mouseEvent) {
        String tag = newTagField.getText();
        Tags tags = new Tags();
        if (tag.isEmpty()) {
            return;
        }
        tags.setTagContent(tag);
        newTagField.clear();
        tagList.getItems().add(tags);
    }

    public void updateNote(MouseEvent mouseEvent) {
        int index = noteList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        Notes n = allNotes.get(index);
        if (noteContent.getText().isEmpty() && noteTitle.getText().isEmpty()) {
            return;
        }
        n.setTitle(noteTitle.getText());
        n.setNoteContent(noteContent.getText());
        n.clearTags();
        ObservableList<Tags> tags = tagList.getItems();
        for (Tags tag : tags) {
            n.addTag(tag);
        }
        updateNoteInDb(n);
        tagList.getItems().clear();
        searchBar.clear();
        noteTitle.clear();
        noteContent.clear();
        initializeNotes();
    }

    public void noteClicked(MouseEvent mouseEvent) {
        int index = noteList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        Notes n = allNotes.get(index);
        tagList.getItems().clear();
        noteTitle.setText(n.getTitle());
        noteContent.setText(n.getNoteContent());
        tagList.getItems().addAll(n.getNotes_tags());
    }

    public void noteDelete(MouseEvent mouseEvent) {
        int index = noteList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        Notes n = allNotes.get(index);
        tagList.getItems().clear();
        noteTitle.clear();
        noteContent.clear();
        deleteNote(n);
        for (Tags notesTag : n.getNotes_tags()) {
            deleteTag(notesTag);
        }
        initializeNotes();
    }

    public void deleteNote(Notes noteToDelete) {
        EntityManager entityManager = View.getENTITY_MANAGER_FACTORY().createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entityManager.contains(noteToDelete) ? noteToDelete : entityManager.merge(noteToDelete));
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
        }
    }

    public void deleteTag(Tags tagToDelete) {
        EntityManager entityManager = View.getENTITY_MANAGER_FACTORY().createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.remove(entityManager.contains(tagToDelete) ? tagToDelete : entityManager.merge(tagToDelete));
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
        }
    }

    public void removeTag(MouseEvent mouseEvent) {
        int index = tagList.getSelectionModel().getSelectedIndex();
        if (index < 0) {
            return;
        }
        tagList.getItems().remove(index);
    }

    public void updateNoteInDb(Notes noteToUpdate) {
        EntityManager entityManager = View.getENTITY_MANAGER_FACTORY().createEntityManager();
        EntityTransaction entityTransaction = null;
        try {
            entityTransaction = entityManager.getTransaction();
            entityTransaction.begin();
            entityManager.merge(noteToUpdate);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction != null) {
                entityTransaction.rollback();
                e.printStackTrace();
            }
        } finally {
            entityManager.close();
        }
    }
}