document.addEventListener("DOMContentLoaded", function () {
  window.AppLayout.renderNavbar("home");

  var listContainer = document.getElementById("publicationList");
  var filterContainer = document.getElementById("tagFilters");
  var alertBox = document.getElementById("alertBox");
  var searchForm = document.getElementById("searchForm");
  var searchInput = document.getElementById("searchInput");
  var summaryText = document.getElementById("listSummary");

  var state = {
    keyword: new URLSearchParams(window.location.search).get("keyword") || "",
    tag: new URLSearchParams(window.location.search).get("tag") || "",
    publications: [],
    tags: []
  };

  searchInput.value = state.keyword;

  searchForm.addEventListener("submit", function (event) {
    event.preventDefault();
    state.keyword = searchInput.value.trim();
    loadPublications();
  });

  filterContainer.addEventListener("click", function (event) {
    var target = event.target.closest(".js-filter-tag");
    if (!target) {
      return;
    }
    state.tag = target.dataset.tag || "";
    loadPublications();
  });

  listContainer.addEventListener("click", async function (event) {
    var favoriteButton = event.target.closest(".js-favorite-toggle");
    var tagLink = event.target.closest(".js-tag-link");

    if (tagLink) {
      state.tag = tagLink.dataset.tag || "";
      loadPublications();
      return;
    }

    if (!favoriteButton) {
      return;
    }

    if (!window.AppAuth.isAuthenticated()) {
      window.AppAuth.redirectToLogin();
      return;
    }

    var publicationId = favoriteButton.dataset.id;
    await toggleFavorite(publicationId);
  });

  async function loadTags() {
    try {
      var response = await window.AppApi.get(window.AppApi.endpoints.tags);
      var tags = window.AppApi.toArray(response.data);
      state.tags = tags.map(function (item) {
        return typeof item === "string" ? item : (item.name || item.tag || "");
      }).filter(Boolean);
    } catch (error) {
      state.tags = [];
    }
    renderTagFilters();
  }

  function renderTagFilters() {
    var buttons = ['<button type="button" class="tag-chip js-filter-tag ' + (state.tag ? "" : "is-active") + '" data-tag="">All</button>'];
    state.tags.forEach(function (tag) {
      buttons.push('<button type="button" class="tag-chip js-filter-tag ' + (state.tag === tag ? "is-active" : "") + '" data-tag="' + window.AppLayout.escapeHtml(tag) + '">' + window.AppLayout.escapeHtml(tag) + "</button>");
    });
    filterContainer.innerHTML = buttons.join("");
  }

  async function loadPublications() {
    alertBox.innerHTML = "";
    window.AppLayout.showLoading(listContainer, "Loading publications...");

    try {
      var response = await window.AppApi.get(window.AppApi.endpoints.publications, {
        params: {
          q: state.keyword || undefined,
          tag: state.tag || undefined
        }
      });

      state.publications = window.AppApi.toArray(response.data);
      if (!state.tags.length && state.publications.length) {
        state.tags = Array.from(new Set(state.publications.flatMap(function (item) {
          return window.AppLayout.getTags(item);
        })));
        renderTagFilters();
      }

      summaryText.textContent = state.publications.length + " publication(s) found" +
        (state.keyword ? ' for "' + state.keyword + '"' : "") +
        (state.tag ? " in tag " + state.tag : "");

      if (!state.publications.length) {
        window.AppLayout.showEmpty(listContainer, "No publications matched the current filters.");
        return;
      }

      listContainer.innerHTML = state.publications.map(function (publication) {
        return window.AppLayout.publicationCard(publication);
      }).join("");
    } catch (error) {
      summaryText.textContent = "";
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
      window.AppLayout.showEmpty(listContainer, "Unable to load publications.");
    }
  }

  async function toggleFavorite(id) {
    var user = window.AppAuth.getUser() || {};
    var endpoint = window.AppApi.fillPath(window.AppApi.endpoints.favoriteToggle, { userId: user.id, id: id });
    try {
      var item = state.publications.find(function (publication) {
        return String(window.AppLayout.getId(publication)) === String(id);
      });

      if (item && window.AppLayout.isFavorite(item)) {
        await window.AppApi.delete(endpoint);
        item.favorite = false;
        item.favorited = false;
        item.inCollection = false;
      } else {
        await window.AppApi.post(endpoint, {});
        if (item) {
          item.favorite = true;
          item.favorited = true;
          item.inCollection = true;
        }
      }

      listContainer.innerHTML = state.publications.map(function (publication) {
        return window.AppLayout.publicationCard(publication);
      }).join("");
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    }
  }

  loadTags();
  loadPublications();
});
