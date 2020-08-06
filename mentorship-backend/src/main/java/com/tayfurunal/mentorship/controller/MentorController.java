package com.tayfurunal.mentorship.controller;

import com.tayfurunal.mentorship.domain.Mentor;
import com.tayfurunal.mentorship.dto.MentorDto;
import com.tayfurunal.mentorship.mapper.MentorMapper;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.payload.ApplyStatusRequest;
import com.tayfurunal.mentorship.service.MentorService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/api/mentors")
@Api
public class MentorController {

    private MentorService mentorService;

    public MentorController(MentorService mentorService) {
        this.mentorService = mentorService;
    }

    @PostMapping("/apply")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createApplication(@Valid @RequestBody MentorDto mentorDto, Principal principal) {
        Mentor mentor = MentorMapper.INSTANCE.toEntity(mentorDto);
        return mentorService.applyMentorship(mentor, principal.getName());
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllApplicationByProgress() {
        return mentorService.getAllByProgress();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getApplicationByProgress(@PathVariable(value = "id", required = true) Long id) {
        return mentorService.getByIdAndProgress(id);
    }

    @PutMapping("/changeStatus/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeApplyStatus(@Valid @RequestBody ApplyStatusRequest status,
                                               @PathVariable(value = "id", required = true) Long id) {
        return mentorService.changeApplyStatus(status, id);
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
