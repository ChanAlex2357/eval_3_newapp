package itu.eval3.newapp.client.services.hr.leave;

import itu.eval3.newapp.client.config.ApiConfig;
import itu.eval3.newapp.client.exceptions.ERPNexException;
import itu.eval3.newapp.client.models.hr.leave.LeaveBalanceDTO;
import itu.eval3.newapp.client.models.hr.leave.LeaveLedgerEntry;
import itu.eval3.newapp.client.models.user.UserErpNext;
import itu.eval3.newapp.client.services.frappe.FrappeCrudService;
import itu.eval3.newapp.client.utils.uri.filters.EqualsFilter;
import itu.eval3.newapp.client.utils.uri.filters.FrappeFilterComponent;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LeaveLedgerService extends FrappeCrudService<LeaveLedgerEntry> {

    /**
     * Regroupe les entrées de congés par type, et calcule le solde utilisé, alloué et restant.
     */
    public List<LeaveBalanceDTO> getLeaveBalances(UserErpNext user, String employeeId) throws ERPNexException {
        List<LeaveLedgerEntry> entries = getEntriesForEmployee(user, employeeId);

        // Grouper par type
        Map<String, List<LeaveLedgerEntry>> groupedByType = entries.stream()
            .collect(Collectors.groupingBy(LeaveLedgerEntry::getLeaveType));

        List<LeaveBalanceDTO> balances = new ArrayList<>();

        for (Map.Entry<String, List<LeaveLedgerEntry>> entry : groupedByType.entrySet()) {
            String type = entry.getKey();
            List<LeaveLedgerEntry> logs = entry.getValue();

            double allocated = logs.stream()
                .filter(e -> "Leave Allocation".equalsIgnoreCase(e.getTransactionType()))
                .mapToDouble(e -> Optional.ofNullable(e.getLeaves()).orElse(0.0))
                .sum();

            double used = logs.stream()
                .filter(e -> "Leave Application".equalsIgnoreCase(e.getTransactionType()))
                .mapToDouble(e -> Optional.ofNullable(e.getLeaves()).orElse(0.0))
                .sum();

            balances.add(new LeaveBalanceDTO(
                type,
                allocated,
                used,
                allocated - used
            ));
        }

        return balances;
    }

    public List<LeaveLedgerEntry> getEntriesForEmployee(UserErpNext user, String employeeId) throws ERPNexException {
        return getEntriesForEmployee(user, employeeId,null);
    }
    public List<LeaveLedgerEntry> getEntriesForEmployee(UserErpNext user, String employeeId, String leaveType) throws ERPNexException {
        FrappeFilterComponent filter = new FrappeFilterComponent();
        filter.addFilter(new EqualsFilter("employee", employeeId));
        if (leaveType != null) {
            filter.addFilter(new EqualsFilter("leave_type", leaveType));
        }
        return getAllDocuments(user, new LeaveLedgerEntry(), LeaveLedgerEntry.class, ApiConfig.ALL_FIELDS, filter, null, null);
    }
}
