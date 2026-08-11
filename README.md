# PSX Institutional Intelligence Terminal v5.7 — Professional Sidebar Terminal Edition



## New in v5.7 — Professional Sidebar Terminal redesign

The interface has been redesigned from the earlier copied-style dashboard into a cleaner **institutional PSX terminal** with a permanent left-side workspace menu for fast access across all modules.

- Restored and redesigned the **left-side navigation panel**
- Replaced the crowded top-level tabs with a professional **module selector** in the sidebar
- Added a new **Command Center** landing screen
- Added **Midnight Terminal** and **Executive Light** theme choices
- Improved cards, buttons, metrics, panels, upload areas, data tables, and chart containers
- Kept all autonomous, fundamentals, Google-link, knowledge-brain, and portfolio-analysis capabilities intact
- Designed for easier use when many modules are present

---


## New in v5.4 — Autopilot Manager

The bot now includes a dedicated **Autopilot Manager** tab and a separate `autopilot_runner.py` script. This is the autonomous research layer that can manage the full analysis workflow in one cycle:

- Scan a watchlist automatically
- Pull the latest technical setup and multi-engine confluence
- Apply the uploaded candlestick, Fibonacci, CWT, trend, secular-bear, and risk frameworks
- Merge optional portfolio CSV data to issue **Hold / Buy More / Average Carefully / Review / Reduce / Avoid**-style actions
- Merge optional fundamentals CSV data to apply fundamental grade, intrinsic value, margin-of-safety, and financial-fakery gates
- Include event/news-risk and price-hazard overrides before allowing aggressive actions
- Save machine-readable outputs in `data/`:
  - `autopilot_decisions.csv`
  - `autopilot_holdings_actions.csv`
  - `autopilot_opportunities.csv`
  - `autopilot_failures.csv`
  - `autopilot_market_brief.csv`
  - `autopilot_latest_summary.md`

### Scheduled autonomous run

```bash
python autopilot_runner.py --symbols NBP,OGDC,MARI,SYS,UBL,SAZEW,NATF
```

With portfolio and fundamentals:

```bash
python autopilot_runner.py --symbols NBP,OGDC,MARI,SYS,UBL --portfolio portfolio.csv --fundamentals fundamentals.csv
```

This version autonomously manages **research, ranking, portfolio diagnosis, risk gating, and alert-ready outputs**. It does **not** place broker orders.

---
## Previous v5.2 visual experiment

The earlier Digital Karachi-inspired visual experiment has now been superseded by the v5.7 professional sidebar terminal redesign.

---
A professional Pakistan Stock Exchange analysis platform that combines:

- Investor profile selection
- Macro checklist and company macro sensitivity
- Multi-style technical trading desk
- CWT / MTF scenario scanners
- Pattern, divergence, FVG, Fibonacci, and confluence analysis
- Prediction and loss-control engine
- Trade tracker and alert center
- Official news / price-hazard watchtower
- Master fundamental scoring for non-banks and banks
- Intrinsic value engine
- Financial fakery / governance red-flag scanner
- Strategy Builder for Strategies 1–4
- Corporate catalyst / explosive stock scanner

---

## New in v5.0

### 1) Investor Profile Engine
Implements the Week 12 investor-profile framework:

1. Pension / 20-year passive
2. Passive Trader 1
3. Passive Trader 2
4. Active Trader 1
5. Active Trader 2
6. Swing Trader
7. Day Trader

The bot asks about horizon, monitoring frequency, funds preference, fundamental usage, technical usage, and intraday trading, then recommends a profile and priority modules.

---

### 2) Macro Checklist
Implements the Week 12 economy-and-stocks checklist:

- Inflation
- Interest rates
- Bond/T-bill yields
- Current account
- Fiscal account
- SBP reserves
- PKR trend
- Oil trend
- Market P/E
- Earnings yield vs bond yield
- Global risk-on / risk-off tone

The dashboard outputs:

- Macro Score
- Macro Regime
- Quick Action
- Checklist table with Positive / Neutral / Negative impact

It also includes a **Company Macro Sensitivity Matrix** for:

- PKR depreciation
- Interest-rate hikes/cuts
- Oil sensitivity
- Export/import exposure
- Sector-specific effects

---

### 3) Master Fundamentals Engine
Upload a CSV/XLSX dataset and the bot:

- Normalizes flexible column names
- Separates banks vs non-financial companies
- Produces Good / Average / Bad factor tables
- Calculates a composite Fundamental Grade and Score %

#### Non-financial company score buckets
- Growth
- Stability
- Valuation
- Inventory / working capital
- Free cash flow

#### Bank score buckets
- Bank growth
- Spread and profitability
- Asset quality
- Funding quality
- Capital adequacy
- Cash quality

---

### 4) Intrinsic Value Composite
Calculates:

- DCF FCF
- DCF EPS
- DCF Cash
- Projected FCF
- Projected Cash
- Peter Lynch Value

Then produces:

- Composite Intrinsic Value — FCF
- Composite Intrinsic Value — Cash
- Margin of Safety %
- Valuation Grade:
  - Attractive
  - Fair / Review
  - Overvalued

The DCF engine supports configurable:
- Discount rate
- Terminal growth
- Growth-stage years
- Terminal-stage years

---

### 5) Financial Fakery / Governance Red-Flag Scanner
Uses the Week 10 red-flag framework:

- CFO deterioration
- cCFO vs cPAT
- Cash/share deterioration
- Receivables vs sales growth
- DSO / CCC stress
- Dividend funding quality
- Debt trend
- Insider signal text
- Optional annual-report / auditor-report text scanning

Keyword flags include:
- Going concern
- Qualified/adverse audit
- Auditor/CFO exit
- Default / non-compliance
- Fraud / investigation
- Impairment / restructuring
- Acquisition / goodwill signals

Output:
- Fraud Risk Level: LOW / MODERATE / HIGH / CRITICAL
- Risk points
- Detailed flags table

---

### 6) Strategy Builder — Strategies 1 to 4
Implements the Week 12 strategy framework:

#### Strategy 1
Top 10% companies by composite fundamental score.

#### Strategy 2
Strategy 1 + red-zone sector tilt:
- Banks
- Oil
- Fertilizer
- Utilities
- Power
- Chemicals

#### Strategy 3
Strategy 2 + low-price / high-beta rotation.

#### Strategy 4
Strategy 3 + intrinsic-value margin-of-safety filter.

The builder outputs candidate tables and CSV downloads for each strategy.

---

### 7) Corporate Catalyst / Explosive Stock Scanner
Implements the Assignment 10 framework:

- Share buybacks
- Capacity expansion
- Solar / energy efficiency
- M&A / merger
- New product / export order
- Production or sales outperformance
- Debt reduction / refinancing

Modes:
- Paste corporate announcement text
- Fetch official PSX announcement rows for selected symbols

Outputs:
- Catalyst Score
- Catalyst Grade
- Explosive Flag: Yes / Watchlist / No

---

## Existing modules retained from v4.0

### Technical & trading
- Multi-Style Trading Desk
- Scalping / Intraday / Swing / Long-Term modes
- Single Symbol PRO
- Scenario Scanner
- Pattern & Divergence Scanner
- Watchlist PRO Scorecard
- Prediction & Loss Control
- Trade Tracker
- Alert Center

### Risk watchtower
- Official news/event risk
- Price-hazard scan
- Gap shock
- Range expansion vs ATR
- Volume spike / thin liquidity
- Drawdown warnings
- Volatility expansion

### Official sources monitored by watchtower code
- PSX company announcements
- PSX financial announcements
- PSX notices
- SBP monetary-policy calendar
- PBS macro/CPI press releases

---

## Run

```bash
pip install -r requirements.txt
streamlit run app.py
```

---

## Data input notes

### Fundamentals
The Master Fundamentals and Strategy Builder tabs accept:
- `.csv`
- `.xlsx`
- `.xls`

The engine uses flexible column name matching, so exact wording is not mandatory. It works best when the file contains:
- Symbol
- Company
- Sector
- Price
- Beta
- Valuation metrics
- Growth metrics
- Cash-flow metrics
- Bank metrics where applicable

### Intrinsic value
Intrinsic-value output requires sufficient inputs such as:
- Current price
- EPS
- FCF / shares
- Cash / shares
- Equity / shares
- Growth rate or EBITDA/EPS growth proxy

### Important
This is a decision-support terminal, not a guarantee of profit. Its purpose is to improve:
- Risk discipline
- Stock selection
- Loss control
- Checklist-based decision making
- Strategy consistency


## v5.5 Complete Knowledge Integration Edition

This edition treats every uploaded framework as part of the bot's master brain.

### Added in v5.5
- New **Uploaded Knowledge Brain** tab showing the integration registry and active engine coverage.
- Autopilot output now includes **Knowledge Applied** tags for every symbol decision.
- Autopilot exports new CSVs:
  - `autopilot_knowledge_registry.csv`
  - `autopilot_engine_coverage.csv`
  - `autopilot_knowledge_summary.csv`
- Autopilot portfolio and fundamentals uploads now accept **CSV, XLSX, and XLS**.
- README integration map explicitly preserves the uploaded:
  - CWT strategy and risk-reward logic
  - Candlestick and Fibonacci concepts
  - Secular bear-market regime logic
  - Bank-analysis scoring concepts
  - Valuation / inventory / cash-flow logic
  - Red-flag / financial-fakery gates
  - PSX ranking workbook and data-quality awareness
  - Earlier document-retrieval architecture and bot workflows

The terminal remains an **analysis and research autopilot**. It does not place broker orders automatically.

## Link-based data inputs added
- **Fundamental data:** Paste a Google Sheets link in the Autopilot, Master Fundamentals, Strategy Builder, or Fundamentals & Rankings panels. The sheet must be shared with link access so the app can export the selected `gid` tab as CSV.
- **Portfolio data:** Paste a Google Drive PDF link in the Autopilot panel. The PDF must be link-accessible and contain a readable holdings table. The bot attempts automatic PDF table extraction; CSV/XLSX upload remains available as a fallback.
- When a pasted link and a local upload are both provided for the same dataset, the pasted link source is used.



## v5.8 Multi-Workspace Terminal Edition
- Sidebar now opens **multiple modules at the same time** instead of forcing one tab/page at a time.
- Added workspace presets: Daily Command Desk, Portfolio + Fundamentals, Trading + Risk Desk, Research Lab, and Open Everything.
- Added three display modes: Stacked Panels, Accordion Panels, and Split Board.
- Designed for workflows such as Autopilot + Fundamentals + Watchtower + Alerts on one screen.

## v6.0 Fundamental Image Import Center

The terminal now includes a **Fundamental Image Import Center** inside **Fundamental & Strategy Lab**.

It accepts six company-specific screenshot pages:

1. Growth
2. Stability
3. Valuation
4. Inventory
5. Cashflow
6. Main Page / Margin of Safety

The import center also accepts a **Google Sheets link** for structured metric data. Supported sheet formats:

- Standard wide fundamentals table with one row per symbol, or
- Long metric table with columns such as `Category`, `Metric`, and `Value`.

Imported image/sheet fundamentals are stored in session state and automatically become the fallback fundamentals source for:

- Decision Center
- Autopilot Manager
- Master Fundamentals
- Strategy Builder
- Fundamentals & Rankings

when no CSV/XLSX or separate Google Sheet link is selected in those desks.

### OCR note
Image extraction is best-effort and uses `Pillow` + `pytesseract`. On Windows, local OCR also needs the desktop **Tesseract OCR engine** installed and available to the system. If OCR is unavailable or the screenshots are difficult to parse, the Google Sheet link remains the most reliable structured input path.

## v6.7 Pattern Engine Update
Added/verified advanced chart pattern detection for:
- Rising Wedge
- Falling Wedge
- Bullish Flag
- Bearish Flag
- Pennant
- Ascending Triangle
- Descending Triangle
- Symmetrical Triangle
- Rectangle / Range Consolidation
- Ascending / Descending Channels
- Double Top / Double Bottom
- Triple Top / Triple Bottom
- Head & Shoulders / Inverse Head & Shoulders
- Broadening Wedge

These patterns appear in the selected-symbol pattern table, pattern scanner, and chart pattern note.

## v7.4 Accuracy Guard Update
This build tightens the Decision Center so it does not give BUY answers from chart signals alone.

Key fixes:
- Fresh BUY is blocked when matched fundamental data is missing.
- Fresh BUY is blocked when fundamentals are weak or valuation/MOS is unfavorable.
- Portfolio stocks now separate HOLD, SELL/REDUCE, BUY MORE, and AVERAGE CAREFULLY using portfolio P&L + technical + fundamental + valuation gates.
- Added Decision Confidence, Technical Status, Fundamental Data Status, Valuation Status, and Data Completeness columns.
- Entry/SL/TP1/TP2/TP3 now have a fallback execution-level builder to avoid blank levels when the base chart plan is incomplete.

## v7.6 - Internet Fundamental Fallback + Technical-Basis Disclosure

This version changes the missing-data behavior requested by the user:

- If a symbol has no uploaded / Google Sheet / image-import fundamental row, the Decision Center first tries to fetch recognizable public fundamental metrics from online sources.
- If online fundamentals are found, they are merged into the same scoring pipeline and marked as `Fundamental Source = Internet fallback`.
- If online fundamentals are not found or remain incomplete, the bot does not pretend the answer is complete. It clearly labels the result as `Technical Basis Only` or `Partial Fundamentals + Technical Basis`.
- Missing fundamental indicators are listed in the result table and full symbol report.
- Entry, Stop Loss, TP1, TP2, and TP3 are still calculated from the technical engine so the user can see a technical trade plan while the missing fundamental indicators are requested.
- Internet lookup logs are saved to `data/autopilot_internet_fundamental_logs.csv` when outputs are persisted.

Important: Public websites can block scraping or change their layout. When that happens, the bot will continue with technical analysis and clearly disclose that fundamentals are missing instead of giving a false all-engine decision.

## v7.7 — Uploaded Knowledge Selector + Scenario Scanner Upgrade

This edition adds a visible selector so the bot can use the user's uploaded material intentionally instead of hiding it inside separate tabs.

### New scanner controls
- **Uploaded Knowledge Selector** is now available inside:
  - Scenario Scanner
  - Pattern & Divergence Scanner
- Built-in profiles:
  - Complete Uploaded Knowledge Brain
  - Scenario Scanner Focus
  - Pattern Scanner Focus
  - PDF Trading Framework Focus
  - Fundamental + Valuation Confirmation
  - Custom Selection

### Supported uploaded-data layers
- Candlestick Patterns
- Chart Patterns: wedge, flag, triangle, rectangle
- Fibonacci Confluence
- CWT Alligator / Trend Structure
- Scenario 1/2/3 MTF Framework
- RSI / MACD Divergence
- Support / Resistance + Breakout
- Risk Reward / Position Sizing
- News / Event Risk Filter
- Secular Market Regime
- Fundamental Quality
- Valuation / Margin of Safety
- Financial Fakery / Red Flags
- Portfolio Action Rules

### Result transparency
Scanner results now include:
- Knowledge Profile
- Selected Knowledge Layers
- Scanner Mode
- Decision Basis

If a selected layer needs extra data, the scanner tells the user what is required, such as OHLCV data, fundamental Google Sheet, Sarmaaya screenshots, main valuation page, or portfolio PDF/Excel.

## Simple Mode (v13.6)
The visible workflow has been reduced to five mandatory workspaces:
1. Fast Up & Down Alert
2. Decision Center
3. Scenario Finder
4. Divergence Finder
5. Portfolio Checker

A sixth supporting page, **Data & Settings**, is retained only for PDF/pattern knowledge uploads, Sarmaaya/fundamental imports, and image-based fundamental data. The underlying chart patterns, candlestick patterns, indicators, divergence checks, risk rules, fundamentals, and uploaded knowledge engines have not been removed.
