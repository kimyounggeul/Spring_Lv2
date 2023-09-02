package com.sparta.board.dto;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
    //    @NotBlank
    @Size(min = 4, max = 10)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")

    private String username;
    //    @NotBlank
    @Size(min = 8, max = 15)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")

    private String password;
}
//    @Pattern(regexp ="^(.+)@(.+)$")
//    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$")
 //   @Pattern(regexp = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$")
//    @NotBlank
//    private String email;
 //   private boolean admin = false;
 //   private String adminToken = "";
