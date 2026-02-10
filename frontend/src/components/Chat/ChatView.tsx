import { useState, useRef, useEffect } from 'react';
import { useChat, Message } from '../../hooks/useChat';
import { MessageList } from './MessageList';
import { ChatInput } from './ChatInput';
import '../../styles/chat.css';

export function ChatView() {
  const { messages, isLoading, error, sendMessage } = useChat();
  const messagesEndRef = useRef<HTMLDivElement>(null);

  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);

  const handleSend = (content: string) => {
    sendMessage(content);
  };

  const quickAsks = [
    '今天卖得怎么样？',
    '本月销售凭证与金蝶差异',
    '下午客流少的原因分析',
    '下周补货建议',
  ];

  return (
    <div className="chat-view">
      <div className="chat-messages">
        {messages.length === 0 && (
          <div style={{ textAlign: 'center', color: 'var(--text-muted)', padding: '40px' }}>
            <p>开始与智能助手对话吧</p>
            <div className="quick-ask" style={{ marginTop: '20px', justifyContent: 'center' }}>
              {quickAsks.map((ask, idx) => (
                <button
                  key={idx}
                  className="quick-ask-btn"
                  onClick={() => handleSend(ask)}
                >
                  {ask}
                </button>
              ))}
            </div>
          </div>
        )}
        <MessageList messages={messages} />
        {error && (
          <div style={{ color: 'var(--danger)', padding: '12px', background: 'var(--bg-card)', borderRadius: '8px' }}>
            错误: {error}
          </div>
        )}
        <div ref={messagesEndRef} />
      </div>
      <ChatInput onSend={handleSend} isLoading={isLoading} />
    </div>
  );
}
