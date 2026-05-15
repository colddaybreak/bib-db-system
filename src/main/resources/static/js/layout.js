(function () {
  function qs(selector) {
    return document.querySelector(selector);
  }

  function escapeHtml(value) {
    return String(value == null ? "" : value)
      .replace(/&/g, "&amp;")
      .replace(/</g, "&lt;")
      .replace(/>/g, "&gt;")
      .replace(/"/g, "&quot;")
      .replace(/'/g, "&#39;");
  }

  function getId(publication) {
    return publication.id || publication.publicationId || publication._id || "";
  }

  function getTitle(publication) {
    return publication.title || publication.name || "Untitled publication";
  }

  function getAuthors(publication) {
    var authors = publication.authors || publication.authorList || publication.creator;
    if (Array.isArray(authors)) {
      return authors.join(", ");
    }
    return authors || "Unknown author";
  }

  function getTags(publication) {
    var tags = publication.tags || publication.tagNames || publication.keywords || publication.labels || [];
    if (typeof tags === "string") {
      return tags.split(",").map(function (item) {
        return item.trim();
      }).filter(Boolean);
    }
    return Array.isArray(tags) ? tags : [];
  }

  function getYear(publication) {
    return publication.year || publication.publicationYear || publication.publishedYear || "";
  }

  function getVenue(publication) {
    return publication.venue || publication.journal || publication.conference || publication.publisher || "";
  }

  function getAbstract(publication) {
    return publication.abstract || publication.abstractText || publication.description || publication.summary || "";
  }

  function isFavorite(publication) {
    return Boolean(publication.favorite || publication.favorited || publication.inCollection);
  }

  function createTagMarkup(tags) {
    if (!tags.length) {
      return '<span class="text-muted small">No tags</span>';
    }
    return tags.map(function (tag) {
      return '<button type="button" class="tag-chip js-tag-link" data-tag="' + escapeHtml(tag) + '">' + escapeHtml(tag) + "</button>";
    }).join("");
  }

  function formatMeta(publication) {
    var parts = [];
    var authors = getAuthors(publication);
    var year = getYear(publication);
    var venue = getVenue(publication);
    if (authors) {
      parts.push(authors);
    }
    if (year) {
      parts.push(year);
    }
    if (venue) {
      parts.push(venue);
    }
    return parts.join(" | ");
  }

  function publicationCard(publication, options) {
    var id = getId(publication);
    var activeFavorite = isFavorite(publication);
    var showFavorite = !options || options.showFavorite !== false;
    var favoriteButton = showFavorite ? (
      '<button type="button" class="favorite-button ' + (activeFavorite ? "is-active" : "") + ' js-favorite-toggle" data-id="' + escapeHtml(id) + '" aria-label="Toggle favorite">' +
      '<i class="bi ' + (activeFavorite ? "bi-star-fill" : "bi-star") + '"></i>' +
      "</button>"
    ) : "";

    return '' +
      '<div class="col">' +
      '  <div class="card publication-card h-100">' +
      '    <div class="card-body d-flex flex-column">' +
      '      <div class="d-flex justify-content-between align-items-start gap-3 mb-2">' +
      '        <h5 class="card-title mb-0"><a href="detail.html?id=' + encodeURIComponent(id) + '">' + escapeHtml(getTitle(publication)) + "</a></h5>" +
               favoriteButton +
      "      </div>" +
      '      <p class="meta-line mb-2">' + escapeHtml(formatMeta(publication)) + "</p>" +
      '      <p class="card-text flex-grow-1">' + escapeHtml(getAbstract(publication) || "No abstract provided.") + "</p>" +
      '      <div class="mb-3">' + createTagMarkup(getTags(publication)) + "</div>" +
      '      <div class="d-flex gap-2 flex-wrap">' +
      '        <a class="btn btn-outline-primary btn-sm" href="detail.html?id=' + encodeURIComponent(id) + '">View details</a>' +
      '        <a class="btn btn-outline-secondary btn-sm" href="edit.html?id=' + encodeURIComponent(id) + '">Edit</a>' +
      "      </div>" +
      "    </div>" +
      "  </div>" +
      "</div>";
  }

  function showAlert(target, type, message) {
    if (!target) {
      return;
    }
    target.innerHTML = '<div class="alert alert-' + type + ' alert-dismissible fade show" role="alert">' +
      escapeHtml(message) +
      '<button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>' +
      "</div>";
  }

  function showLoading(target, message) {
    if (!target) {
      return;
    }
    target.innerHTML = '<div class="text-center py-5"><div class="spinner-border text-primary" role="status"></div><p class="mt-3 text-muted mb-0">' +
      escapeHtml(message || "Loading...") +
      "</p></div>";
  }

  function showEmpty(target, message) {
    if (!target) {
      return;
    }
    target.innerHTML = '<div class="empty-state"><i class="bi bi-inboxes fs-1 d-block mb-3"></i><p class="mb-0">' +
      escapeHtml(message || "No data available.") +
      "</p></div>";
  }

  function renderNavbar(activePage) {
    var mountNode = qs("#app-navbar");
    if (!mountNode) {
      return;
    }

    var loggedIn = window.AppAuth && window.AppAuth.isAuthenticated();
    mountNode.innerHTML = '' +
      '<nav class="navbar navbar-expand-lg bg-white border-bottom sticky-top">' +
      '  <div class="container">' +
      '    <a class="navbar-brand text-primary" href="index.html">BibConnect</a>' +
      '    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mainNavbar" aria-controls="mainNavbar" aria-expanded="false" aria-label="Toggle navigation">' +
      '      <span class="navbar-toggler-icon"></span>' +
      "    </button>" +
      '    <div class="collapse navbar-collapse" id="mainNavbar">' +
      '      <ul class="navbar-nav me-auto mb-2 mb-lg-0">' +
      '        <li class="nav-item"><a class="nav-link ' + (activePage === "home" ? "active" : "") + '" href="index.html">Publications</a></li>' +
      '        <li class="nav-item"><a class="nav-link ' + (activePage === "collection" ? "active" : "") + '" href="mycollection.html">My Collection</a></li>' +
      '        <li class="nav-item"><a class="nav-link ' + (activePage === "edit" ? "active" : "") + '" href="edit.html">Add Publication</a></li>' +
      "      </ul>" +
      '      <div class="d-flex gap-2">' +
               (loggedIn
                 ? '<button type="button" class="btn btn-outline-secondary btn-sm" id="logoutButton">Logout</button>'
                 : '<a class="btn btn-outline-primary btn-sm" href="login.html">Login</a><a class="btn btn-primary btn-sm" href="register.html">Register</a>') +
      "      </div>" +
      "    </div>" +
      "  </div>" +
      "</nav>";

    var logoutButton = qs("#logoutButton");
    if (logoutButton) {
      logoutButton.addEventListener("click", function () {
        window.AppAuth.logout();
      });
    }
  }

  window.AppLayout = {
    qs: qs,
    escapeHtml: escapeHtml,
    getId: getId,
    getTitle: getTitle,
    getAuthors: getAuthors,
    getTags: getTags,
    getYear: getYear,
    getVenue: getVenue,
    getAbstract: getAbstract,
    isFavorite: isFavorite,
    publicationCard: publicationCard,
    createTagMarkup: createTagMarkup,
    showAlert: showAlert,
    showLoading: showLoading,
    showEmpty: showEmpty,
    renderNavbar: renderNavbar
  };
})();
