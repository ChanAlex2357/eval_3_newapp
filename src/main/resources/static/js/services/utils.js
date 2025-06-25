export function formatCurrency(amount) {
    if (!amount) return 'Ar 0';
    let formated_amount = new Intl.NumberFormat('fr-FR', {
        style: 'currency',
        currency: 'MGA',
    }).format(amount);

    return formated_amount
}

export function formatDate(dateString){
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleDateString('en-EN');
}

export function doDownloadPdf(blob, fileName){
    const blobUrl = URL.createObjectURL(blob);

    const a = document.createElement("a");
    a.href = blobUrl;
    a.download = fileName;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
    URL.revokeObjectURL(blobUrl);
}

export function downloadSalaryPdf(button, id) {
    const label = button.querySelector('.label');
    const spinner = button.querySelector('.spinner');

    const safeId = id.replaceAll("/", "__");
    const url = `/api/pdf/salary-slip/${safeId}`;

    // Afficher le spinner
    label.classList.add("d-none");
    spinner.classList.replace("d-none", "d-inline");

    fetch(url)
        .then(response => {
            console.log(response);
            
            if (!response.ok) throw new Error("Erreur lors de la récupération du PDF");
            return response.blob();
        })
        .then(blob => {
            doDownloadPdf(blob, `bulletin-${id.replaceAll("/", "_")}.pdf`);            
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


export function extractFormData(formData){
    const queryParams = {};
    formData.forEach((value, key) => {
        queryParams[key] = value;
    });
    return queryParams;
}


export function removeRow(button) {
    const row = button.closest('tr');
    const tbody = row.closest('tbody');
    const rows = tbody.querySelectorAll('tr');

    // Ne pas supprimer la dernière ligne
    if (rows.length > 1) {
        row.remove();
    } else {
        alert("Impossible de supprimer la dernière ligne.");
    }
}

export function dynamiseTableRow(adderId, rowTemplateId) {
    const rowTemplate = document.getElementById(rowTemplateId);
    const rowAdder = document.getElementById(adderId);

    if (!rowTemplate || !rowAdder) {
        console.warn("Element not found:", rowTemplateId, adderId);
        return;
    }

    const tbodySelector = rowAdder.getAttribute("data-table-body");
    const tbody = document.querySelector(tbodySelector);

    if (!tbody) {
        console.warn("Tbody not found with selector:", tbodySelector);
        return;
    }

    rowAdder.addEventListener("click", function (e) {
        e.preventDefault();

        const newRow = rowTemplate.cloneNode(true);
        newRow.removeAttribute("id"); // Remove ID to prevent duplicates

        // Reset values of inputs, selects, and textareas
        newRow.querySelectorAll("input, select, textarea").forEach(el => {
            if (el.tagName === "SELECT") {
                el.selectedIndex = 0;
            } else {
                el.value = "";
            }
        });

        // Optional: remove hidden classes if you're hiding the template
        newRow.classList.remove("d-none", "template");


        tbody.appendChild(newRow);
    });
}
