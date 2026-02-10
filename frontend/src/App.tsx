import { useState } from 'react';
import { Layout } from './components/Layout/Layout';
import { HomeView } from './pages/HomeView';
import { ChatView } from './components/Chat/ChatView';
import { TrusteeCalendarView } from './pages/TrusteeCalendarView';
import { DashboardView } from './pages/DashboardView';
import { InspectionView } from './pages/InspectionView';
import { LogisticsDispatchView } from './pages/LogisticsDispatchView';
import { MarketView } from './pages/MarketView';
import { ContractView } from './pages/ContractView';
import './styles/variables.css';
import './styles/layout.css';

function App() {
  const [currentView, setCurrentView] = useState('home');
  const [currentSessionId, setCurrentSessionId] = useState<string | undefined>();

  const renderView = () => {
    switch (currentView) {
      case 'home':
        return <HomeView />;
      case 'chat':
        return <ChatView sessionId={currentSessionId} />;
      case 'trustee-calendar':
        return <TrusteeCalendarView />;
      case 'dashboard':
        return <DashboardView />;
      case 'inspection':
        return <InspectionView />;
      case 'logistics-dispatch':
        return <LogisticsDispatchView />;
      case 'market':
        return <MarketView />;
      case 'contract':
        return <ContractView />;
      default:
        return (
          <div style={{ padding: '40px', textAlign: 'center', color: 'var(--text-muted)' }}>
            <p>{currentView} 页面开发中...</p>
          </div>
        );
    }
  };

  return (
    <>
      <div className="bg-grid" />
      <Layout 
        currentView={currentView} 
        onViewChange={setCurrentView}
        currentSessionId={currentSessionId}
        onSessionChange={setCurrentSessionId}
      >
        {renderView()}
      </Layout>
    </>
  );
}

export default App;
