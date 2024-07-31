package com.example.sbb.member.service;

import com.example.sbb.global.exception.DataNotFoundException;
import com.example.sbb.member.domain.Member;
import com.example.sbb.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Member getMember(String membername) {
        Optional<Member> member = memberRepository.findByMembername(membername);
        if (member.isPresent()) {
            return member.get();
        } else {
            throw new DataNotFoundException("member not found");
        }
    }
}
