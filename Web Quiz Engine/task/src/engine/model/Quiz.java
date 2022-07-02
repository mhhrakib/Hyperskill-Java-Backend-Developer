package engine.model;

import java.util.List;

public class Quiz {
    private String title;
    private String text;
    private List<String> options;

    public Quiz() {
    }

    public Quiz(String title, String text, List<String> options) {
        this.title = title;
        this.text = text;
        this.options = options;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @java.lang.Override
    public java.lang.String toString() {
        return "Quiz{" +
                "title='" + title + '\'' +
                ", text='" + text + '\'' +
                ", options=" + options +
                '}';
    }
}

