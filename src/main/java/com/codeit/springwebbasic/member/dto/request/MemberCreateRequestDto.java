package com.codeit.springwebbasic.member.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// @Getter, @NoArgsConstructor, @AllArgsConstructor 모두 삭제!
// record: java 16+, 불변 데이터 운반 객체를 아주 간결하게 작성할 수 있도록 제공되는 클래스.
// 컴파일 과정에서 record를 불변의 객체로 만들어 줍니다. (필드에 private final이 다 붙음, setter는 제공되지 않음)
// getter는 기본 제공되는데, 이름에 get이 붙지 않습니다.
// Lombok의 builder 사용 가능 (목적에 맞게 사용하세요!)
@Schema(description = "회원 생성을 위한 요청 DTO")
public record MemberCreateRequestDto(
        @Schema(description = "회원 이름", example = "홍길동")
        @NotBlank(message = "이름은 필수입니다.")
        String name,

        @Schema(description = "이메일", example = "hong1234@naver.com")
        @NotBlank(message = "이메일은 필수입니다.")
        @Email(message = "올바른 이메일 형식이 아닙니다.")
        String email,

        @Schema(description = "전화번호", example = "010-1234-5678")
        @NotBlank(message = "전화번호는 필수입니다.")
        @Pattern(regexp = "^\\d{3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식이 올바르지 않습니다.")
        String phone
) {
        // 본문이 비어있어도 됩니다.
        // 필요하다면 여기에 다른 종류의 생성자, 정적 메서드 등을 선언할 수 있습니다.
}

