document.addEventListener("DOMContentLoaded", function () {
  if (!window.AppAuth.requireAuth()) {
    return;
  }

  window.AppLayout.renderNavbar("edit");

  var params = new URLSearchParams(window.location.search);
  var publicationId = params.get("id");
  var form = document.getElementById("publicationForm");
  var alertBox = document.getElementById("alertBox");
  var submitButton = document.getElementById("submitButton");
  var pageHeading = document.getElementById("pageHeading");
  var pageLead = document.getElementById("pageLead");

  if (publicationId) {
    pageHeading.textContent = "Edit publication";
    pageLead.textContent = "Update the publication record and save the latest metadata.";
  }

  form.addEventListener("submit", async function (event) {
    event.preventDefault();
    alertBox.innerHTML = "";
    submitButton.disabled = true;
    submitButton.textContent = publicationId ? "Saving..." : "Creating...";

    var payload = {
      title: form.title.value.trim(),
      authors: form.authors.value.trim(),
      year: toNumber(form.year.value),
      doi: form.doi.value.trim(),
      url: form.url.value.trim(),
      abstractText: form.abstract.value.trim()
    };
    var tags = splitByComma(form.tags.value);

    try {
      var response;
      if (publicationId) {
        var updateEndpoint = window.AppApi.fillPath(window.AppApi.endpoints.publicationDetail, { id: publicationId });
        response = await window.AppApi.put(updateEndpoint, payload);
      } else {
        response = await window.AppApi.post(window.AppApi.endpoints.publications, payload);
      }

      var item = window.AppApi.toItem(response.data) || {};
      var targetId = window.AppLayout.getId(item) || publicationId || "";
      await saveTags(targetId, tags);
      window.AppLayout.showAlert(alertBox, "success", publicationId ? "Publication updated successfully." : "Publication created successfully.");
      setTimeout(function () {
        window.location.href = targetId ? "detail.html?id=" + encodeURIComponent(targetId) : "index.html";
      }, 900);
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    } finally {
      submitButton.disabled = false;
      submitButton.textContent = publicationId ? "Save Changes" : "Create Publication";
    }
  });

  function splitByComma(value) {
    return value.split(",").map(function (item) {
      return item.trim();
    }).filter(Boolean);
  }

  function toNumber(value) {
    var number = Number(value);
    return Number.isFinite(number) && value !== "" ? number : null;
  }

  function fillForm(item) {
    form.title.value = item.title || "";
    form.authors.value = Array.isArray(item.authors) ? item.authors.join(", ") : (item.authors || "");
    form.year.value = window.AppLayout.getYear(item) || "";
    form.venue.value = window.AppLayout.getVenue(item) || "";
    form.doi.value = item.doi || "";
    form.url.value = item.url || "";
    form.tags.value = window.AppLayout.getTags(item).join(", ");
    form.abstract.value = window.AppLayout.getAbstract(item) || "";
  }

  async function saveTags(id, tags) {
    if (!id || !tags.length) {
      return;
    }
    var endpoint = window.AppApi.fillPath(window.AppApi.endpoints.publicationTags, { id: id });
    for (var index = 0; index < tags.length; index += 1) {
      await window.AppApi.post(endpoint, null, {
        params: {
          name: tags[index]
        }
      });
    }
  }

  async function loadPublication() {
    if (!publicationId) {
      return;
    }
    try {
      var endpoint = window.AppApi.fillPath(window.AppApi.endpoints.publicationDetail, { id: publicationId });
      var response = await window.AppApi.get(endpoint);
      fillForm(window.AppApi.toItem(response.data));
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    }
  }

  loadPublication();
});
