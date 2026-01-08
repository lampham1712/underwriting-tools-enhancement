import React from 'react';
import styled, { keyframes, css } from 'styled-components';
import { SystemStatus, Status } from '../../types/SystemStatus';

const spin = keyframes`
  from { transform: rotate(0deg); }
  to { transform: rotate(360deg); }
`;

const Card = styled.div<{ status: Status }>`
  background-color: ${({ status }) => status === Status.ONLINE ? '#1e2a22' : '#2a1e1e'};
  border: 1px solid ${({ status }) => status === Status.ONLINE ? '#2e7d32' : '#c62828'};
  border-radius: 4px;
  padding: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  min-width: 200px;
`;

const Name = styled.div`
  font-weight: 500;
  color: #fff;
`;

const StatusIndicator = styled.div<{ status: Status }>`
  width: 10px;
  height: 10px;
  border-radius: 50%;
  background-color: ${({ status }) => status === Status.ONLINE ? '#4caf50' : '#f44336'};
  box-shadow: 0 0 5px ${({ status }) => status === Status.ONLINE ? '#4caf50' : '#f44336'};
`;

const Timestamps = styled.div`
  font-size: 0.7rem;
  color: #aaa;
  text-align: right;
  margin-right: 10px;
`;

const RefreshIcon = styled.button<{ $isRefreshing?: boolean }>`
  background: none;
  border: none;
  color: #fff;
  cursor: pointer;
  opacity: 0.7;
  &:hover { opacity: 1; }
  ${({ $isRefreshing }) => $isRefreshing && css`
    animation: ${spin} 1s linear infinite;
    cursor: wait;
  `}
`;

interface Props {
  system: SystemStatus;
  onRefresh?: (code: string) => void;
  isRefreshing?: boolean;
}

export const MiniSystemCard: React.FC<Props> = ({ system, onRefresh, isRefreshing }) => {
  return (
    <Card status={system.status}>
      <StatusIndicator status={system.status} />
      <div style={{ flex: 1, marginLeft: '10px' }}>
        <Name>{system.displayName}</Name>
      </div>
      <Timestamps>
        <div>Last: {new Date(system.lastChecked).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })}</div>
      </Timestamps>
      <RefreshIcon 
        onClick={() => !isRefreshing && onRefresh && onRefresh(system.systemCode)}
        $isRefreshing={isRefreshing}
        disabled={isRefreshing}
      >
        &#x21bb;
      </RefreshIcon>
    </Card>
  );
};
