import { Message } from '../../hooks/useChat';

interface MessageBubbleProps {
  message: Message;
}

export function MessageBubble({ message }: MessageBubbleProps) {
  return (
    <div className={`message-bubble ${message.role}`}>
      <div className="message-avatar">
        {message.role === 'user' ? '我' : 'AI'}
      </div>
      <div className="message-content">{message.content}</div>
    </div>
  );
}
