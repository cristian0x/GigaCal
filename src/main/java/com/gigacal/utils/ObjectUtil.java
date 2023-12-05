package com.gigacal.utils;

import java.util.Collection;
import java.util.Map;

public class ObjectUtil {

    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            return ((String) o).length() == 0;
        } else if (o instanceof Integer) {
            return o.equals(0);
        } else if (o instanceof Long) {
            return o.equals(0L);
        } else if (o instanceof Double) {
            return o.equals(0.0);
        } else if (o instanceof Collection) {
            return ((Collection<?>) o).isEmpty();
        } else if (o instanceof Map) {
            return ((Map<?, ?>) o).isEmpty();
        } else {
            return false;
        }
    }
}
