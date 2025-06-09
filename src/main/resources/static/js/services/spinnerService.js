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