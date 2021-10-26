package org.firstinspires.ftc.teamcode.util;

import java.util.Objects;

public class ObjectUtils {

    public static void requireNonNull(Object... objects) {
        for (Object object : objects) {
            Objects.requireNonNull(object);
        }
    }
}
