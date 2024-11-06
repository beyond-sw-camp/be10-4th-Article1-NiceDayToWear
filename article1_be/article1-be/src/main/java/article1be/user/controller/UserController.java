package article1be.user.controller;

import article1be.common.exception.CustomException;
import article1be.common.exception.ErrorCode;
import article1be.security.util.SecurityUtil;
import article1be.user.dto.UserDataDTO;
import article1be.user.dto.UserResponseDTO;
import article1be.user.dto.UserUpdateDTO;
import article1be.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입 시, 닉네임, 선호도 등록
    @PostMapping("/data")
    public ResponseEntity<Void> createUserData(@RequestBody @Valid UserDataDTO userData) {
        userService.createUserData(userData);

        return ResponseEntity.ok().build();
    }

    // 회원 개인 정보 조회
    @GetMapping("/detail")
    public ResponseEntity<UserResponseDTO> getUserDetails() {
        Long userSeq = SecurityUtil.getCurrentUserSeq();

        log.info("로그인 되어 있는 userSeq: {}", userSeq);

        UserResponseDTO userDetail = userService.getUserDetail(userSeq);

        System.out.println(userDetail.toString());

        return ResponseEntity.ok(userDetail);
    }

    // 회원 정보 (닉네임) 수정
    @PutMapping("/nickname")
    public ResponseEntity<String> updateUserNickname(@RequestBody UserUpdateDTO updateData) {
        Long userSeq = SecurityUtil.getCurrentUserSeq();

        if(userSeq != null) {
            userService.updateUser(userSeq, updateData.getUserNickname());

            return ResponseEntity.ok("닉네임 수정 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

    // 회원 탈퇴 (soft delete)
    @DeleteMapping
    public ResponseEntity<String> deleteUser() {
        Long userSeq = SecurityUtil.getCurrentUserSeq();

        if(userSeq != null) {
            userService.deleteUser(userSeq);

            return ResponseEntity.ok("탈퇴 성공");
        } else {
            throw new CustomException(ErrorCode.NEED_LOGIN);
        }
    }

}
