import '../styles/home.css';

export function HomeView() {
  const currentDate = new Date();
  const weekdays = ['周日', '周一', '周二', '周三', '周四', '周五', '周六'];
  const dateStr = `${currentDate.getFullYear()}-${String(currentDate.getMonth() + 1).padStart(2, '0')}-${String(currentDate.getDate()).padStart(2, '0')} ${weekdays[currentDate.getDay()]}`;
  
  const greeting = currentDate.getHours() < 12 ? '早上好' : currentDate.getHours() < 18 ? '下午好' : '晚上好';

  return (
    <div className="home-view">
      <div className="home-welcome-bar">
        <span className="home-welcome-greeting">
          <span className="home-greeting-line1">
            <span className="home-greeting-prefix">{greeting}，</span>
            <span className="home-greeting-name">张三</span>
          </span>
          <span className="home-greeting-line2">
            <span className="home-greeting-tagline">
              让我帮你把生意越做越<span className="greeting-yi">易</span>~
            </span>
          </span>
        </span>
        <span className="home-welcome-divider">·</span>
        <span className="home-welcome-date">{dateStr}</span>
      </div>

      <div className="home-guide-layout">
        <div className="home-guide-panel">
          <h2 className="home-guide-title">操作指引</h2>
          <div className="home-guide-list">
            <div className="home-guide-item">
              <span className="home-guide-num">01</span>
              <div className="home-guide-content">
                <h4>左侧导航</h4>
                <p>展开自动化、工具栏，进入门店经营、巡检、单据流、物流调度；对话历史可继续过往会话</p>
              </div>
            </div>
            <div className="home-guide-item">
              <span className="home-guide-num">02</span>
              <div className="home-guide-content">
                <h4>托管日历</h4>
                <p>按日开启/关闭各模块 AI 托管，选择门店或仓库</p>
              </div>
            </div>
            <div className="home-guide-item">
              <span className="home-guide-num">03</span>
              <div className="home-guide-content">
                <h4>创建对话</h4>
                <p>发起新会话，咨询财务、销售、补货、定价等业务问题</p>
              </div>
            </div>
            <div className="home-guide-item">
              <span className="home-guide-num">04</span>
              <div className="home-guide-content">
                <h4>模块内操作</h4>
                <p>查看任务、执行确认、查看报告；托管开启则自动执行</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
