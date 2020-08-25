package com.tayfurunal.mentorship.controller;

import com.tayfurunal.mentorship.dto.TopicDto;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.service.TopicService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/topics")
@Api
public class TopicController {

    private TopicService topicService;

    TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @PostMapping()
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> createTopic(@Valid @RequestBody TopicDto topicDto) {
        return topicService.createMainTopic(topicDto);
    }

    @PostMapping("/{main}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> addSubTopic(@PathVariable(value = "main", required = true) String main,
                                  @Valid @RequestBody TopicDto topicDto) {
        return topicService.addSubTopic(main, topicDto.getSubTitle());
    }

    @DeleteMapping("/remove")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<?> removeMainTopic(@RequestParam(value = "main", required = false) String main,
                                      @RequestParam(value = "sub", required = false) String sub) {
        if (main != null) {
            return topicService.removeMainTopic(main);
        } else {
            return topicService.removeSubTopic(sub);
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getTopicById(@PathVariable(value = "id", required = true) Long id) {
        return topicService.getById(id);
    }

    @GetMapping
    ResponseEntity<?> getAllTopic() {
        return topicService.getAll();
    }


    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ApiError> handleValidationException(MethodArgumentNotValidException exception,
                                                              HttpServletRequest request) {
        ApiError apiError = new ApiError(false, 400, "Validation Error", request.getServletPath());

        BindingResult result = exception.getBindingResult();
        Map<String, String> validationErrors = new HashMap<>();
        for (FieldError fieldError : result.getFieldErrors()) {
            validationErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        apiError.setValidationErrors(validationErrors);
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

}
