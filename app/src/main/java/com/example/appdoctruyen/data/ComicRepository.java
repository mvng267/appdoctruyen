package com.example.appdoctruyen.data;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ComicRepository {

    public interface OnComicFetchListener {
        void onComicFetch(List<Comics> comics);
        void onComicFetchError(String errorMessage);
    }

    public static void fetchComics(OnComicFetchListener listener) {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        firestore.collection("Comics")
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {
                        List<Comics> comics = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();
                        if (querySnapshot != null) {
                            List<DocumentSnapshot> documents = querySnapshot.getDocuments();
                            for (DocumentSnapshot document : documents) {
                                String id = document.getString("id");
                                String content = document.getString("content");
                                String img = document.getString("img");
                                String title = document.getString("title");
                                String userID = document.getString("userID");
                                Comics comic = new Comics(id, content, img, title, userID);
                                comics.add(comic);
                            }
                        }
                        listener.onComicFetch(comics);
                    } else {
                        listener.onComicFetchError("Error fetching comics from Firestore");
                    }
                });
    }

    public static class Comics {
        private String id;
        private String content;
        private String img;
        private String title;
        private String userID;

        public Comics(String id, String content, String img, String title, String userID) {
            this.id = id;
            this.content = content;
            this.img = img;
            this.title = title;
            this.userID = userID;
        }

        public String getId() {
            return id;
        }

        public String getContent() {
            return content;
        }

        public String getImg() {
            return img;
        }

        public String getTitle() {
            return title;
        }

        public String getUserID() {
            return userID;
        }
    }
}
