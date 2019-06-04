import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Map;
import java.util.TreeMap;

public class JsonObjects {
    private Map<String, Object> objects;
    private Object getObject(String value) throws IllegalArgumentException {
        if (value.matches("^[+-]?\\d+$"))
            return Integer.parseInt(value);
        if (value.matches("^[+-]?([0-9]+([.][0-9]*)?|[.][0-9]+)$"))
            return Double.parseDouble(value);
        if (value.matches("^(true)|(false)$"))
            return Boolean.parseBoolean(value);
        if (value.matches("^\".*\"$"))
            return value.substring(1, value.length() - 1);
        Object res = getObject(
                value.split("[ .]+"), true
        );
        if (res == null) {
            throw new IllegalArgumentException("Object " + value + " don't exist");
        }
        return res;
    }
    private Object getObject(String[] expression, boolean safe) {
        Object currentObject = objects;
        for (String object : expression) {
            if (!((Map)currentObject).containsKey(object)) {
                if (!safe) {
                    ((Map)currentObject).put(object, new TreeMap<>());
                } else {
                    return null;
                }
            }
            currentObject = ((Map)currentObject).get(object);
        }
        int b;
        b = 6;
        int c = b + 2;
        return currentObject;
    }
    public JsonObjects() {
        objects = new TreeMap<>();
    }
    public boolean process(String command) {
        int indexEqual = command.indexOf('=');
        String value = command.substring(indexEqual + 1).trim();
        String[] expression = command.substring(0, indexEqual).split("[ .]+");

        Object objValue = getObject(value);
        if (objValue != null) {
            Map<String, Object> currentObject = (Map<String, Object>)getObject(expression, false);
            currentObject.clear();
            currentObject.put("value", objValue);
            return true;
        }
        return false;
    }
    public String toJSON() {
        Gson jsonBuilder = new GsonBuilder().setPrettyPrinting().create();

        return jsonBuilder.toJson(objects);
    }
}
