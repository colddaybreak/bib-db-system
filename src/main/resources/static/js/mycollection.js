document.addEventListener("DOMContentLoaded", function () {
  if (!window.AppAuth.requireAuth()) {
    return;
  }

  window.AppLayout.renderNavbar("collection");

  var listContainer = document.getElementById("collectionList");
  var alertBox = document.getElementById("alertBox");
  var summaryText = document.getElementById("collectionSummary");
  var items = [];

  listContainer.addEventListener("click", async function (event) {
    var favoriteButton = event.target.closest(".js-favorite-toggle");
    var deleteButton = event.target.closest(".js-publication-delete");
    var tagLink = event.target.closest(".js-tag-link");

    if (tagLink) {
      window.location.href = "index.html?tag=" + encodeURIComponent(tagLink.dataset.tag || "");
      return;
    }

    if (deleteButton) {
      await deletePublication(deleteButton.dataset.id);
      return;
    }

    if (!favoriteButton) {
      return;
    }

    var publicationId = favoriteButton.dataset.id;
    await removeFavorite(publicationId);
  });

  async function loadCollection() {
    window.AppLayout.showLoading(listContainer, "Loading your saved publications...");
    try {
      var user = window.AppAuth.getUser() || {};
      var response = await window.AppApi.get(window.AppApi.fillPath(window.AppApi.endpoints.favorites, { userId: user.id }));
      items = window.AppApi.toArray(response.data).map(function (item) {
        item.favorite = true;
        item.favorited = true;
        item.inCollection = true;
        return item;
      });

      summaryText.textContent = items.length + " saved publication(s)";

      if (!items.length) {
        window.AppLayout.showEmpty(listContainer, "Your collection is empty right now.");
        return;
      }

      listContainer.innerHTML = items.map(function (item) {
        return window.AppLayout.publicationCard(item);
      }).join("");
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
      window.AppLayout.showEmpty(listContainer, "Unable to load your collection.");
    }
  }

  async function removeFavorite(id) {
    try {
      var user = window.AppAuth.getUser() || {};
      var endpoint = window.AppApi.fillPath(window.AppApi.endpoints.favoriteToggle, { userId: user.id, id: id });
      await window.AppApi.delete(endpoint);
      items = items.filter(function (item) {
        return String(window.AppLayout.getId(item)) !== String(id);
      });
      summaryText.textContent = items.length + " saved publication(s)";
      if (!items.length) {
        window.AppLayout.showEmpty(listContainer, "Your collection is empty right now.");
        return;
      }
      listContainer.innerHTML = items.map(function (item) {
        return window.AppLayout.publicationCard(item);
      }).join("");
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    }
  }

  async function deletePublication(id) {
    if (!id || !window.confirm("Delete this publication? This action cannot be undone.")) {
      return;
    }

    try {
      var endpoint = window.AppApi.fillPath(window.AppApi.endpoints.publicationDetail, { id: id });
      await window.AppApi.delete(endpoint);
      items = items.filter(function (item) {
        return String(window.AppLayout.getId(item)) !== String(id);
      });
      summaryText.textContent = items.length + " saved publication(s)";
      if (!items.length) {
        window.AppLayout.showEmpty(listContainer, "Your collection is empty right now.");
        return;
      }
      listContainer.innerHTML = items.map(function (item) {
        return window.AppLayout.publicationCard(item);
      }).join("");
      window.AppLayout.showAlert(alertBox, "success", "Publication deleted successfully.");
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    }
  }

  loadCollection();
});
