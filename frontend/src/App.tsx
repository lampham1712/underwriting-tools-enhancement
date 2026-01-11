import React from 'react';
import { QueryClient, QueryClientProvider, MutationCache, QueryCache } from '@tanstack/react-query';
import { Dashboard } from './pages/Dashboard';

const queryClient = new QueryClient({
  queryCache: new QueryCache({
    onError: (error) => {
      console.error('Global Query Error:', error);
      // specific error handling logic here
    },
  }),
  mutationCache: new MutationCache({
    onError: (error) => {
      console.error('Global Mutation Error:', error);
      // specific error handling logic here
    },
  }),
});

function App() {
  return (
    <QueryClientProvider client={queryClient}>
      <Dashboard />
    </QueryClientProvider>
  );
}

export default App;
