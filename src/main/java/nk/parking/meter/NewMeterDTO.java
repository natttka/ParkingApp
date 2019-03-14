package nk.parking.meter;

import org.springframework.stereotype.Component;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
public class NewMeterDTO {

    @NotNull
    private Long customerId;

    @NotNull
    @FutureOrPresent
    private LocalDateTime startTime;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
}
