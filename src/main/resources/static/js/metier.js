export async function fetchEmployees(apiUrl = '/api/employees') {
    try {
        const response = await fetch(apiUrl);
        const body = await response.json();
        return body.data || [];
    } catch (error) {
        console.error('Erreur lors de la récupération des employés :', error);
        return [];
    }
}

export function renderEmployeeTable(data, tableId = 'empTable') {
    const table = document.getElementById(tableId);
    if (!table) return;

    table.innerHTML = ""; // optionnel : reset le tableau

    data.forEach(employee => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>
                <a href='/hr/employees/${employee.name}'>${employee.name}</a>
            </td>
            <td>${employee.first_name}</td>
            <td>${employee.gender}</td>
            <td>${new Date(employee.date_of_joining).toLocaleDateString()}</td>
            <td>${employee.designation}</td>
            <td>
                <a href="/hr/employees/${employee.id}/edit" class="btn btn-primary">Edit</a>
                <a href="/hr/employees/${employee.id}/delete" class="btn btn-danger">Delete</a>
            </td>
        `;
        table.appendChild(row);
    });
}
