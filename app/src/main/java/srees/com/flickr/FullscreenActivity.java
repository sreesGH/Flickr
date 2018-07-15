package srees.com.flickr;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import srees.com.flickr.Service.ImageService;

public class FullscreenActivity extends AppCompatActivity {

    private ImageView fullScreenImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        fullScreenImageView = findViewById(R.id.fullscreen_image_view);
        Intent intent = getIntent();
        String url = intent.getStringExtra(MainActivity.IMAGE_URL);
        if(url != null) {
            new ImageService(url, fullScreenImageView).execute();
        }
    }
}
