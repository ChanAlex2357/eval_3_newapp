export function build_api_url(apiUrl,queryParams = {}){
    const url = new URL(apiUrl, window.location.origin)
    addUrlPararms(url,queryParams)
    return url;
}

export function addUrlPararms(url, params){
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
