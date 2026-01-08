import { StatusSummary, SystemStatus } from '../types/SystemStatus';

const API_BASE_URL = 'http://localhost:8080/api/system-status';

export const SystemStatusService = {
    getSummary: async (): Promise<StatusSummary> => {
        const response = await fetch(`${API_BASE_URL}/summary`);
        if (!response.ok) {
            throw new Error('Failed to fetch system status summary');
        }
        return response.json();
    },

    getAll: async (): Promise<SystemStatus[]> => {
        const response = await fetch(`${API_BASE_URL}`);
        if (!response.ok) {
            throw new Error('Failed to fetch system statuses');
        }
        return response.json();
    },

    refreshSystem: async (code: string): Promise<SystemStatus> => {
        const response = await fetch(`${API_BASE_URL}/refresh/${code}`, {
            method: 'POST'
        });
        if (!response.ok) {
            throw new Error(`Failed to refresh system ${code}`);
        }
        return response.json();
    },

    refreshAll: async (): Promise<void> => {
        const response = await fetch(`${API_BASE_URL}/refresh-all`, {
            method: 'POST'
        });
        if (!response.ok) {
             throw new Error('Failed to refresh all systems');
        }
    }
};
