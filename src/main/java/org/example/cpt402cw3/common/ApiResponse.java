package org.example.cpt402cw3.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Uniform Response Format")
public class ApiResponse<T> {
    @Schema(description = "Response Code: 200 success, non-200 failure")
    private int code;

    @Schema(description = "Response Message")
    private String message;

    @Schema(description = "Response Data")
    private T data;

    // Success Response
    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(200, "success", data);
    }

    // Error Response
    public static <T> ApiResponse<T> error(int code, String message) {
        return new ApiResponse<>(code, message, null);
    }
}