export interface Message {
  id: string;
  role: 'user' | 'assistant';
  content: string;
  timestamp: string;
}

const API_BASE = import.meta.env.VITE_API_BASE || '';

export async function getMessages(sessionId: string): Promise<Message[]> {
  const response = await fetch(`${API_BASE}/v1/sessions/${sessionId}/messages`);

  if (!response.ok) {
    throw new Error(`Failed to get messages: ${response.status}`);
  }

  return response.json();
}

export async function addMessage(sessionId: string, role: 'user' | 'assistant', content: string): Promise<void> {
  const response = await fetch(`${API_BASE}/v1/sessions/${sessionId}/messages`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ role, content }),
  });

  if (!response.ok) {
    throw new Error(`Failed to add message: ${response.status}`);
  }
}

export async function clearMessages(sessionId: string): Promise<void> {
  const response = await fetch(`${API_BASE}/v1/sessions/${sessionId}/messages`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    throw new Error(`Failed to clear messages: ${response.status}`);
  }
}
