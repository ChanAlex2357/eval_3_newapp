export async function fetchEmployees(apiUrl = '/api/employees', queryParams = {}) {
    const url = new URL(apiUrl,window.location.origin);
    
    Object.entries(queryParams).forEach(([key, value]) => {
        if (value !== "") {
            url.searchParams.append(key, value);
        }
    });
    const data = fetchData(url.toString(),{
        method:'GET'
    })
    return data;

}

export async function fetchData(apiUrl, params={},fail){
    try {
        const response = await fetch(apiUrl, {
            params
        });
        const body = await response.json();
        return body.data || [];
    } catch (error) {
        console.error(`Erreur lors de la récupération des donnes depuis ${apiUrl} : ${error}`);
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
            <td>${employee.first_name} ${employee.last_name}</td>
            <td>${employee.gender}</td>
            <td>${new Date(employee.date_of_joining).toLocaleDateString()}</td>
            <td>${employee.designation}</td>
            `;
            // <td>
            //     <a href="/hr/employees/${employee.id}/edit" class="btn btn-primary">Edit</a>
            //     <a href="/hr/employees/${employee.id}/delete" class="btn btn-danger">Delete</a>
            // </td>
        table.appendChild(row);
    });
}

export function downloadSalaryPdf(button, id) {
    const label = button.querySelector('.label');
    const spinner = button.querySelector('.spinner');

    const safeId = id.replaceAll("/", "__");
    const url = `/api/pdf/salary-slip/${safeId}`;

    // Afficher le spinner
    label.style.display = "none";
    spinner.style.display = "inline";

    fetch(url)
        .then(response => {
            if (!response.ok) throw new Error("Erreur lors de la récupération du PDF");
            return response.blob();
        })
        .then(blob => {
            const fileName = `bulletin-${id.replaceAll("/", "_")}.pdf`;
            const blobUrl = URL.createObjectURL(blob);

            const a = document.createElement("a");
            a.href = blobUrl;
            a.download = fileName;
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            URL.revokeObjectURL(blobUrl);
        })
        .catch(err => {
            console.error("Erreur PDF :", err);
            alert("Impossible de générer le PDF.");
        })
        .finally(() => {
            // Restaurer le bouton
            label.style.display = "inline";
            spinner.style.display = "none";
        });
}

