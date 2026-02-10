import { useState } from 'react';
import { Layout } from './components/Layout/Layout';
import { HomeView } from './pages/HomeView';
import { ChatView } from './components/Chat/ChatView';
import './styles/variables.css';
import './styles/layout.css';

function App() {
  const [currentView, setCurrentView] = useState('home');

  const renderView = () => {
    switch (currentView) {
      case 'home':
        return <HomeView />;
      case 'chat':
        return <ChatView />;
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
      <Layout currentView={currentView} onViewChange={setCurrentView}>
        {renderView()}
      </Layout>
    </>
  );
}

export default App;
