package com.sparta.board.service;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.MessageResponseDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.entity.User;
import com.sparta.board.entity.enumSet.ErrorType;
import com.sparta.board.exception.RestApiException;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원가입
    @Transactional
    public ResponseEntity<MessageResponseDto> signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) {
            throw new RestApiException(ErrorType.DUPLICATED_USERNAME);
        }

        // 입력한 username, password 로 user 객체 만들어 repository 에 저장
        userRepository.save(User.of(requestDto, password));

        return ResponseEntity.ok(MessageResponseDto.of(HttpStatus.OK, "회원가입 성공"));

    }

    // 로그인
    @Transactional(readOnly = true)
    public ResponseEntity<MessageResponseDto> login(LoginRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인 & 비밀번호 확인
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty() || !passwordEncoder.matches(password, user.get().getPassword())) {
            throw new RestApiException(ErrorType.NOT_MATCHING_INFO);
        }

        // header 에 들어갈 JWT 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.set(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.get().getUsername(), user.get().getRole()));

        return ResponseEntity.ok()  // status -> OK
                .headers(headers)   // headers -> JWT
                .body(MessageResponseDto.of(HttpStatus.OK, "로그인 성공"));  // body -> MessageResponseDto

    }

    // 회원 탈퇴
    public ResponseEntity<MessageResponseDto> signout(LoginRequestDto requestDto, User user) {

        // 비밀번호 확인
        String password = requestDto.getPassword();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RestApiException(ErrorType.NOT_MATCHING_PASSWORD);
        }

        userRepository.delete(user);

        return ResponseEntity.ok(MessageResponseDto.of(HttpStatus.OK, "회원탈퇴 완료"));
    }
}
