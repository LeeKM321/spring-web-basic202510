package com.codeit.springwebbasic.member.service;

import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.entity.Member;
import com.codeit.springwebbasic.member.entity.MemberGrade;
import com.codeit.springwebbasic.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto createMember(MemberCreateRequestDto requestDto) {

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

        Member saved = memberRepository.save(member);
        return MemberResponseDto.from(member);

    }

    public MemberResponseDto getMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Entity Not Found: {}", id);
                    return new IllegalArgumentException("회원을 찾을 수 없습니다: " + id);
                });

        log.info("findMember: {}", member);
        return MemberResponseDto.from(member);
    }

    public List<MemberResponseDto> searchMembers(String name) {
        List<Member> memberList = memberRepository.findByNameContaining(name);
        return getMemberResponseDtos(memberList);
    }

    public List<MemberResponseDto> getAllMembers() {
        List<Member> memberList = memberRepository.findAll();
        return getMemberResponseDtos(memberList);
    }

    private List<MemberResponseDto> getMemberResponseDtos(List<Member> memberList) {
        return memberList.stream()
                .map(MemberResponseDto::from)
                .collect(Collectors.toList());
    }
}
