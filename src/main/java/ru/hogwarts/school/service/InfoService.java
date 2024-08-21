package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class InfoService {
    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }

    public Integer oldCount(){
        return Stream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }

    public String timeCount(){
        long newStart = System.currentTimeMillis();
        newCount();
        long newEnd = System.currentTimeMillis();
        long countNew = newStart-newEnd;
        long oldStart = System.currentTimeMillis();
        oldCount();
        long oldEnd = System.currentTimeMillis();
        long countOld = oldStart-oldEnd;

        return "Новый способ : " + countNew + "\nСтарый способ" + countOld;
    }
    public Integer newCount (){
        return IntStream.iterate(1, a -> a + 1)
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }
}
