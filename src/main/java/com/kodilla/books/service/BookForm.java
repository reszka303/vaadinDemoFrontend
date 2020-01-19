package com.kodilla.books.service;

import com.kodilla.books.BookType;
import com.kodilla.books.MainView;
import com.kodilla.books.domain.Book;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class BookForm extends FormLayout {

    private MainView mainView;
    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private TextField publicationYear = new TextField("Publication Year");
    private ComboBox<BookType> type = new ComboBox<>("Book Type");
    private Binder binder = new Binder<>(Book.class);
    private BookService service = BookService.getInstance();

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");

    public BookForm(MainView mainView){
        type.setItems(BookType.values());
        HorizontalLayout buttons = new HorizontalLayout(save,delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(title,author,publicationYear,type,buttons);
        binder.bindInstanceFields(this);

        save.addClickListener(event -> save());
        save.addClickListener(event -> delete());
    }

    private void save() {
        Book book = (Book) binder.getBean();
        service.save(book);
        mainView.refresh();
        setBook(null);
    }

    private void delete() {
        Book book = (Book) binder.getBean();
        service.delete(book);
        mainView.refresh();
        setBook(null);
    }

    public void setBook(Book book) {
        binder.setBean(book);

        if (book == null) {
            setVisible(false);
        } else {
            setVisible(true);
            title.focus();
        }
    }
}