package ru.itis.masternode;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.itis.masternode.scheduler.service.Scheduler;
import ru.itis.masternode.scheduler.service.impl.SequenceScheduler;
import ru.itis.masternode.service.TaskManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AppConfiguration implements CommandLineRunner {

    @Bean
    public ExecutorService executorService() {
        return Executors.newCachedThreadPool();
    }

    @Bean
    public Scheduler scheduler() {
        return new SequenceScheduler();
    }

    @Bean
    public TaskManager taskManager() {
        return new TaskManager(scheduler());
    }

    @Override
    public void run(String... args) {
        executorService().submit(scheduler());
        executorService().submit(taskManager());
    }

}
