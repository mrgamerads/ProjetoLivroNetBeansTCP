
package com.tecnicasprogramacao.livro;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Precondition;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author v-mayconlima
 */
public class LivroProvider {
    
    CollectionReference reference;
    
    static Firestore db;
    
    public static boolean inseriLivro(String collection, String document, Map<String, Object> data){
        
        db = FirestoreClient.getFirestore();
        
        try{
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<WriteResult> result = docRef.set(data);
            System.out.println("Inserido corretamente!");
            return true;
        }
        catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
        return false;
    }
    
    public static boolean atualizaLivro(String collection, String document, Map<String, Object> data){
        
        db = FirestoreClient.getFirestore();
        
        try{
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<WriteResult> result = docRef.update(data);
            System.out.println("Atualizado corretamente!");
            return true;
        }
        catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
        return false;
    }

    public static boolean excluiLivro(String collection, String document){
        
        db = FirestoreClient.getFirestore();
        
        try{
            DocumentReference docRef = db.collection(collection).document(document);
            ApiFuture<WriteResult> result = docRef.delete(Precondition.NONE);
            System.out.println("Deletado com sucesso!");
            return true;
        }
        catch (Exception e){
            System.out.println("Erro: " + e.getMessage());
        }
        return false;
    }

    public static void carregarTabelaLivros(JTable table){
        
        db = FirestoreClient.getFirestore();
        
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("Titulo");
        model.addColumn("Autor");
        model.addColumn("Editora");
        model.addColumn("Ano Edicao");
        model.addColumn("Localizacao");
        
        try{
           CollectionReference livros = Conexion.db.collection("Livro");
           ApiFuture<QuerySnapshot> querySnap = livros.get();
           
           for(DocumentSnapshot document: querySnap.get().getDocuments()){
               model.addRow(new Object[]{
                   document.getId(),
                   document.getString("Titulo"),
                   document.getString("Autor"),
                   document.getString("Editora"),
                   document.get("AnoEdicao"),
                   document.getString("Localizacao"),                   
               });
           }
        }
        catch (InterruptedException | ExecutionException e){
            System.out.println("Erro: " + e.getMessage());           
        }
        
        table.setModel(model);
    }
}
