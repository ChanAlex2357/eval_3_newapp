import { build_api_url, fetchData } from "./apiService";
import { formatDate, formatCurrency } from "./utils";

export async function fetchSalaries( queryParams = {}){
    const url = build_api_url("/api/salaries", queryParams)
    const data = await fetchData(url.toString(),{
        method:'GET'
    })
    return data;
}

export function renderSalaries(salaries, tableBodySelector = "#salaryTable tbody") {
    const tbody = document.querySelector(tableBodySelector);
    tbody.innerHTML = ""; // reset

    salaries.forEach((salary, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${salary.name}</td>
            <td>${formatDate(new Date(salary.start_date).toLocaleDateString())}</td>
            <td>${formatDate(new Date(salary.end_date).toLocaleDateString())}</td>
            <td>${salary.salary_structure}</td>
            <td>${formatCurrency(salary.gross_pay.toFixed(2))}</td>
            <td>${formatCurrency(salary.net_pay.toFixed(2))}</td>
        `;
        tbody.appendChild(row);
    });
}
