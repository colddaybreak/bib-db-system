document.addEventListener("DOMContentLoaded", function () {
  if (window.AppAuth.redirectIfAuthenticated()) {
    return;
  }

  var form = document.getElementById("registerForm");
  var alertBox = document.getElementById("alertBox");
  var submitButton = document.getElementById("registerButton");

  form.addEventListener("submit", async function (event) {
    event.preventDefault();
    alertBox.innerHTML = "";

    if (form.password.value !== form.confirmPassword.value) {
      window.AppLayout.showAlert(alertBox, "warning", "Passwords do not match.");
      return;
    }

    submitButton.disabled = true;
    submitButton.textContent = "Creating...";

    var payload = {
      username: form.username.value.trim(),
      email: form.email.value.trim(),
      password: form.password.value
    };

    try {
      await window.AppApi.post(window.AppApi.endpoints.register, payload);
      window.AppLayout.showAlert(alertBox, "success", "Registration successful. You can sign in now.");
      form.reset();
      setTimeout(function () {
        window.location.href = "login.html";
      }, 1200);
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    } finally {
      submitButton.disabled = false;
      submitButton.textContent = "Create Account";
    }
  });
});
