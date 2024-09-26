package com.questoes.questoes.web.exception;

import com.questoes.questoes.web.exception.exceptions.EntityNotFoundException;
import com.questoes.questoes.web.exception.exceptions.InvalidCredencialException;
import com.questoes.questoes.web.exception.exceptions.RegisteredUserException;
import com.questoes.questoes.web.exception.exceptions.UUIDNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class ApiExceptionHandler {

    private final MessageSource messageSource;

    @ExceptionHandler(UUIDNotFoundException.class)
    public ResponseEntity<ErrorMessage> uuidNotFoundException(Exception e, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, String.format(e.getMessage())));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessage> entityNotFoundException(Exception ex, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.NOT_FOUND, String.format(ex.getMessage())));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorMessage> methodArgumentNotValidException(MethodArgumentNotValidException ex,
                                                                        HttpServletRequest request,
                                                                        BindingResult result) {
        log.error("Api Error - ", ex);
        StringBuilder mensagem = new StringBuilder();

        for(FieldError error : result.getFieldErrors()) {
            mensagem.append(error.getDefaultMessage()).append("\n");
        }

        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        request,
                        HttpStatus.UNPROCESSABLE_ENTITY,
                            mensagem.toString().trim()
                        ));
    }

    @ExceptionHandler(InvalidCredencialException.class)
    public ResponseEntity<ErrorMessage> invalidCredencialException(InvalidCredencialException ex, HttpServletRequest request){
        Object [] params = new Object[]{ex.getNome()};
        String message = messageSource.getMessage("exception.invalidCredencialException", params, request.getLocale());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, message));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> internalServerErrorException(Exception ex, HttpServletRequest request) {
        ErrorMessage error = new ErrorMessage(
                request, HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()
        );
        log.error("Erro interno do servidor Erro {} {} ", error, ex.getMessage());
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorMessage> erroBancoDeDadosException(Exception ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request, HttpStatus.SERVICE_UNAVAILABLE, HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()
        );
        log.error("Erro ao salvar");
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);
    }
    @ExceptionHandler(RegisteredUserException.class)
    public ResponseEntity<ErrorMessage> erroRegisteredUser(Exception ex, HttpServletRequest request) {
        ErrorMessage errorMessage = new ErrorMessage(
                request, HttpStatus.BAD_REQUEST, ex.getMessage()
        );
        log.error("Usuário já cadastrado");
        return ResponseEntity.status((HttpStatus.BAD_REQUEST)).contentType(MediaType.APPLICATION_JSON)
                .body(errorMessage);
    }


}
