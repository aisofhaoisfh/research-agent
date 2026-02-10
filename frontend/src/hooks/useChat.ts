import { useState, useCallback } from 'react';
import { streamChat, SSEEvent } from '../api/chat';

export interface Message {
  id: string;
  role: 'user' | 'assistant';
  content: string;
  timestamp: Date;
}

export function useChat() {
  const [messages, setMessages] = useState<Message[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const sendMessage = useCallback(async (content: string, sessionId?: string) => {
    if (!content.trim() || isLoading) return;

    // 添加用户消息
    const userMessage: Message = {
      id: Date.now().toString(),
      role: 'user',
      content: content.trim(),
      timestamp: new Date(),
    };
    setMessages((prev) => [...prev, userMessage]);

    // 创建 AI 消息占位符
    const aiMessageId = (Date.now() + 1).toString();
    const aiMessage: Message = {
      id: aiMessageId,
      role: 'assistant',
      content: '',
      timestamp: new Date(),
    };
    setMessages((prev) => [...prev, aiMessage]);

    setIsLoading(true);
    setError(null);

    try {
      let fullContent = '';

      for await (const event of streamChat({ message: content, sessionId })) {
        if (event.type === 'message') {
          try {
            // 尝试解析 JSON（可能是转义的字符串）
            let text = event.data;
            if (text.startsWith('"') && text.endsWith('"')) {
              text = JSON.parse(text);
            }
            fullContent = text;
            setMessages((prev) =>
              prev.map((msg) =>
                msg.id === aiMessageId ? { ...msg, content: fullContent } : msg
              )
            );
          } catch (e) {
            // 如果不是 JSON，直接使用原始数据
            fullContent += event.data;
            setMessages((prev) =>
              prev.map((msg) =>
                msg.id === aiMessageId ? { ...msg, content: fullContent } : msg
              )
            );
          }
        } else if (event.type === 'error') {
          try {
            const errorData = JSON.parse(event.data);
            setError(errorData.message || '发生错误');
          } catch {
            setError(event.data || '发生错误');
          }
          // 移除 AI 消息占位符
          setMessages((prev) => prev.filter((msg) => msg.id !== aiMessageId));
        } else if (event.type === 'done') {
          break;
        }
      }
    } catch (err) {
      setError(err instanceof Error ? err.message : '发送消息失败');
      // 移除 AI 消息占位符
      setMessages((prev) => prev.filter((msg) => msg.id !== aiMessageId));
    } finally {
      setIsLoading(false);
    }
  }, [isLoading]);

  const clearMessages = useCallback(() => {
    setMessages([]);
    setError(null);
  }, []);

  return {
    messages,
    isLoading,
    error,
    sendMessage,
    clearMessages,
  };
}
