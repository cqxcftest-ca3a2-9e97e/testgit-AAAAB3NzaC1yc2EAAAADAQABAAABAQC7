package com.forte.runtime.test;

import com.forte.runtime.model.AbstractDocument;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by WangBin on 2016/4/29.
 */
@Document(collection = "test")
public class Model extends AbstractDocument {

    //@PersistenceConstructor
    public Model(String value) {
        this.value = value;
    }

    public Model() {
    }

    private String value;
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
