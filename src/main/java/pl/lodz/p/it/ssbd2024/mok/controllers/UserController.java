package pl.lodz.p.it.ssbd2024.mok.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.SemanticException;
import org.hibernate.query.sqm.PathElementException;
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
import pl.lodz.p.it.ssbd2024.messages.AdministratorMessages;
import pl.lodz.p.it.ssbd2024.messages.FilterMessages;
import pl.lodz.p.it.ssbd2024.messages.OptimisticLockExceptionMessages;
import pl.lodz.p.it.ssbd2024.messages.VerificationTokenMessages;
import pl.lodz.p.it.ssbd2024.model.Administrator;
import pl.lodz.p.it.ssbd2024.model.Owner;
import pl.lodz.p.it.ssbd2024.model.Tenant;
import pl.lodz.p.it.ssbd2024.model.filtering.SearchCriteria;
import pl.lodz.p.it.ssbd2024.model.filtering.builders.AdministratorSpecificationBuilder;
import pl.lodz.p.it.ssbd2024.model.filtering.builders.OwnerSpecificationBuilder;
import pl.lodz.p.it.ssbd2024.model.filtering.builders.TenantSpecificationBuilder;
import pl.lodz.p.it.ssbd2024.model.filtering.builders.UserSpecificationBuilder;
import pl.lodz.p.it.ssbd2024.mok.dto.*;
import pl.lodz.p.it.ssbd2024.model.User;
import pl.lodz.p.it.ssbd2024.mok.dto.UpdateUserDataRequest;
import pl.lodz.p.it.ssbd2024.mok.dto.UserResponse;
import pl.lodz.p.it.ssbd2024.mok.mappers.UserMapper;
import pl.lodz.p.it.ssbd2024.mok.services.AdministratorService;
import pl.lodz.p.it.ssbd2024.mok.services.OwnerService;
import pl.lodz.p.it.ssbd2024.mok.services.TenantService;
import pl.lodz.p.it.ssbd2024.mok.services.UserService;
import pl.lodz.p.it.ssbd2024.util.Signer;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final TenantService tenantService;
    private final OwnerService ownerService;
    private final AdministratorService administratorService;
    private final Signer signer;

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAll() {
        return ResponseEntity.ok(userService.getAll().stream().map(UserMapper::toUserResponse).toList());
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/filtered")
    public ResponseEntity<FilteredUsersResponse> getAllFiltered(@RequestBody FilteredUsersRequest request,
                                                                @RequestParam(name = "pageNum", defaultValue = "0") int pageNum,
                                                                @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<SearchCriteria> criteriaList = request.searchCriteriaList();
        FilteredUsersResponse response;
        try {
            switch (request.role()) {
                case "TENANT" -> {
                    TenantSpecificationBuilder builder = new TenantSpecificationBuilder();

                    if (criteriaList == null) {
                        criteriaList = new ArrayList<>();
                    }

                    criteriaList.add(new SearchCriteria("active", "eq", true));
                    criteriaList.forEach(x -> {
                        x.setDataOption(request.dataOption());
                        builder.with(x);
                    });

                    Page<Tenant> result = tenantService.getAllFiltered(builder.build(), pageable);
                    response = new FilteredUsersResponse(result.stream().map(Tenant::getUser).map(UserMapper::toUserResponse).toList(),
                            result.getTotalPages());
                }
                case "OWNER" -> {
                    OwnerSpecificationBuilder builder = new OwnerSpecificationBuilder();

                    if (criteriaList == null) {
                        criteriaList = new ArrayList<>();
                    }

                    criteriaList.add(new SearchCriteria("active", "eq", true));
                    criteriaList.forEach(x -> {
                        x.setDataOption(request.dataOption());
                        builder.with(x);
                    });

                    Page<Owner> result = ownerService.getAllFiltered(builder.build(), pageable);
                    response = new FilteredUsersResponse(result.stream().map(Owner::getUser).map(UserMapper::toUserResponse).toList(),
                            result.getTotalPages());
                }
                case "ADMINISTRATOR" -> {
                    AdministratorSpecificationBuilder builder = new AdministratorSpecificationBuilder();

                    if (criteriaList == null) {
                        criteriaList = new ArrayList<>();
                    }

                    criteriaList.add(new SearchCriteria("active", "eq", true));
                    criteriaList.forEach(x -> {
                        x.setDataOption(request.dataOption());
                        builder.with(x);
                    });

                    Page<Administrator> result = administratorService.getAllFiltered(builder.build(), pageable);
                    response = new FilteredUsersResponse(result.stream().map(Administrator::getUser).map(UserMapper::toUserResponse).toList(),
                            result.getTotalPages());
                }
                default -> {
                    UserSpecificationBuilder builder = new UserSpecificationBuilder();

                    if (criteriaList != null) {
                        criteriaList.forEach(x -> {
                            x.setDataOption(request.dataOption());
                            builder.with(x);
                        });
                    }

                    Page<User> result = userService.getAllFiltered(builder.build(), pageable);
                    response = new FilteredUsersResponse(result.stream().map(UserMapper::toUserResponse).toList(),
                            result.getTotalPages());
                }
            }

        } catch (InvalidDataException | SemanticException | PathElementException | NullPointerException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, FilterMessages.INVALID_DATA);
        }

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
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @PostMapping("/{id}/block")
    public ResponseEntity<Void> blockUser(@PathVariable UUID id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getPrincipal();
        UUID administratorId = UUID.fromString(jwt.getSubject());

        if (administratorId.equals(id)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, AdministratorMessages.OWN_ADMINISTRATOR_BLOCK);
        }
        try {
            userService.blockUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UserAlreadyBlockedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PostMapping("/{id}/unblock")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> unblockUser(@PathVariable UUID id) {
        try {
            userService.unblockUser(id);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (UserAlreadyUnblockedException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @PutMapping("/{id}/update-data")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<UserResponse> updateUserData(@PathVariable UUID id,
                                                       @RequestBody @Valid UpdateUserDataRequest request,
                                                       @RequestHeader(HttpHeaders.IF_MATCH) String tagValue
    ) {
        try {
            User user = userService.updateUserData(id, UserMapper.toUser(request), tagValue);
            return ResponseEntity.ok(UserMapper.toUserResponse(user));
        } catch (NotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } catch (ApplicationOptimisticLockException e) {
            throw new ResponseStatusException(HttpStatus.PRECONDITION_FAILED, e.getMessage());
        }
    }

    @PostMapping("/{id}/email-update-request")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<Void> sendUpdateEmail(@PathVariable UUID id) {
        try {
            userService.sendEmailUpdateEmail(id);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PatchMapping("/update-email")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> updateUserEmail(@RequestBody @Valid UserEmailUpdateRequest request) {
        try {
            userService.changeUserEmail(request.token(), request.email());
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (VerificationTokenUsedException | NotFoundException | VerificationTokenExpiredException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    @PreAuthorize("permitAll()")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordRequest request) {
        try {
            userService.sendChangePasswordEmail(request.email());
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (TokenGenerationException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, VerificationTokenMessages.TOKEN_GENERATION_FAILED);
        } catch (UserBlockedException | UserNotVerifiedException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        }
    }
}
