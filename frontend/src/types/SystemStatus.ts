export enum Status {
    ONLINE = 'ONLINE',
    OFFLINE = 'OFFLINE',
    UNKNOWN = 'UNKNOWN',
    MAINTENANCE = 'MAINTENANCE'
}

export enum GlobalStatus {
    ONLINE = 'ONLINE',
    PARTIAL = 'PARTIAL',
    OFFLINE = 'OFFLINE'
}

export interface StatusSummary {
    totalSystems: number;
    onlineCount: number;
    offlineCount: number;
    globalStatus: GlobalStatus;
}

export interface SystemStatus {
    id: string;
    systemCode: string;
    displayName: string;
    status: Status;
    lastChecked: string;
    nextCheck: string;
    errorMessage: string | null;
    isCritical: boolean;
    updatedAt: string;
}
