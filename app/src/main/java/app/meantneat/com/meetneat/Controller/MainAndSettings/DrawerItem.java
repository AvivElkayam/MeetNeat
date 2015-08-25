package app.meantneat.com.meetneat.Controller.MainAndSettings;

/**
 * Created by mac on 8/22/15.
 */
public class DrawerItem {
    private String title;
    private int drawable;

    public DrawerItem(String title, int drawable) {
        this.title = title;
        this.drawable = drawable;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getDrawable() {
        return drawable;
    }

    public void setDrawable(int drawable) {
        this.drawable = drawable;
    }
}
