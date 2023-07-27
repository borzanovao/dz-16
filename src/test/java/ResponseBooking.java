import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ResponseBooking {
    private Integer bookingId;
    private BookingDates booking;

}
