/*package com.shopstyle.customer.exceptions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.bind.MethodArgumentNotValidException;

class ResourceExceptionHandlerTest {

    @InjectMocks
    private ResourceExceptionHandler exceptionHandler;

    @Mock
    private MethodArgumentNotValidException methodArgumentNotValidException;
    private StandardError standardError;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

/*
    @Test
    void methodArgumentNotValidExceptionWhenFirstIsNull() {
       ResponseEntity<StandardError> response = exceptionHandler
                .methodArgumentNotValidException
                        (new MethodArgumentNotValidException("firstName cannot be null"),
                                new MockHttpServletRequest());

        response.getBody().getPath();
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("firstName cannot be null", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());

        assertNotEquals("/user/2", response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());
    }

    @Test
    void httpMessageNotReadableException() {
    }

    @Test
    void testHttpMessageNotReadableException() {
    }

    @Test
    void testMethodArgumentNotValidException() {
    }
}

*/