package com.betting.karakoc;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class KarakocApplicationTests {

    @Test
    void merhaba() {
        String selamla = "Selamlar sayin kullanici.";
        assertEquals("Selamlar sayin kullanici.", selamla);
    }

}
