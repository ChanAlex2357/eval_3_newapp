export async function fetchEmployees(queryParams = {}) {
    console.log("fetchEmployees called with params:", queryParams);
    
    const apiUrl = '/api/employees';
    const url = new URL(apiUrl,window.location.origin);
    addUrlPararms(url, queryParams);

    const data = await fetchData(url.toString(),{
        method:'GET'
    })
    return data;

}

export function getRowSpinner(colspan = 10, label = "Chargement...", type="border") {
    return `
        <tr>
            <td colspan="${colspan}" class="text-center">
                ${getSpinner(label, type)}
            </td>
        </tr>`;
}

export function getSpinner(label = "Chargement...", type="border") {
    const htmlSpin = `
        <div class="spinner-container">
            <div class="spinner-${type}  text-primary" role="status">
                <span class="visually-hidden">Chargement...</span>
            </div>
            <div class="mt-2 text-muted">${label}</div>
        </div>`;
    return htmlSpin;
}

export function formatCurrency(amount) {
    if (!amount) return 'Ar 0';
    return new Intl.NumberFormat('mg-MG', {
        style: 'currency',
        currency: 'MGA'
    }).format(amount);
}

export function formatDate(dateString) {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-EN');
}


export async function fetchSalaries( queryParams = {}){
    const apiUrl="/api/salaries";
    const url = new URL(apiUrl, window.location.origin);
    addUrlPararms(url, queryParams);

    const data = await fetchData(url.toString(),{
        method:'GET'
    })
    return data;
}

function addUrlPararms(url, params){
    Object.entries(params).forEach(([key,value]) => {
        if(value !== ""){
            url.searchParams.append(key, value)
        }
    });
}

export async function fetchData(apiUrl, params={}){
    try {
        const response = await fetch(apiUrl, params);
        const body = await response.json();
        return body.data || [];
    } catch (error) {
        console.error(`Erreur lors de la récupération des donnes depuis ${apiUrl} : ${error}`);
        return [];
    }
}

export function renderSalaries(salaries, tableBodySelector = "#salaryTable tbody") {
    const tbody = document.querySelector(tableBodySelector);
    tbody.innerHTML = ""; // reset

    salaries.forEach((salary, index) => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${salary.name}</td>
            <td>${new Date(salary.start_date).toLocaleDateString()}</td>
            <td>${new Date(salary.end_date).toLocaleDateString()}</td>
            <td>${salary.salary_structure}</td>
            <td>${salary.gross_pay.toFixed(2)}</td>
            <td>${salary.net_pay.toFixed(2)}</td>
        `;
        tbody.appendChild(row);
    });
}

export function renderEmployeeTable(data, tableId = 'empTable') {
    const table = document.getElementById(tableId);
    if (!table) return;

    table.innerHTML = ""; // optionnel : reset le tableau

    console.log(data);
    
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
            <td>${new Date(employee.date_of_joining).toLocaleDateString()}</td>
            <td>${employee.company}</td>
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
    const d = document.getElementById(id)

    // Afficher le spinner
    label.classList.add("d-none");
    spinner.classList.replace("d-none", "d-inline");

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
            label.classList.replace("d-none", "d-inline");
            spinner.classList.replace("d-inline", "d-none");
        });
}

