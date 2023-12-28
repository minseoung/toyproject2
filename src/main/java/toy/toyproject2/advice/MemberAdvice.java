package toy.toyproject2.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import toy.toyproject2.advice.errorDto.ErrorResult;
import toy.toyproject2.exception.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice(basePackages = {"toy.toyproject2.controller"})
public class MemberAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedLonginIdException.class)
    public ErrorResult<Map<String, String>> dupLoginIdExHandler(DuplicatedLonginIdException e) {
        Map<String, String> error = new HashMap<>();
        error.put("loginid", e.getMessage());
        return new ErrorResult<>(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DuplicatedNicknameException.class)
    public ErrorResult<Map<String, String>> dupNicknameExHandler(DuplicatedNicknameException e) {
        Map<String, String> error = new HashMap<>();
        error.put("nickname", e.getMessage());
        return new ErrorResult<>(error);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorResult<Map<String, String>> bindingExHandler(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors()
                .forEach(x -> errors.put(((FieldError) x).getField(), x.getDefaultMessage()));
        return new ErrorResult<>(errors);
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST) //만약 나이에 문자를 입력하면 이 예외가 뜨길래 잡긴했는데 문제는 어떻게 처리해야할지다.
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ErrorResult<String> notParseJasonExHandler(HttpMessageNotReadableException e) {
        return new ErrorResult<>("숫자입력칸에 문자를 입력하셨습니다.");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotExistMemberException.class)
    public ErrorResult<String> noMemberExHandler(NotExistMemberException e) {
        return new ErrorResult<>(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(LoginFailedException.class)
    public ErrorResult<String> loginFailExHandler(LoginFailedException e) {
        return new ErrorResult<>(e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotLoginedException.class)
    public ErrorResult<String> notLoginExHandler(NotLoginedException e) {
        return new ErrorResult<>(e.getMessage());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorResult<String> anyExHandler(Exception e) {
        return new ErrorResult<>(e.getMessage());
    }
}
