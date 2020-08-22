package com.tayfurunal.mentorship.controller;

import com.tayfurunal.mentorship.domain.Phase;
import com.tayfurunal.mentorship.dto.MentorshipDto;
import com.tayfurunal.mentorship.payload.ApiError;
import com.tayfurunal.mentorship.payload.PhaseRequest;
import com.tayfurunal.mentorship.service.MentorshipService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/mentorships")
@Api
public class MentorshipController {

    private MentorshipService mentorshipService;

    public MentorshipController(MentorshipService mentorshipService) {
        this.mentorshipService = mentorshipService;
    }

    @PostMapping
    public ResponseEntity<?> createMentorship(@Valid @RequestBody MentorshipDto mentorshipDto, Principal principal) {
        return mentorshipService.createMentorship(mentorshipDto, principal.getName());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMentorshipById(@PathVariable(value = "id", required = true) Long id, Principal principal) {
        return mentorshipService.getMentorshipDetailsById(id, principal.getName());
    }

    @PostMapping("/addPhase/{id}")
    public ResponseEntity<?> addPhase(@PathVariable(value = "id", required = true) Long id, @Valid @RequestBody Phase phase) {
        return mentorshipService.addPhaseToMentorship(id, phase);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> startMentorshipById(@PathVariable(value = "id", required = true) Long id) {
        return mentorshipService.startMentorship(id);
    }

    @PostMapping("/completePhase/{id}")
    public ResponseEntity<?> completePhase(@PathVariable(value = "id", required = true) Long id,
                                           Principal principal, @Valid @RequestBody PhaseRequest phase) {
        return mentorshipService.completePhase(id, principal.getName(), phase);
    }

    @GetMapping("/me")
    public ResponseEntity<?> getMyMentorshipsMentor(Principal principal) {
        return mentorshipService.getMyMentorships(principal.getName());
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
