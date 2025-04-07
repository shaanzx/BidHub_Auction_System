document.addEventListener('DOMContentLoaded', function() {
    /*const API_BASE_URL = 'http://localhost:8080/api/v1';

    // Form validation for registration
    const registrationForm = document.getElementById('registrationForm');
    if (registrationForm) {
        registrationForm.addEventListener('submit', async function(event) {
            event.preventDefault();

            if (!this.checkValidity()) {
                event.stopPropagation();
            } else {
                const formData = {
                    name: document.getElementById('name').value,
                    userEmail: document.getElementById('email').value,
                    address: document.getElementById('address').value,
                    contact: document.getElementById('contact').value,
                    nic: document.getElementById('nic').value,
                    password: document.getElementById('password').value,
                    role: 'USER'
                };

                try {
                    const response = await fetch('http://localhost:8080/api/v1/user/register', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify(formData)
                    });

                    const data = await response.json();

                    if (response.status === 201) { // Created
                        localStorage.setItem('token', data.data.token);
                        localStorage.setItem('userEmail', data.content.email);

                        // Show success message
                        alert('Registration successful!');

                        // Redirect to dashboard or home page
                        window.location.href = 'userIndex.html';
                    } else if (response.status === 406) { // Not Acceptable
                        alert('Email already in use. Please use a different email.');
                    } else {
                        throw new Error(data.message || 'Registration failed');
                    }
                } catch (error) {
                    console.error('Registration error:', error);
                    alert('Registration failed: ' + error.message);
                }
            }

            this.classList.add('was-validated');
        });*/

        // Password confirmation validation
        const password = document.getElementById('password');
        const confirmPassword = document.getElementById('confirmPassword');

        confirmPassword.addEventListener('input', function() {
            if (this.value !== password.value) {
                this.setCustomValidity('Passwords do not match');
            } else {
                this.setCustomValidity('');
            }
        });


    // Form validation for login
   /* const loginForm = document.getElementById('loginForm');
    if (loginForm) {
        loginForm.addEventListener('submit', function(event) {
            event.preventDefault();

            if (!this.checkValidity()) {
                event.stopPropagation();
            } else {
                const formData = {
                    email: document.getElementById('loginEmail').value,
                    password: document.getElementById('loginPassword').value
                };

                fetch(`${API_BASE_URL}/auth/authenticate`, {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(formData)
                })
                    .then(response => {
                        if (response.ok) {
                            return response.json();
                        } else {
                            throw new Error('Login failed');
                        }
                    })
                    .then(data => {
                        // Store the token in localStorage
                        localStorage.setItem('token', data.content.token);
                        localStorage.setItem('userEmail', data.content.email);

                        // Show success message
                        alert('Login successful!');

                        // Redirect to dashboard or home page
                        window.location.href = 'userIndex.html';
                    })
                    .catch(error => {
                        console.error('Login error:', error);
                        alert('Login failed: ' + error.message);
                    });
            }

            this.classList.add('was-validated');
        });
    }*/

    // Password visibility toggle for registration
    const togglePassword = document.getElementById('togglePassword');
    const toggleConfirmPassword = document.getElementById('toggleConfirmPassword');

    if (togglePassword) {
        togglePassword.addEventListener('click', function() {
            const password = document.getElementById('password');
            togglePasswordVisibility(password, this);
        });
    }

    if (toggleConfirmPassword) {
        toggleConfirmPassword.addEventListener('click', function() {
            const confirmPassword = document.getElementById('confirmPassword');
            togglePasswordVisibility(confirmPassword, this);
        });
    }

    // Password visibility toggle for login
    const toggleLoginPassword = document.getElementById('toggleLoginPassword');
    if (toggleLoginPassword) {
        toggleLoginPassword.addEventListener('click', function() {
            const loginPassword = document.getElementById('loginPassword');
            togglePasswordVisibility(loginPassword, this);
        });
    }

    function togglePasswordVisibility(inputField, button) {
        const type = inputField.getAttribute('type') === 'password' ? 'text' : 'password';
        inputField.setAttribute('type', type);

        const icon = button.querySelector('i');
        icon.classList.toggle('bi-eye');
        icon.classList.toggle('bi-eye-slash');
    }

    // Real-time validation
    const inputs = document.querySelectorAll('.form-control');
    inputs.forEach(input => {
        input.addEventListener('input', function() {
            if (this.checkValidity()) {
                this.classList.remove('is-invalid');
                this.classList.add('is-valid');
            } else {
                this.classList.remove('is-valid');
                this.classList.add('is-invalid');
            }
        });
    });

    // Function to check if user is authenticated
    function isAuthenticated() {
        return localStorage.getItem('token') !== null;
    }

    // Function to get the authentication token
    function getToken() {
        return localStorage.getItem('token');
    }

    // Function to logout
    function logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('userEmail');
        window.location.href = 'login.html';
    }
});