import '../Layout/MainArea.css';

interface MainAreaProps {
  title: string;
  subtitle?: string;
  onCreateChat: () => void;
  children: React.ReactNode;
}

export function MainArea({ title, subtitle, onCreateChat, children }: MainAreaProps) {
  return (
    <main className="main-area">
      <header className="main-header">
        <div>
          <h2>{title}</h2>
          {subtitle && <div className="subtitle">{subtitle}</div>}
        </div>
        <button className="create-chat-btn" onClick={onCreateChat}>
          + 创建对话
        </button>
      </header>
      <div className="main-content">{children}</div>
    </main>
  );
}
