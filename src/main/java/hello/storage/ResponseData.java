package hello.storage;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.http.converter.json.GsonFactoryBean;

/**
 * Created by admin on 04.08.2017.
 */
public class ResponseData {

    private int error;
    private String message;
    private int success;
    private String filename;

    public static ResponseData createSuccess(String filename) {
        ResponseData data = new ResponseData();
        data.success = 1;
        data.filename = filename;
        return data;
    }

    public static ResponseData createError(String message) {
        ResponseData data = new ResponseData();
        data.error = 1;
        data.message = message;
        return data;
    }

    @Override
    public String toString() {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objectMapper.writer().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
