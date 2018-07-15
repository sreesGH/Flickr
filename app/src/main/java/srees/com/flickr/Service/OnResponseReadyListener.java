package srees.com.flickr.Service;

public interface OnResponseReadyListener {
    void onResponseReady(String responseData);
    void onError();
}
