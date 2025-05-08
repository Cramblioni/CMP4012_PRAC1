package evaluation;

public class BooleanValue extends BaseValue {
    public boolean value;
    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public BaseValue add(BaseValue other) throws DataError {
        if (other instanceof BooleanValue val) {
            return new BooleanValue( this.value || val.value );
        }
        throw new NoImplementationError();
    }
    @Override
    public BaseValue multiply(BaseValue other) throws DataError {
        if (other instanceof BooleanValue val) {
            return new BooleanValue( this.value && val.value );
        }
        throw new NoImplementationError();
    }
    @Override
    public BaseValue negate() throws DataError {
        if (other instanceof BooleanValue val) {
            return new BooleanValue( !this.value );
        }
        throw new NoImplementationError();
    }

    @Override
    public BaseValue equal(BaseValue other) throws DataError {
        return multiply(other);
    }
}
