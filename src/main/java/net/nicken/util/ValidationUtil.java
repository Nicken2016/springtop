package net.nicken.util;


import net.nicken.util.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id){
        checkNotFound(found, "id = "+id);
    }

    public static <T> T checkNotFoundWithId(T object, int id){
        return checkNotFound(object, "id = "+id);
    }

    public static <T> T checkNotFound(T object, String msg){
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg){
        if(!found){
            throw new NotFoundException("Not found entity with "+msg);
        }
    }

    public static void checkNew(HasId bean){
        if(!bean.isNew()){
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void checkIdConsistent(HasId bean, int id){
        if(bean.isNew()){
            bean.setId(id);
        }else if (bean.getId() != id){
            throw new IllegalArgumentException(bean + " must be with id = " + id);
        }
    }

    public static ResponseEntity<String> getErrorResponse(BindingResult result){
        StringBuilder sb = new StringBuilder();
        result.getFieldErrors().forEach(fe -> sb.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append("<br>"));
        return new ResponseEntity<String>(sb.toString(), HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
