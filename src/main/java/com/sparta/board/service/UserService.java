package com.sparta.board.service;


import com.sparta.board.dto.BoardRequestDto;
import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.UserResponseDto;
import com.sparta.board.entity.Board;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.BoardRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Component
@RequiredArgsConstructor
public class UserService {
    private final BoardRepository boardRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";
  
   public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) { //isPresent() 현재 Optional에 넣어준 값이 존재하는지 확인해주는 메서드
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // email 중복확인
        String email = requestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 Email 입니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, email, role);
        userRepository.save(user);

@Transactional
    public ResponseEntity<UserResponseDto> login(LoginRequestDto requestDto, HttpServletResponse res) {
    //값을 비교하기 위해 LoginRequesDto에서 꺼냄

    String username = requestDto.getUsername(); // LoginRequesDto저장된 username
    String password = requestDto.getPassword(); // LoginRequesDto저장된 password

    try {
        // 사용자 확인  DB에서 username컬럼명에 해당하는 사용자를 찾지 못한 경우, IllegalArgumentException 예외던짐
        Optional<Board> boardOptional = boardRepository.findByUsername(username);
        if (boardOptional.isEmpty()) {
            // 사용자를 찾을 수 없을 때
            throw new IllegalArgumentException("등록된 사용자가 없습니다.");
        }
        // 사용자가 있을 때
        Board board = boardOptional.get();
        // 비밀번호 일치 확인 entity에 저장된 비밀번호와 내가 받은 비밀번호 매치되는지 확인
        if (passwordEncoder.matches(password, board.getPassword())) {
            //ID 존재하고 비밀번호 일치할경우 인증이 된 경우 JWT 생성하고 쿠키에 저장을 해서 response 객체에 추가
            String token = jwtUtil.createToken(board.getUsername());
            jwtUtil.addJwtToCookie(token, res);

            // ID 존재하고 비밀번호 일치할경우 ResponseEntity를 이용해 메세지와 상태코드 반환
            UserResponseDto dto = new UserResponseDto();
            dto.setMsg("로그인 성공");
            dto.setStatusCode(String.valueOf(HttpStatus.OK.value()));
            return ResponseEntity.status(HttpStatus.OK).body(dto);
        }else{
            // 비밀번호 불일치 시 IllegalArgumentException 예외던짐
            throw new IllegalArgumentException("비밀번호가 불일치 합니다.");
        }
    } // 예외 처리
    catch (IllegalArgumentException e){
        String errorMessage = e.getMessage();
        UserResponseDto dto = new UserResponseDto();
        dto.setMsg(errorMessage); // msg란에 에러메시지 값으로 초기화
        dto.setStatusCode(String.valueOf(HttpStatus.NOT_FOUND.value())); // statusCode에 404 값으로 입력
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(dto);
    }
 }

