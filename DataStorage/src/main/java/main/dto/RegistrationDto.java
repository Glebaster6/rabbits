package main.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationDto implements Serializable {
    private String facilityName;
    private String facilityDescription;
    private String login;
    private String password;
}
