package com.codeit.springwebbasic.member.controller;

import com.codeit.springwebbasic.common.dto.ApiResponse;
import com.codeit.springwebbasic.member.dto.request.MemberCreateRequestDto;
import com.codeit.springwebbasic.member.dto.response.MemberResponseDto;
import com.codeit.springwebbasic.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/members")
@RequiredArgsConstructor
@Slf4j // log 라는 이름의 Logger 객체를 바로 생성.
public class MemberController {

    private final MemberService memberService;
//    private final Logger log = LoggerFactory.getLogger(MemberController.class);

    // 회원 가입
    // url: /api/members: POST
    // 데이터: name: string(필수), email: string(필수), phone: string(필수, 전번 형식 검사 필요)
    // 요청 DTO: MemberCreateRequestDto
    // 비즈니스로직: 이메일 중복 체크 필요, DTO를 Entity로 변환해서 멤버 저장
    // 응답: id, name, email, phone, grade, joinedAt
    // 상태 코드: 201 CREATED
    @PostMapping
    public ResponseEntity<ApiResponse<MemberResponseDto>> createMember(
            @Valid @RequestBody MemberCreateRequestDto requestDto) {
        log.info("/api/v1/members: POST, dto: {}", requestDto);
        MemberResponseDto responseDto = memberService.createMember(requestDto);

        ApiResponse<MemberResponseDto> response = ApiResponse.success(responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 회원 조회 (단일)
    // url: /api/members/멤버id: GET
    // 비즈니스로직: 회원 조회 후 리턴, 회원 없을 시 "회원을 찾을 수 없습니다." | 400 응답
    // 응답: 위에서 사용한 Response용 DTO로 응답 | 200 OK
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<MemberResponseDto>> getMember(@PathVariable Long id) {
        MemberResponseDto member = memberService.getMember(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiResponse.success(member));
    }

    // 전체 회원 조회 & 검색
    // url: /api/members?name=xxx: GET  | name은 전달되지 않을 수도 있습니다.
    // name이 전달되지 않으면 전체 조회, name이 전달 된다면 name이 포함된 회원을 조회.
    // 비즈니스로직: 각 상황에 맞는 Service 메서드를 호출해서 리턴.
    // 응답: 조회된 회원(Response DTO)을 리스트에 담아서 리턴 | 200 OK
    @GetMapping
    public ResponseEntity<ApiResponse<List<MemberResponseDto>>> getMembers(
            @RequestParam(required = false) String name
    ) {

        List<MemberResponseDto> members
                = name != null
                ? memberService.searchMembers(name) : memberService.getAllMembers();

        return ResponseEntity.ok(ApiResponse.success(members));
    }


}
