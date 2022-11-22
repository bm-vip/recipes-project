package maxima.europe.recipes.config;

import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import static maxima.europe.recipes.util.MapperHelper.getOrDefault;

/**
 * @author Behrooz Mohamadi
 * @email behroooz.mohamadi@gmail.com
 * @date 3/27/2018 11:42 AM
 */
@Component
@AllArgsConstructor
public class MessageConfig {
    final MessageSource messageSource;

    public String getMessage(String code, String... args) {
        return getOrDefault(() -> messageSource.getMessage(code, args, LocaleContextHolder.getLocale()), code, NoSuchMessageException.class);
    }
}
