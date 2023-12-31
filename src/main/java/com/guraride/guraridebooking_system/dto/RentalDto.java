package com.guraride.guraridebooking_system.dto;

import com.guraride.guraridebooking_system.models.Bike;
import com.guraride.guraridebooking_system.models.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RentalDto {
    private Long rental_id;
    @DateTimeFormat(pattern = "yyyy-MM-dd' T 'HH:mm")
//    @NotNull(message = "Start time should not be null")
    private LocalDateTime startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd' T 'HH:mm")
//    @NotNull(message = "End time should not be null")
    private LocalDateTime endTime;
//    @NotNull(message = "Returned status should not be null")
    private Boolean isReturned;
    @NotEmpty(message = "Status should not be empty")
    private String status;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;
    @Valid
//    @NotNull(message = "User should not be null")
    private User user;
    @Valid
//    @NotNull(message = "Bike should not be null")
    private Bike bike;
}
