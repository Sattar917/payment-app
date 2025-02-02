package az.sattarhadjiev.paymentgateway.config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "jwt")
@FieldDefaults(level = PRIVATE)
public class AppProperties {

    String secret;

}
