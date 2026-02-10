import { describe, it, expect, vi, beforeEach } from 'vitest';
import { renderHook, act } from '@testing-library/react';
import { useChat } from '../useChat';
import * as chatApi from '../../api/chat';

// Mock the chat API
vi.mock('../../api/chat', () => ({
  streamChat: vi.fn(),
}));

describe('useChat', () => {
  beforeEach(() => {
    vi.clearAllMocks();
  });

  it('should initialize with empty messages', () => {
    const { result } = renderHook(() => useChat());
    
    expect(result.current.messages).toEqual([]);
    expect(result.current.isLoading).toBe(false);
    expect(result.current.error).toBe(null);
  });

  it('should add user message when sending', async () => {
    const mockStream = async function* () {
      yield { type: 'message', data: '"Hello"' };
      yield { type: 'done', data: '{}' };
    };
    
    vi.mocked(chatApi.streamChat).mockReturnValue(mockStream() as any);
    
    const { result } = renderHook(() => useChat());
    
    await act(async () => {
      await result.current.sendMessage('Test message');
    });
    
    expect(result.current.messages.length).toBeGreaterThan(0);
    expect(result.current.messages[0].role).toBe('user');
    expect(result.current.messages[0].content).toBe('Test message');
  });

  it('should not send empty message', async () => {
    const { result } = renderHook(() => useChat());
    
    await act(async () => {
      await result.current.sendMessage('   ');
    });
    
    expect(result.current.messages.length).toBe(0);
  });

  it('should clear messages', () => {
    const { result } = renderHook(() => useChat());
    
    act(() => {
      result.current.clearMessages();
    });
    
    expect(result.current.messages).toEqual([]);
    expect(result.current.error).toBe(null);
  });
});
