import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBooking {
    private String firstname;
    private String lastname;
    private Integer totalPrice;
    private Boolean depositPaid;
    private BookingDates bookingdates;
    private String additionalNeeds;
}
