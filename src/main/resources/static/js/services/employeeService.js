import  {build_api_url, fetchData}  from "./apiService.js";
import  {formatDate } from "./utils.js";

export async function fetchEmployees(queryParams = {}) {
    const url = build_api_url('/api/employees',queryParams);
    const data = await fetchData(url.toString(),{
        method:'GET'
    })
    return data;

}

export function renderEmployeeTable(data, tableId = 'empTable') {
    const table = document.getElementById(tableId);
    if (!table) return;

    table.innerHTML = ""; // optionnel : reset le tableau
    
    if (!data.length) {
        table.innerHTML = `
            <tr>
                <td colspan="8">
                    <div class="d-flex justify-content-center">
                        <span class="m-5">Aucun emloyee trouvé</span>
                    </div>
                </td>
            </tr>
        `;
        return;
    }

    data.forEach(employee => {
        const row = document.createElement('tr');
        row.innerHTML = `
            <td>
                <a href='/hr/employees/${employee.name}'>${employee.name}</a>
            </td>
            <td>${employee.first_name} ${employee.last_name}</td>
            <td>${employee.gender}</td>
            <td>${formatDate(new Date(employee.date_of_joining).toLocaleDateString())}</td>
            <td>${employee.company}</td>
            `;
            // <td>${employee.designation}</td>
            // <td>
            //     <a href="/hr/employees/${employee.id}/edit" class="btn btn-primary">Edit</a>
            //     <a href="/hr/employees/${employee.id}/delete" class="btn btn-danger">Delete</a>
            // </td>
        table.appendChild(row);
    });
}
