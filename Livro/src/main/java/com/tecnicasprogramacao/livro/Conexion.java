
package com.tecnicasprogramacao.livro;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import java.io.FileInputStream;
import java.io.IOException;


/**
 *
 * @author v-mayconlima
 */
public class Conexion {
    
    public static Firestore db;
    
    public static void conectarFirebase(){
        try {
           FileInputStream sa = new FileInputStream("Livro.json");
           FirebaseOptions options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(sa))
            .build();

            FirebaseApp.initializeApp(options);
            db = FirestoreClient.getFirestore();
            System.out.print("Sucesso ao Conectar! ");
        }
        catch (IOException e){
            System.out.print("Erro: " +e.getMessage());
        }
    }
    
}
