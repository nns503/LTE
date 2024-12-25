package lte.backend.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MvcResult;

public class JsonMvcResponseMapper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseJsonResponse(MvcResult result, Class<T> responseType) throws Exception {
        MockHttpServletResponse response = result.getResponse();
        response.setCharacterEncoding("UTF-8");
        String responseBody = response.getContentAsString();

        return objectMapper
                .registerModule(new JavaTimeModule())
                .readValue(responseBody, responseType);
    }
}
