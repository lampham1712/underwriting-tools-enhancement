import { StatusSummary, SystemStatus } from '../types/SystemStatus';
import { api } from './api';

const ENDPOINT = '/system-status';

export const SystemStatusService = {
    getSummary: async (): Promise<StatusSummary> => {
        return api.get<StatusSummary>(`${ENDPOINT}/summary`);
    },

    getAll: async (): Promise<SystemStatus[]> => {
        return api.get<SystemStatus[]>(`${ENDPOINT}`);
    },

    refreshSystem: async (code: string): Promise<SystemStatus> => {
        return api.post<SystemStatus>(`${ENDPOINT}/refresh/${code}`);
    },

    refreshAll: async (): Promise<void> => {
        return api.post<void>(`${ENDPOINT}/refresh-all`);
    }
};

