const container = document.querySelector('.container');
const loginlink = document.querySelector('.SignInLink');
const registerLink = document.querySelector('.SignUpLink');

registerLink.addEventListener('click', () => {
  container.classList.add('active');
});
loginlink.addEventListener('click', () => {
  container.classList.remove('active');
});
