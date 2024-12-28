package com.st61019.SemA.dao.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecoverPasswordByTokenRequest {
    private String username;
    private String token;
    private String newPassword;
    private String newPasswordAgain;
}
