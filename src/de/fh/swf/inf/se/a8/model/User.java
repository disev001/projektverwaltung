package de.fh.swf.inf.se.a8.model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * Created by dsee on 20.12.2016.
 */
public class User {

    private IntegerProperty id;
    private StringProperty username;
    private StringProperty password;

    public int getId() {
        return id.get();
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getUsername() {
        return username.get();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public String getPassword() {
        return password.get();
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public boolean isIsDozent() {
        return isDozent.get();
    }

    public BooleanProperty isDozentProperty() {
        return isDozent;
    }

    public void setIsDozent(boolean isDozent) {
        this.isDozent.set(isDozent);
    }

    private BooleanProperty isDozent;

}
