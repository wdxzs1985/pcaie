package jp.pcaie.validator;


public interface Validator<T> {

    boolean validate(T value);

}
