document.addEventListener("DOMContentLoaded", function () {
  if (window.AppAuth.redirectIfAuthenticated()) {
    return;
  }

  var form = document.getElementById("loginForm");
  var alertBox = document.getElementById("alertBox");
  var submitButton = document.getElementById("loginButton");

  form.addEventListener("submit", async function (event) {
    event.preventDefault();
    alertBox.innerHTML = "";
    submitButton.disabled = true;
    submitButton.textContent = "Signing in...";

    var payload = {
      username: form.username.value.trim(),
      password: form.password.value
    };

    try {
      var response = await window.AppApi.post(window.AppApi.endpoints.login, payload);
      var data = window.AppApi.toItem(response.data) || {};
      var user = data.user || data;
      var token = data.token || data.accessToken || data.jwt || (user && user.id ? "user-" + user.id : "");
      if (!user || !user.id) {
        throw new Error("Login succeeded but user id was not returned.");
      }

      user = Object.assign({
        username: payload.username
      }, user);
      window.AppAuth.saveSession(token, user);

      var next = new URLSearchParams(window.location.search).get("next") || "index.html";
      window.location.href = next;
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    } finally {
      submitButton.disabled = false;
      submitButton.textContent = "Sign In";
    }
  });
});
