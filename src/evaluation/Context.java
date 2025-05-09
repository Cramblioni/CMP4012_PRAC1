package evaluation;

import java.util.Hashtable;

public class Context {
    Context prior = null;
    Hashtable<String, BaseValue> data;

    public Context(Context prior) {
        this.prior = prior;
        this.data = new Hashtable<>();
    }
    public  Context() {
        this.prior = null;
        this.data = new Hashtable<>();
    }

    public BaseValue get(String name) throws MissingNameError {
        if (data.contains(name)) {
            return data.get(name);
        }
        if (prior == null) {
            throw new MissingNameError();
        }
        return prior.get(name);
    }
    public void put(String name, BaseValue value) {
        data.put(name, value);
    }
}
