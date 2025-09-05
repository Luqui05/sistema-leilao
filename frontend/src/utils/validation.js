export function validateEmail(email) {
  return /^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(String(email).trim());
}

export function validatePassword(pass) {
  const len = pass.length >= 6;
  const up = /[A-Z]/.test(pass);
  const low = /[a-z]/.test(pass);
  const num = /\d/.test(pass);
  const sp = /[^A-Za-z0-9]/.test(pass);
  return { len, up, low, num, sp, valid: len && up && low && num && sp };
}

export function passwordsMatch(p1, p2) {
  return p1 === p2 && p1.length > 0;
}