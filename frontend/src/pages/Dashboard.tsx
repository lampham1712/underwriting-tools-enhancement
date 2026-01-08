import React, { useState } from 'react';
import styled from 'styled-components';
import { useQuery, useMutation, useQueryClient } from '@tanstack/react-query';
import { SystemStatusService } from '../services/SystemStatusService';
import { SystemStatusCard } from '../components/system-status/SystemStatusCard';

const DashboardContainer = styled.div`
  padding: 20px;
  background-color: #121212;
  min-height: 100vh;
  color: #fff;
`;

export const Dashboard: React.FC = () => {
    const queryClient = useQueryClient();
    const [refreshingCode, setRefreshingCode] = useState<string | null>(null);

    const { data: summary, isLoading: isLoadingSummary } = useQuery({
        queryKey: ['systemStatusSummary'],
        queryFn: SystemStatusService.getSummary,
        refetchInterval: 10 * 60 * 1000 // 10 minutes
    });

    const { data: systems, isLoading: isLoadingSystems } = useQuery({
        queryKey: ['systemStatusList'],
        queryFn: SystemStatusService.getAll,
        refetchInterval: 10 * 60 * 1000 // 10 minutes
    });

    const refreshSystemMutation = useMutation({
        mutationFn: SystemStatusService.refreshSystem,
        onMutate: (code) => setRefreshingCode(code),
        onSettled: () => setRefreshingCode(null),
        onSuccess: () => {
             queryClient.invalidateQueries({ queryKey: ['systemStatusSummary'] });
             queryClient.invalidateQueries({ queryKey: ['systemStatusList'] });
        }
    });

    const refreshAllMutation = useMutation({
        mutationFn: SystemStatusService.refreshAll,
        onSuccess: () => {
             // Since refreshAll is async (202 Accepted), immediate invalidation might not show updates.
             // But we can Invalidate anyway to clear old data or trigger refetch.
             // Ideally we might want to poll for a bit or wait. For now, simple invalidation.
             queryClient.invalidateQueries({ queryKey: ['systemStatusSummary'] });
             queryClient.invalidateQueries({ queryKey: ['systemStatusList'] });
             
             // Optional: Refetch after a small delay to allow async jobs to process
             setTimeout(() => {
                queryClient.invalidateQueries({ queryKey: ['systemStatusSummary'] });
                queryClient.invalidateQueries({ queryKey: ['systemStatusList'] });
             }, 2000);
        }
    });

    const handleRefresh = (code: string) => {
        refreshSystemMutation.mutate(code);
    };

    const handleRefreshAll = () => {
        refreshAllMutation.mutate();
    };

    return (
        <DashboardContainer>
            <h1>Operations Dashboard</h1>
            <SystemStatusCard 
                summary={summary}
                systems={systems}
                isLoading={isLoadingSummary || isLoadingSystems}
                onRefreshSystem={handleRefresh}
                onRefreshAll={handleRefreshAll}
                refreshingSystemCode={refreshingCode}
                isRefreshingAll={refreshAllMutation.isPending}
            />
        </DashboardContainer>
    );
};
