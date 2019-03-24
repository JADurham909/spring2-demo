package hello;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Greeting implements Serializable {

    private final long _id;
    private final String _content;

    @JsonCreator
    public Greeting(@JsonProperty("id") long id, @JsonProperty("content") String content) {
        this._id = id;
        this._content = content;
    }

    public long getId() {
        return _id;
    }

    public String getContent() {
        return _content;
    }

}