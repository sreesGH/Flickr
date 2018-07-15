package srees.com.flickr.Models;

import java.util.Arrays;

public class Photos {
    private String total;

    private String page;

    private String pages;

    private Photo[] photo;

    private String perpage;

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getPage ()
    {
        return page;
    }

    public void setPage (String page)
    {
        this.page = page;
    }

    public String getPages ()
    {
        return pages;
    }

    public void setPages (String pages)
    {
        this.pages = pages;
    }

    public Photo[] getPhoto ()
    {
        return photo;
    }

    public void setPhoto (Photo[] photo)
    {
        this.photo = photo;
    }

    public String getPerpage ()
    {
        return perpage;
    }

    public void setPerpage (String perpage)
    {
        this.perpage = perpage;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [total = "+total+", page = "+page+", pages = "+pages+", photo = "+ Arrays.toString(photo) +", perpage = "+perpage+"]";
    }
}
