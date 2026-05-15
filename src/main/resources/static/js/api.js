(function () {
  function resolveApiBaseUrl() {
    if (window.API_BASE_URL) {
      return window.API_BASE_URL;
    }
    if (window.location.protocol === "file:") {
      return "http://localhost:8080/api";
    }
    return "/api";
  }

  var API_BASE_URL = resolveApiBaseUrl();

  var endpoints = {
    login: "/auth/login",
    register: "/auth/register",
    publications: "/publications",
    publicationDetail: "/publications/{id}",
    publicationTags: "/publications/{id}/tags",
    tags: "/tags",
    favorites: "/users/{userId}/bookmarks",
    favoriteToggle: "/users/{userId}/bookmarks/{id}"
  };

  function fillPath(template, params) {
    return template.replace(/\{(\w+)\}/g, function (_, key) {
      return encodeURIComponent(params[key] == null ? "" : params[key]);
    });
  }

  function readToken() {
    try {
      return localStorage.getItem("bib_token") || "";
    } catch (error) {
      return "";
    }
  }

  function createClient() {
    var client = axios.create({
      baseURL: API_BASE_URL,
      timeout: 10000
    });

    client.interceptors.request.use(function (config) {
      var token = readToken();
      if (token) {
        config.headers.Authorization = "Bearer " + token;
      }
      return config;
    });

    return client;
  }

  var client = createClient();

  function unwrap(payload) {
    if (payload == null) {
      return payload;
    }
    if (Object.prototype.hasOwnProperty.call(payload, "data")) {
      return payload.data;
    }
    if (Object.prototype.hasOwnProperty.call(payload, "result")) {
      return payload.result;
    }
    return payload;
  }

  function toArray(payload) {
    var data = unwrap(payload);
    if (Array.isArray(data)) {
      return data;
    }
    if (data && Array.isArray(data.items)) {
      return data.items;
    }
    if (data && Array.isArray(data.content)) {
      return data.content;
    }
    if (data && Array.isArray(data.records)) {
      return data.records;
    }
    return [];
  }

  function toItem(payload) {
    var data = unwrap(payload);
    if (data && data.item) {
      return data.item;
    }
    return data;
  }

  function getErrorMessage(error) {
    if (!error) {
      return "Unexpected error.";
    }
    if (error.response && error.response.data) {
      var data = error.response.data;
      if (typeof data === "string") {
        return data;
      }
      return data.message || data.error || "Request failed.";
    }
    if (window.location.protocol === "file:") {
      return "API request failed. This page is opened as a local file. Please run the Spring Boot backend and access the page through http://localhost:8080, or set window.API_BASE_URL to the real backend address.";
    }
    if (error.code === "ECONNABORTED") {
      return "The request timed out. Please check whether the backend service is running.";
    }
    return error.message || "Network error.";
  }

  window.AppApi = {
    baseURL: API_BASE_URL,
    endpoints: endpoints,
    fillPath: fillPath,
    unwrap: unwrap,
    toArray: toArray,
    toItem: toItem,
    getErrorMessage: getErrorMessage,
    get: function (url, config) {
      return client.get(url, config);
    },
    post: function (url, data, config) {
      return client.post(url, data, config);
    },
    put: function (url, data, config) {
      return client.put(url, data, config);
    },
    delete: function (url, config) {
      return client.delete(url, config);
    }
  };
})();
