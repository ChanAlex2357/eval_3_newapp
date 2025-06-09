import { build_api_url, fetchData } from "./apiService";

export async function fetchAssignments(queryParams = {}){
    const apiUrl = '/api/assignments';
    const url = build_api_url(apiUrl, queryParams);
    return await fetchData(url.toString(),{
        method:'GET'
    })
}
