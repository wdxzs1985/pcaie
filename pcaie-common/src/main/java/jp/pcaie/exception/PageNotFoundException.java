package jp.pcaie.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class PageNotFoundException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -5150361458019795899L;

}
