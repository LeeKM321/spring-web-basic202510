package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Member createMember(MemberCreateRequestDto requestDto) {

        // 이메일 중복 체크
        if (memberRepository.existsByEmail(requestDto.email())) {
            throw new IllegalArgumentException("이미 등록된 이메일 입니다: " + requestDto.email());
        }

        Member member = Member.builder()
                .name(requestDto.name())
                .email(requestDto.email())
                .phone(requestDto.phone())
                .grade(MemberGrade.BRONZE)
                .joinedAt(LocalDateTime.now())
                .build();

        return memberRepository.save(member);

    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("회원을 찾을 수 없습니다: " + id));
    }

    public List<Member> searchMembers(String name) {
        return memberRepository.findByNameContaining(name);
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }
}
