package jp.pcaie.admin.validator;

public interface Validator<T> {

    boolean validate(T value);
}
