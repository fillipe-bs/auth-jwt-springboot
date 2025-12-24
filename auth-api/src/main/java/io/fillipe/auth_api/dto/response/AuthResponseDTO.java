package io.fillipe.auth_api.dto.response;

public class AuthResponseDTO {

    private String message;

    public AuthResponseDTO(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
