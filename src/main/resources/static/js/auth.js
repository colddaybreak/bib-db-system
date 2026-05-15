(function () {
  var TOKEN_KEY = "bib_token";
  var USER_KEY = "bib_user";

  function getToken() {
    try {
      return localStorage.getItem(TOKEN_KEY) || "";
    } catch (error) {
      return "";
    }
  }

  function setToken(token) {
    localStorage.setItem(TOKEN_KEY, token || "");
  }

  function clearToken() {
    localStorage.removeItem(TOKEN_KEY);
  }

  function setUser(user) {
    localStorage.setItem(USER_KEY, JSON.stringify(user || {}));
  }

  function getUser() {
    try {
      var raw = localStorage.getItem(USER_KEY);
      return raw ? JSON.parse(raw) : null;
    } catch (error) {
      return null;
    }
  }

  function clearUser() {
    localStorage.removeItem(USER_KEY);
  }

  function isAuthenticated() {
    var user = getUser();
    return Boolean(getToken() || (user && user.id));
  }

  function saveSession(token, user) {
    setToken(token);
    setUser(user);
  }

  function clearSession() {
    clearToken();
    clearUser();
  }

  function redirect(path) {
    window.location.href = path;
  }

  function redirectToLogin() {
    var currentPath = window.location.pathname.split("/").pop() || "index.html";
    var currentQuery = window.location.search || "";
    redirect("login.html?next=" + encodeURIComponent(currentPath + currentQuery));
  }

  function requireAuth() {
    if (!isAuthenticated()) {
      redirectToLogin();
      return false;
    }
    return true;
  }

  function redirectIfAuthenticated() {
    if (isAuthenticated()) {
      redirect("index.html");
      return true;
    }
    return false;
  }

  function logout() {
    clearSession();
    redirect("login.html");
  }

  window.AppAuth = {
    getToken: getToken,
    getUser: getUser,
    isAuthenticated: isAuthenticated,
    saveSession: saveSession,
    clearSession: clearSession,
    requireAuth: requireAuth,
    redirectIfAuthenticated: redirectIfAuthenticated,
    redirectToLogin: redirectToLogin,
    logout: logout
  };
})();
