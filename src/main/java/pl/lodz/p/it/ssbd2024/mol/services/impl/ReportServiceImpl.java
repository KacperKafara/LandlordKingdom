package pl.lodz.p.it.ssbd2024.mol.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import pl.lodz.p.it.ssbd2024.exceptions.NotFoundException;
import pl.lodz.p.it.ssbd2024.exceptions.handlers.ErrorCodes;
import pl.lodz.p.it.ssbd2024.messages.LocalExceptionMessages;
import pl.lodz.p.it.ssbd2024.model.*;
import pl.lodz.p.it.ssbd2024.mol.dto.AllLocalsReport;
import pl.lodz.p.it.ssbd2024.mol.dto.LocalReport;
import pl.lodz.p.it.ssbd2024.mol.repositories.*;
import pl.lodz.p.it.ssbd2024.mol.services.ReportService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ReportServiceImpl implements ReportService {
    private final PaymentRepository paymentRepository;
    private final LocalRepository localRepository;
    private final VariableFeeRepository variableFeeRepository;
    private final FixedFeeRepository fixedFeeRepository;
    private final RentRepository rentRepository;

    @Override
    public LocalReport getLocalReport(UUID localId, UUID userId, LocalDate startDate, LocalDate endDate)
            throws NotFoundException {
        Local local = localRepository.findByOwner_User_IdAndId(userId, localId)
                .orElseThrow(() -> new NotFoundException(LocalExceptionMessages.LOCAL_NOT_FOUND, ErrorCodes.LOCAL_NOT_FOUND));

        List<Payment> payments = paymentRepository.findByLocalIdAndUserId(localId, userId);
        List<VariableFee> variableFees = variableFeeRepository.findByLocalIdAndUserId(localId, userId);
        List<FixedFee> fixedFees = fixedFeeRepository.findByLocalIdAndUserId(localId, userId);
        long longestRentDays = rentRepository.getLongestRentByLocalId(localId, userId)
                .map(longestRent -> (longestRent.getEndDate().toEpochDay() - longestRent.getStartDate().toEpochDay()))
                .orElse(0L);

        long shortestRentDays = rentRepository.getShortestRentByLocalId(localId, userId)
                .map(shortestRent -> (shortestRent.getEndDate().toEpochDay() - shortestRent.getStartDate().toEpochDay()))
                .orElse(0L);

        int rentCount = rentRepository.countRentsByUserIdAndLocalId(localId, userId);

        return new LocalReport(local, payments, variableFees, fixedFees, rentCount, longestRentDays, shortestRentDays);
    }

    @Override
    public AllLocalsReport getReport(UUID userId) {
        List<Rent> rents = rentRepository.findAllByOwnerId(userId);
        List<VariableFee> variableFees = variableFeeRepository.findAllByOwnerId(userId);
        List<FixedFee> fixedFees = fixedFeeRepository.findAllByOwnerId(userId);
        List<Payment> payments = paymentRepository.findAllByOwnerId(userId);

        return new AllLocalsReport(rents, variableFees, fixedFees, payments);
    }
}
