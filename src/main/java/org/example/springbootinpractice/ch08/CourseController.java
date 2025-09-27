package org.example.springbootinpractice.ch08;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@Controller
public class CourseController {
    @MessageMapping("request-response")
    public Mono<Course> requestResponse(final Course course) {
        log.info("In CourseController.requestResponse : {}", course);
        return Mono.just(new Course("You course name : " + course.getCourseName()));
    }
    @MessageMapping("fire-and-forget")
    public Mono<Void> fireAndForget(final Course course) {
        log.info("In CourseController.firAndForger : {}", course);
        return Mono.empty();
    }
    @MessageMapping("request-stream")
    public Flux<Course> requestStream(final Course course) {
        log.info("In CourseController.requestStream : {}", course);
        return Flux.interval(Duration.ofSeconds(1))
                .map(index -> new Course("You course name : " + course.getCourseName() + index))
                .log();
    }
    @MessageMapping("stream-stream")
    public Flux<Course> channel(final Flux<Integer> settings) {
        log.info("In CourseController.stream-stream(channel)");
        return settings
                .doOnNext(setting -> log.info("Requested interval is {} seconds", setting))
                .doOnCancel(() -> log.warn("Client canceled th channel"))
                .switchMap(setting -> Flux.interval(Duration.ofSeconds(setting))
                        .map(index -> new Course("Spring Response #: " + index))
                ).log();
    }
}
