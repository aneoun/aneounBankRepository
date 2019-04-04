
package com.aneoun.bank;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

@SpringBootApplication
public class BankApplication {

	public static void main(final String[] args) throws IOException {

		FileInputStream serviceAccount;
		try {
			serviceAccount = new FileInputStream("aneoun-bb7ec-firebase-adminsdk-ntno9-6e3e27cadd.json");
			final FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(GoogleCredentials.fromStream(serviceAccount)).setDatabaseUrl("https://aneoun-bb7ec.firebaseio.com").build();

			final FirebaseApp defaultApp = FirebaseApp.initializeApp(options);

			final FirebaseAuth defaultAuth = FirebaseAuth.getInstance(defaultApp);
			final FirebaseDatabase defaultDatabase = FirebaseDatabase.getInstance(defaultApp);

		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		SpringApplication.run(BankApplication.class, args);
	}

}
