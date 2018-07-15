package srees.com.flickr.Models;

public class FlickrResponse {
    private Photos photos;

    private String stat;

    public Photos getPhotos ()
    {
        return photos;
    }

    public void setPhotos (Photos photos)
    {
        this.photos = photos;
    }

    public String getStat ()
    {
        return stat;
    }

    public void setStat (String stat)
    {
        this.stat = stat;
    }

    @Override
    public String toString()
    {
        return "FlikrResponse [photos = "+photos+", stat = "+stat+"]";
    }
}
