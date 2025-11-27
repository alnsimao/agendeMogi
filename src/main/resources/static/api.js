// api.js - centraliza chamadas à API
const API_BASE = "http://localhost:8080";

async function request(url, opts = {}) {
  const res = await fetch(API_BASE + url, opts);
  if (!res.ok) {
    const text = await res.text();
    throw new Error(text || ("HTTP " + res.status));
  }
  // se não tiver corpo (204) retorna null
  const contentType = res.headers.get("content-type") || "";
  if (res.status === 204 || contentType.indexOf("application/json") === -1) return null;
  return res.json();
}

export async function get(url) { return await request(url); }

export async function post(url, body) {
  return await request(url, {
    method: "POST",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(body)
  });
}

export async function put(url, body) {
  return await request(url, {
    method: "PUT",
    headers: {"Content-Type":"application/json"},
    body: JSON.stringify(body)
  });
}

export async function del(url) {
  return await request(url, { method: "DELETE" });
}
