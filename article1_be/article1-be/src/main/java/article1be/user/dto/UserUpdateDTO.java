package article1be.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserUpdateDTO {

    @NotBlank(message = "닉네임은 필수 입력값입니다.")
    @Size(min = 1, max = 15, message = "닉네임은 1자 이상, 15자 이하이어야 합니다.")
    private String userNickname;

}