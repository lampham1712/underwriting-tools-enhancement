export class ApiError extends Error {
    constructor(public status: number, message: string) {
        super(message);
    }
}

const BASE_URL = 'http://localhost:8080/api';

export const api = {
    get: async <T>(endpoint: string): Promise<T> => {
        const response = await fetch(`${BASE_URL}${endpoint}`);
        if (!response.ok) {
            throw new ApiError(response.status, `GET ${endpoint} failed: ${response.statusText}`);
        }
        return response.json();
    },
    post: async <T>(endpoint: string, body?: any): Promise<T> => {
        const response = await fetch(`${BASE_URL}${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: body ? JSON.stringify(body) : undefined,
        });
        if (!response.ok) {
            throw new ApiError(response.status, `POST ${endpoint} failed: ${response.statusText}`);
        }
        // Check for 202 or 204 or empty content
        if (response.status === 204 || response.status === 202 || response.headers.get('content-length') === '0') {
             // Return empty object for void promises or similar
             return {} as T;
        }
        try {
            return await response.json();
        } catch {
             return {} as T;
        }
    }
};
