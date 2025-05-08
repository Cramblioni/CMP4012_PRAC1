package evaluation;

public class NumberValue extends BaseValue {
    public float value;
    public NumberValue(float value) {
        this.value = value;
    }
    @Override
    public BaseValue add(BaseValue other) throws DataError {
        if (other instanceof NumberValue val) {
            return new NumberValue(value + val.value);
        }
        throw new NoImplementationError();
    }
    @Override
    public BaseValue subtract(BaseValue other) throws DataError {
        if (other instanceof NumberValue val) {
            return new NumberValue(value - val.value);
        }
        throw new NoImplementationError();
    }
    @Override
    public BaseValue multiply(BaseValue other) throws DataError {
        if (other instanceof NumberValue val) {
            return new NumberValue(value * val.value);
        }
        throw new NoImplementationError();
    }
    @Override
    public BaseValue divide(BaseValue other) throws DataError {
        if (other instanceof NumberValue val) {
            return new NumberValue(value * val.value);
        }
        throw new NoImplementationError();
    }

    @Override
    public BaseValue equal(BaseValue other) throws DataError {
        if (other instanceof NumberValue val) {
            return new NumberValue(value * val.value);
        }
        throw new NoImplementationError();
    }
    @Override
    public BaseValue less(BaseValue other) throws DataError {
        if (other instanceof NumberValue val) {
            return new NumberValue(value * val.value);
        }
        throw new NoImplementationError();
    }
    @Override
    public BaseValue greater(BaseValue other) throws DataError {
        if (other instanceof NumberValue val) {
            return new NumberValue(value * val.value);
        }
        throw new NoImplementationError();
    }
}
