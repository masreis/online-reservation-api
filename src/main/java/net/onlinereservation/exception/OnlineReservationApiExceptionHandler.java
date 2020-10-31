package net.onlinereservation.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import net.onlinereservation.response.Response;

@ControllerAdvice
public class OnlineReservationApiExceptionHandler<T> {

    @ExceptionHandler(value = { ReservationNotFoundException.class, HotelNotFoundException.class })
    protected ResponseEntity<Response<T>> handleNotFoundException(Exception exception) {
        Response<T> response = new Response<>();
        response.addError(exception.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

}
