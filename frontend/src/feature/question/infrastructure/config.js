export const config = {
    apiUrl: import.meta.env.VITE_API_URL || "/api/v1",
    timeout: import.meta.env.VITE_TIMEOUT || 60000,
    isDev: import.meta.env.MODE === 'development',
}
