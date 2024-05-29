package pl.lodz.p.it.ssbd2024.mok.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SemanticException;
import org.hibernate.query.sqm.PathElementException;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import pl.lodz.p.it.ssbd2024.exceptions.*;
import pl.lodz.p.it.ssbd2024.messages.FilterMessages;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchCriteria;
import pl.lodz.p.it.ssbd2024.model.filtering.builders.RoleSpecificationBuilder;
import pl.lodz.p.it.ssbd2024.model.filtering.builders.SpecificationBuilder;
import pl.lodz.p.it.ssbd2024.model.filtering.builders.UserSpecificationBuilder;
import pl.lodz.p.it.ssbd2024.mok.dto.*;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.mok.mappers.FilteredUsersMapper;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserFilterMapper;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserMapper;
import pl.lodz.p.it.ssbd2024.mok.repositories.UserFilterRepository;
import pl.lodz.p.it.ssbd2024.mok.services.*;
import pl.lodz.p.it.ssbd2024.util.Signer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@Scope("prototype")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TenantService tenantService;
    private final OwnerService ownerService;
    private final AdministratorService administratorService;
    private final Signer signer;
    private final TimezoneService timezoneService;
    private final UserFilterRepository userFilterRepository;
    private final UserFilterService userFilterService;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(UserMapper::toUserResponse).toList());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/filtered")
    public ResponseEntity<FilteredUsersResponse> getAllFiltered(@RequestBody FilteredUsersRequest request,
                                                                @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) throws NotFoundException {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<SearchCriteria> criteriaList = request.searchCriteriaList();
        List<String> roles = Arrays.asList("TENANT", "OWNER", "ADMINISTRATOR");

        if (criteriaList == null) {
            criteriaList = new ArrayList<>();
        }

        if (roles.contains(request.role())) {
            criteriaList.add(new SearchCriteria("active", "eq", true));
        }

        criteriaList.forEach(x -> {
            x.setDataOption(request.dataOption());
        });

        FilteredUsersResponse response;
        try {
            switch (request.role()) {
                case "TENANT" -> {
                    SpecificationBuilder<Tenant> builder = new RoleSpecificationBuilder<>();

                    builder.with(criteriaList);

                    Page<Tenant> result = tenantService.getAllFiltered(builder.build(), pageable);
                    response = new FilteredUsersResponse(result.stream().map(Tenant::getUser).map(UserMapper::toUserResponse).toList(),
                            result.getTotalPages());
                }
                case "OWNER" -> {
                    SpecificationBuilder<Owner> builder = new RoleSpecificationBuilder<>();

                    builder.with(criteriaList);

                    Page<Owner> result = ownerService.getAllFiltered(builder.build(), pageable);
                    response = FilteredUsersMapper.accesslevelToFilteredUsersResponse(result);
                }
                case "ADMINISTRATOR" -> {
                    SpecificationBuilder<Administrator> builder = new RoleSpecificationBuilder<>();

                    builder.with(criteriaList);

                    Page<Administrator> result = administratorService.getAllFiltered(builder.build(), pageable);
                    response = FilteredUsersMapper.accesslevelToFilteredUsersResponse(result);
                }
                default -> {
                    SpecificationBuilder<User> builder = new UserSpecificationBuilder();

                    builder.with(criteriaList);

                    Page<User> result = userService.getAllFiltered(builder.build(), pageable);
                    response = FilteredUsersMapper.userToFilteredUsersResponse(result);
                }
            }

        } catch (InvalidDataException | SemanticException | PathElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FilterMessages.INVALID_DATA, e);
        }

        UserFilter userFilter = UserFilterMapper.toUserFilter(request, pageSize);
        userFilterService.createOrUpdate(userFilter);


        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/{id}")
    public ResponseEntity<DetailedUserResponse> get(@PathVariable UUID id) {
        try {
            User user = userService.getUserById(id);
            List<String> roles = userService.getUserRoles(id);
            return ResponseEntity
                    .ok()
                    .header(HttpHeaders.ETAG, signer.generateSignature(user.getId(), user.getVersion()))
                    .body(UserMapper.toDetailedUserResponse(user, roles));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID administratorId = UUID.fromString(jwt.getSubject());
        try {
            userService.blockUser(id, administratorId);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (UserAlreadyBlockedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (AdministratorOwnBlockException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e);
        }
    }

    @PostMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> unblockUser(@PathVariable UUID id) {
        try {
            userService.unblockUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (UserAlreadyUnblockedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserResponse> updateUserData(@PathVariable UUID id,
                                                       @RequestBody @Valid UpdateUserDataRequest request,
                                                       @RequestHeader(HttpHeaders.IF_MATCH) String tagValue
    ) {
        try {
            Timezone timezone = timezoneService.findByTimezoneName(request.timezone());
            User user = userService.updateUserData(id, UserMapper.toUser(request, timezone), tagValue);
            return ResponseEntity.ok(UserMapper.toUserResponse(user));
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (ApplicationOptimisticLockException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage(), e);
        }
    }

    @PostMapping("/{id}/email-update-request")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> sendUpdateEmail(@PathVariable UUID id, @RequestBody @Valid StartUpdateEmailRequest request) {
        try {
            userService.sendEmailUpdateVerificationEmail(id, request.email());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED, e);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (IdenticalFieldValueException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        }
    }

    @PostMapping("/reset-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        try {
            userService.sendChangePasswordEmail(request.email());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED, e);
        } catch (UserBlockedException | UserNotVerifiedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @PostMapping("/reactivate")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> reactiveUser(@RequestParam String token) {
        try {
            userService.reactivateUser(token);
            return ResponseEntity.ok().build();
        } catch (VerificationTokenExpiredException | VerificationTokenUsedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
