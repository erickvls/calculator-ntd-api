package ntd.calculator.api.services;

import ntd.calculator.api.models.account.Account;
import ntd.calculator.api.models.operation.Operation;
import ntd.calculator.api.models.record.Record;
import ntd.calculator.api.models.user.User;
import ntd.calculator.api.repositories.RecordRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecordServiceTest {

    @Mock
    private RecordRepository recordRepository;

    @InjectMocks
    private RecordService recordService;

    private User user;
    private Account account;
    private Operation operation;
    private BigDecimal cost;
    private BigDecimal result;
    private Record record;

    @BeforeEach
    void setUp() {
        user = new User();
        account = new Account();
        operation = new Operation();
        cost = new BigDecimal("100.00");
        result = new BigDecimal("150.00");
        account.setBalance(new BigDecimal("500.00"));
        record = Record.builder()
                .user(user)
                .amount(cost)
                .operation(operation)
                .userBalance(account.getBalance())
                .operationResponse(result.toString())
                .date(new Date())
                .build();
    }

    @Test
    void shouldSaveRecordCorrectly() {

        when(recordRepository.save(any(Record.class))).thenAnswer(invocation -> invocation.getArgument(0));

        var savedRecord = recordService.createRecord(user, account, operation, cost, result);

        assertEquals(user, savedRecord.getUser());
        assertEquals(cost, savedRecord.getAmount());
        assertEquals(operation, savedRecord.getOperation());
        assertEquals(account.getBalance(), savedRecord.getUserBalance());
        assertEquals(result.toString(), savedRecord.getOperationResponse());

        verify(recordRepository).save(savedRecord);
    }
}

