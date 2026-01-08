import React from 'react';
import styled from 'styled-components';
import { StatusSummary, GlobalStatus } from '../../types/SystemStatus';

const HeaderContainer = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background-color: #1e1e1e;
  color: #fff;
  border-bottom: 1px solid #333;
`;

const Title = styled.h2`
  margin: 0;
  font-size: 1.2rem;
  display: flex;
  align-items: center;
  gap: 10px;
`;

const StatusBadge = styled.span<{ status: GlobalStatus }>`
  width: 12px;
  height: 12px;
  border-radius: 50%;
  background-color: ${({ status }) => {
    switch (status) {
      case GlobalStatus.ONLINE: return '#4caf50';
      case GlobalStatus.PARTIAL: return '#ff9800';
      case GlobalStatus.OFFLINE: return '#f44336';
      default: return '#999';
    }
  }};
`;

const Counters = styled.div`
  display: flex;
  gap: 20px;
`;

const CounterItem = styled.div`
  font-size: 0.9rem;
  
  span {
    font-weight: bold;
    margin-left: 5px;
  }
`;

const RefreshButton = styled.button`
  background-color: #333;
  color: #fff;
  border: 1px solid #555;
  border-radius: 4px;
  padding: 5px 10px;
  cursor: pointer;
  transition: all 0.2s;
  
  &:hover {
    background-color: #444;
  }
  
  &:disabled {
    opacity: 0.5;
    cursor: wait;
  }
`;

interface Props {
  summary: StatusSummary | undefined;
  isLoading: boolean;
  onRefreshAll?: () => void;
  isRefreshing?: boolean;
}

export const StatusHeader: React.FC<Props> = ({ summary, isLoading, onRefreshAll, isRefreshing }) => {
  if (isLoading || !summary) {
    return (
      <HeaderContainer>
         <Title>System Status</Title>
         <Counters>Loading...</Counters>
      </HeaderContainer>
    );
  }

  return (
    <HeaderContainer>
      <Title>
        System Status
        <StatusBadge status={summary.globalStatus} />
      </Title>
      <div style={{ display: 'flex', gap: '20px', alignItems: 'center' }}>
          <Counters>
            <CounterItem>Total: <span>{summary.totalSystems}</span></CounterItem>
            <CounterItem style={{ color: '#4caf50' }}>Online: <span>{summary.onlineCount}</span></CounterItem>
            <CounterItem style={{ color: '#f44336' }}>Offline: <span>{summary.offlineCount}</span></CounterItem>
          </Counters>
          {onRefreshAll && (
              <RefreshButton onClick={onRefreshAll} disabled={isRefreshing}>
                 {isRefreshing ? 'Refreshing...' : 'Refresh All'}
              </RefreshButton>
          )}
      </div>
    </HeaderContainer>
  );
};
