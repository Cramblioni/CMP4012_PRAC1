package evaluation;

public class BodyVisitor extends BaseVisitor {
    Context module;
    Context local;

    public BodyVisitor(Context module, Context local) {
        this.module = module;
        this.local = local;
    }
}
