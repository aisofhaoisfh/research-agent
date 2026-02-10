import { useState } from 'react';
import { Sidebar } from './Sidebar';
import { MainArea } from './MainArea';
import { useSessions } from '../../hooks/useSessions';

interface LayoutProps {
  children: React.ReactNode;
  currentView: string;
  onViewChange: (view: string) => void;
  currentSessionId?: string;
  onSessionChange?: (sessionId: string | undefined) => void;
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

export function Layout({ 
  children, 
  currentView, 
  onViewChange,
  currentSessionId,
  onSessionChange,
}: LayoutProps) {
  const { sessions, createNewSession, removeSession } = useSessions();

  const handleCreateChat = async () => {
    try {
      const newSession = await createNewSession();
      onSessionChange?.(newSession.id);
      onViewChange('chat');
    } catch (error) {
      console.error('创建会话失败:', error);
      // 即使创建失败，也切换到对话视图
      onViewChange('chat');
    }
  };

  const handleHistoryClick = (id: string) => {
    onSessionChange?.(id);
    onViewChange('chat');
  };

  const handleHistoryDelete = async (id: string) => {
    try {
      await removeSession(id);
      if (currentSessionId === id) {
        onSessionChange?.(undefined);
      }
    } catch (error) {
      console.error('删除会话失败:', error);
    }
  };

  const viewInfo = VIEW_TITLES[currentView] || { title: currentView };

  // 将会话转换为历史项格式
  const historyItems = sessions.map((s) => ({
    id: s.id,
    title: s.title,
  }));

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
