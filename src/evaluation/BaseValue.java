package evaluation;

public abstract class BaseValue {
    public BaseValue add(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }
    public BaseValue subtract(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }
    public BaseValue multiply(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }
    public BaseValue divide(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }

    public BaseValue equal(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }
    public BaseValue less(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }
    public BaseValue greater(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }

    public BaseValue notEqual(BaseValue other) throws DataError {
        return equal(other).negate();
    }

    public BaseValue negate() throws DataError {
        throw new NoImplementationError();
    }

    public BaseValue access(BaseValue other) throws DataError {
        throw new NoImplementationError();
    }

    public BaseValue call(BaseValue[] args) throws DataError {
        throw new NoImplementationError();
    }
}
