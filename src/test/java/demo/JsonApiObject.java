package demo;

import com.google.appengine.repackaged.org.codehaus.jackson.annotate.JsonIgnore;
import com.google.gson.Gson;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Id;
import hello.entity.Student;

import java.lang.reflect.Field;
import java.util.HashMap;

public class JsonApiObject<T> {

    @JsonIgnore
    private Class<T> clazz;
    private String type;
    private Object id;
    private HashMap<String, Object> attributes;

    public static JsonApiObject getInstance(Class clazz) {
        JsonApiObject jsonApiObject = new JsonApiObject();
        jsonApiObject.setClazz(clazz);
        return jsonApiObject;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public HashMap<String, Object> getAttributes() {
        return attributes;
    }

    public void setAttributes(HashMap<String, Object> attributes) {
        this.attributes = attributes;
    }

    public void setObject(Object obj) {
        this.type = obj.getClass().getSimpleName().toLowerCase();
        this.attributes = new HashMap<>();
        try {
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (fields[i].isAnnotationPresent(Id.class)) {
                    this.id = fields[i].get(obj);
                } else {
                    this.attributes.put(fields[i].getName(), fields[i].get(obj));
                }
            }
        } catch (Exception ex) {

        }
    }

    public T getContegit remote add origin https://github.com/luyendh-fpt/appengine-endpoint.git
    gntFromJson(String content) {
        JsonApiObject jsonApiObject = new Gson().fromJson(content, JsonApiObject.class);
        T obj = null;
        try {
            obj = clazz.newInstance();
            Field[] fields = obj.getClass().getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                if (jsonApiObject.getAttributes().containsKey(fields[i].getName())) {
                    fields[i].set(obj, jsonApiObject.getAttributes().get(fields[i].getName()));
                }else if (fields[i].isAnnotationPresent(Id.class)) {
                    fields[i].set(obj, jsonApiObject.getId());
                }
            }
        } catch (Exception ex) {

        }
        return obj;
    }
}
