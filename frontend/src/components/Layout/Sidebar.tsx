import { useState } from 'react';
import '../Layout/Sidebar.css';

interface SidebarProps {
  currentView: string;
  onViewChange: (view: string) => void;
  historyItems: Array<{ id: string; title: string }>;
  onHistoryClick: (id: string) => void;
  onHistoryDelete: (id: string) => void;
  onCreateChat: () => void;
}

export function Sidebar({
  currentView,
  onViewChange,
  historyItems,
  onHistoryClick,
  onHistoryDelete,
  onCreateChat,
}: SidebarProps) {
  const [autoExpanded, setAutoExpanded] = useState(false);
  const [toolExpanded, setToolExpanded] = useState(false);

  return (
    <aside className="sidebar">
      <div className="sidebar-logo">
        <div className="logo-icon">营</div>
        <div className="logo-text">
          <h1>营营无忧</h1>
          <span>精准运作让经营变得更加容易</span>
        </div>
      </div>
      <nav className="sidebar-nav">
        <div
          className={`nav-item ${currentView === 'home' ? 'active' : ''}`}
          onClick={() => onViewChange('home')}
        >
          <span className="icon">🏠</span>
          <span>首页</span>
        </div>

        <div className="nav-label" onClick={() => setAutoExpanded(!autoExpanded)}>
          <span>自动化</span>
          <span className="chevron">{autoExpanded ? '▼' : '▶'}</span>
        </div>
        {autoExpanded && (
          <div className="nav-section-content">
            <div
              className={`nav-item nav-item-sub ${currentView === 'trustee-calendar' ? 'active' : ''}`}
              onClick={() => onViewChange('trustee-calendar')}
            >
              <span className="icon">📅</span>
              <span>托管日历</span>
            </div>
            <div className="nav-sublabel">门店自动化</div>
            <div
              className={`nav-item nav-item-sub ${currentView === 'dashboard' ? 'active' : ''}`}
              onClick={() => onViewChange('dashboard')}
            >
              <span className="icon">⚙️</span>
              <span>AI门店经营</span>
            </div>
            <div
              className={`nav-item nav-item-sub ${currentView === 'inspection' ? 'active' : ''}`}
              onClick={() => onViewChange('inspection')}
            >
              <span className="icon">🔍</span>
              <span>AI门店巡检</span>
            </div>
            <div className="nav-sublabel">运营自动化</div>
            <div
              className={`nav-item nav-item-sub ${currentView === 'logistics-dispatch' ? 'active' : ''}`}
              onClick={() => onViewChange('logistics-dispatch')}
            >
              <span className="icon">🚚</span>
              <span>AI物流调度</span>
            </div>
          </div>
        )}

        <div className="nav-label" onClick={() => setToolExpanded(!toolExpanded)}>
          <span>工具栏</span>
          <span className="chevron">{toolExpanded ? '▼' : '▶'}</span>
        </div>
        {toolExpanded && (
          <div className="nav-section-content">
            <div
              className={`nav-item ${currentView === 'market' ? 'active' : ''}`}
              onClick={() => onViewChange('market')}
            >
              <span className="icon">🛒</span>
              <span>市调选品分析</span>
            </div>
            <div
              className={`nav-item ${currentView === 'contract' ? 'active' : ''}`}
              onClick={() => onViewChange('contract')}
            >
              <span className="icon">📋</span>
              <span>合同智检</span>
            </div>
          </div>
        )}

        <div className="nav-label">对话历史</div>
        {historyItems.map((item) => (
          <div
            key={item.id}
            className="history-item"
            onClick={() => onHistoryClick(item.id)}
          >
            <span className="history-item-text">{item.title}</span>
            <button
              className="history-item-delete"
              onClick={(e) => {
                e.stopPropagation();
                onHistoryDelete(item.id);
              }}
              title="删除"
            >
              ×
            </button>
          </div>
        ))}
      </nav>
      <div className="sidebar-user">
        <div className="user-avatar">张</div>
        <div className="user-info">
          <div className="name">张三</div>
          <div className="role">运营部 · 已登录</div>
        </div>
      </div>
    </aside>
  );
}
