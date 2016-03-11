package org.zalando.planb.revocation.api;

import org.springframework.beans.TypeMismatchException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.zalando.planb.revocation.domain.Problem;

import lombok.extern.slf4j.Slf4j;

/**
 * Provides global exception handling for all controllers.
 *
 * @author  <a href="mailto:team-greendale@zalando.de">Team Greendale</a>
 */
@ControllerAdvice
@RequestMapping(produces = "application/x.problem+json")
@Slf4j
public class ExceptionsResource {

    /**
     * Handles missing parameters in requests.
     *
     * @param   e  the exception triggering the error
     *
     * @return  a {@link Problem} with the error information from the exception.
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem missingParameters(final MissingServletRequestParameterException e) {
        return Problem.fromException(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles illegal arguments in parameters.
     *
     * <p>Used for example when a {@code null} value is assigned to a parameter that shouldn't be {@code null}</p>
     *
     * @param   e  the exception triggering the error
     *
     * @return  a {@link Problem} with the error information from the exception.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem illegalArgument(final IllegalArgumentException e) {
        return Problem.fromException(e, HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles type mismatch errors in parameter values.
     *
     * @param   e  the exception triggering the error
     *
     * @return  a {@link Problem} with the type mismatch error information.
     */
    @ExceptionHandler(TypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem typeMismatch(final TypeMismatchException e) {
        return Problem.fromMessage("Type mismatch in parameter value.", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles messages which are not readable because they are not valid JSON, or have an invalid JSON structure.
     *
     * @param   e  the exception triggering the error
     *
     * @return  a {@link Problem} with the message not readable information.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Problem messageNotReadable(final HttpMessageNotReadableException e) {
        return Problem.fromMessage("Invalid JSON, or invalid JSON structure.", HttpStatus.BAD_REQUEST);
    }

    /**
     * Handles all remaining exceptions not covered by the other handlers.
     *
     * <p>When an exception that is not covered by this {@link ControllerAdvice}, returns an HTTP 500 Internal Server
     * Error with a generic {@link Problem} informing the unexpected error.</p>
     *
     * @param   e  the exception triggering the error
     *
     * @return  a {@link Problem} with the unexpected error information.
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public Problem handleGenericException(final Exception e) {
        log.error("An unexpected error occurred: {}", e.getMessage() != null ? e.getMessage() : e.getClass().getName());
        log.debug("Error details: ", e);

        return Problem.fromMessage("An unexpected error occurred", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}