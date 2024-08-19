package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {
    @Value("${server.port}")
    private String port;

    public String getPort() {
        return port;
    }
    public Integer count (){
        return Stream.iterate(1, a -> a + 1)
                .parallel()
                .limit(1_000_000)
                .reduce(0, (a, b) -> a + b);
    }
}
