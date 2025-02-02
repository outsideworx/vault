package application.controller;

import application.entity.security.Member;
import application.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
final class MemberController {
    private final MemberService memberService;

    @GetMapping("/")
    String save() {
        return "Hello world!";
    }

    @PutMapping("/members")
    void save(@RequestBody @Valid Member member) {
        memberService.save(member);
    }
}