package com.gb.market.market.logservice.handlers;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
public class Handler extends AbstractHandler {
    private final String name;


    public Handler(String name) {
        this.name = name;
    }

    @Override
    public void handle(String channel, String message) {
        if (channel.equals(this.name)) {
            LocalDateTime time = LocalDateTime.now();
            StringBuilder sb = new StringBuilder();
            sb.append(time.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")))
                    .append(" Service ")
                    .append(channel)
                    .append(" : ")
                    .append(message);
            log.info(String.format("Контроллер %s: %s", channel, message));
            saveToFile(sb.toString());
        } else {
            super.handle(channel, message);
        }
    }

    private void saveToFile(String message) {
        File file = new File(name + ".log");
        try (FileOutputStream fis = new FileOutputStream(file, true);
             BufferedWriter writer = new BufferedWriter((new OutputStreamWriter(fis, StandardCharsets.UTF_8)))) {
            writer.write(65279);
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            log.error(String.format("Отсутствует доступ к файлу: %s", file.getPath()));
        }
    }

}
