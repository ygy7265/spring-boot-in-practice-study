package org.example.springbootinpractice.ch08;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
public class Course {
    private UUID curseId = UUID.randomUUID();
    private long created = Instant.now().getEpochSecond();
    private String courseName;


    public Course(String courseName) {
        this.courseName = courseName;
    }

}
