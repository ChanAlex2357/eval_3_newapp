import { fetchData, build_api_url } from "./apiService.js";

export async function fetchDashboardData(queryParams){
    const apiUrl="/api/dashboard";
    const url = build_api_url(apiUrl,queryParams);
    const data = await fetchData(url.toString(),{
        method:'GET'
    })
    return data;
}
