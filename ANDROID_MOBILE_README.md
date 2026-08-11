# Android Mobile Backend Notes

This is the mobile-ready copy of the original PSX Streamlit backend.

Changes are intentionally small:
- Sidebar starts collapsed, which is easier on Android screens.
- Native Android navigation can select a workspace using a URL query parameter:
  - `?page=alerts`
  - `?page=decision`
  - `?page=scenario`
  - `?page=divergence`
  - `?page=portfolio`
  - `?page=settings`

All PSX analysis engines and the five main workspaces remain unchanged.
Deploy this folder to Streamlit Cloud and paste the resulting public URL into the Android app.
