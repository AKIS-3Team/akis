package com.example.akis.boundedContext.member.service;

import com.example.akis.base.rq.Rq;
import com.example.akis.boundedContext.member.entity.Member;
import com.example.akis.boundedContext.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final Rq rq;

    public Optional<Member> findByUsername(String username) {
        return memberRepository.findByUsername(username);
    }
}
