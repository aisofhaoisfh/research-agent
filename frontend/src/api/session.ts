export interface Session {
  id: string;
  title: string;
  createdAt: string;
  updatedAt: string;
  messageCount: number;
}

export interface SessionRequest {
  title?: string;
}

const API_BASE = import.meta.env.VITE_API_BASE || '';

export async function createSession(title?: string): Promise<Session> {
  const response = await fetch(`${API_BASE}/v1/sessions`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ title } as SessionRequest),
  });

  if (!response.ok) {
    throw new Error(`Failed to create session: ${response.status}`);
  }

  return response.json();
}

export async function listSessions(): Promise<Session[]> {
  const response = await fetch(`${API_BASE}/v1/sessions`);

  if (!response.ok) {
    throw new Error(`Failed to list sessions: ${response.status}`);
  }

  return response.json();
}

export async function getSession(id: string): Promise<Session> {
  const response = await fetch(`${API_BASE}/v1/sessions/${id}`);

  if (!response.ok) {
    throw new Error(`Failed to get session: ${response.status}`);
  }

  return response.json();
}

export async function deleteSession(id: string): Promise<void> {
  const response = await fetch(`${API_BASE}/v1/sessions/${id}`, {
    method: 'DELETE',
  });

  if (!response.ok) {
    throw new Error(`Failed to delete session: ${response.status}`);
  }
}

export async function updateSession(id: string, title: string): Promise<Session> {
  const response = await fetch(`${API_BASE}/v1/sessions/${id}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ title } as SessionRequest),
  });

  if (!response.ok) {
    throw new Error(`Failed to update session: ${response.status}`);
  }

  return response.json();
}
