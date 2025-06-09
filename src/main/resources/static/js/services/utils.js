export function formatCurrency(amount) {
    if (!amount) return 'Ar 0';
    return new Intl.NumberFormat('mg-MG', {
        style: 'currency',
        currency: 'MGA'
    }).format(amount);
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