package lte.backend.auth.controller;

import lombok.RequiredArgsConstructor;
import lte.backend.auth.dto.request.JoinRequest;
import lte.backend.auth.service.AuthService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class AuthController implements AuthApi {

    private final AuthService authService;

    @PostMapping("/join")
    public void join(
            @RequestBody @Validated JoinRequest request
    ){
        authService.join(request);
    }
}
