package app.meantneat.com.meetneat.Model;

/**
 * Created by mac on 5/25/15.
 */
public class MyModel {
    private ModelInterface model;

    private static MyModel ourInstance = new MyModel();

    public static MyModel getInstance() {
        return ourInstance;
    }

    private MyModel() {
        model = new ParseModel();
    }
    public ModelInterface getModel()
    {
        return  model;
    }
    public  interface ModelInterface
    {
        public  void LoginToSpray(String userName, String password);
    }
}
