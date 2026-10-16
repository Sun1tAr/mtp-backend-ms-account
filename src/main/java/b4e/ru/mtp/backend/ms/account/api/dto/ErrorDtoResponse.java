package b4e.ru.mtp.backend.ms.account.api.dto;

import b4e.ru.mtp.backend.ms.account.exception.MtpErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class ErrorDtoResponse {

    private MtpErrorCode code;
    private String message;
    private Object data;


}
