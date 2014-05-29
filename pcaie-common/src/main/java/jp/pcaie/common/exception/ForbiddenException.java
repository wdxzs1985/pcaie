package jp.pcaie.common.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class ForbiddenException extends RuntimeException {

    /**
     * 
     */
    private static final long serialVersionUID = -4660834161119974816L;

}
