package pl.lodz.p.it.ssbd2024.mol.mappers;

import pl.lodz.p.it.ssbd2024.model.Application;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mol.dto.OwnApplicationResponse;
import pl.lodz.p.it.ssbd2024.util.TimezoneMapper;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class ApplicationMapper {
    public static OwnApplicationResponse toOwnApplicationResponse(Application application) {
        User user = application.getTenant().getUser();
        String timezone = user.getTimezone() != null ?
                user.getTimezone().getName() : "UTC";
        String createdAt = TimezoneMapper.convertUTCToAnotherTimezoneSimple(application.getCreatedAt(), timezone, user.getLanguage());
        return new OwnApplicationResponse(
                application.getId(),
                createdAt,
                application.getLocal().getId(),
                application.getLocal().getName(),
                application.getLocal().getAddress().getCountry(),
                application.getLocal().getAddress().getCity(),
                application.getLocal().getAddress().getStreet());
    }

    public static List<OwnApplicationResponse> toGetOwnApplications(List<Application> applications) {
        return applications.stream().map(ApplicationMapper::toOwnApplicationResponse).toList();
    }
}
