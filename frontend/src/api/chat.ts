export interface ChatRequest {
  message: string;
  sessionId?: string;
}

export interface ErrorResponse {
  code: string;
  message: string;
  details?: unknown;
}

export type SSEEventType = 'message' | 'done' | 'error';

export interface SSEEvent {
  type: SSEEventType;
  data: string;
}

const API_BASE = import.meta.env.VITE_API_BASE || '';

export async function* streamChat(request: ChatRequest): AsyncGenerator<SSEEvent, void, unknown> {
  const response = await fetch(`${API_BASE}/v1/chat`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(request),
  });

  if (!response.ok) {
    throw new Error(`HTTP error! status: ${response.status}`);
  }

  const reader = response.body?.getReader();
  if (!reader) {
    throw new Error('Response body is not readable');
  }

  const decoder = new TextDecoder();
  let buffer = '';

  try {
    while (true) {
      const { done, value } = await reader.read();
      if (done) break;

      buffer += decoder.decode(value, { stream: true });
      const lines = buffer.split('\n');
      buffer = lines.pop() || '';

      let eventType: SSEEventType = 'message';
      let eventData = '';

      for (const line of lines) {
        if (line.startsWith('event: ')) {
          const type = line.substring(7).trim();
          if (type === 'message' || type === 'done' || type === 'error') {
            eventType = type;
          }
        } else if (line.startsWith('data: ')) {
          eventData = line.substring(6).trim();
        } else if (line === '') {
          if (eventData) {
            yield { type: eventType, data: eventData };
            eventData = '';
            eventType = 'message';
          }
        }
      }
    }

    // 处理剩余的 buffer
    if (buffer.trim()) {
      const lines = buffer.split('\n');
      for (const line of lines) {
        if (line.startsWith('event: ')) {
          const type = line.substring(7).trim();
          if (type === 'message' || type === 'done' || type === 'error') {
            eventType = type;
          }
        } else if (line.startsWith('data: ')) {
          const data = line.substring(6).trim();
          if (data) {
            yield { type: eventType, data };
          }
        }
      }
    }
  } finally {
    reader.releaseLock();
  }
}

export async function checkHealth(): Promise<boolean> {
  try {
    const response = await fetch(`${API_BASE}/v1/health`);
    return response.ok;
  } catch {
    return false;
  }
}
