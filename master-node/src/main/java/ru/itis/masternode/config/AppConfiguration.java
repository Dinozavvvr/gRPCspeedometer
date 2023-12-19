package ru.itis.masternode.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;

@Configuration
@EnableConfigurationProperties(WorkerConfiguration.class)
public class AppConfiguration implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        setupFirebase();
    }

    public void setupFirebase() throws IOException {
        File serviceAccount = ResourceUtils.getFile("classpath:firebase/service-account.json");
        GoogleCredentials credentials = GoogleCredentials.fromStream(
                new FileInputStream(serviceAccount));
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        Firestore firestore = FirestoreClient.getFirestore();
    }

}
