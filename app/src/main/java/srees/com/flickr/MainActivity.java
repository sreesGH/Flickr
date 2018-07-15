package srees.com.flickr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import srees.com.flickr.Models.FlickrResponse;
import srees.com.flickr.Models.Photo;
import srees.com.flickr.Service.OnResponseReadyListener;
import srees.com.flickr.Service.WebService;

public class MainActivity extends AppCompatActivity implements RecyclerViewAdapter.ItemListener {

    public static final String IMAGE_URL = "intent_extre_image_url" ;
    String BASE_URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=3e7cc266ae2b0e0d78e279ce8e361736&format=json&nojsoncallback=1&safe_search=1&text=%s&page=%s";
    String PHOTO_URL = "http://farm%s.staticflickr.com/%s/%s_%s_n.jpg";
    int SPAN_COUNT = 3;
    ArrayList<String> imageList = new ArrayList<>();
    private RecyclerView flickrRecyclerView;
    private EditText editText;
    private WebService searchService;
    private Timer timer;
    private long INPUT_DELAY = 600;
    private RecyclerViewAdapter adapter;
    private GridLayoutManager layoutManager;
    private int currentPage = 1;
    private int maxPages = 1;
    private boolean isLoading;
    private String currentKeyWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flickrRecyclerView = findViewById(R.id.flickr_images_recyclerView);
        layoutManager = new GridLayoutManager(this, SPAN_COUNT);
        flickrRecyclerView.setLayoutManager(layoutManager);
        adapter = new RecyclerViewAdapter(this, imageList, this);
        flickrRecyclerView.setAdapter(adapter);
        flickrRecyclerView.addOnScrollListener(recyclerViewOnScrollListener);

        editText = findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(final CharSequence cs, int arg1, int arg2, int arg3) {
                if (timer != null) {
                    timer.cancel();
                }
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        newSearch(cs.toString(), false);
                    }
                }, INPUT_DELAY);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {
            }
        });
    }

    private void newSearch(String term, final boolean isPagination) {
        if(term != null && !term.isEmpty()) {
            currentKeyWord = term;
            String searchUrl = String.format(BASE_URL, term, currentPage);

            if(searchService != null) {
                searchService.cancel(true);
                searchService = null;
            }
            isLoading = true;
            searchService = new WebService(new OnResponseReadyListener() {
                @Override
                public void onResponseReady(String responseData) {
                    createImageUrlList(responseData, isPagination);
                }

                @Override
                public void onError() {

                }
            });
            searchService.execute(searchUrl);
        }
    }

    private void createImageUrlList(String responseData, boolean isPagination) {
        isLoading = false;
        Gson gson = new Gson();
        FlickrResponse response = gson.fromJson(responseData, FlickrResponse.class);
        maxPages =  Integer.parseInt(response.getPhotos().getPages());
        Photo[] photos = response.getPhotos().getPhoto();

        if(!isPagination) {
            imageList.clear();
        }

        for (Photo photo : photos) {
            imageList.add(String.format(PHOTO_URL, photo.getFarm(), photo.getServer(),
                    photo.getId(), photo.getSecret()));
        }

        adapter.setList(imageList);
    }

    @Override
    public void onItemClick(String item) {
        Intent intent = new Intent(this, FullscreenActivity.class);
        intent.putExtra(IMAGE_URL, item);
        startActivity(intent);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!isLoading && currentPage <= maxPages) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0) {
                    loadNextPage();
                }
            }
        }
    };

    private void loadNextPage() {
        currentPage++;
        newSearch(currentKeyWord, true);
    }
}
