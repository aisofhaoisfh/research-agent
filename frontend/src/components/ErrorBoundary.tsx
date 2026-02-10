import React, { Component, ErrorInfo, ReactNode } from 'react';

interface Props {
  children: ReactNode;
  fallback?: ReactNode;
}

interface State {
  hasError: boolean;
  error?: Error;
}

export class ErrorBoundary extends Component<Props, State> {
  public state: State = {
    hasError: false,
  };

  public static getDerivedStateFromError(error: Error): State {
    return { hasError: true, error };
  }

  public componentDidCatch(error: Error, errorInfo: ErrorInfo) {
    console.error('Uncaught error:', error, errorInfo);
  }

  public render() {
    if (this.state.hasError) {
      return this.props.fallback || (
        <div style={{ 
          padding: '40px', 
          textAlign: 'center', 
          color: 'var(--danger)',
          background: 'var(--bg-card)',
          borderRadius: '12px',
          margin: '24px'
        }}>
          <h2>出现错误</h2>
          <p>{this.state.error?.message || '未知错误'}</p>
          <button 
            onClick={() => this.setState({ hasError: false, error: undefined })}
            style={{
              marginTop: '16px',
              padding: '8px 16px',
              background: 'var(--accent)',
              color: 'white',
              border: 'none',
              borderRadius: '6px',
              cursor: 'pointer'
            }}
          >
            重试
          </button>
        </div>
      );
    }

    return this.props.children;
  }
}
