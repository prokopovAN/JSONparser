import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.TreeMap;

public class JsonObjects {
    private Map<String, Object> objects;
    JsonObjects() {
        objects = new TreeMap<>();
    }
    void process(String command) {
        int indexEqual = command.indexOf('=');
        String value = command.substring(indexEqual + 1).trim();
        String[] expression = command.substring(0, indexEqual).split("[ .]+");

        Map<String, Object> currentObject = objects;
        for (String object : expression) {
            if (!currentObject.containsKey(object)) {
                currentObject.put(object, new TreeMap<>());
            }
            currentObject = (Map<String, Object>)currentObject.get(object);
        }
        currentObject.clear();
        if (value.equals("key1")) {
            currentObject.put("value", objects.get(value));
        } else {
            currentObject.put("value", value);
        }
    }
    String toJSON() {
        Gson jsonBuilder = new GsonBuilder().setPrettyPrinting().create();
        return jsonBuilder.toJson(objects);
    }
}
