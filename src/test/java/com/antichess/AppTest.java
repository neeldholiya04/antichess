package com.antichess;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class AppTest {

    @Test
    @DisplayName("App should exist and be instantiable")
    void testAppExists() {
        assertDoesNotThrow(() -> {
            App app = new App();
        });
    }

    @Test
    @DisplayName("Main method should exist")
    void testMainMethodExists() {
        assertDoesNotThrow(() -> {
            App.class.getMethod("main", String[].class);
        });
    }
}