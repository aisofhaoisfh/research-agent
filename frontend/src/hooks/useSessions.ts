import { useState, useEffect, useCallback } from 'react';
import { Session, createSession, listSessions, deleteSession } from '../api/session';

export function useSessions() {
  const [sessions, setSessions] = useState<Session[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const loadSessions = useCallback(async () => {
    setIsLoading(true);
    setError(null);
    try {
      const data = await listSessions();
      setSessions(data);
    } catch (err) {
      setError(err instanceof Error ? err.message : '加载会话列表失败');
    } finally {
      setIsLoading(false);
    }
  }, []);

  useEffect(() => {
    loadSessions();
  }, [loadSessions]);

  const createNewSession = useCallback(async (title?: string) => {
    try {
      const newSession = await createSession(title);
      setSessions((prev) => [newSession, ...prev]);
      return newSession;
    } catch (err) {
      setError(err instanceof Error ? err.message : '创建会话失败');
      throw err;
    }
  }, []);

  const removeSession = useCallback(async (id: string) => {
    try {
      await deleteSession(id);
      setSessions((prev) => prev.filter((s) => s.id !== id));
    } catch (err) {
      setError(err instanceof Error ? err.message : '删除会话失败');
      throw err;
    }
  }, []);

  return {
    sessions,
    isLoading,
    error,
    createNewSession,
    removeSession,
    refreshSessions: loadSessions,
  };
}
