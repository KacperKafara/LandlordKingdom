package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.mol.dto.OwnApplicationResponse;
import pl.lodz.p.it.ssbd2024.util.TimezoneMapper;

public class ApplicationMapper {
    public static OwnApplicationResponse toOwnApplicationResponse(Application application) {
        return new OwnApplicationResponse(
                application.getId(),
                TimezoneMapper.convertUTCToAnotherTimezoneSimple(
                        application.getCreatedAt(),
                        application.getTenant().getUser().getTimezone() != null ? application.getTenant().getUser().getTimezone().toString() : "UTC",
                        application.getTenant().getUser().getLanguage()),
                application.getLocal().getId(),
                application.getLocal().getName()
        );
    }
}
