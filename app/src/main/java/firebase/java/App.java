package firebase.java;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.*;

public class App {
    private static String ADMIN_SDK = "/home/alexandresoares/Desktop/firebase-java/adminsdk.json";
    private static String DATABASE_URL = "https://myffbproject-default-rtdb.firebaseio.com/";

    private static void setup() {
        // Fetch the service account key JSON file contents
        FileInputStream serviceAccount;
        try {
            serviceAccount = new FileInputStream(ADMIN_SDK);

            // Initialize the app with a service account, granting admin privileges
            FirebaseOptions options;
            try {
                options = FirebaseOptions.builder()
                        .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                        // The database URL depends on the location of the database
                        .setDatabaseUrl(DATABASE_URL)
                        .build();
                FirebaseApp.initializeApp(options);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static DatabaseReference getRef(String path) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(path);
        return ref;
    }

    public static void pushTag(String tagId, Tag tag) {
        DatabaseReference ref = getRef(tagId);
        ApiFuture<Void> future = ref.setValueAsync(tag);
        try {
            future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    public static void getTag(String tagId) {
        // TODO this does not work
        // DatabaseReference ref = getRef(tagId);
        // ref.child(tagId).addListenerForSingleValueEvent(new ValueEventListener() {
        // @Override
        // public void onDataChange(@NonNull DataSnapshot snapshot) {
        // User userProfile=snapshot.getValue(User.class);
        // if(userProfile!=null)
        // {

        // if(userProfile.morning_List!=null)
        // {
        // for(int i=0;i<userProfile.morning_List.size();i++)
        // {
        // FoodItem foodItem=new FoodItem(userProfile.morning_List.get(i));
        // mFoodList.add(new FoodItem(foodItem.getFoodName(),foodItem.getAmount()));
        // }
        // }
        // }
    }

    public static void main(String[] args) {
        setup();

        // Push a tag to firebase
        pushTag("1", new Tag("Object 1"));
        pushTag("2", new Tag("Object 2"));
        pushTag("3", new Tag("Object 3"));

        // Update a tag on firebase
        pushTag("1", new Tag("1st Object"));

        // Get a tag from firebase
        // Tag tag = getTag("1");
        // getTag("1");
    }
}

class Tag {
    public String name;

    public Tag(String name) {
        this.name = name;
    } // DatabaseReference ref = getRef(tagId);
}
