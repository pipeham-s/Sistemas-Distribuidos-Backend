package com.example.backend_sistemas_distribuidos.business.entities.auxiliar;


import com.example.backend_sistemas_distribuidos.business.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    private String username;
    private String password;


}
