package com.example.sbb.member.service;

import com.example.sbb.member.domain.Member;
import com.example.sbb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    @Autowired
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member create(String membername, String email, String password) {
        Member member = Member.builder()
                .membername(membername)
                .email(email)
                .password(passwordEncoder.encode(password))
                .build();
        memberRepository.save(member);
        return member;
    }
}
