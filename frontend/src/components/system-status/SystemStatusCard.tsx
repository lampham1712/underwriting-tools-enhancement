import React from 'react';
import styled from 'styled-components';
import { StatusSummary, SystemStatus } from '../../types/SystemStatus';
import { StatusHeader } from './StatusHeader';
import { MiniSystemCard } from './MiniSystemCard';

const Container = styled.div`
  background-color: #1e1e1e;
  border-radius: 8px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.3);
  max-width: 1000px;
  margin: 0 auto;
`;

const Grid = styled.div`
  display: grid;
  grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
  gap: 12px;
  padding: 16px;
`;

const EmptyState = styled.div`
  padding: 20px;
  text-align: center;
  color: #888;
`;

interface Props {
  summary: StatusSummary | undefined;
  systems: SystemStatus[] | undefined;
  isLoading: boolean;
  onRefreshSystem?: (code: string) => void;
  onRefreshAll?: () => void;
  refreshingSystemCode?: string | null;
  isRefreshingAll?: boolean;
}

export const SystemStatusCard: React.FC<Props> = ({ 
  summary, 
  systems, 
  isLoading, 
  onRefreshSystem,
  onRefreshAll,
  refreshingSystemCode,
  isRefreshingAll
}) => {
  return (
    <Container>
      <StatusHeader 
        summary={summary} 
        isLoading={isLoading} 
        onRefreshAll={onRefreshAll}
        isRefreshing={isRefreshingAll}
      />
      {isLoading ? (
         <EmptyState>Loading systems...</EmptyState>
      ) : (
        <Grid>
            {systems?.length === 0 && <EmptyState>No systems monitored.</EmptyState>}
            {systems?.map(system => (
                <MiniSystemCard 
                    key={system.systemCode} 
                    system={system} 
                    onRefresh={onRefreshSystem}
                    isRefreshing={refreshingSystemCode === system.systemCode}
                />
            ))}
        </Grid>
      )}
    </Container>
  );
};
