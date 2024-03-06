package com.example.retrofittestapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private TextView textViewResult;
    private JsonPlaceHolderApi jsonPlaceHolderApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewResult = findViewById(R.id.test_view_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        //getPosts();
        //getComments();
        createPost();
    }

    public void getPosts(){
        Call<List<Post>> call = jsonPlaceHolderApi.getPosts(4,"ID","desc");

        call.enqueue(new Callback<List<Post>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                assert posts != null;
                for (Post post : posts){
                    String content ="";
                    content += "ID: "+post.getId()+"\n";
                    content += "User ID: "+post.getUserId()+"\n";
                    content += "Title: "+post.getTitle()+"\n";
                    content += "Text: "+post.getText()+"\n";
                    content += "\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    public void getComments(){
        Call<List<Comment>> call = jsonPlaceHolderApi.getComments(4);

        call.enqueue(new Callback<List<Comment>>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<List<Comment>> call, @NonNull Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : "+response.code());
                    return;
                }
                List<Comment> comments = response.body();
                for (Comment comment : comments){
                    String content ="";
                    content += "ID: "+comment.getId()+"\n";
                    content += "Post ID: "+comment.getPostId()+"\n";
                    content += "Name: "+comment.getName()+"\n";
                    content += "Email: "+comment.getEmail()+"\n";
                    content += "Text: "+comment.getText()+"\n";
                    content += "\n";

                    textViewResult.append(content);
                }
            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }

    private void createPost(){
        Post post = new Post(23,"New Title","New Text");
        Call<Post> call = jsonPlaceHolderApi.createPost(post);
        call.enqueue(new Callback<Post>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : "+response.code());
                    return;
                }
                Post postResponse = response.body();
                String content ="";
                content += "Code: "+response.code()+ "\n";
                content += "ID: "+postResponse.getId()+"\n";
                content += "User ID: "+postResponse.getUserId()+"\n";
                content += "Title: "+postResponse.getTitle()+"\n";
                content += "Text: "+postResponse.getText()+"\n";
                content += "\n";

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });
    }
}