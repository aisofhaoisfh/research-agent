import { useState } from 'react';
import { Sidebar } from './Sidebar';
import { MainArea } from './MainArea';

interface LayoutProps {
  children: React.ReactNode;
  currentView: string;
  onViewChange: (view: string) => void;
}

const VIEW_TITLES: Record<string, { title: string; subtitle?: string }> = {
  home: { title: '首页', subtitle: '操作指引' },
  chat: { title: '对话', subtitle: '智能助手' },
  'trustee-calendar': { title: '托管日历', subtitle: 'AI 托管管理' },
  dashboard: { title: 'AI门店经营', subtitle: '经营分析' },
  inspection: { title: 'AI门店巡检', subtitle: '巡检任务' },
  'logistics-dispatch': { title: 'AI物流调度', subtitle: '运力调度' },
  market: { title: '市调选品分析', subtitle: '市场分析' },
  contract: { title: '合同智检', subtitle: '合同检查' },
};

export function Layout({ children, currentView, onViewChange }: LayoutProps) {
  const [historyItems, setHistoryItems] = useState<Array<{ id: string; title: string }>>([
    { id: '1', title: '本月销售凭证与金蝶差异' },
    { id: '2', title: '下午客流少的原因分析' },
  ]);

  const handleCreateChat = () => {
    onViewChange('chat');
  };

  const handleHistoryClick = (id: string) => {
    onViewChange('chat');
    // TODO: 加载对应会话
  };

  const handleHistoryDelete = (id: string) => {
    setHistoryItems((prev) => prev.filter((item) => item.id !== id));
  };

  const viewInfo = VIEW_TITLES[currentView] || { title: currentView };

  return (
    <div className="app">
      <Sidebar
        currentView={currentView}
        onViewChange={onViewChange}
        historyItems={historyItems}
        onHistoryClick={handleHistoryClick}
        onHistoryDelete={handleHistoryDelete}
        onCreateChat={handleCreateChat}
      />
      <MainArea
        title={viewInfo.title}
        subtitle={viewInfo.subtitle}
        onCreateChat={handleCreateChat}
      >
        {children}
      </MainArea>
    </div>
  );
}
