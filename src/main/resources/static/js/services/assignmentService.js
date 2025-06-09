import { build_api_url, fetchData } from "./apiService.js";
import {formatCurrency} from './utils.js'

export async function fetchAssignments(queryParams = {}){
    const apiUrl = '/api/assignments';
    const url = build_api_url(apiUrl, queryParams);
    return await fetchData(url.toString(),{
        method:'GET'
    })
}

export function renderAssignmentsTable(data, body_id){
    const historyBody = document.getElementById(body_id)
    historyBody.innerHTML = ""

    data.forEach( (assignment,index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td class="text-center">${assignment.from_date}</td>
            <td class="text-center">
                <span class="badge bg-dark col-4">
                    ${formatCurrency(assignment.base)}
                </span>
            </td>
        `;
        historyBody.appendChild(row);
    })

}
