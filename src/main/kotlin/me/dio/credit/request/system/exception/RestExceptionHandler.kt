package me.dio.credit.request.system.exception

import org.springframework.dao.DataAccessException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.validation.ObjectError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.time.LocalDateTime
import java.util.HashMap

@RestControllerAdvice
class RestExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handlerValidException(ex: MethodArgumentNotValidException): ResponseEntity<ExceptionDetails>{
        var errors: MutableMap<String, String?> = HashMap()

        ex.bindingResult.allErrors.forEach {
            error: ObjectError ->
            val fieldName: String = (error as FieldError).field
            val messageError: String? = error.defaultMessage
            errors[fieldName] = messageError
        }

        val exDetails = ExceptionDetails(
            title = "Bad Request! Consult the documentation!",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.toString(),
            details = errors
        )

        return ResponseEntity(exDetails, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(DataAccessException::class)
    fun handlerValidException(ex: DataAccessException): ResponseEntity<ExceptionDetails>{
        val exDetails = ExceptionDetails(
            title = "Conflict! Consult the documentation!",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.CONFLICT.value(),
            exception = ex.javaClass.toString(),
            details = mutableMapOf(ex.cause.toString() to ex.message)
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exDetails)
    }

    @ExceptionHandler(BusinessException::class)
    fun handlerValidException(ex: BusinessException): ResponseEntity<ExceptionDetails>{
        val exDetails = ExceptionDetails(
            title = "Bad Request! Consult the documentation!",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.toString(),
            details = mutableMapOf(ex.cause.toString() to ex.message)
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exDetails)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handlerValidException(ex: IllegalArgumentException): ResponseEntity<ExceptionDetails>{
        val exDetails = ExceptionDetails(
            title = "Bad Request! Consult the documentation!",
            timestamp = LocalDateTime.now(),
            status = HttpStatus.BAD_REQUEST.value(),
            exception = ex.javaClass.toString(),
            details = mutableMapOf(ex.cause.toString() to ex.message)
        )

        return ResponseEntity.status(HttpStatus.CONFLICT).body(exDetails)
    }
}