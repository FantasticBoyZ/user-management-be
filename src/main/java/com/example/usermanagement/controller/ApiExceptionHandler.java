package com.example.usermanagement.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@CrossOrigin("*")
public class ApiExceptionHandler {

    /**
//     * Tất cả các Exception không được khai báo sẽ được xử lý tại đây
//     */
//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
//    public ErrorMessage handleAllException(Exception ex, WebRequest request) {
//        System.out.println(ex.getMessage());
//        // quá trình kiểm soat lỗi diễn ra ở đây
//        return new ErrorMessage(10000, ex.getLocalizedMessage());
//    }
//
//    /**
//     * IndexOutOfBoundsException sẽ được xử lý riêng tại đây
//     */
//    @ExceptionHandler(IndexOutOfBoundsException.class)
//    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
//    public ErrorMessage TodoException(Exception ex, WebRequest request) {
//        return new ErrorMessage(10100, "Đối tượng không tồn tại");
//    }
}
