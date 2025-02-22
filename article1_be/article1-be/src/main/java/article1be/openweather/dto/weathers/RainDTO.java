package article1be.openweather.dto.weathers;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RainDTO {

    /** 지난 1 시간 동안의 강우량, mm */
    @JsonProperty("1h")
    private float rain1h;

    /** 지난 3 시간 동안의 강우량, mm */
    @JsonProperty("3h")
    private float rain3h;

}
