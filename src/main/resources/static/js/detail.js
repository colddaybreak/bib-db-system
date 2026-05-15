document.addEventListener("DOMContentLoaded", function () {
  window.AppLayout.renderNavbar("home");

  var content = document.getElementById("detailContent");
  var alertBox = document.getElementById("alertBox");
  var params = new URLSearchParams(window.location.search);
  var publicationId = params.get("id");
  var publication = null;

  if (!publicationId) {
    window.AppLayout.showEmpty(content, "Publication id is missing.");
    return;
  }

  content.addEventListener("click", async function (event) {
    var favoriteButton = event.target.closest(".js-favorite-toggle");
    var tagLink = event.target.closest(".js-tag-link");

    if (tagLink) {
      window.location.href = "index.html?tag=" + encodeURIComponent(tagLink.dataset.tag || "");
      return;
    }

    if (!favoriteButton) {
      return;
    }

    if (!window.AppAuth.isAuthenticated()) {
      window.AppAuth.redirectToLogin();
      return;
    }

    await toggleFavorite();
  });

  function renderDetail(item) {
    publication = item;
    var tags = window.AppLayout.getTags(item);
    var favoriteActive = window.AppLayout.isFavorite(item);

    content.innerHTML = '' +
      '<div class="card content-card">' +
      '  <div class="card-body p-4 p-lg-5">' +
      '    <div class="d-flex justify-content-between align-items-start gap-3 mb-3">' +
      '      <div>' +
      '        <h1 class="h3 page-title mb-2">' + window.AppLayout.escapeHtml(window.AppLayout.getTitle(item)) + "</h1>" +
      '        <p class="meta-line mb-0">' + window.AppLayout.escapeHtml(window.AppLayout.getAuthors(item)) + "</p>" +
      "      </div>" +
      '      <button type="button" class="favorite-button ' + (favoriteActive ? "is-active" : "") + ' js-favorite-toggle" data-id="' + window.AppLayout.escapeHtml(window.AppLayout.getId(item)) + '">' +
      '        <i class="bi ' + (favoriteActive ? "bi-star-fill" : "bi-star") + '"></i>' +
      "      </button>" +
      "    </div>" +
      '    <div class="detail-row"><span class="detail-label">Year</span><span>' + window.AppLayout.escapeHtml(window.AppLayout.getYear(item) || "-") + "</span></div>" +
      '    <div class="detail-row"><span class="detail-label">Venue</span><span>' + window.AppLayout.escapeHtml(window.AppLayout.getVenue(item) || "-") + "</span></div>" +
      '    <div class="detail-row"><span class="detail-label">DOI</span><span>' + window.AppLayout.escapeHtml(item.doi || "-") + "</span></div>" +
      '    <div class="detail-row"><span class="detail-label">URL</span><span>' + (item.url ? '<a href="' + window.AppLayout.escapeHtml(item.url) + '" target="_blank" rel="noreferrer">' + window.AppLayout.escapeHtml(item.url) + "</a>" : "-") + "</span></div>" +
      '    <div class="mb-4"><span class="detail-label d-block mb-2">Tags</span>' + window.AppLayout.createTagMarkup(tags) + "</div>" +
      '    <div class="mb-4"><h2 class="h5">Abstract</h2><p class="mb-0">' + window.AppLayout.escapeHtml(window.AppLayout.getAbstract(item) || "No abstract provided.") + "</p></div>" +
      '    <div class="d-flex gap-2 flex-wrap">' +
      '      <a class="btn btn-outline-secondary" href="index.html">Back to list</a>' +
      '      <a class="btn btn-primary" href="edit.html?id=' + encodeURIComponent(window.AppLayout.getId(item)) + '">Edit publication</a>' +
      "    </div>" +
      "  </div>" +
      "</div>";
  }

  async function toggleFavorite() {
    try {
      var user = window.AppAuth.getUser() || {};
      var endpoint = window.AppApi.fillPath(window.AppApi.endpoints.favoriteToggle, { userId: user.id, id: publicationId });
      if (window.AppLayout.isFavorite(publication)) {
        await window.AppApi.delete(endpoint);
        publication.favorite = false;
        publication.favorited = false;
        publication.inCollection = false;
      } else {
        await window.AppApi.post(endpoint, {});
        publication.favorite = true;
        publication.favorited = true;
        publication.inCollection = true;
      }
      renderDetail(publication);
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
    }
  }

  async function loadDetail() {
    window.AppLayout.showLoading(content, "Loading publication details...");
    try {
      var endpoint = window.AppApi.fillPath(window.AppApi.endpoints.publicationDetail, { id: publicationId });
      var response = await window.AppApi.get(endpoint);
      renderDetail(window.AppApi.toItem(response.data));
    } catch (error) {
      window.AppLayout.showAlert(alertBox, "danger", window.AppApi.getErrorMessage(error));
      window.AppLayout.showEmpty(content, "Unable to load publication details.");
    }
  }

  loadDetail();
});
